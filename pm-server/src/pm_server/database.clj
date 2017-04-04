(ns pm-server.database
  (:require [clojure.java.jdbc :as sql]
            [pm-server.crypt :as crypt]))

(declare last-inserted-id property-data properties? properties-string
         entry-for-list props-for-list)

(def db-filename (str (System/getProperty "user.home") "/.pm/db.sqlite"))
(def connection-uri (str "jdbc:sqlite:" db-filename "?foreign_keys=on;"))
(def db {:connection-uri connection-uri})

(def show-query (str "select sites.name, sites.password, "
                     "properties.key, properties.value "
                     "from sites "
                     "left join properties on sites.id = properties.site_id "
                     "where sites.name = ?"))

(def list-query (str "select sites.name, "
                     "group_concat(properties.value, ' ') as props "
                     "from sites "
                     "left join properties on sites.id = properties.site_id "
                     "group by sites.name;"))

(defn show
  [main-pwd site-name]
  (let [c-site-name (crypt/encrypt site-name main-pwd)
        result (sql/query db [show-query c-site-name])
        password (crypt/decrypt (:password (first result)) main-pwd)]
    (if (properties? result)
      (str password "\n" (properties-string main-pwd result))
      password)))

(defn- properties?
  [sql-result]
  (:key (first sql-result)))

(defn properties-string
  [main-pwd sql-result]
  (defn in-clear [value] (crypt/decrypt value main-pwd))
  (->>
    sql-result
    (map #(str (in-clear (:key %)) " " (in-clear (:value %))))
    (clojure.string/join "\n")))

(defn list_
  [main-pwd]
  (->> (sql/query db [list-query])
       (map #(entry-for-list main-pwd %))
       (clojure.string/join "\n")))

(defn entry-for-list
  [main-pwd entry]
  (let [site (crypt/decrypt (:name entry) main-pwd)]
    (if (nil? (:props entry))
      site
      (props-for-list main-pwd site (:props entry)))))

(defn- props-for-list
  [main-pwd site props]
  (let [decrypted (->> (clojure.string/split props #" ")
                       (map #(crypt/decrypt % main-pwd))
                       (clojure.string/join ", "))]
    (str site " (" decrypted ")")))

(defn rm
  [main-pwd site-name]
  (sql/delete! db :sites ["name=?" (crypt/encrypt site-name main-pwd)])
  "ok")

(defn insert
  [main-pwd site-name site-pwd properties]
  (let [c-site-name (crypt/encrypt site-name main-pwd)
        c-site-pwd (crypt/encrypt site-pwd main-pwd)
        result (sql/insert! db :sites {:name c-site-name :password c-site-pwd})
        site-id (last-inserted-id result)]
    (doseq [property properties]
      (sql/insert! db :properties (property-data main-pwd site-id property))))
  "ok")

(defn- last-inserted-id
  "Retreive the ID of the last inserted row"
  [insertion-status]
  ((first (keys (first insertion-status))) (first insertion-status)))

(defn- property-data
  [main-pwd id property]
  (let [k (crypt/encrypt (name (first property)) main-pwd)
        v (crypt/encrypt (last property) main-pwd)]
    {:site_id id :key k :value v}))

(defn init
  [main-pwd]
  (sql/insert! db :mainpass {:crypted (crypt/encrypt main-pwd main-pwd)}))
