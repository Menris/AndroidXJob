package kz.cheesenology.androidxtest.room.tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "test_tab")
data class TestEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Int? = null,
    @ColumnInfo(name = "test_num") var testNum: Int,
    @ColumnInfo(name = "test_name") var testName: String
)