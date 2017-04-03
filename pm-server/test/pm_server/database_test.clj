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
