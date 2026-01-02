package com.example.projetopokedex.data.local.cards

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_cards")
data class UserCardEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userEmail: String,
    val pokemonId: Int,
    val pokemonName: String,
    val type: String,
    val hp: Int,
    val attackName1: String?,
    val attackPower1: Int?,
    val attackName2: String?,
    val attackPower2: Int?,
    val imageUrl: String?,
    val cardNumber: Int,
    val createdAt: Long
)