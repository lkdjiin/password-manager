(ns pm-server.database-test
  (:require [clojure.test :refer :all]
            [pm-server.database :refer :all]))

(deftest test-properties-string
  (let [props '({:name "n", :password "p",
                 :key "qvdaNHQtJuKUAle8bZ9D/g==",
                 :value "S1LHYKB8zJ/qiqq8nBnStw=="},
                {:name "n", :password "p",
                 :key "BpHpjKmtblSlS/7LqILd1A==",
                 :value "hrPrampj1+24fIMFdqAzOA=="})]
    (is (= (properties-string "The awesome secret key" props) "a 1\nb 2"))))

(deftest test-list-entry
  (let [secret-key "The awesome secret key"
        with-props {:name "qvdaNHQtJuKUAle8bZ9D/g=="
                    :props "S1LHYKB8zJ/qiqq8nBnStw== hrPrampj1+24fIMFdqAzOA=="}
        without-props {:name "qvdaNHQtJuKUAle8bZ9D/g=="
                       :props nil}]
    (is (= (entry-for-list secret-key with-props) "a (1, 2)"))
    (is (= (entry-for-list secret-key without-props) "a"))))
