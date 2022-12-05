(ns solutions.day5
  (:require
    [readers :refer [read-str]]
    [common :refer [>>]]
    [clojure.string :as str]))

;; ===================== Parsing 

(defn make-init-state [state-lines]
  (->> state-lines
       (map (>> #(partition-all 4 %) #(map second %)))
       (drop-last 1)
       (apply map list) ;; transpose
       (mapv #(remove (partial = \space) %))))

(defn read-move [line]
  (let [[ _ & rest] (re-matches #"move (\d+) from (\d+) to (\d+)" line)
        [n to from] (map read-string rest)]
    [n (- to 1) (- from 1)]))  ;; Make zero indexed to simply the later steps

;; ===================== Logic

(defn mover-part1 [taken dest] (concat (reverse taken) dest))
(defn mover-part2 [taken dest] (concat taken dest))

(defn apply-move [mover stacks [n from to]]
  (let [[moved remainder] (split-at n (get stacks from))
        result (mover moved (get stacks to))]
    (assoc stacks from remainder to result)))

(defn do-moves [init-state moves mover]
  (->> moves
       (reduce (partial apply-move mover) init-state)
       (map first)
       (apply str)))

;; ===================== Solving

(defn solve [filename]
  (let [lines (read-str filename)
        [init _ move-lines] (partition-by str/blank? lines)
        init-state (make-init-state init)
        moves (map read-move move-lines)]
    {:part1 (do-moves init-state moves mover-part1)
     :part2 (do-moves init-state moves mover-part2)}))
