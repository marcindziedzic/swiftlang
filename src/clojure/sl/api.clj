(ns sl.api
  (:require [clojure.data.json :as json])
  (:use [sl.in-memory-datastore]))

(def ^:private available-sheets ["Mon" "Tue" "Wed" "Thu" "Fir"])

(defn add-word [q]
  "")

(defn get-sheets []
  (json/write-str available-sheets))
