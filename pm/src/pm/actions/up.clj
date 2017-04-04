(ns pm.actions.up
  (:require [pm.input :as input]))

(use '[clojure.java.shell :only [sh]])

; FIXME Make those paths dynamic!
(def servername "/home/xavier/perso/password-manager/pm-server/target/uberjar/pm-server-0.1.0-SNAPSHOT-standalone.jar")
(def serverlog "/home/xavier/.pm/server.log")

(def unlocked  "Unlocked for 5 minutes")
(def already-up "Already up and running")

(defn launch-server
  [pwd]
  (sh "sh"
      "-c"
      (str "nohup java -jar " servername " " pwd " &>> " serverlog " &"))
  unlocked)

(defn action
  []
  (launch-server (input/read-password)))

(defn upwarning
  [pwd]
  (launch-server pwd))
