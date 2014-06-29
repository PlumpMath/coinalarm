(ns coinalarm.persistence
  (:require [taoensso.carmine :as car :refer (wcar)]
            [coinalarm.schemas :as schemas]))

(def server1-conn {:pool {} :spec {}}) ; See `wcar` docstring for opts

(defmacro wcar* [& body] `(car/wcar server1-conn ~@body))

;; USER

;; TODO: Also delete the user, this only deletes our refeference
(defn reset-users! []
  (wcar* (car/set "users" [])))

(defn create-user! [user]
  (let [users (wcar* (car/get "users"))
        users (or users [])]
  (wcar*
   (car/set "users" (into [] (set (cons (:phone user) users)))) ;; improve
   (car/set (:phone user) user))))

(defn update-user! [user]
  (let [old-user (wcar* (car/get (:phone user)))
        updated-user (merge old-user user)]
  (wcar*
   (car/set (:phone user) updated-user))))

(defn get-user [phone]
  (wcar* (car/get phone)))

(defn get-all-user []
  (let [users (wcar* (car/get "users"))]
    (map #(wcar* (car/get %)) users)))

;; HISTORICAL BTC DATA

(def max-number-h-btc 100) ;; limit the number of historical entries, we don't need that many

(defn reset-h-btcs! []
  (wcar* (car/set "h-btcs" [])))
