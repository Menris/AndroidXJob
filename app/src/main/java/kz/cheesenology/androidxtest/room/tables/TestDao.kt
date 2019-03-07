package kz.cheesenology.androidxtest.room.tables

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface TestDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(testEntity: TestEntity)
}