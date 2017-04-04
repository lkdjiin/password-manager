(ns pm.actions.list_-test
  (:require [clojure.test :refer :all]
            [pm.actions.list_ :refer :all]
            [pm.messages :refer :all]))

(deftest test-curl-arg
  (is (= (curl-arg) "'http://localhost:9909/list'")))

(deftest test-action
  (let [response {:exit 0 :out "web/foo.com\ncb/cic.fr\nweb/bar.com" :err ""}
        failed {:exit 7 :out "" :err "conn. failed"}
        expected "cb/cic.fr\nweb/bar.com\nweb/foo.com"]
    (with-redefs [curl-command (fn [] response)]
      (is (= (action) expected)))
    (with-redefs [curl-command (fn [] failed)]
      (is (= (action) msg-locked)))))

(deftest test-format-output
  (is (= (format-output "foo.com") "foo.com"))
  (is (= (format-output "foo.com\nbar.com") "bar.com\nfoo.com")))
