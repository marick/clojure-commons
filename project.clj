(defproject marick/clojure-commons "0.3.1"
  :description "Marick's clojure utilities"
  :url "https://github.com/marick/clojure-commons"
  :pedantic? :warn
  :license {:name "The MIT License (MIT)"
            :url "http://opensource.org/licenses/mit-license.php"
            :distribution :repo}

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [swiss-arrows "1.0.0" :exclusions [org.clojure/clojure]]
                 [slingshot "0.12.1"]]

  :profiles {:dev {:dependencies [[midje "1.7.0-beta1" :exclusions [org.clojure/clojure]]]}
             :1.4 {:dependencies [[org.clojure/clojure "1.4.0"]]}
             :1.5.0 {:dependencies [[org.clojure/clojure "1.5.0"]]}
             :1.5.1 {:dependencies [[org.clojure/clojure "1.5.1"]]}
             :1.6 {:dependencies [[org.clojure/clojure "1.6.0"]]}
             :1.7 {:dependencies [[org.clojure/clojure "1.7.0-beta1"]]}
             }

  ;; For Clojure snapshots
  :repositories {"sonatype-oss-public" "https://oss.sonatype.org/content/groups/public/"}
  :deploy-repositories [["releases" :clojars]]
)

  
