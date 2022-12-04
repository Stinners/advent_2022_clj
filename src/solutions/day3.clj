(ns solutions.day3
  (:require [readers :refer [read-str]]
            [common :refer [>> sum]]
            [clojure.set :refer [intersection]]))

(def priorities
  (zipmap 
    "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
    (drop 1 (range))))

(defn find-common [lines]
  (->> lines (map set) (apply intersection) first))

;; ========================== Part 1

(defn get-shared [contents]
  (let [half (* 0.5 (count contents))
        halves (split-at half contents)]
    (find-common halves)))

(defn sum-priorities [contents]
  (->> contents
       (map (>> get-shared priorities))
       sum))

;; ========================== Part 2 

(defn sum-badges [contents]
  (->> contents
       (partition 3)
       (map (>> find-common priorities))
       sum))

;; ========================== Main

(defn solve [filename]
  (let [lines (read-str filename)]
    {:part1 (sum-priorities lines)
     :part2 (sum-badges lines)}))

(solve "problems/day3.txt")
