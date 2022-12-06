(ns solutions.day6)

(defn part-n [n signal] 
  (->> signal 
        (partition n 1)
        (take-while #(not (apply distinct? %)))
        count 
        (+ n)))

(defn solve [filename]
  (let [signal (slurp filename)]
    {:part1 (part-n 4 signal)
     :part2 (part-n 14 signal)}))
