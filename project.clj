(def version
  (or (System/getenv "VERSION")
      "0.0.8"))

(defproject ai.mypulse/flagsmith-clj version
  :description "A Clojure wrapper around the Flagsmith Java SDK"
  :url "https://github.com/mypulse-uk/flagsmith-clj"
  :license {:name "The MIT License"
            :url "https://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [com.flagsmith/flagsmith-java-client "7.1.0"]]

  :plugins [[lein-cloverage "1.2.3"]
            [lein-shell "0.5.0"]
            [lein-ancient "0.7.0"]
            [lein-changelog "0.3.2"]
            [lein-eftest "0.5.9"]
            [lein-codox "0.10.8"]
            [lein-kibit "0.1.8"]
            [lein-bikeshed "0.5.2"]]

  :profiles
  {:shared {:dependencies [[kelveden/clj-wiremock "1.8.0"]
                           [freeport "1.0.0"]
                           [metosin/jsonista "0.3.7"]
                           [eftest "0.5.9"]]
            :eftest {:multithread? false}}
   :dev    [:shared]
   :test [:shared]

   :release
   {:release-tasks
    [["deploy"]]}}

  :target-path "target/%s/"

  :bikeshed {:max-line-length 120}

  :repl-options {:init-ns flagsmith-clj.core}

  :deploy-repositories
  {"releases" {:url "https://repo.clojars.org"
               :username :env/clojars_deploy_username
               :password :env/clojars_deploy_token
               :sign-releases false}})
