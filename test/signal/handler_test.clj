(ns signal.handler-test
  (:require [clojure.test   :refer :all]
            [signal.handler :refer :all]))

(defn send-signal-to-myself
  [sig]
  (let [pid     (.pid (java.lang.ProcessHandle/current))
        runtime (Runtime/getRuntime)
        cmdline (format "kill -%s %s" sig pid)]
    (.exec runtime cmdline)))

(deftest sighandler-test
  (testing "sending USR1"
    (let [calls (atom 0)]
      (with-handler :usr1 (swap! calls inc))
      (send-signal-to-myself "USR1")
      (is (= 1 @calls))
      (with-handler :usr1)
      (send-signal-to-myself "USR1")
      (is (= 1 @calls)))))
