(ns solutions.day3
  (:require [readers :refer [read-str]]
            [clojure.set :refer [intersection]]
            [common :refer [>>]]))

(def priorities
  (zipmap 
    "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
    (drop 1 (range))))

(defn find-common [lines]
  (->> lines (map set) (apply intersection) first))

(defn get-shared [contents]
  (let [half (* 0.5 (count contents))
        halves (split-at half contents)]
    (find-common halves)))

(defn sum-priorities [contents]
  (->> contents
       (map (>> get-shared priorities))
       (apply +)))

(defn sum-badges [contents]
  (->> contents
       (partition 3)
       (map (>> find-common priorities))
       (apply +)))

;; ========================== Part 2 

(defn solve [filename]
  (let [lines (read-str filename)]
    {:part1 (sum-priorities lines)
     :part2 (sum-badges lines)}))

(solve "problems/day3.txt")
