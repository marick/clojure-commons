(ns commons.maps
  "Map functions not so commonly used I want them in core"
  (:use commons.clojure.core))

;; These will be removed when we drop support of Clojure 1.4.
(declare hash-map-duplicates-ok dissoc-keypath transform-in transform)

(defn invert
  "Produce a map with values as keys.
   Values are assumed unique."
  [map]
  (reduce (fn [so-far [key val]]
            (assoc so-far val key))
          {}
          map))

(defn conj-into
  "`original` is a map. `additions` is a sequence of keys and values (not a map).
  Each key is used to identify a value within the map. That `original` value is
  updated by conjing on the associated `additions` value.
   
        (conj-into {:a [1] :b '(1)} :a 2 :b 2) => '{:a [1 2] :b (2 1)}
   
   If the key isn't present in the map, it is created as a list containing
   the value.

        (conj-into {} :a 1) => '{:a (1)}
"
  [original & additions]
  (loop [[k v & more :as all] additions
         so-far original]
    (if (nil? all) 
      so-far
      (recur more
             (transform so-far k #(conj % v))))))

(defn key-difference
  "Remove (as with `dissoc`) all the keys in `original` that are in
   `unwanted`.
   
        (key-difference {:a 1, :b 2} {:b ..irrelevant.., :c ..irrelevant..}) => {:a 1}
"

  [original unwanted]
  (apply dissoc original (keys unwanted)))

;;; Following are superseded by variants in clojure.core. Begin phasing out once
;;; 1.4 is no longer supported.

(defn dissoc-keypath
  "Like `dissoc`, but takes a sequence of keys.
   There must be at least two keys.

   Will be removed with support for Clojure 1.4"
  [map keys]
  (let [[path-to-end-key end-key] [(butlast keys) (last keys)]
        ending-container (get-in map path-to-end-key)
        without-key (dissoc ending-container end-key)]
    (assoc-in map path-to-end-key without-key)))


(defn transform-in
   "Will be removed with support for Clojure 1.4"
  ([map keyseq f default]
     (assoc-in map keyseq
               (f (get-in map keyseq default))))
  ([map keyseq f]
     (transform-in map keyseq f nil)))

(defn transform
  "Will be removed with support for Clojure 1.4"
  ([map key f] (transform-in map [key] f))
  ([map key f default] (transform-in map [key] f default)))
  
(defn hash-map-duplicates-ok
  "Like hash-map, except duplicate keys are OK. Last one takes precedence.
   This is required for Clojure 1.4."
  [& keys-and-vals]
  (if (empty? keys-and-vals)
    {}
    (apply assoc {} keys-and-vals)))



