(ns pm-server.crypt-test
  (:require [clojure.test :refer :all]
            [pm-server.crypt :refer :all]))

(deftest test-crypt
  (let [text "xavier"
        secret-key "The awesome secret key"
        encrypted "nyyvdXeYHfsMId0lnqMe+A=="]
    (is (instance? String (encrypt text secret-key)))
    (is (= (encrypt text secret-key) encrypted))
    (is (= (decrypt encrypted secret-key) text))))
