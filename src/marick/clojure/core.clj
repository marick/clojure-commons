(ns ^{:doc "Functions I wouldn't mind to see in clojure.core"}
  marick.clojure.core
  (:use marick.clojure.versions)
  (:require clojure.pprint
            clojure.set
            swiss.arrows
            marick.clojure.backwards-compatibility))
            
;;; Types and pseudo types

(defn regex? [x]
  (= (class x) java.util.regex.Pattern))

(defn stringlike?
  "String or regex"
  [x]
  (or (string? x)
      (regex? x)))

(defn classic-map? [x]
  (.isInstance clojure.lang.APersistentMap x))

(when-1-5-
  (defn record? [x]
    (and (map? x) (not (classic-map? x)))))


(defn extended-fn? [x]
  (or (fn? x)
    (= (class x) clojure.lang.MultiFn)))
  
(defn named? [x]
  (instance? clojure.lang.Named x))

(defn listlike? [x]
  (or (list? x)
      (seq? x)))


;;; Annoyances

(defn strictly [loose-predicate]
  (comp boolean loose-predicate))

(def any? (strictly some))

(def not-empty? (strictly seq))


;;; Vars

(defn var-root [var]
  (alter-var-root var identity))

(defn var-name ;; from `useful`
  "Get the namespace-qualified name of a var."
  [v]
  (apply symbol (map str ((juxt (comp ns-name :ns)
                            :name)
                          (meta v)))))


;;; Namespaces

(defmacro defalias  ;; from `useful`
  "Defines an alias for a var: a new var with the same root binding (if
any) and similar metadata. The metadata of the alias is its initial
metadata (as provided by def) merged into the metadata of the original."
  [dst src]
  `(alias-var (quote ~dst) (var ~src)))

(letfn [(move-var [var sym]
          (let [sym (with-meta sym (assoc (meta var) :ns *ns*))]
            (if (.hasRoot var)
              (intern *ns* sym (var-root var))
              (intern *ns* sym))))]

  (defn immigrate
    "Create a public var in this namespace for each public var in the
  namespaces named by ns-names. The created vars have the same name, root
  binding, and metadata as the original except that their :ns metadata
  value is this namespace."
    [& ns-names]
    (doseq [ns ns-names]
      (require ns)
      (doseq [[sym ^clojure.lang.Var var] (ns-publics ns)]
        (move-var var sym))))
  
  (defn immigrate-from
    "Like `immigrate`, except wth a list of named symbols."
    [ns symbols]
    (doseq [sym symbols]
      (move-var (ns-resolve ns sym) sym))))

;;; Maps

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
  


;;; Sequences

(defn vertical-slices
  "Given N sequences, return one sequence whose first element
   is a sequence of all the first elements, etc."
  [& sequences]
  (apply (partial map (fn [& args] args)) sequences))


;;; Copied from utilize to remove dependencies. 
;;; https://github.com/AlexBaranosky/Utilize

(defn separate
  "Split coll into two sequences, one that matches pred and one that doesn't. Unlike the
  version in clojure.contrib.seq-utils, pred is only called once per item."
  [pred coll]
  (let [pcoll (map #(vector % (pred %)) coll)]
    (for [f [filter remove]]
      (map first (f second pcoll)))))

(defn find-first
  "Returns the first item of coll where (pred item) returns logical true."
  [pred coll]
  (first (filter pred coll)))

(defn only
  "Gives the sole element of a sequence"
  [coll]
  (if (seq (rest coll))
    (throw (RuntimeException. "should have precisely one item, but had at least 2"))
    (if (seq coll)
      (first coll)
      (throw (RuntimeException. "should have precisely one item, but had 0")))))


;;; Sets

(immigrate-from 'clojure.set '[union difference subset? intersection])

;;; Control flow

;;; Control flow

(defmacro pred-cond 
  "Checks each predicate against the item, returning the corresponding 
   result if it finds a match, otherwise returning nil.
   Assumes item to be a value, as it will get evaluated multiple times."
  [item pred result & preds+results]
  (cond (= pred :else ) result
        (not (seq preds+results)) `(if (~pred ~item) ~result nil) ;; last condition, but no :else in the form
        :else `(if (~pred ~item)
                 ~result
                 (pred-cond ~item ~@preds+results))))

(immigrate-from 'swiss.arrows '[-<>])

;;; Printing

(immigrate-from 'clojure.pprint '[pprint cl-format])

;;; Compatibility

(immigrate 'marick.clojure.backwards-compatibility)
