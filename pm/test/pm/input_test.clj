(ns pm.input-test
  (:require [clojure.test :refer :all]
            [pm.input :refer :all]))

(deftest test-read-password
  (let [password "PaSsWoRd"]
    (is (= (with-in-str password (read-password)) password))))

(deftest test-read-password-clear
  (let [password "foobar"]
    (is (= (with-in-str password (read-password-clear)) password)))
  (let [password "xyz"
        expected "Password: "]
    (is (= (with-out-str (with-in-str password (read-password-clear)))
           expected))))
