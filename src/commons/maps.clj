(ns commons.maps
  "Map functions not so commonly used I want them in core")

(defn hash-map-duplicates-ok
  "Like hash-map, except duplicate keys are OK. Last one takes precedence.
   This is required for Clojure 1.4."
  [& keys-and-vals]
  (if (empty? keys-and-vals)
    {}
    (apply assoc {} keys-and-vals)))

(defn invert
  "Produce a map with values as keys.
   Values are assumed unique."
  [map]
  (reduce (fn [so-far [key val]]
            (assoc so-far val key))
          {}
          map))


;;; Following are superseded by variants in clojure.core. Begin phasing out once
;;; 1.4 is no longer supported.

(defn dissoc-keypath
  "Like `dissoc`, but takes a sequence of keys.
   There must be at least two keys."
  [map keys]
  (let [[path-to-end-key end-key] [(butlast keys) (last keys)]
        ending-container (get-in map path-to-end-key)
        without-key (dissoc ending-container end-key)]
    (assoc-in map path-to-end-key without-key)))


(defn transform-in
  ([map keyseq f default]
     (assoc-in map keyseq
               (f (get-in map keyseq default))))
  ([map keyseq f]
     (transform-in map keyseq f nil)))

(defn transform
  ([map key f] (transform-in map [key] f))
  ([map key f default] (transform-in map [key] f default)))
  

