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

(deftest test-check
  (with-redefs [pm-server.database/check (constantly true)]
    (is (= (:headers (route-check {})) {"Content-Type" "text/plain"}))
    (is (= (:body (route-check {})) "ok"))
    (is (= (:status (route-check {})) 200)))
  (with-redefs [pm-server.database/check (constantly false)]
    (is (= (:status (route-check {})) 401))
    (is (= (:body (route-check {})) "Wrong password"))))

(deftest test-routing
  (with-redefs [route-check (constantly "fine")]
    (is (= (routing {:uri "/check"}) "fine"))))

(deftest test-route
  (is (true? (route "foo" {:uri "foo"})))
  (is (false? (route "foo" {:uri "bar"}))))

(deftest test-init
  (with-redefs [pm-server.database/init (constantly {:last_inserted 1})
                pm-server.security/exit-now! (constantly 0)]
    (is (= (-main "pass" "init") 0))))

(deftest test-recognizing-initialization
  (is (true? (initialization? '("pass" "init"))))
  (is (false? (initialization? '("pass"))))
  (is (false? (initialization? '("pass" "foobar")))))
