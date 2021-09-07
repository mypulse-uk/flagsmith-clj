(ns flagsmith-clj.utils
  (:require
    [clojure.test :refer :all]))


(defn create-feature
  [{:keys [feature-name enabled type description state-value]
    :or   {feature-name :some-feature
           enabled      true
           type         "boolean"
           description  ""}}]
  {:feature             {:name        (name feature-name)
                         :type        type
                         :description description}
   :feature_state_value state-value
   :enabled             enabled})
