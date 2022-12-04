(ns main
  (:gen-class)
  (:require [solutions.day1 :as day1]
            [solutions.day2 :as day2]
            [solutions.day3 :as day3]
            [clojure.spec.alpha :as s]
            [clojure.pprint :as pp]))

(s/def :input/dir #(.contains ["examples" "problems"] %1))
(s/def :input/day (fn [s] (every? #(Character/isDigit %) s)))
(s/def :input/input (s/cat :dir :input/dir :day :input/day))

(def solutions 
  {1 day1/solve 
   2 day2/solve
   3 day3/solve})

(defn error [check? message value]
  (when (check? value)
    (println message)
    (System/exit 1))
  value)

(defn get-solution [day]
  (->> day 
       Integer.
       solutions
       (error nil? (str "Not solution found for day " day))))

(defn get-args []
  (->> *command-line-args*
      (s/conform :input/input)
      (error s/invalid? "Aguments must be one of the strings 'examples' or 'problems' followed by a number")))

(defn -main []
  (let [{dir :dir day :day} (get-args)
        solution (get-solution day)
        filename (str dir "/day" day ".txt")]
    (pp/pprint (solution filename))))

(-main)

