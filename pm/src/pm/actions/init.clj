(ns pm.actions.init
  (:require [pm.messages :refer :all]
            [pm.server :refer :all]
            [pm.input :as input]))

(use '[clojure.java.shell :only [sh]])

(declare launch-server)

(defn action
  []
  (launch-server (input/read-password-clear))
  msg-initialized)

(defn launch-server
  [pwd]
  (sh "sh" "-c" (str "java -jar " (servername) " " pwd " init")))
