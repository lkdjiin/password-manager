(ns pm.actions.show
  (:require [pm.messages :refer :all]
            [pm.url :as url]))

(use '[clojure.java.shell :only [sh]])

(declare curl-command curl-arg)

(defn action
  [site]
  (let [result (curl-command site)
        status (:exit result)]
    (if (= status 0)
      (:out result)
      msg-locked)))

(defn curl-command
  [string]
  (sh "sh" "-c" (str "curl " (curl-arg string))))

(defn curl-arg
  [string]
  (str "'http://localhost:9909/show?" (url/query-string {:q string}) "'"))
