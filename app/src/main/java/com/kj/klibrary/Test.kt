package com.kj.klibrary

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.util.*
import java.util.regex.Pattern

fun main() {
//    val clazz = MainActivity::class.java
//    println(clazz.name)

//    val clazz2 = Class.forName("com.kj.klibrary.MainActivity")
//    println(clazz2.name)
//    //fields[]对应的属性字段
////    for (field in clazz2.fields) {
////        println(field.name)
////    }
//
//    val methods = clazz2.methods
//    for (method in methods) {
//        println(method.name)
//    }
    //newInstance()方法
//    try {
//        val titleItemClazz = TitleItem::class.java
//        val t = titleItemClazz.newInstance()
//        println(t)
//    } catch (e: Exception) {
//        e.printStackTrace()
//    }

//    val ssss = "夕拾小说牛批到炸#深度链接AE585SEXX#"
    val ssss = "##1BBA1B##"
    val result = pickCode(ssss)
    for (s in result) {
        println("测试口令 = $s")
    }

    val localDate = LocalDate.now()
    println("今天 = $localDate")
    println("year = ${localDate.year}  Month = ${localDate.monthValue}  Day = ${localDate.dayOfMonth}")

    val customD = LocalDate.of(2020, 9, 12)
    println("自定义日期 = $customD")
    print("判断日期是否相等：result = ${localDate == customD}")
}

//长按复制此条消息，下载并打开“小说免费书城”即可获得新用户神秘礼包#IVGD2VZPEU347b#
 fun pickCode(ss: String): List<String> {
//    val pattern = Pattern.compile("#\\u6df1\\u5ea6\\u94fe\\\\u6df1\\\\u5ea6\\\\u94fe\\u63a5([a-zA-Z0-9_]+)#")
    val pattern = Pattern.compile("#([a-zA-Z0-9_]+)#")
    val m = pattern.matcher(ss)
    val lst: MutableList<String> = ArrayList()
    while (m.find()) {
        lst.add(m.group(1))
    }
    return lst
}