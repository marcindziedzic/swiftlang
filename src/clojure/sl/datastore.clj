(ns sl.datastore
  (:require [taoensso.carmine :as redis]
            [sl.conf :as conf]))

(def pool (redis/make-conn-pool))

(defmacro with-conn [& body] `(redis/with-conn pool conf/redis-config ~@body))

(defn clear []
  (with-conn
    (redis/flushall)))