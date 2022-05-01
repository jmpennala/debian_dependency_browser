(ns app.deps.file-reader
  (:require [clojure.java.io :as io])
  (:import (java.io BufferedReader)))

(defn get-file-as-line-seq [file]
  (-> (io/reader file)
    (BufferedReader.)
    (line-seq)))