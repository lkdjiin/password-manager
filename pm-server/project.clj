(defproject pm-server "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "GNU General Public License v3.0"
            :url "https://www.gnu.org/licenses/gpl-3.0.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [ring "1.6.0-RC1"]
                 [org.clojure/java.jdbc "0.7.0-alpha3"]
                 [org.xerial/sqlite-jdbc "3.16.1"]]
  :main ^:skip-aot pm-server.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
