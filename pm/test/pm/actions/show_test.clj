(ns pm.actions.show-test
  (:require [clojure.test :refer :all]
            [pm.actions.show :refer :all]
            [pm.messages :refer :all]))

(deftest test-action
  (with-redefs [curl-command (fn [_] {:exit 0 :out "1234" :err ""})]
    (is (= (action "web/foo.net") "1234")))
  (with-redefs [curl-command (fn [_] {:exit 7 :out "" :err "conn. failed"})]
    (is (= (action "web/foo.net") msg-locked))))

(deftest test-curl-arg
  (is (= (curl-arg "foo/bar") "'http://localhost:9909/show?q=foo%2Fbar'"))
  (is (= (curl-arg "web/foo") "'http://localhost:9909/show?q=web%2Ffoo'")))
