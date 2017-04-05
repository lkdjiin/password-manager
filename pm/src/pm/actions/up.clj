(ns pm.actions.up
  (:require [pm.input :as input]
            [pm.server :refer :all]))

(use '[clojure.java.shell :only [sh]])

(def unlocked  "Unlocked for 5 minutes")

(defn launch-server
  [pwd]
  (sh "sh"
      "-c"
      (str "nohup java -jar " (servername) " " pwd " &>> " (serverlog) " &"))
  unlocked)

(defn action
  []
  (launch-server (input/read-password)))

(defn upwarning
  [pwd]
  (launch-server pwd))
