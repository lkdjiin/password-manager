(ns pm.input)

(defn read-password
  "Read a password interactively from the console.
  java -jar (lein uberjar) will use readPassword with no echo,
  but this is not available in the lein repl."
  []
  (print "Password:")
  (flush)
  (if (System/console)
    (String. (.readPassword (System/console)))
    (read-line)))

(defn read-password-clear
  "Read a password from the console. The password is **written** as you
  type it."
  []
  (print "Password: ")
  (flush)
  (read-line))
