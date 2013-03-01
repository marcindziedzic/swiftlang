(ns sl.api
  (:use [ring.util.response :only [response]])
  (:require [sl.query-parser :as qp]))

(def ^:private available-sheets ["Mon" "Tue" "Wed" "Thu" "Fir"])

(def ^:private available-sheet
  (->
    qp/sheet-template
    (qp/add-word ["dog" "pies"])
    (qp/add-word ["canary" "kanarek"])
    (qp/add-word ["home market" "rynek krajowy"])))

(defn add-word [expr]
  (response (qp/parse-query-string expr)))

(defn get-sheets []
  (response available-sheets))

(defn get-sheet [name]
  (response available-sheet))