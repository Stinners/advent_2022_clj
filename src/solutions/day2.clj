(ns solutions.day2
  (:require [readers :refer [read-split]]
            [clojure.set :refer [ map-invert]]))

;; ============== Rock-Paper-Scissors Rules 

(def shape-score   {:rock 1 :paper 2 :scissors 3})
(def outcome-score {:win 6 :draw 3 :lose 0})

(def beats 
    {:paper :rock
     :rock :scissors
     :scissors :paper})

(def loses (map-invert beats))

(defn get-outcome [you opp]
  (cond 
    (= (you beats) opp)  :win
    (= (you loses) opp)  :lose
    :else                :draw))

(defn score [[opp you]] 
  (+ (shape-score you) (outcome-score (get-outcome you opp))))

;; ============== Part 1

(def decode-left        {"A" :rock "B" :paper "C" :scissors})
(def decode-right-part1 {"X" :rock "Y" :paper "Z" :scissors})

(defn part1 [lines]
  (->> lines 
       (map (fn [[opp you]] [(decode-left opp) (decode-right-part1 you)]))
       (map score)
       (apply +)))
  
;; ============== Part 2

(def decode-right-part2 {"X" :lose "Y" :draw  "Z" :win})

(defn chose-outcome [opp target]
  (case target 
    :win  (loses opp)
    :lose (beats opp)
    :draw  opp))

(defn part2 [lines]
  (->> lines 
       (map (fn [[opp you]] [(decode-left opp) (decode-right-part2 you)]))
       (map (fn [[opp target]] [opp (chose-outcome opp target)]))
       (map score)
       (apply +)))

;; ============== Solve

(defn solve [filename]
  (let [lines (read-split filename)]
      {:part1 (part1 lines)
       :part2 (part2 lines)})) 

(solve "problems/day2.txt")
