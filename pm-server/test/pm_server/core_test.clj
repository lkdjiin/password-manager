(ns pm-server.core-test
  (:require [clojure.test :refer :all]
            [pm-server.core :refer :all]))

(deftest test-route-list
  (with-redefs [pm-server.database/list_ (constantly "ok")]
    (is (= (:status (route-list {})) 200))
    (is (= (:headers (route-list {})) {"Content-Type" "text/plain"}))
    (is (= (:body (route-list {})) "ok"))))

(deftest test-route-show
  (with-redefs [pm-server.database/show (constantly "ok")]
    (is (= (:status (route-show {})) 200))
    (is (= (:headers (route-show {})) {"Content-Type" "text/plain"}))
    (is (= (:body (route-show {})) "ok"))))

(deftest test-route-rm
  (with-redefs [pm-server.database/rm (constantly "ok")]
    (is (= (:status (route-rm {})) 200))
    (is (= (:headers (route-rm {})) {"Content-Type" "text/plain"}))
    (is (= (:body (route-rm {})) "ok"))))

(deftest test-route-insert
  (with-redefs [pm-server.database/insert (constantly "ok")]
    (is (= (:status (route-insert {})) 200))
    (is (= (:headers (route-insert {})) {"Content-Type" "text/plain"}))
    (is (= (:body (route-insert {})) "ok"))))

(deftest test-route
  (is (true? (route "foo" {:uri "foo"})))
  (is (false? (route "foo" {:uri "bar"}))))
