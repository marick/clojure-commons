(ns commons.clojure.f-sequences
  (:use midje.sweet)
  (:require [commons.sequences :as subject]))

(fact "vertical-slices"
  (subject/vertical-slices [1 2 3] [:a :b :c])
  => [ [1 :a] [2 :b] [3 :c]])

(fact "separate"
  (subject/separate odd? []) => [ [] [] ]
  (subject/separate odd? [1]) => [ [1] [] ]
  (subject/separate odd? [1 2 3 4]) => [ [1 3] [2 4] ]

  (fact "lazy"
    (ffirst (subject/separate odd? (range))) => 1))

(fact "only"
  (subject/only [1]) => 1
  (subject/only [1 2]) => (throws RuntimeException)
  (subject/only []) => (throws RuntimeException))


