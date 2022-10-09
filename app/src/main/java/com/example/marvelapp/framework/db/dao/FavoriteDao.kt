package com.example.marvelapp.framework.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.marvelapp.framework.db.entity.FavoriteEntity
import kotlinx.coroutines.flow.Flow
import me.davidsonsilva.core.data.DbConstants

@Dao
interface FavoriteDao {

    @Query("SELECT * from ${DbConstants.FAVORITES_TABLE_NAME}")
    fun loadFavorites(): Flow<List<FavoriteEntity>>

    @Query("SELECT * from ${DbConstants.FAVORITES_TABLE_NAME} where id = :characterId")
    suspend fun hasFavorite(characterId: Int) :FavoriteEntity?

    @Insert(onConflict = REPLACE)
    suspend fun insertFavorite(favoriteEntity: FavoriteEntity)

    @Delete
    suspend fun deleteFavorite(favoriteEntity: FavoriteEntity)
}