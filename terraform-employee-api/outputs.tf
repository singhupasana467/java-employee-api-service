output "namespace" {
  value = var.namespace
}

output "service_name" {
  value = kubernetes_service.employee-api.metadata[0].name
}
