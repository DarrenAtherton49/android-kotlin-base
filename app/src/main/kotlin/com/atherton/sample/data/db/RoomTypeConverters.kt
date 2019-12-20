package com.atherton.sample.data.db

import androidx.room.TypeConverter

internal class RoomTypeConverters {

    @TypeConverter
    fun stringToListOfStrings(string: String?): List<String>? {
        return string.takeIf { !it.isNullOrBlank() }
            ?.split(",")
            ?.fold(listOf()) { list, s ->
                list + s
            }
    }

    @TypeConverter
    fun listOfStringsToString(stringList: List<String>?): String? {
        return stringList?.joinToString(separator = ",")
    }

    @TypeConverter
    fun stringToListOfInts(value: String?): List<Int>? {
        val list = mutableListOf<Int>()
        value.takeIf { !it.isNullOrBlank() }
            ?.split(",")
            ?.map { integerAsString: String ->
                list.add(integerAsString.toInt())
            }
        return list
    }

    @TypeConverter
    fun listOfIntsToString(value: List<Int>?): String? {
        return value?.joinToString(separator = ",")
    }
}
