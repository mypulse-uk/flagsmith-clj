(defproject flagsmith-clj "0.1.0-SNAPSHOT"
  :description "A Clojure wrapper around the Flagsmith Java SDK"
  :url "https://github.com/Global-Online-Health/flagsmith-clj"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url  "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [com.flagsmith/flagsmith-java-client "3.1"]]
  :profiles {:shared {:dependencies
                      [[kelveden/clj-wiremock "1.5.7"]
                       [freeport "1.0.0"]
                       [metosin/jsonista "0.3.3"]]}

             :dev    [:shared {:source-paths ["dev"]}]}
  :repl-options {:init-ns flagsmith-clj.core})
