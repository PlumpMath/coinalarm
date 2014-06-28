(ns coinalarm.core
  (:require [cljs.reader :as reader]
            [goog.events :as events]
            [goog.dom :as gdom]
            [coinalarm.phone :as phone]
            [sablono.core :as html :refer-macros [html]]
            [coinalarm.markets :as markets]
            [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true])
  (:import [goog.net XhrIo]
           [goog.net.EventType]
           [goog.events EventType]))

(enable-console-print!)

(def app-state (atom {:unused ""}))

(defn main-component [app owner]
  (om/component
    (html [:div.container
            [:div.front (om/build markets/market-selector app)]])))

(om/root main-component app-state {:target (. js/document (getElementById "app"))})
