(ns flagsmith-clj.core
  (:import
    (com.flagsmith
      FlagsmithClient))
  (:import
    (com.flagsmith.models
      Flag)))

(defn- flag->map
  [^Flag flag]
  {:feature-name (keyword (.getFeatureName flag))
   :state-value  (.getValue flag)
   :enabled      (.getEnabled flag)})

(defn new-client
  "Creates a new flagsmith client using the given api-key"
  ([api-key]
   (new-client api-key {}))
  ([api-key options]
   (let [{:keys [base-uri logging-enabled]} options]
     (cond->
       (FlagsmithClient/newBuilder)
       :always (.setApiKey api-key)
       :always (.withApiUrl base-uri)
       logging-enabled (.enableLogging)
       :always (.build)))))


(defn get-flags
  "Retrieves list of all flags"
  [^FlagsmithClient client]
  (into [] (map flag->map (.getAllFlags (.getEnvironmentFlags client)))))


(defn has-feature
  "Retrieves value of a given feature"
  [^FlagsmithClient client feature]
  (let [flags (.getEnvironmentFlags client)]
    (try
      (.isFeatureEnabled flags (name feature))
      (catch Exception _
        false))))
