(ns app.deps.util
  (:require [clojure.string :as str]))

(defn process-newline [line]
  (str/replace line #"^\s\." ""))

(defn remove-leading-whitespace [line]
  (str/replace line #"^\s" ""))

(def processors (comp
                  (partial map remove-leading-whitespace)
                  (partial map process-newline)))

(defn process-description [raw-description]
  (->>
    (str/split-lines raw-description)
    (processors)
    (str/join "\n")))