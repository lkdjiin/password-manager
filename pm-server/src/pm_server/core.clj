(ns pm-server.core
  (:require [pm-server.database :as db]
            [pm-server.security :as security])
  (:gen-class))

(use 'ring.util.response
     'ring.adapter.jetty
     'ring.middleware.params)

(defn respond-with [text]
  (content-type (response text) "text/plain"))

(defn route-list [request]
  (respond-with (db/list_ (:main-pwd request))))

(defn route-show [request]
  (let [query (:q (:params request))]
    (respond-with (db/show (:main-pwd request) query))))

(defn route-rm [request]
  (let [query (:q (:params request))]
    (respond-with (db/rm (:main-pwd request) query))))

(defn route-insert [request]
  (let [params (:params request)
        site-name (:q params)
        site-pwd (:p params)
        others (dissoc params :q :p)]
    (respond-with (db/insert (:main-pwd request) site-name site-pwd others))))

(defn route-init
  [request]
  (respond-with (db/init (:main-pwd request))))

(defn route
  [path request]
  (= path (:uri request)))

(defn routing
  [request]
  (cond
    (route "/init" request) (route-init request)
    (route "/list" request) (route-list request)
    (route "/show" request) (route-show request)
    (route "/rm" request) (route-rm request)
    (route "/insert" request) (route-insert request)
    :else {:status 404 :headers {} :body ""}))

(defn keywordize-params [handler]
  (fn [request]
    (let [k-params (clojure.walk/keywordize-keys (:params request))]
      (handler (assoc (dissoc request :params) :params k-params)))))

(defn wrap-value [handler k v]
  (fn [request]
    (handler (assoc request k v))))

(defn -main
  [& args]
  (security/exit-in-future)
  (def app
    (-> routing
        (keywordize-params)
        (wrap-value :main-pwd (first args))
        (wrap-params)))
  (run-jetty app {:port 9909}))
