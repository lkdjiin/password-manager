(ns pm.actions.up-test
  (:require [clojure.test :refer :all]
            [pm.actions.up :refer :all]))

(deftest test-up
  (with-redefs [launch-server (constantly "ok")]
    (is (= (with-in-str "pass" (action)) "ok"))))

(deftest test-upwarning
  (with-redefs [launch-server (constantly "ok")]
    (is (= (upwarning "pass")) "ok")))
