(ns flagsmith-clj.core-test
  (:require
    [clj-wiremock.core :as wmk]
    [clojure.test :refer :all]
    [flagsmith-clj.core :as flagsmith]
    [flagsmith-clj.utils :as utils]
    [freeport.core :refer [get-free-port!]]
    [jsonista.core :as json])
  (:import
    (com.flagsmith
      FlagsmithClient)))


(def wiremock-port (get-free-port!))
(def wiremock-url (str "http://localhost:" wiremock-port))

(use-fixtures :once (partial wmk/wiremock-fixture [{:port wiremock-port}]))


(let [api-key "someKey"]
  (deftest initialization
    (testing "generates a new client"
      (is (= FlagsmithClient
             (type (flagsmith/new-client api-key)))))

    (testing "generates a new client with options"
      (is (= FlagsmithClient
             (type (flagsmith/new-client api-key {:base-uri        wiremock-url
                                                  :logging-enabled true}))))))

  (deftest has-feature
    (wmk/with-stubs
      [{:req [:GET (str "/flags/?page=1")]
        :res [200 {:body (json/write-value-as-string [(utils/create-feature
                                                        {:feature-name :existing-feature
                                                         :enabled      true})])}]}]
      (let [client (flagsmith/new-client api-key {:base-uri wiremock-url})]
        (testing "has feature"
          (is (true? (flagsmith/has-feature client :existing-feature))))

        (testing "does not have feature"
          (is (false? (flagsmith/has-feature client :missing-feature)))))))

  (deftest get-flags
    (let [feature-1 {:feature-name :feature-1
                     :enabled      true
                     :type         "string"
                     :description  "A fancy feature"
                     :state-value  "Experiment A"}
          feature-2 {:feature-name :feature-2
                     :enabled      false
                     :type         "boolean"
                     :description  "This thing is disabled"
                     :state-value  nil}]
      (wmk/with-stubs
        [{:req [:GET (str "/flags/?page=1")]
          :res [200 {:body (json/write-value-as-string [(utils/create-feature feature-1)
                                                        (utils/create-feature feature-2)])}]}]
        (let [client (flagsmith/new-client api-key {:base-uri wiremock-url})]
          (testing "has feature"
            (is (= [feature-1 feature-2] (flagsmith/get-flags client)))))))))
