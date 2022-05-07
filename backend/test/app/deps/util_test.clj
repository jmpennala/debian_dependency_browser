(ns app.deps.util-test
  (:require [clojure.test :refer :all]
            [fudje.sweet :refer :all]
            [app.deps.util :as u]))

(deftest deps-util-tests
  (fact "newline processing"
    (u/process-newline " .\n") => "\n")
  (fact "description is transformed properly"
    (u/process-description (slurp "resources/test/description_fxt.txt")) => (slurp "resources/test/expected_description.txt")))