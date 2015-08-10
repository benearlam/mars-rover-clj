(ns clojure-mars-rover.core
  (:require [clojure-mars-rover.parse :refer [parse-payload]]))

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
  [[x y dir]]
  (let [[+x +y] (movement-matrix dir)]
    [(+ x +x) (+ y +y) dir]))

(defn- check-position
  [current-position last-known-position plateau]
  (if (some true? (map > current-position plateau))
    (conj (vec last-known-position) 'RIP)
    current-position))

(defn- process-command
  [plateau [x y dir :as start] command]
  (cond
    (= command \M) (-> start
                       move-forward
                       (check-position start plateau))
    (= command \L) [x y (turn-left dir)]
    (= command \R) [x y (turn-right dir)]))

(defn- process-rover
  [{:keys [start commands]} plateau]
  (reduce (fn [pos command]
            (let [new-pos (process-command plateau pos command)]
              (if (some #{'RIP} new-pos)
                (reduced new-pos)
                new-pos))) start commands))

(defn mars-landing
  [payload]
  (let [{:keys [plateau rovers]} (parse-payload payload)]
    (for [rover rovers]
      (process-rover rover plateau))))