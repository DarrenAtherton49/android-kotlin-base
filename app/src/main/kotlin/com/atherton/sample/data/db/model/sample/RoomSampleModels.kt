package com.atherton.sample.data.db.model.sample

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

private const val ID = "id"

@Entity(
    tableName = "sample_model",
    indices = [
        Index(value = [ID], unique = false)
    ]
)
data class RoomSampleModel(

    @PrimaryKey @ColumnInfo(name = ID) val id: Long,
    @ColumnInfo(name = "name") val name: String?
)
