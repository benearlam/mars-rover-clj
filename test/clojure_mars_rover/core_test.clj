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
               (mars-landing payload) => '([1 3 N])))

       (fact "it lands and moves as per the spec LMLMLMLMM"
             (let [payload (str "5 5" "\n" "1 2 N" "\n" "LMLMLMLMM" "\n" "3 3 E" "\n" "MMRMMRMRRM")]
               (mars-landing payload) => '([1 3 N] [5 1 E])))

       (fact "falling off the plateau leaves RIP"
             (let [payload (str "5 5" "\n" "5 5 N" "\n" "M")]
               (mars-landing payload) => '([5 5 N RIP])))

       (fact "falling off the plateau terminates the rover"
             (let [payload (str "5 5" "\n" "5 5 N" "\n" "MMMMMM")]
               (mars-landing payload) => '([5 5 N RIP]))))

