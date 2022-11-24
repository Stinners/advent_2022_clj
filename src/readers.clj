(ns readers)

(defn read-lines
  [process-lines filename]
  (with-open [rdr (clojure.java.io/reader filename)]
    (doall (map process-lines (line-seq rdr)))))

(defn parse-int [i] (Integer. i))

(def read-ints (partial read-lines #(Integer. %1))) 

(comment
  (read-lines identity "examples/day1.txt")
  (read-ints "examples/day1.txt"))
