# -*- Mode: ruby -*-

require 'rubygems'
require 'rake'

def doit(text)
    puts "== " + text
    system(text)
end

desc "Check many versions of Clojure"
# Note: If you change this, you need to also change .travis.yml
task :compatibility do
  doit("lein with-profile +1.4:+1.5.0:+1.5.1:+1.6:+1.7 midje :config .compatibility-test-config")
end
