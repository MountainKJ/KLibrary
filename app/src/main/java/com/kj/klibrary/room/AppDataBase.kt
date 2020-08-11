package com.kj.klibrary.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 101)
abstract class AppDataBase : RoomDatabase() {
    abstract fun userDao(): UserDao
}