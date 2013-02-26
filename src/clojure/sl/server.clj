(ns sl.server
  (:use [compojure.core :exclude [routes]]
        [compojure.route :only [resources not-found]]
        [ring.middleware.params :only [wrap-params]]
        [ring.adapter.jetty :only [run-jetty]])
  (:require [sl.pages]
            [sl.api]))

(defroutes routes
  (GET "/" [] (sl.pages/home))

  (GET "/api/sheets" [] (sl.api/get-sheets))
  (GET "/api/sheet/:name" [name] (sl.api/get-sheet name))

  (GET "/api/word/add" [] (sl.api/add-word ""))

  (resources "/")
  (not-found "Page not found"))

(def handler
  (wrap-params routes))

(defn- get-port []
  (Integer/parseInt (or (System/getenv "PORT") "8080")))

(defn -main []
  (run-jetty handler {:port (get-port)}))
