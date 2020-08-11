package com.kj.klibrary.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "first_name") val firstName: String?,
    @ColumnInfo(name = "last_name") val lastName: String?,
    @ColumnInfo(name = "sex") val sex: Int,
    @ColumnInfo(name = "age") val age: Int,
    @ColumnInfo(name = "address") val address: String?
) {
    override fun toString(): String {
        return "info = $uid $firstName$lastName 性别:${if(sex == 1) "男" else  "女"} 地址：$address"
    }
}