(ns pm-server.security)

(defn exit-now!
  []
  (System/exit 0))

(defn exit-in-future
  "Close this server in 5 minutes by default"
  [& [millisecondes]]
  (future (Thread/sleep (or millisecondes 300000)) (exit-now!)))
