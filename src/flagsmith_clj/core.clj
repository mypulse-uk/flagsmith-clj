(ns flagsmith-clj.core
  (:import
    com.flagsmith.FlagsmithClient))


(defn- add-base-uri-if-available
  [client value]
  (if value
    (.withApiUrl client value)
    client))


(defn new-client
  "Creates a new flagsmith client using the given api-key"
  ([api-key]
   (new-client api-key {}))
  ([api-key options]
   (let [{:keys [base-uri]} options]
     (->
       (FlagsmithClient/newBuilder)
       (.setApiKey api-key)
       (add-base-uri-if-available base-uri)
       (.build)))))


(defn has-feature
  "Retrieves value of a given feature"
  [client feature]
  (.hasFeatureFlag client (name feature)))
