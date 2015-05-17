(ns commons.clojure.core
  "Functions I wouldn't mind seeing in clojure.core"
  (:require [such.immigration :as immigrate]
            ;; individual requires are needed for codox
            clojure.set clojure.pprint slingshot.slingshot such.shorthand such.symbols
            such.types such.vars such.versions
            ;; update clojure.core documentation
            such.better-doc))

(immigrate/selection 'clojure.set '[union difference subset? intersection rename-keys])
(immigrate/selection 'clojure.pprint '[pprint cl-format])
(immigrate/selection 'slingshot.slingshot '[try+ throw+])

;; Grab suchwow functions
(immigrate/namespaces 'such.shorthand 'such.types 'such.wide-domains)

;; And selected local functions:
(immigrate/namespaces 'commons.control-flow)
(immigrate/namespaces 'commons.clojure.backwards-compatibility
                      'commons.environment
                      'commons.random)

