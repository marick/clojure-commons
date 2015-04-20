(ns marick.clojure.f-core
  (:use marick.clojure.core )
  (:use midje.sweet))

;;; Types and pseudo types

(defrecord R [a])

(facts "about recognizing classic maps"
  (classic-map? {}) => truthy
  (classic-map? (R. 1)) => falsey
  (classic-map? 1) => falsey)

(facts "about recognizing records"
  (record? {}) => falsey
  (record? (R. 1)) => truthy
  (record? 1) => falsey)

(defrecord ExampleNamed []
  clojure.lang.Named
  (getName [this] "name")
  (getNamespace [this] "namespace"))

(tabular
  (fact "can tell if a thing is Named"
    (named? ?thing) => ?result)
  
  ?thing           ?result
  'a               truthy
  :keyword         truthy
  (ExampleNamed.)  truthy
  "a"              falsey
  1                falsey
  \c               falsey)


;; ;;; Vars

(def #^:dynamic var-with-root :original-value)
(fact "can get the root value"
  (var-root #'var-with-root) => :original-value
  (binding [var-with-root "some other value"]
    var-with-root => "some other value"
    (var-root #'var-with-root) => :original-value))

(fact "can get a namespace-qualified variable name"
  (var-name #'var-with-root) => 'marick.clojure.f-core/var-with-root)

;;; Maps

(fact "1.4 doesn't allow duplicates in hash-map arguments, so we have an alternative"
  (hash-map-duplicates-ok) => {} 
  (hash-map-duplicates-ok :a 1 :b 2) => {:a 1 :b 2}
  (hash-map-duplicates-ok :a 1 :b 2 :b 33333) => {:a 1 :b 33333})

(fact "invert"
  (invert {:a 1, :b 2}) => {1 :a, 2 :b})

(fact "dissoc-keypath"
  (fact "removes a key/value pair"
    (dissoc-keypath {:by-name {:name1 1, :name2 2}} [:by-name :name1])
    =>              {:by-name {          :name2 2}}
    (dissoc-keypath {:by-name {:name1 1}} [:by-name :name1])
    =>              {:by-name {        }}
    (dissoc-keypath {"1" {"2" {"3.1" 3, "3.2" 3}}} ["1" "2" "3.1"])
    =>              {"1" {"2" {         "3.2" 3}}})
  (fact "leaves the map alone if the last key is incorrect"
    (dissoc-keypath {:by-name {:name1 1}} [:by-name :NOTFOUND])
    =>              {:by-name {:name1 1}})
  (fact "requires that the path up to the last key exists"
    (dissoc-keypath {:by-name {:name1 1}} [:NOTFOUND :name1])
    =not=>          {:NOTFOUND {:name1 1}}))

(fact "transform entries in a map"
  (fact "normal case"
    (transform-in {:a {:b 1}} [:a :b] inc) => {:a {:b 2}})
  (fact "can take a default"
    (transform-in {} [:a :b] inc 0) => {:a {:b 1}})

  (fact "single-level shorthand")
    (transform {:a 1} :a inc)    => {:a 2}
    (transform {}     :a inc 0 ) => {:a 1})



;; ;;; Sequences


(fact "vertical-slices"
  (vertical-slices [1 2 3]
                   [:a :b :c])
  => [ [1 :a] [2 :b] [3 :c]])

(fact "separate"
  (separate odd? []) => [ [] [] ]
  (separate odd? [1]) => [ [1] [] ]
  (separate odd? [1 2 3 4]) => [ [1 3] [2 4] ]

  (fact "lazy"
    (ffirst (separate odd? (range))) => 1))

(fact "find-first"
  (find-first odd? nil) => nil
  (find-first odd? [1 2 3]) => 1)

(fact "only"
  (only [1]) => 1
  (only [1 2]) => (throws RuntimeException)
  (only []) => (throws RuntimeException))

