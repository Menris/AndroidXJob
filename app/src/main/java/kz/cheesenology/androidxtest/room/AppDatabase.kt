package kz.cheesenology.androidxtest.room

import androidx.room.Database
import androidx.room.RoomDatabase
import kz.cheesenology.androidxtest.room.tables.TestDao
import kz.cheesenology.androidxtest.room.tables.TestEntity

@Database(entities = [TestEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun testDao(): TestDao
}