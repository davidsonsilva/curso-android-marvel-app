package com.example.marvelapp.framework

import kotlinx.coroutines.flow.Flow
import me.davidsonsilva.core.data.repository.StorageLocalDataSource
import me.davidsonsilva.core.data.repository.StorageRepository
import javax.inject.Inject

class StorageRepositoryImpl @Inject constructor (
    private val storageLocalDataSource: StorageLocalDataSource
) :StorageRepository {

    override val sorting: Flow<String> get() = storageLocalDataSource.sorting

    override suspend fun saveSorting(sorting: String) {
        storageLocalDataSource.saveSorting(sorting)
    }
}