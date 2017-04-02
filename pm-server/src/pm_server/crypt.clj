(ns pm-server.crypt
  (:import (javax.crypto Cipher KeyGenerator SecretKey)
           (javax.crypto.spec SecretKeySpec)
           (java.security SecureRandom)))

(declare to-bytes get-cipher get-raw-key base64 debase64)

(defn encrypt
  [text key]
  (let [bytes (to-bytes text)
        cipher (get-cipher Cipher/ENCRYPT_MODE key)]
    (base64 (.doFinal cipher bytes))))

(defn decrypt
  [text key]
  (let [cipher (get-cipher Cipher/DECRYPT_MODE key)]
    (String. (.doFinal cipher (debase64 text)))))

(defn- to-bytes
  [s]
  (.getBytes s "UTF-8"))

(defn- get-cipher
  [mode seed]
  (let [key-spec (SecretKeySpec. (get-raw-key seed) "AES")
        cipher (Cipher/getInstance "AES")]
    (.init cipher mode key-spec)
    cipher))

(defn- get-raw-key
  [seed]
  (let [keygen (KeyGenerator/getInstance "AES")
        sr (SecureRandom/getInstance "SHA1PRNG")]
    (.setSeed sr (to-bytes seed))
    (.init keygen 256 sr)
    (.. keygen generateKey getEncoded)))

(defn- base64
  [b]
  (.encodeToString (java.util.Base64/getEncoder) b))

(defn- debase64
  [s]
  (.decode (java.util.Base64/getDecoder) s))
