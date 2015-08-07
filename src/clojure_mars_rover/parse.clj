(ns clojure-mars-rover.parse
  (:require [clojure.string :as string]))

(defn- split-and-read
  [string]
  (-> string
      (string/split #" ")
      (#(map read-string %))))

(defn- parse-plateau
  [plateau]
  (split-and-read plateau))

(defn- parse-rover
  [[start commands]]
  {:start    (split-and-read start)
   :commands (vec commands)})

(defn- parse-rovers
  [rovers]
  (->> rovers
       (partition 2)
       (map parse-rover)))

(defn parse-payload
  [payload]
  (let [[plateau & rovers] (string/split-lines payload)]
    {:plateau (parse-plateau plateau)
     :rovers  (parse-rovers rovers)}))
