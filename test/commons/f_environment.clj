(ns commons.f-environment
  (:use midje.sweet)
  (:require [commons.environment :as subject]))

(fact "env"
  (subject/env :home) => string?
  (subject/env :does-not-exist) => (throws))

(fact "env-nil-ok"
  (subject/env-nil-ok :home) => string?
  (subject/env-nil-ok :does-not-exist) => nil)


