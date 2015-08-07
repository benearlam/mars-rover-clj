(ns clojure-mars-rover.core-test
  (:require [midje.sweet :refer :all]
            [clojure-mars-rover.core :refer :all]))

(facts "`mars-landing`"
       (fact "it lands and moves north, incrementing the y axis"
             (let [payload (str "5 5" "\n" "1 1 N" "\n" "M")]
               (mars-landing payload) => '([1 2 N])))

       (fact "it lands facing north and moves left leaving it facing west"
             (let [payload (str "5 5" "\n" "1 1 N" "\n" "L")]
               (mars-landing payload) => '([1 1 W])))

       (fact "it lands and moves east, incrementing the x axis"
             (let [payload (str "5 5" "\n" "1 1 E" "\n" "M")]
               (mars-landing payload) => '([2 1 E])))

       (fact "it lands and moves south, decrementing the y axis"
             (let [payload (str "5 5" "\n" "2 2 S" "\n" "M")]
               (mars-landing payload) => '([2 1 S])))

       (fact "it lands and moves west, decrementing the x axis"
             (let [payload (str "5 5" "\n" "2 2 W" "\n" "M")]
               (mars-landing payload) => '([1 2 W])))

       (fact "it lands and moves as per the spec LMLMLMLMM"
             (let [payload (str "5 5" "\n" "1 2 N" "\n" "LMLMLMLMM")]
               (mars-landing payload) => '([1 3 N]))))

(facts "`parse-payload"
       (fact "it turns the payload into a map"
             (let [payload (str "5 5" "\n" "1 1 N" "\n" "M" "\n" "1 2 S" "\n" "MLM")]
               (parse-payload payload) => {:plateau [5 5]
                                           :rovers  [{:start    [1 1 'N]
                                                      :commands [\M]}
                                                     {:start    [1 2 'S]
                                                      :commands [\M\L\M]}]})))
