package com.atherton.sample.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.atherton.sample.data.db.dao.SampleDao
import com.atherton.sample.data.db.model.sample.RoomSampleModel
import com.atherton.sample.util.extension.ioThread

@Database(
    entities = [
        RoomSampleModel::class
    ],
    version = 1
)
@TypeConverters(RoomTypeConverters::class)
abstract class RoomDb : RoomDatabase() {

    abstract fun getSampleDao(): SampleDao

    companion object {
        private const val ROOM_DB_NAME = "sample_database"

        @Volatile private var INSTANCE: RoomDb? = null

        fun getInstance(context: Context, useInMemory: Boolean): RoomDb {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context, useInMemory).also { INSTANCE = it }
            }
        }

        private fun buildDatabase(context: Context, useInMemory: Boolean): RoomDb {
            val databaseBuilder = if(useInMemory) {
                Room.inMemoryDatabaseBuilder(context, RoomDb::class.java)
            } else {
                Room.databaseBuilder(context, RoomDb::class.java, ROOM_DB_NAME)
            }
            return databaseBuilder
                .fallbackToDestructiveMigration()
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        ioThread {
                            prePopulateDatabase(getInstance(context, useInMemory))
                        }
                    }
                })
                .build()
        }

        private fun prePopulateDatabase(roomDb: RoomDb) {

        }
    }
}
