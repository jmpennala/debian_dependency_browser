(ns app.deps.deps-parser-test
  (:require [clojure.test :refer :all]
            [app.deps.deps-parser :as deps-parser]
            [app.deps.file-reader :as file-reader]
            [clojure.string :as str]))

(def expected-description (str "Wietse Venema's network logger, also known as TCPD or LOG_TCP.."
                            "These programs log the client host name of incoming telnet,ftp, rsh, rlogin, finger etc. "
                            "requests..Security options are: - access control per host, domain and/or service;"
                            "- detection of host name spoofing or host address spoofing;"
                            " - booby traps to implement an early-warning system."
                            "Wietse Venema's network logger, also known as TCPD or LOG_TCP.\n .\n"
                            "These programs log the client host name of incoming telnet,"))

(deftest deps-parser-test
  (let [deps-seq (file-reader/get-file-as-line-seq "resources/test/deps.fxt")]
    (testing "deps parser"
      (let [result (deps-parser/parse-deps deps-seq)
            libws-result (first (filter #(= (get % "package") "tcpd") result))]
        (is (= 3 (count result)))
        (is (= (get libws-result "package") "tcpd"))
        (is (= (get libws-result "short_description") "Wietse Venema's TCP wrapper utilities"))
        (is (> (str/index-of (get libws-result "description") "These programs log the client host name") 0))
        (is (= (get libws-result "depends") ["libwrap0" "libc6"]))))))
