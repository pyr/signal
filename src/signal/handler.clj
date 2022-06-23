(ns signal.handler
  "Signal handling functions"
  (:import clojure.lang.Compiler
           clojure.lang.DynamicClassLoader))

(defn- set-compiler-class-loader
  "Ensures that the class loader in which a handler will
   execute runs in a context which can load clojure classes."
  []
  (let [thread (Thread/currentThread)]
    (when-not (instance? DynamicClassLoader (.getContextClassLoader thread))
      (.setContextClassLoader thread (DynamicClassLoader.
                                      (.getClassLoader Compiler))))))

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
    (handle [sig]
      (set-compiler-class-loader)
      (handler (signal->kw sig)))))

(defn on-signal
  "Execute handler when signal is caught"
  [signal handler]
  (sun.misc.Signal/handle (->signal signal) (->handler handler)))

(defmacro with-handler
  "Install a signal handler which will execute a function
   body when a UNIX signal is caught"
  [signal & body]
  `(on-signal ~signal (fn [_#] ~@body)))
