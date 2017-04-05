(ns pm.actions.up-test
  (:require [clojure.test :refer :all]
            [pm.actions.up :refer :all]))

(deftest test-up
  (with-redefs [launch-server (constantly true)
                http-check (constantly {:status 200 :headers {} :body ""})]
    (is (= (with-in-str "pass" (action)) unlocked)))
  (with-redefs [launch-server (constantly true)
                http-check (constantly {:status 401 :headers {} :body ""})]
    (is (= (with-in-str "pass" (action)) wrong-password))))

(deftest test-upwarning
  (with-redefs [launch-server (constantly true)
                http-check (constantly {:status 200 :headers {} :body ""})]
    (is (= (upwarning "pass") unlocked)))
  (with-redefs [launch-server (constantly true)
                http-check (constantly {:status 401 :headers {} :body ""})]
    (is (= (upwarning "pass") wrong-password))))
