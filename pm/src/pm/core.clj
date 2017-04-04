(ns pm.core
  (:require [pm.actions.up :as up]
            [pm.actions.init :as init]
            [pm.actions.show :as show]
            [pm.actions.list_ :as list_]
            [pm.actions.insert :as insert]
            [pm.actions.default :as default])
  (:gen-class))

(defn exit-now!
  []
  (System/exit 0))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [action (first args)]
    (cond
      (= "up" action) (println (up/action))
      (= "init" action) (println (init/action))
      (= "upwarning" action) (println (up/upwarning (second args)))
      (= "show" action) (println (show/action (second args)))
      (= "insert" action) (println (insert/action (rest args)))
      (= "list" action) (println (list_/action))
      :else (default/action)))
  (exit-now!))
