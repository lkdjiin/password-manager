(ns pm-server.database
  (:require [clojure.java.jdbc :as sql]
            [pm-server.crypt :as crypt]))

(declare last-inserted-id property-data properties? properties-string)

(def db-filename (str (System/getProperty "user.home") "/.pm/db.sqlite"))
(def connection-uri (str "jdbc:sqlite:" db-filename "?foreign_keys=on;"))
(def db {:connection-uri connection-uri})

(def show-query (str "select sites.name, sites.password, "
                     "properties.key, properties.value "
                     "from sites "
                     "left join properties on sites.id = properties.site_id "
                     "where sites.name = ?"))

(defn show
  [main-pwd site-name]
  (let [c-site-name (crypt/encrypt site-name main-pwd)
        result (sql/query db [show-query c-site-name])
        password (crypt/decrypt (:password (first result)) main-pwd)]
    (if (properties? result)
      (str password "\n" (properties-string result))
      password)))

(defn- properties?
  [sql-result]
  (:key (first sql-result)))

(defn- properties-string
  [sql-result]
  (clojure.string/join "\n" (map #(str (:key %) ": " (:value %)) sql-result)))

(defn list_
  [main-pwd]
  (->> (sql/query db ["SELECT name FROM sites"])
       (map #(:name %))
       (clojure.string/join "\n")))

(defn rm
  [main-pwd site-name]
  (sql/delete! db :sites ["name=?" site-name])
  "ok")

(defn insert
  [main-pwd site-name site-pwd properties]
  (let [c-site-name (crypt/encrypt site-name main-pwd)
        c-site-pwd (crypt/encrypt site-pwd main-pwd)
        result (sql/insert! db :sites {:name c-site-name :password c-site-pwd})
        site-id (last-inserted-id result)]
    (doseq [property properties]
      (sql/insert! db :properties (property-data site-id property))))
  "ok")

(defn- last-inserted-id
  "Retreive the ID of the last inserted row"
  [insertion-status]
  ((first (keys (first insertion-status))) (first insertion-status)))

(defn- property-data
  [id property]
  {:site_id id :key (name (first property)) :value (last property)})
