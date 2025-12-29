package com.example.projetopokedex.data.local.cards

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserCardDao {
    @Query("SELECT * FROM user_cards WHERE userEmail = :email ORDER BY createdAt DESC")
    fun getCardsForUser(email: String): Flow<List<UserCardEntity>>

    @Query("SELECT * FROM user_cards WHERE userEmail = :email")
    suspend fun getCardsForUserOnce(email: String): List<UserCardEntity>


    @Insert
    suspend fun insert(card: UserCardEntity)
}