(ns commons.random)

(defn guid [] (str (java.util.UUID/randomUUID)))
(def uuid guid)
