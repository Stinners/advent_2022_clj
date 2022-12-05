(ns solutions.day5
  (:require
    [readers :refer [read-str]]
    [common :refer [>>]]
    [clojure.string :as str]))

(defn is-space? [c] (= c \space))

(defn make-init-state [state-lines]
  (->> state-lines
       (map (>> #(partition-all 4 %) #(map second %)))
       (drop-last 1)
       (apply map list)                ;; transpose
       (map #(remove is-space? %))
       vec))

(defn read-move [line]
  (let [[ _ & rest] (re-matches #"move (\d+) from (\d+) to (\d+)" line)
        [n to from] (map read-string rest)]
    [n (- to 1) (- from 1)]))  ;; Make zero indexed

(defn apply-move [stacks [n from to]]
  (let [[moved remainder] (split-at n (get stacks from))
        result (concat (reverse moved) (get stacks to))]
    (assoc stacks from remainder to result)))

(defn part1 [init-state moves]
  (->> moves
       (reduce apply-move init-state)
       (map first)
       (apply str)))

(defn apply-move2 [stacks [n from to]]
  (let [[moved remainder] (split-at n (get stacks from))
        result (concat moved (get stacks to))]
    (assoc stacks from remainder to result)))

(defn part2 [init-state moves]
  (->> moves
       (reduce apply-move2 init-state)
       (map first)
       (apply str)))

(defn solve [filename]
  (let [lines (read-str filename)
        [init _ move-lines] (partition-by str/blank? lines)
        init-state (make-init-state init)
        moves (map read-move move-lines)]
    {:part1 (part1 init-state moves)
     :part2 (part2 init-state moves)}))

(solve "examples/day5.txt")
(solve "problems/day5.txt") ; "RTGWZTHLD"
  
