(ns app.deps.core
  (:require [muuntaja.core :as m]
            [reitit.ring :as ring]
            [reitit.coercion.spec]
            [reitit.ring.coercion :as rrc]
            [reitit.ring.middleware.muuntaja :as muuntaja]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.middleware.cors :refer [wrap-cors]]
            [ring.adapter.jetty :refer [run-jetty]]
            [app.deps.file-reader :as fr]
            [app.deps.deps-parser :as dp])
  (:gen-class))

(def deps-file "resources/status.real")

(defn get-deps []
  (->
    (fr/get-file-as-line-seq deps-file)
    (dp/parse-deps)))

(defn deps-get-handler [_]
  {:status 200
   :body   (get-deps)})

(def app
  (->
    (ring/router
      ["/api"
       ["/deps" {:get {:handler deps-get-handler}}]]
      {:data {:coercion   reitit.coercion.spec/coercion
              :muuntaja   m/instance
              :middleware [rrc/coerce-request-middleware
                           muuntaja/format-response-middleware
                           rrc/coerce-response-middleware]}})
    (ring/ring-handler)
    (wrap-reload)
    (wrap-cors
      :access-control-allow-origin [#"http://localhost:3000"]
      :access-control-allow-methods [:get :put :post :delete])))

(defn -main []
  (run-jetty #'app {:port 4040}))