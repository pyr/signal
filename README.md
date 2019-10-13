signal: UNIX signal handlers in clojure
=======================================

This is just a bit of code I end up copying through-out my projects.
This projects bring one macro and one function of interest.

[![CircleCI](https://circleci.com/gh/pyr/signal.svg?style=svg)](https://circleci.com/gh/pyr/signal)

## Usage

Pull-in the following dependency:

```clojure
  [spootnik/signal "0.2.4"]
```

The main signatures:

- `(on-signal signal handler)`: Execute handler (a function of one argument, the signal keyword).
- `(with-handler signal & body)`: Handle signal by calling the body of forms supplied.

A few additional signatures may come in handy:

- `(->signal signal)`: Convert a signal keyword or string to a `sun.misc.Signal` instance.
- `(signal->number signal)`: Show the number for a signal.
- `(signal->kw signal)`: Convert a `sun.misc.Signal` instance to a keyword.
- `(->handler f)`: Convert a function of one argument to a `sun.misc.SignalHandler` instance.

## Using with component

Here's one way of hooking this up with a component system:

```clojure
  (with-handler :term
    (info "caught SIGTERM, quitting.")
    (alter-var-root #'system component/stop-system)
    (System/exit 0))

  (with-handler :hup
    (info "caught SIGHUP, reloading.")
	(alter-var-root #'system (comp component/start-system component/stop-system)))
```


## License

Copyright © 2016 Pierre-Yves Ritschard <pyr@spootnik.org>

