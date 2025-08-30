variable "namespace" {
    type = string
    default = "employee-api"
}

variable "image" {
    type = string
    default = "techietech/employee-api-service:latest"
}

variable "kubeconfig_path" {
    type = string
    default = "C:/Users/timbe/.kube/config"
}