(ns ^{:doc "Support for different clojure versions"}
  marick.clojure.versions
  (:require marick.clojure.backwards-compatibility))

(defmacro when-1-4 [& body] 
  (when (= (:minor *clojure-version*) 4)
    `(do ~@body)))

(defmacro when-1-5- [& body] 
  (when (<= (:minor *clojure-version*) 5)
    `(do ~@body)))

(defmacro when-1-5+ [& body] 
  (when (>= (:minor *clojure-version*) 5)
    `(do ~@body)))

(defmacro when-1-6+ [& body] 
  (when (>= (:minor *clojure-version*) 6)
    `(do ~@body)))

