(ns flagsmith-clj.core
  (:import
    com.flagsmith.FlagsmithClient))


(defn new-client
  "Creates a new flagsmith client using the given api-key"
  [api-key]
  (->
    (FlagsmithClient/newBuilder)
    (.setApiKey api-key)
    (.build)))
