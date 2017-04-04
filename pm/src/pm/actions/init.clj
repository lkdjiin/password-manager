(ns pm.actions.init
  (:require [pm.messages :refer :all]))

(use '[clojure.java.shell :only [sh]])

(declare curl-command curl-arg)

(defn action
  []
  (let [result (curl-command)
        status (:exit result)]
    (if (zero? status)
      "Database initialized"
      msg-locked)))

(defn curl-command
  []
  (sh "sh" "-c" (str "curl " (curl-arg))))

(defn curl-arg
  []
  "'http://localhost:9909/init'")
