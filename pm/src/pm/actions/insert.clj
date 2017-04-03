(ns pm.actions.insert
  (:require [pm.messages :refer :all]
            [pm.url :as url]))

(use '[clojure.java.shell :only [sh]])

(declare curl-command curl-arg)

(defn action
  [[site password & properties]]
  (let [mapping (merge {:q site :p password} (apply hash-map properties))
        result (curl-command mapping)
        status (:exit result)]
    (if (= status 0)
      "Inserted"
      msg-locked)))

(defn curl-command
  [mapping]
  (sh "sh" "-c" (str "curl " (curl-arg mapping))))

(defn curl-arg
  [mapping]
  (str "'http://localhost:9909/insert?" (url/query-string mapping) "'"))
