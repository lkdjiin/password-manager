(ns pm.actions.insert-test
  (:require [clojure.test :refer :all]
            [pm.actions.insert :refer :all]
            [pm.messages :refer :all]))

(deftest test-action
  (let [good-result {:exit 0 :out "ok" :err ""}
        bad-result {:exit 7 :out "" :err "conn. failed"}
        args ["foo.net" "pass"]]
    (with-redefs [curl-command (fn [_] good-result)]
      (is (= (action args) "Inserted")))
    (with-redefs [curl-command (fn [_] bad-result)]
      (is (= (action args) msg-locked)))))
