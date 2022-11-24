(ns solutions.day1
  (:require [readers :refer [read-ints]]))

(defn part1 [numbers]
  (->> numbers 
       (partition 2 1)
       (filter (fn [[fst snd]] (< fst snd)))
       count))

(defn part2 [numbers]
  (->> numbers 
       (partition 3 1)
       (map #(apply + %))
       part1))

(defn solve [filename]
  (let [numbers (read-ints filename)]
    {:part1 (part1 numbers)
     :part2 (part2 numbers)}))
