(ns sl.api
  (:require [clojure.data.json :as json]
            [sl.query-parser :as qp])
  (:use [sl.in-memory-datastore]))

(def ^:private available-sheets ["Mon" "Tue" "Wed" "Thu" "Fir"])

(def ^:private available-sheet
  (->
    qp/sheet-template
    (qp/add-word ["dog" "pies"])
    (qp/add-word ["canary" "kanarek"])
    (qp/add-word ["home market" "rynek krajowy"])))

(defn add-word [q]
  "NotYetImplemented")

(defn get-sheets []
  (json/write-str available-sheets))

(defn get-sheet [name]
  (json/write-str available-sheet))