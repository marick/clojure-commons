(ns commons.clojure.f-maps
  (:use midje.sweet)
  (:require [commons.maps :as subject]))

;;; Maps

(fact "1.4 doesn't allow duplicates in hash-map arguments, so we have an alternative"
  (subject/hash-map-duplicates-ok) => {} 
  (subject/hash-map-duplicates-ok :a 1 :b 2) => {:a 1 :b 2}
  (subject/hash-map-duplicates-ok :a 1 :b 2 :b 33333) => {:a 1 :b 33333})

(fact "invert"
  (subject/invert {:a 1, :b 2}) => {1 :a, 2 :b})

(fact "dissoc-keypath"
  (fact "removes a key/value pair"
    (subject/dissoc-keypath {:by-name {:name1 1, :name2 2}} [:by-name :name1])
    =>              {:by-name {          :name2 2}}
    (subject/dissoc-keypath {:by-name {:name1 1}} [:by-name :name1])
    =>              {:by-name {        }}
    (subject/dissoc-keypath {"1" {"2" {"3.1" 3, "3.2" 3}}} ["1" "2" "3.1"])
    =>              {"1" {"2" {         "3.2" 3}}})
  (fact "leaves the map alone if the last key is incorrect"
    (subject/dissoc-keypath {:by-name {:name1 1}} [:by-name :NOTFOUND])
    =>              {:by-name {:name1 1}})
  (fact "requires that the path up to the last key exists"
    (subject/dissoc-keypath {:by-name {:name1 1}} [:NOTFOUND :name1])
    =not=>          {:NOTFOUND {:name1 1}}))

(fact "transform entries in a map"
  (fact "normal case"
    (subject/transform-in {:a {:b 1}} [:a :b] inc) => {:a {:b 2}})
  (fact "can take a default"
    (subject/transform-in {} [:a :b] inc 0) => {:a {:b 1}})

  (fact "single-level shorthand")
    (subject/transform {:a 1} :a inc)    => {:a 2}
    (subject/transform {}     :a inc 0 ) => {:a 1})


