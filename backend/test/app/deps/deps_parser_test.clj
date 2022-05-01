(ns app.deps.deps-parser-test
  (:require [clojure.test :refer :all]
            [app.deps.deps-parser :as deps-parser]
            [app.deps.file-reader :as file-reader]))

(deftest deps-parser-test
  (let [deps-seq (file-reader/get-file-as-line-seq "resources/test/deps.fxt")]
  (testing "foo"
    (let [result (deps-parser/parse-deps deps-seq)]
      (is (= [{"package" "libws-commons-util-java"
               "description" "Common utilities from the Apache Web Services Project\n This is a small collection of utility classes, that allow high\n performance XML processing based on SAX."
               "depends" ["python"]}
           {"package" "python-pkg-resources"
            "description" "Package Discovery and Resource Access using pkg_resources\n The pkg_resources module provides an API for Python libraries to\n access their resource files, and for extensible applications and\n frameworks to automatically discover plugins.  It also provides\n runtime support for using C extensions that are inside zipfile-format\n eggs, support for merging packages that have separately-distributed\n modules or subpackages, and APIs for managing Python's current\n \"working set\" of active packages."
            "depends" ["python"]}
           {"package" "tcpd"}
             "description" "Wietse Venema's TCP wrapper utilities\n Wietse Venema's network logger, also known as TCPD or LOG_TCP.\n .\n These programs log the client host name of incoming telnet,\n ftp, rsh, rlogin, finger etc. requests.\n .\n Security options are:\n  - access control per host, domain and/or service;\n  - detection of host name spoofing or host address spoofing;\n  - booby traps to implement an early-warning system."
              "depends" ["python"]] result))))))
