(defproject pm "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "GNU General Public License v3.0"
            :url "https://www.gnu.org/licenses/gpl-3.0.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [clj-http-lite "0.3.0"]]
  :main ^:skip-aot pm.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
