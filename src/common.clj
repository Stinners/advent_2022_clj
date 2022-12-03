(ns common)

(defn >> [& funcs]
  (apply comp (reverse funcs)))
    
