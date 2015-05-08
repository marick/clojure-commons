(defproject marick/clojure-commons "0.7.1"
  :description "Marick's clojure utilities"
  :url "https://github.com/marick/clojure-commons"
  :pedantic? :warn
  :license {:name "The MIT License (MIT)"
            :url "http://opensource.org/licenses/mit-license.php"
            :distribution :repo}

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [swiss-arrows "1.0.0" :exclusions [org.clojure/clojure]]
                 [environ "1.0.0"]
                 [marick/suchwow "2.1.1" :exclusions [org.clojure/clojure]]
                 [slingshot "0.12.1"]
                 [commons-codec/commons-codec "1.10"]]

  :profiles {:dev {:dependencies [[midje "2.0.0-SNAPSHOT" :exclusions [org.clojure/clojure]]]}
             :1.4 {:dependencies [[org.clojure/clojure "1.4.0"]]}
             :1.5.0 {:dependencies [[org.clojure/clojure "1.5.0"]]}
             :1.5.1 {:dependencies [[org.clojure/clojure "1.5.1"]]}
             :1.6 {:dependencies [[org.clojure/clojure "1.6.0"]]}
             :1.7 {:dependencies [[org.clojure/clojure "1.7.0-beta1"]]}
             }

  :plugins [[lein-midje "3.1.3"]
            [codox "0.8.11"]]

  :codox {:src-dir-uri "https://github.com/marick/clojure-commons/blob/master/"
          :src-linenum-anchor-prefix "L"
          :output-dir "/var/tmp/clojure-commons-doc"
          :defaults {:doc/format :markdown}}

  :aliases {"compatibility" ["with-profile" "+1.4:+1.5.0:+1.5.1:+1.6:+1.7" "midje" ":config" ".compatibility-test-config"]
            "travis" ["with-profile" "+1.4:+1.5.0:+1.5.1:+1.6:+1.7" "midje"]}

  ;; For Clojure snapshots
  :repositories {"sonatype-oss-public" "https://oss.sonatype.org/content/groups/public/"}
  :deploy-repositories [["releases" :clojars]]
)
