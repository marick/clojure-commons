(ns commons.function-makers)

(defn mkfn:any?
  "Constructs a strict predicate that takes a single argument.
   That predicate returns `true` iff any of the `preds` is 
   truthy of that argument.
   
        (def stringlike? (mkfn:any? string? regex?))
        (stringlike? []) => false
        (stringlike? \"\") => true
   
        (def has-favs? (mkfn:any? (partial some #{0 4}) odd?)
        (has-favs? [2 4]) => true
        (has-favs? [1 6]) => true
   
   Stops checking after the first success. A predicate created from
   no arguments always returns `true`."
  [preds]
  (if (empty? preds)
    (constantly true)
    (fn [arg]
      (loop [[candidate & remainder :as preds] preds]
        (cond (empty? preds)  false
              (candidate arg) true
              :else           (recur remainder))))))

