(ns app.deps.deps-parser-test
  (:require [clojure.test :refer :all]
            [app.deps.deps-parser :as deps-parser]
            [app.deps.file-reader :as file-reader]
            [clojure.string :as str]))

(deftest deps-parser-test
  (let [deps-seq (file-reader/get-file-as-line-seq "resources/test/deps.fxt")]
    (testing "deps parser"
      (let [result (deps-parser/parse-deps deps-seq)
            libws-result (first (filter #(= (get % "package") "tcpd") result))]
        (is (= 3 (count result)))
        (is (= (get libws-result "package") "tcpd"))
        (is (= (get libws-result "short_description") "Wietse Venema's TCP wrapper utilities"))
        (is (str/includes? (get libws-result "description") "These programs log the client host name"))
        (is (= (get libws-result "depends") ["libwrap0" "libc6"]))))))
