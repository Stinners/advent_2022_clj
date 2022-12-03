(ns solutions.day2
  (:require [readers :refer [read-split]]
            [clojure.set :refer [ map-invert]]
            [common :refer [sum]]))

;; ============== Rock-Paper-Scissors Rules 

(def shape-score   {:rock 1 :paper 2 :scissors 3})

(def beats 
    {:paper :rock
     :rock :scissors
     :scissors :paper})

(def loses (map-invert beats))

(defn get-outcome-score [you opp]
  (cond 
    (= (you beats) opp)  6    ;; win
    (= (you loses) opp)  0    ;; lose
    :else                3))  ;; draw

(defn score-games [games]
  (defn score [[opp you]] (+ (shape-score you) (get-outcome-score you opp)))
  (sum (map score games)))

;; ============== Part 1

(def decode-left        {"A" :rock "B" :paper "C" :scissors})
(def decode-right-part1 {"X" :rock "Y" :paper "Z" :scissors})

(defn part1 [lines]
  (for [[opp you] lines]
    [(decode-left opp) (decode-right-part1 you)]))
  
;; ============== Part 2

(def decode-right-part2 {"X" :lose "Y" :draw  "Z" :win})

(defn chose-outcome [opp target]
  (case target 
    :win  (loses opp)
    :lose (beats opp)
    :draw  opp))

(defn part2 [lines]
    (for [[opp target] lines
          :let [opp (decode-left opp)
                target (decode-right-part2 target)
                you (chose-outcome opp target)]]
      [opp you]))

;; ============== Solve

(defn solve [filename]
  (let [lines (read-split filename)]
      {:part1 (-> lines part1 score-games)
       :part2 (-> lines part2 score-games)})) 
