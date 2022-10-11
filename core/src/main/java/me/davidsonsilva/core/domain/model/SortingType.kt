package me.davidsonsilva.core.domain.model

enum class SortingType(val value:String) {
    ORDER_BY_NAME(value = "name"),
    ORDER_BY_MODIFIED(value = "modified"),
    ORDER_ASCENDING(value = "ascending"),
    ORDER_DESCENDING(value = "descending")
}