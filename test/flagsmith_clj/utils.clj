(ns flagsmith-clj.utils
  (:require
    [clojure.test :refer :all]))


(defn create-feature
  [feature-name enabled]
  {:feature    {:name        (name feature-name)
                :type        "boolean"
                :description ""}
   :stateValue nil
   :enabled    enabled})
