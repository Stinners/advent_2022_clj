(ns solutions.day7
  (:require
    [readers :refer [read-str]]
    [clojure.zip :as zip]
    [clojure.string :as str]))

(defn new-dir [dir-name] {:dir-name dir-name :size 0 :contents '()})

(def filesystem 
  (zip/zipper
    (fn [node] (contains? node :contents))
    (fn [node] (node :contents))
    (fn [node children] (assoc node :contents children))
    (new-dir "\\")))

(defn get-children [filesystem]
  (->> filesystem
       zip/down
       (iterate zip/right)
       (take-while some?)))

(defn check-loc [[node tree] dir-name]
  (and (some? node) 
       (= dir-name (node :dir-name))))
    
(defn cd [filesystem dir-name]
  (case dir-name
    ".." (zip/up filesystem)
    (or (->> filesystem get-children (filter #(check-loc % dir-name)) first)
        (-> filesystem (zip/insert-child (new-dir dir-name)) zip/down))))

(defn eval-line [tree line]
  (let [parts (str/split line #" ")
        fst (first parts)
        snd (second parts)]
    (cond 
      (and (= fst "$") (= snd "cd")) (cd tree (nth parts 2))
      (re-matches #"\d+" fst) (zip/insert-child tree {:filename snd :size (read-string fst)})
      :else tree)))

(defn sum-directories [node]
  (cond 
    (some? (node :filename)) node
    :else (let [children (map sum-directories (node :contents)) 
                size (apply + (map :size children))]
           (assoc node :size size :contents children))))

(defn flatten [fs]
  (if (fs :dir-name)
    (concat (list (fs :size)) (apply concat (map flatten (fs :contents))))
    '())) 
    
(defn part1 [dirs]
  (->> dirs (filter #(>= 100000 %)) (apply +)))

(defn part2 [dirs required-space]
  (->> dirs (filter #(<= required-space %)) sort first))
  

(defn solve [filename]
  (let [lines (rest (read-str filename))
        fs (->> lines (reduce eval-line filesystem) zip/root sum-directories)
        required-space (- (fs :size) 40000000)
        dirs (flatten fs)]
     {:part1 (part1 dirs)
      :part2 (part2 dirs required-space)}))
