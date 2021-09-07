(ns flagsmith-clj.core
  (:import
    (com.flagsmith
      Feature
      Flag
      FlagsmithClient)))


(defn- add-base-uri-if-available
  [^FlagsmithClient client
   value]
  (if value
    (.withApiUrl client value)
    client))


(defn- enable-logging
  [^FlagsmithClient client logging-enabled]
  (if logging-enabled
    (.enableLogging client)
    client))


(defn- flag->map
  [^Flag flag]
  (let [^Feature feature (.getFeature flag)]
    {:feature-name (keyword (.getName feature))
     :type         (.getType feature)
     :description  (.getDescription feature)
     :state-value  (.getStateValue flag)
     :enabled      (.isEnabled flag)}))


(defn new-client
  "Creates a new flagsmith client using the given api-key"
  ([api-key]
   (new-client api-key {}))
  ([api-key options]
   (let [{:keys [base-uri logging-enabled]} options]
     (->
       (FlagsmithClient/newBuilder)
       (.setApiKey api-key)
       (add-base-uri-if-available base-uri)
       (enable-logging logging-enabled)
       (.build)))))


(defn get-flags
  "Retrieves list of all flags"
  [^FlagsmithClient client]
  (map flag->map (.getFeatureFlags client)))


(defn has-feature
  "Retrieves value of a given feature"
  [^FlagsmithClient client feature]
  (.hasFeatureFlag client (name feature)))
