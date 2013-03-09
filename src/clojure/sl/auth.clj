(ns sl.auth
  (:use [sl.conf :only [openid-callback-url]])
  (:require [sl.openid :as openid]))

(def identifiers
  {:google "https://www.google.com/accounts/o8/id"
   :yahoo "https://me.yahoo.com"})

(defn authenticate [{session :session {:keys [rp]} :params}]
  (openid/redirect ((keyword rp) identifiers) session openid-callback-url))

(defn validate [request]
  (if (openid/validate request) "valid" "invalid"))