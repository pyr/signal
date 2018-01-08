(defproject spootnik/signal "0.2.2"
  :description "system signal handler for clojure."
  :url "https://github.com/pyr/signal"
  :license {:name "MIT/ISC License"}
  :jvm-opts ["-Dclojure.compiler.direct-linking=true"]
  :global-vars {*warn-on-reflection* true}
  :dependencies [[org.clojure/clojure "1.9.0"]])
