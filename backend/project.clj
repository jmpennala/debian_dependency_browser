(defproject app "1.0.0-SNAPSHOT"
  :description "Debian dependency backend"
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [ring-server "0.5.0"]
                 [ring-cors "0.1.13"]
                 [metosin/reitit "0.5.18"]]
  :repl-options {:init-ns app.core}
  :plugins [[lein-auto "0.1.3"]]
  :main app.deps.core)
