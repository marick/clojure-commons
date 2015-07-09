(ns commons.clojure.core
  "Functions I wouldn't mind seeing in clojure.core"
  (:require [such.immigration :as immigrate]
            ;; This updates clojure.core's documentation
            such.better-doc))

;;; Other people's code

(immigrate/import-vars [clojure.set
                          union difference subset? intersection rename-keys]
                       [clojure.pprint
                          pprint cl-format]
                       [slingshot.slingshot
                          try+ throw+]
                       [swiss.arrows
                          -<> -!> -!>> -<>> some-<> some-<>>])
(immigrate/import-prefixed-vars clojure.string str-)


;; Suchwow code

(immigrate/import-vars [such.control-flow
                         branch-on]
                       [such.sequences
                         bifurcate only]
                       [such.vars
                         has-root-value? root-value])


(immigrate/import-all-vars such.environment
                           such.shorthand
                           such.random
                           such.types
                           such.wide-domains
                           such.wrongness
                           )

