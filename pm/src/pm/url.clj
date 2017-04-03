(ns pm.url
  (:import (java.net URLEncoder)))

(declare query-arg query-key query-value)

(defn url-encode
  [string]
  (URLEncoder/encode string "UTF-8"))

(defn query-string
  [mapping]
  (->>
    mapping
    (map #(query-arg %))
    (clojure.string/join "&")))

(defn- query-arg
  [pair]
  (str (query-key pair) "=" (query-value pair)))

(defn- query-key
  [pair]
  (url-encode (name (first pair))))

(defn- query-value
  [pair]
  (url-encode (second pair)))
