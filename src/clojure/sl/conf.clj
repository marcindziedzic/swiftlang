(ns sl.conf)

(defn- localhost [url-part]
  (str "http://localhost:5000" url-part))

(defn parse-uri [u]
  (let [uri (bean (java.net.URI. u))
        userInfo (:userInfo uri)
        splittedUserInfo (seq (.split (if userInfo userInfo "") ":"))]
      (merge uri {:user (first splittedUserInfo) :password (second splittedUserInfo)})))

(def redis-url
  (get (System/getenv) "REDISTOGO_URL" "redis://127.0.0.1:6379"))

(def redis-config
  (merge (parse-uri redis-url) {:timeout 0 :db 0}))

(def openid-callback-url
  "OPENID_CALLBACK_URL" (localhost "/openid"))