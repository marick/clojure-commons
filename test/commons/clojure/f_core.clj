(ns commons.clojure.f-core
  (:use midje.sweet))
(require 'such.immigration)(such.immigration/namespaces-by-reference 'commons.clojure.core)

(fact "checks each pred against the result of the first expression, returning if it finds a match" 
  (branch-on "abcde" 
    #(.contains % "xyz") "contains 'xyz'" 
    string? "string"
    :else "neither") => "string"

  (branch-on 1 
    even? "even" 
    string? "string"
    :else "neither") => "neither"
  
  ; default is nil
  (branch-on 1 
    even? "even") => nil)

;;; Printing

(fact "some clojure.pprint functions are immigrated"
  (cl-format nil "1: ~A" 1) => "1: 1")

