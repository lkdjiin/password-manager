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
