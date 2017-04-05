(ns pm.actions.init-test
  (:require [clojure.test :refer :all]
            [pm.actions.init :refer :all]
            [pm.messages :refer :all]))

(deftest test-action
  (with-redefs [launch-server (constantly {:exit 0 :out "ok"})]
    (is (= (with-in-str "pass" (action)) msg-initialized))
    (is (= (with-out-str (with-in-str "pass" (action))) "Password: "))))
