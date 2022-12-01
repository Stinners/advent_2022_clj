(ns solutions.day1
  (:require [readers :refer [read-str]]))

(defn- sum-elf [cals] 
  (->> cals 
      (map read-string)
      (apply +)))

(defn- read-elves [filename]
  (->> filename 
       read-str
       (partition-by #(= % ""))
       (filter #(not= % `("")))
       (map sum-elf)))

(defn- elves-sum [elves n]
  (->> elves
       sort
       (take-last n)
       (apply +)))

(defn solve [filename]
  (let [elves (read-elves filename)]
    {:part1 (elves-sum elves 1)
     :part2 (elves-sum elves 3)}))
