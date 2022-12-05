(ns day-tests
  (:require [solutions.day1 :as day1]
            [solutions.day2 :as day2]
            [solutions.day3 :as day3]
            [solutions.day4 :as day4]
            [solutions.day5 :as day5]
            [clojure.test :refer :all]))

(defn test-day [directory day solver part1 part2]
  (let [filename (str directory "/day" day ".txt")
        actual (solver filename)]
    (testing (str directory " part 1") 
      (is (= part1 (actual :part1))))
    (testing (str directory " part 2")
      (is (= part2 (actual :part2))))))
    
(deftest day1-test
  (test-day "examples" 1 day1/solve 24000 45000)
  (test-day "problems" 1 day1/solve 73211 213958))

(deftest day2-test
  (test-day "examples" 2 day2/solve 15 12)
  (test-day "problems" 2 day2/solve 10310 14859))

(deftest day3-test
  (test-day "examples" 3 day3/solve 157 70)
  (test-day "problems" 3 day3/solve 7581 2525))

(deftest day4-test
  (test-day "examples" 4 day4/solve 2 4)
  (test-day "problems" 4 day4/solve 305 811))

(deftest day5-test
  (test-day "examples" 5 day5/solve "CMZ" "MCD")
  (test-day "problems" 5 day5/solve "RTGWZTHLD" "STHGRZZFR"))


(run-tests)
