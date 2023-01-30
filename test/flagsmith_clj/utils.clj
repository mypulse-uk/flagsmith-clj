(ns flagsmith-clj.utils
  (:require
    [clojure.test :refer :all]))


(defn create-feature
  [{:keys [feature-name enabled state-value]
    :or   {feature-name :some-feature
           enabled      true}}]
  {:feature             {:name        (name feature-name)}
   :feature_state_value state-value
   :enabled             enabled})
