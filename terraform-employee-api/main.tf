resource "kubernetes_namespace" "employee" {
    metadata {
        name = var.namespace
    }
}

resource "kubernetes_deployment" "employee-api" {
    metadata {
        name = "employee-api-service"
        namespace = var.namespace
    }
    spec {
        replicas = 1
        selector {
            match_labels = {
                app = "employee-api"
            }
        }
        template {
            metadata{
                labels = {
                    app = "employee-api"
                }
            }
            spec {
                container {
                    image = var.image
                    name = "employee-api"
                    port {
                        container_port = 8080
                    }
                }
            }
        }
    }
}

resource "kubernetes_service" "employee-api" {
    metadata {
        name = "employee-api-service"
        namespace = var.namespace
    }
    spec {
        selector = {
            app = "employee-api"
        }
        port {
            port = 80
            target_port = 8080
        }
        type = "ClusterIP"
    }
}