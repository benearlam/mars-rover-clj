(ns clojure-mars-rover.core-test
  (:require [midje.sweet :refer :all]
            [clojure-mars-rover.core :refer :all]))

(facts "`mars-landing`"
       (fact "it lands and moves north, incrementing the y axis"
             (let [payload (str "5 5" "\n" "1 1 N" "\n" "M")]
               (mars-landing payload) => '([1 2 N]))))

(facts "`parse-payload"
       (fact "it turns the payload into a map"
             (let [payload (str "5 5" "\n" "1 1 N" "\n" "M" "\n" "1 2 S" "\n" "MLM")]
               (parse-payload payload) => {:plateau [5 5]
                                           :rovers  [{:start    [1 1 'N]
                                                      :commands [\M]}
                                                     {:start    [1 2 'S]
                                                      :commands [\M\L\M]}]})))
