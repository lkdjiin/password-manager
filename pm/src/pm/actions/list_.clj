(ns pm.actions.list_)
(use '[clojure.java.shell :only [sh]])

(declare curl-command curl-arg format-output)

(defn action
  []
  (format-output (:out (curl-command))))

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
