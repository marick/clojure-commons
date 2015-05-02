(ns ^{:doc "Functions I wouldn't mind to see in clojure.core"}
  commons.clojure.core
  (:require [such.immigration :as immigrate]))

;; I want these to be more available.
(immigrate/selection 'clojure.set '[union difference subset? intersection])
(immigrate/selection 'clojure.pprint '[pprint cl-format])
(immigrate/selection 'slingshot.slingshot '[try+ throw+])

;; Grab suchwow functions
(immigrate/namespaces 'such.shorthand 'such.symbols
                      'such.types 'such.vars 'such.versions 
                      ;; including the overriding namespaces
                      'such.better-doc 'such.wide-domains)

;; And selected local functions:
;(immigrate/selection 'commons.control-flow '[pred-cond -<> -<>> -!> -!>> -!<> some-<> some-<>>])
(immigrate/namespaces 'commons.control-flow)
(immigrate/namespaces 'commons.clojure.backwards-compatibility
                      'commons.environment
                      'commons.random)

