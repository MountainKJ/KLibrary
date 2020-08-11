package com.kj.klibrary.room

import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.blankj.utilcode.util.Utils

class RoomHelper private constructor() {

    companion object{
        @Volatile private var instance : RoomHelper? = null
        fun get(): RoomHelper = instance ?: synchronized(this){
            instance ?: RoomHelper().also {
                instance = it
            }
        }

        private const val DB_NAME = "kLibrary.db"
    }

    val MIGRATION_101_102 = object : Migration(101,102) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("")
        }
    }

    val db = Room.databaseBuilder(Utils.getApp(), AppDataBase::class.java, DB_NAME)
//        .addMigrations(MIGRATION_101_102)
        .build()

}