package com.kj.klibrary.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao{
    @Insert
    suspend fun insertAll(vararg users: User)

    @Delete
    suspend fun delete(user: User)

    @Query("select * from user")
    suspend fun getAll(): List<User>

    @Query("select * from user where uid in (:userIds)")
    suspend fun loadAllByIds(userIds: IntArray): List<User>

    @Query("select * from user where first_name like :first and last_name like :last limit 1")
    fun findByName(first: String, last: String): User
}