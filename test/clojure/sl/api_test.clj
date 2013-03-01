(ns sl.api-test
  (:use [sl.api :only [get-sheets get-sheet add-word]]
        [expectations]))

(defn successful-ring-response-with-body [body]
  {:status 200, :headers {}, :body body})

(expect (successful-ring-response-with-body ["Mon" "Tue" "Wed" "Thu" "Fir"])
  (get-sheets))

(expect (successful-ring-response-with-body {:words {"home market" "rynek krajowy", "canary" "kanarek", "dog" "pies"}})
  (get-sheet "name"))

(expect (successful-ring-response-with-body ["dog" "pies"])
  (add-word "#dog @pies"))