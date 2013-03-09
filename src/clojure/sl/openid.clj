(ns sl.openid
  (:import [org.openid4java.consumer ConsumerManager]
           [org.openid4java.message ParameterList])
  (:require [ring.util.response :as resp]))

; temporarily copied from https://github.com/stephenalindsay/clj-openid/blob/master/src/clj_openid/core.clj

(defn- auth-request [identifier return-url]
  (let [cm (ConsumerManager.)
        discoveries (.discover cm identifier)
        discovery-info (.associate cm discoveries)
        auth-req (.authenticate cm discovery-info return-url)
        destination-url (.getDestinationUrl auth-req true)]
    {:manager cm
     :discovery-info discovery-info
     :destination-url destination-url}))

(defn redirect [identifier session return-url]
  (let [req (auth-request identifier return-url)]
    (assoc (resp/redirect (:destination-url req))
      :session (merge session {:openid-req req}))))

(defn- rebuild-request-url
  [{:keys [uri server-port scheme query-string server-name] :as req}]
  (str (name scheme) "://" server-name (if server-port (str ":" server-port)) uri "?" query-string))

(defn- map->hashmap
  [map]
  (reduce (fn [m [k v]]
            (.put m (name k) (str v)) m) (java.util.HashMap.) map))

(defn validate [{:keys [params session] :as req}]
  (if-let [openid-req (:openid-req session)]
    (let [{:keys [manager discovery-info]} openid-req
          request-url (rebuild-request-url req)
          param-list (ParameterList. (map->hashmap params))
          verification (.verify manager request-url param-list discovery-info)]
      (.getVerifiedId verification))
    (throw (RuntimeException. "No openid request in session"))))