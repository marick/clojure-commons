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
  doit("lein compatibility")
end
