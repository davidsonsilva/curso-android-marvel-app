package me.davidsonsilva.core.data.mapper

import me.davidsonsilva.core.data.StorageConstants
import me.davidsonsilva.core.domain.model.SortingType
import javax.inject.Inject

class SortingMapper @Inject constructor() {

    private val nameAscending =
        SortingType.ORDER_BY_NAME.value to SortingType.ORDER_ASCENDING.value
    private val nameDescending =
        SortingType.ORDER_BY_NAME.value to SortingType.ORDER_DESCENDING.value
    private val modifierAscending =
        SortingType.ORDER_BY_MODIFIED.value to SortingType.ORDER_ASCENDING.value
    private val modifierDescending =
        SortingType.ORDER_BY_MODIFIED.value to SortingType.ORDER_DESCENDING.value


    fun mapToPair(sorting:String) :Pair<String,String> {
       return when(sorting) {
            StorageConstants.ORDER_BY_NAME_ASCENDING -> nameAscending
            StorageConstants.ORDER_BY_NAME_DESCENDING -> nameDescending
            StorageConstants.ORDER_BY_MODIFIED_ASCENDING -> modifierAscending
            StorageConstants.ORDER_BY_MODIFIED_DESCENDING -> modifierDescending
            else -> nameAscending
        }
    }

    fun mapFromPair(sortingPair: Pair<String,String>) : String {
        val orderBy = sortingPair.first
        val order = sortingPair.second
        return when(orderBy) {
            SortingType.ORDER_BY_NAME.value -> when(order) {
                SortingType.ORDER_ASCENDING.value -> StorageConstants.ORDER_BY_NAME_ASCENDING
                SortingType.ORDER_DESCENDING.value -> StorageConstants.ORDER_BY_NAME_DESCENDING
                else -> StorageConstants.ORDER_BY_NAME_ASCENDING
            }
            SortingType.ORDER_BY_MODIFIED.value -> when(order) {
                SortingType.ORDER_ASCENDING.value -> StorageConstants.ORDER_BY_MODIFIED_ASCENDING
                SortingType.ORDER_DESCENDING.value -> StorageConstants.ORDER_BY_MODIFIED_DESCENDING
                else -> StorageConstants.ORDER_BY_MODIFIED_ASCENDING
            }
            else -> StorageConstants.ORDER_BY_NAME_ASCENDING
        }
    }
}