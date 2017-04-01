(ns pm.actions.default-test
  (:require [clojure.test :refer :all]
            [pm.actions.default :refer :all]))

(deftest test-action
  (is (= (with-out-str (action)) "Please RTFM!\n")))
