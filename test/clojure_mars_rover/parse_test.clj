(ns clojure-mars-rover.parse-test
  (:require [midje.sweet :refer :all]
            [clojure-mars-rover.parse :refer :all]))

(fact "it turns the payload into a map"
      (let [payload (str "5 5" "\n" "1 1 N" "\n" "M" "\n" "1 2 S" "\n" "MLM")]
        (parse-payload payload) => {:plateau [5 5]
                                    :rovers  [{:start    [1 1 'N]
                                               :commands [\M]}
                                              {:start    [1 2 'S]
                                               :commands [\M \L \M]}]}))