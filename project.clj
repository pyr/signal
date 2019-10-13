(let [cfg   (clojure.edn/read-string (slurp "deps.edn"))
      deps  (for [[k {:keys [mvn/version exclusions]}] (:deps cfg)]
              [k version :exclusions exclusions])
      paths (:paths cfg)]

  (defproject spootnik/signal "0.2.5-SNAPSHOT"
    :description "system signal handler for clojure."
    :url "https://github.com/pyr/signal"
    :license {:name "MIT/ISC License"}
    :jvm-opts ["-Dclojure.compiler.direct-linking=true"]
    :pedantic? :abort
    :aliases {"kaocha" ["with-profile" "+dev" "run" "-m" "kaocha.runner"]
              "junit"  ["with-profile" "+dev" "run" "-m" "kaocha.runner"
                        "--plugin" "kaocha.plugin/junit-xml" "--junit-xml-file"
                        "target/junit/results.xml"]}
    :deploy-repositories [["snapshots" :clojars] ["releases" :clojars]]
    :profiles {:dev {:dependencies [[lambdaisland/kaocha           "0.0-554"]
                                    [lambdaisland/kaocha-junit-xml "0.0-70"]]
                     :pedantic?    :warn}}
    :dependencies ~deps
    :source-paths ~paths))
