(ns sl.api-test
  (:use [sl.api :only [get-sheets]]
        [expectations]))

(expect "[\"Mon\",\"Tue\",\"Wed\",\"Thu\",\"Fir\"]"
  (get-sheets))