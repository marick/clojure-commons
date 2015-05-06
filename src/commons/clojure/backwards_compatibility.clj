(ns ^:no-doc commons.clojure.backwards-compatibility
    "Functions grabbed from newer versions of Clojure, 
     so we can maintain backwards compatibility.")

(letfn [(missing? [sym] (not (ns-resolve 'clojure.core sym)))]

  (defmacro ^:private defn-once [sym & rest]
    (when (missing? sym)
      `(defn ~sym ~@rest)))

  (defmacro ^:private defn-once-in-core [sym & rest]
    (when (missing? sym)
      `(intern 'clojure.core '~sym (fn ~@rest))))

  (defmacro ^:private move-once-to-core [source-namespace syms]
    (when (missing? (first syms))
      `(do
         (require '~source-namespace)
         (doseq [sym# '~syms]
           (let [var# (ns-resolve '~source-namespace sym#)]
             (intern 'clojure.core (with-meta sym# (meta var#)) var#)))))))

(move-once-to-core slingshot.ex-info [ex-info ex-data])
(move-once-to-core commons.clojure.one-dot-five [cond-> cond->> as-> some-> some->>])
