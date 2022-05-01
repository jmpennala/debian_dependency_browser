(ns app.core
  (:require [muuntaja.core :as m]
            [reitit.ring :as ring]
            [reitit.coercion.spec]
            [reitit.ring.coercion :as rrc]
            [reitit.ring.middleware.muuntaja :as muuntaja]
            [reitit.ring.middleware.parameters :as parameters]
            [ring.server.standalone :as server]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.middleware.cors :refer [wrap-cors]]
            [ring.adapter.jetty :refer [run-jetty]]
            [app.deps.core :as deps])
  (:gen-class))

(defn deps-get-handler [params]
  {:status 200
   :body   (deps/get-deps)})

(def app
  (->
    (ring/router
      ["/api"
       ["/deps" {:get {;:parameters {:query {}}
                       ;:responses  {200 {:body {:total int?}}}
                       :handler    deps-get-handler}}]]
      ;; router data affecting all routes
      {:data {:coercion   reitit.coercion.spec/coercion
              :muuntaja   m/instance
              :middleware [;parameters/parameters-middleware
                           rrc/coerce-request-middleware
                           muuntaja/format-response-middleware
                           rrc/coerce-response-middleware]}})
    (ring/ring-handler)
    (wrap-reload)
    (wrap-cors :access-control-allow-origin [#"http://localhost:3000"]
               :access-control-allow-methods [:get :put :post :delete])))


(defn -main []
  ;(server/serve #'app {:port 4040})
  (run-jetty #'app {:port 4040}))