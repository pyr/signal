(ns signal.handler
  "Signal handling functions")

(defn ^sun.misc.Signal ->signal
  "Convert a keyword to an appropriate Signal instance."
  [signal]
  (sun.misc.Signal. (-> signal name .toUpperCase)))

(defn ^Long signal->number
  "Find out a signal's number"
  [signal]
  (-> signal ->signal .getNumber))

(defn ^clojure.lang.Keyword signal->kw
  "Translate a signal to a keyword"
  [^sun.misc.Signal s]
  (-> s .getName .toLowerCase keyword))

(defn ^sun.misc.SignalHandler ->handler
  "Convert class to signal handler."
  [handler]
  (proxy [sun.misc.SignalHandler] []
    (handle [sig] (handler (signal->kw sig)))))

(defn on-signal
  "Execute handler when signal is caught"
  [signal handler]
  (sun.misc.Signal/handle (->signal signal) (->handler handler)))

(defmacro with-handler
  "Install a signal handler which will execute a function
   body when a UNIX signal is caught"
  [signal & body]
  `(on-signal ~signal (fn [_#] ~@body)))
