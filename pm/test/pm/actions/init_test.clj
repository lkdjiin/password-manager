(ns pm.actions.init-test
  (:require [clojure.test :refer :all]
            [pm.actions.init :refer :all]
            [pm.messages :refer :all]))

(deftest test-action
  (let [response {:exit 0 :out "" :err ""}
        failed {:exit 7 :out "" :err "conn. failed"}]
    (with-redefs [curl-command (fn [] response)]
      (is (= (action) "Database initialized")))
    (with-redefs [curl-command (fn [] failed)]
      (is (= (action) msg-locked)))))

(deftest test-curl-arg
  (is (= (curl-arg) "'http://localhost:9909/init'")))
