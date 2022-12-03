(ns common)

(defn >> [& funcs]
  (apply comp (reverse funcs)))

(defn sum [nums]
  (apply + nums))
    
