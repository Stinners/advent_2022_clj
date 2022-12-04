(ns solutions.day4
  (:require [readers :refer [read-str]]))

(defn read-pair [line]
  (->> line
       (re-matches #"(\d+)-(\d+),(\d+)-(\d+)")
       rest
       (map read-string)))

(defn overlaps-completly? [[fst snd trd fth]]
  (or
    (and (<= fst trd snd) (<= fst fth snd))
    (and (<= trd fst fth) (<= trd snd fth))))

(defn overlaps-at-all? [[fst snd trd fth]]
    (or (<= fst trd snd) (<= fst fth snd)
        (<= trd fst fth) (<= trd snd fth)))

(defn count-when [condition values] 
  (count (filter condition values)))

(defn solve [filename]
  (let [lines (read-str filename)
        pairs (map read-pair lines)]
    {:part1 (count-when overlaps-completly? pairs)
     :part2 (count-when overlaps-at-all? pairs)}))

(solve "problems/day4.txt")
