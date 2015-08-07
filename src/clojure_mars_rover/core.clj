(ns clojure-mars-rover.core
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

(def movement-matrix
  {'N [0 1]})

(defn- process-command
  [[x y dir :as position] command]
  (let [[+x +y] (movement-matrix dir)]
    [(+ x +x) (+ y +y) dir]))

(defn- process-rover
  [{:keys [start commands]} plateau]
  (reduce process-command start commands))

;(map read-string (string/split plateau #" "))
(defn mars-landing
  [payload]
  (let [{:keys [plateau rovers]} (parse-payload payload)]
    (for [rover rovers]
      (process-rover rover plateau))))