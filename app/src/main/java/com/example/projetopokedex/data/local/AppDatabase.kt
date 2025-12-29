package com.example.projetopokedex.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.projetopokedex.data.local.cards.UserCardDao
import com.example.projetopokedex.data.local.cards.UserCardEntity

@Database(
    entities = [UserCardEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userCardDao(): UserCardDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "pokedex.db"
                ).build().also { INSTANCE = it }
            }
    }
}