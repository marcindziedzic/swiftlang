(ns sl.server
  (:use [compojure.core :exclude [routes]]
        [compojure.route :only [resources not-found]]
        [compojure.handler :only [site]]
        [ring.middleware.params :only [wrap-params]]
        [ring.middleware.json :only [wrap-json-response wrap-json-body wrap-json-params]]
        [ring.adapter.jetty :only [run-jetty]])
  (:require [sl.pages]
            [sl.api]))

(defroutes site-routes*
  (GET "/" [] (sl.pages/home))
  (resources "/")
  (not-found "Page not found"))

(defroutes api-routes*
  (GET  "/api/sheets" [] (sl.api/get-sheets))
  (GET  "/api/sheet/:name" [name] (sl.api/get-sheet name))
  (POST "/api/word/add" {params :params} (sl.api/add-word (get params "expr"))))

(def site-handler
  (site #'site-routes*))

(def api-handler
  (->
    #'api-routes*
    (wrap-params)
    (wrap-json-response)
    (wrap-json-body)
    (wrap-json-params)))

(defroutes handler
  (ANY "*" [] api-handler)
  (ANY "*" [] site-handler))

(defn- get-port []
  (Integer/parseInt (or (System/getenv "PORT") "8080")))

(defn -main []
  (run-jetty handler {:port (get-port)}))