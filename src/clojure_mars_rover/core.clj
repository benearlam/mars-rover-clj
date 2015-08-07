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
  {'N [0 1]
   'E [1 0]
   'S [0 -1]
   'W [-1 0]})

(def compass-readings
  ['N 'E 'S 'W])

(defn- next-direction
  [inc-or-dec default current-dir]
  (get compass-readings (inc-or-dec (.indexOf compass-readings current-dir)) default))

(def turn-left
  (partial next-direction dec 'W))

(def turn-right
  (partial next-direction inc 'N))

(defn- move-forward
  [x y dir]
  (let [[+x +y] (movement-matrix dir)]
    [(+ x +x) (+ y +y) dir]))

(defn- process-command
  [[x y dir] command]
  (cond
    (= command \M) (move-forward x y dir)
    (= command \L) [x y (turn-left dir)]
    (= command \R) [x y (turn-right dir)]))

(defn- process-rover
  [{:keys [start commands]} plateau]
  (reduce process-command start commands))

(defn mars-landing
  [payload]
  (let [{:keys [plateau rovers]} (parse-payload payload)]
    (for [rover rovers]
      (process-rover rover plateau))))