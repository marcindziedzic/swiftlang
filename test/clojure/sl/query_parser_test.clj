(ns sl.query-parser-test
  (:use [sl.query-parser]
        [expectations]))

(def sheet
  { :created "today"
    :words {"dog" "pies" }})

(def updated-sheet
  { :created "today"
    :words {"dog" "pies"
            "canary" "kanarek"}})

(given [query result] (expect result (parse-query-string query))
  "#dog @pies"                        ["dog" "pies"]
  "@pies #dog"                        ["dog" "pies"]
  "@pies#dog"                         ["dog" "pies"]
  "#home market @rynek krajowy"       ["home market" "rynek krajowy"]
  "  @rynek krajowy  #home market  "  ["home market" "rynek krajowy"]
  "@pies"                             nil
  "#dog"                              nil
  ""                                  nil)

(given [word sheet result] (expect result (add-word sheet word))
  ["canary" "kanarek"] sheet updated-sheet
  ["dog" "pies"]       sheet sheet
  nil                  sheet sheet
  ["canary" "kanarek"] nil   nil
  nil                  nil   nil)

(expect true (contains? sheet-template :words))
(expect true (map? (sheet-template :words)))