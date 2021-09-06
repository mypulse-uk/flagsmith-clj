(ns flagsmith-clj.core-test
  (:require
    [clojure.test :refer :all]
    [flagsmith-clj.core :as flagsmith])
  (:import
    (com.flagsmith
      FlagsmithClient)))


(deftest initialization
  (testing "generates a new client"
    (is (= FlagsmithClient
           (type (flagsmith/new-client "someKey"))))))
