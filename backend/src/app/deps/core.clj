(ns app.deps.core
  (:require [app.deps.file-reader :as fr]
            [app.deps.deps-parser :as dp]))

(def deps-file "resources/status.real")

(defn get-deps []
  (->
    (fr/get-file-as-line-seq deps-file)
    (dp/parse-deps)))