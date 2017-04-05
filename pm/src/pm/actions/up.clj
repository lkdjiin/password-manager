(ns pm.actions.up
  (:require [pm.input :as input]
            [pm.server :refer :all]
            [clj-http.lite.client :as client]))

(use '[clojure.java.shell :only [sh]])

(declare launch-server http-check check-password)

(def unlocked  "Unlocked for 5 minutes")
(def wrong-password "Wrong password, closing server")

(defn action
  []
  (launch-server (input/read-password))
  (check-password))

(defn upwarning
  [pwd]
  (launch-server pwd)
  (check-password))

(defn launch-server
  [pwd]
  (sh "sh"
      "-c"
      (str "nohup java -jar " (servername) " " pwd " &>> " (serverlog) " &")))

(defn check-password
  []
  (if (= 200 (:status (http-check)))
    unlocked
    wrong-password))

(defn http-check
  []
  (client/get "http://localhost:9909/check"))

