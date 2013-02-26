(ns sl.api-test
  (:use [sl.api :only [get-sheets get-sheet add-word]]
        [expectations]))

(expect "[\"Mon\",\"Tue\",\"Wed\",\"Thu\",\"Fir\"]"
  (get-sheets))

(expect "{\"words\":{\"home market\":\"rynek krajowy\",\"canary\":\"kanarek\",\"dog\":\"pies\"}}"
  (get-sheet "name"))

(expect "NotYetImplemented"
  (add-word "#dog @pies"))