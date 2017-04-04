(ns pm.actions.list_
  (:require [pm.messages :refer :all]))

(use '[clojure.java.shell :only [sh]])

(declare curl-command curl-arg format-output)

(defn action
  []
  (let [result (curl-command)
        status (:exit result)]
    (if (zero? status)
      (format-output (:out result))
      msg-locked)))

(defn curl-command
  []
  (sh "sh" "-c" (str "curl " (curl-arg))))

(defn curl-arg
  []
  "'http://localhost:9909/list'")

(defn format-output
  [string]
  (->> (clojure.string/split string #"\n")
       sort
       (clojure.string/join "\n")))
