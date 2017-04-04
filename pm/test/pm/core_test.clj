(ns pm.core-test
  (:require [clojure.test :refer :all]
            [pm.core :refer :all]))

(deftest test-init
  (let [response {:exit 0 :out "" :err ""}]
    (with-redefs [pm.actions.init/curl-command (fn [] response)
                  exit-now! (constantly true)]
      (is (= (with-out-str (-main "init")) "Database initialized\n")))))

(deftest test-show
  (let [response {:exit 0 :out "password" :err ""}]
    (with-redefs [pm.actions.show/curl-command (fn [_] response)
                  exit-now! (constantly true)]
      (is (= (with-out-str (-main "show" "web/foo.net")) "password\n")))))

(deftest test-insert
  (let [response {:exit 0 :out "ok" :err ""}]
    (with-redefs [pm.actions.insert/curl-command (fn [_] response)
                  exit-now! (constantly true)]
      (is (= (with-out-str (-main "insert" "foo.net" "pass")) "Inserted\n")))))

(deftest test-list_
  (let [response {:exit 0 :out "web/foo.com\ncb/cic.fr\nweb/bar.com" :err ""}
        expected "cb/cic.fr\nweb/bar.com\nweb/foo.com\n"]
    (with-redefs [pm.actions.list_/curl-command (fn [] response)
                  exit-now! (constantly true)]
      (is (= (with-out-str (-main "list")) expected)))))

(deftest test-default
  (with-redefs [exit-now! (constantly true)]
    (is (= (with-out-str (-main)) "Please RTFM!\n"))))
