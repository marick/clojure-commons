(ns commons.ns
  (:require [such.immigration :as immigrate]
            [such.vars :as vars]))

(immigrate/namespaces 'such.ns)

(defn alias-var  ;; from `useful`
  "Create a var with the supplied name in the current namespace, having the same
metadata and root-binding as the supplied var."
  [name ^clojure.lang.Var var]
  (apply intern *ns*
         (with-meta name (merge {:dont-test (str "Alias of " (vars/name-as-string var))}
                                (meta var)
                                (meta name)))
         (when (.hasRoot var) [@var])))

(defmacro defalias  ;; from `useful`
  "Defines an alias for a var: a new var with the same root binding (if
any) and similar metadata. The metadata of the alias is its initial
metadata (as provided by def) merged into the metadata of the original."
  [dst src]
  `(alias-var (quote ~dst) (var ~src)))

