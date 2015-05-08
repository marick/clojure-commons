(ns commons.f-function-makers (:require [commons.function-makers :as subject])
  (:use midje.sweet))


(fact "mkfn:any?"
  ((subject/mkfn:any? [odd? even?]) 1) => true
  ((subject/mkfn:any? [pos? neg?]) 0) => false
  ((subject/mkfn:any? [:key :word]) {:key false}) => false
  ((subject/mkfn:any? [:key :word]) {:key false :word 3}) => true
  ((subject/mkfn:any? [#{1 2} #{3 4}]) 3) => true
  ;; stops at first match
  ((subject/mkfn:any? [(partial = 3) (fn[_](throw (new Error "boom!")))]) 3) => true
  ;; Any empty list means that everything matches
  ((subject/mkfn:any?  []) 3) => true)

