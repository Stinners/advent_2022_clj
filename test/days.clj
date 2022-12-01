(ns day-tests
  (:require [solutions.day1 :as day1]
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

(run-tests)
