(ns app.deps.deps-parser
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def multiline-initial-value {:enabled            false
                              :key                nil
                              :intermediate-value nil})

(defn- assoc-key-val-to-current-dependency [deps key val]
  (assoc-in deps [:current-dep key] val))

(defn- drop-version [dependency]
  (let [version-idx (str/index-of dependency " (")]
    (if version-idx (subs dependency 0 version-idx) dependency)))

(defn process-depends [deps value]
  (->> (str/split value #", ")
    (map drop-version)
    (set)                                                   ; remove duplicates
    (vec)
    (assoc-key-val-to-current-dependency deps "depends")))

(defn- init-new-package-to-deps [{:keys [current-dep] :as deps} value]
  (->
    (when current-dep (update deps :all-deps conj current-dep)) ; push previous dep to :all-deps vector
    (assoc-key-val-to-current-dependency "package" value))) ; start new dependency collection

(defn begin-multiline-parsing [deps key]
  (assoc deps :multiline {:enabled            true
                          :multiline-key      key
                          :intermediate-value ""}))

(defn continue-multiline-parsing [deps value]
  (let [trimmed-value (subs value 1)]
    (update-in deps [:multiline :intermediate-value] str trimmed-value)))

(defn finish-multiline-parsing [{{:keys [multiline-key intermediate-value]} :multiline :as deps}]
  (-> (assoc-key-val-to-current-dependency deps multiline-key intermediate-value)
    (assoc :multiline multiline-initial-value)))

(defn process-line [deps line]
  (let [multiline-parsing-active (-> deps :multiline :enabled)]
    (if (and (str/starts-with? line " ") multiline-parsing-active)
      (continue-multiline-parsing deps line)
      (let [line-parts (str/split line #":")
            key (first line-parts)
            value (when (second line-parts) (str/trim (second line-parts)))
            updated-deps (if multiline-parsing-active (finish-multiline-parsing deps) deps)]
        (case key
          "Package" (init-new-package-to-deps updated-deps value)
          "Depends" (process-depends updated-deps value)
          "Description" (-> (assoc-key-val-to-current-dependency deps "short_description" value)
                          (begin-multiline-parsing "description"))
          updated-deps)))))

(defn finish-parsing [{:keys [current-dep] :as deps}]
  (when current-dep (update deps :all-deps conj current-dep)))

(def reduce-initial-value
  {:all-deps    []
   :current-dep nil
   :multiline   multiline-initial-value})

(defn parse-deps [line-seq]
  (-> (reduce
        process-line
        reduce-initial-value
        line-seq)
    (finish-parsing)
    :all-deps))