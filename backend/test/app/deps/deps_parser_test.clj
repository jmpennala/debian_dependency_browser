(ns app.deps.deps-parser-test
  (:require [clojure.test :refer :all]
            [app.deps.deps-parser :as deps-parser]
            [app.deps.file-reader :as file-reader]
            [clojure.string :as str]
            [fudje
             [core :refer [mocking in-background]]
             [sweet :refer :all]]))

(deftest deps-parser-tests
  (let [result (->
                 (file-reader/get-file-as-line-seq "resources/test/deps.fxt")
                 (deps-parser/parse-deps))]
    (fact "parsing deps fixture gives 3 dependencies"
      (count result) => 3)
    (fact "dependencies contains packages"
      (map #(get % "package") result) => ["tcpd" "python-pkg-resources" "libws-commons-util-java"])))