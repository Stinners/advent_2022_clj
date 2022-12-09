(ns solutions.day8
  (:require
    [readers :refer [read-str]]
    [common :refer [>> sum]]
    [clojure.string :as str]))

(defn make-grid [filename]
  (let [lines (read-str filename)]
      (mapv (>> #(str/split % #"") #(mapv read-string %)) lines)))

(defn row [trees y] (get trees y))
(defn column [trees x] (->> trees count range (mapv #(get (get trees %) x))))

(defn split-slice [slice n]
  (let [[a b] (split-at n slice)]
    [a (rest b)]))

(defn tree-visible [tree approch]
  (or (empty? approch)
      (< (apply max approch) tree)))

(defn get-approches [trees x y]
  (let [[x1 x2] (split-slice (row trees y) x)
        [y1 y2] (split-slice (column trees x) y)]
    [(reverse  x1) x2 (reverse y1) y2]))

(defn visible? [trees x y]
  (let [approches (get-approches trees x y)
        tree (get (get trees y) x)]
    (some #(tree-visible tree %) approches)))

(defn part1 [trees]
  (let [indexes (range (count trees))]
    (sum (for [x indexes y indexes
               :when (visible? trees x y)]
          1))))

(defn viewing-distance [height approch]
  (let [n-shorter (count (take-while #(> height %) approch))]
    (if (= n-shorter (count approch))
      n-shorter
      (+ 1 n-shorter))))

(defn scenic-score [trees x y]
  (let [approches (get-approches trees x y)
        tree (get (get trees y) x)]
    (->> approches (map #(viewing-distance tree %)) (apply *))))

(defn part2 [trees]
  (let [indexes (range (count trees))]
    (apply max (for [x indexes y indexes]
                  (scenic-score trees x y)))))

(defn solve [filename]
  (let [trees (make-grid filename)]
    {:part1 (part1 trees)
     :part2 (part2 trees)}))
