(ns pm.input-test
  (:require [clojure.test :refer :all]
            [pm.input :refer :all]))

(deftest test-read-password
  (let [password "PaSsWoRd"]
    (is (= (with-in-str password (read-password)) password))))
