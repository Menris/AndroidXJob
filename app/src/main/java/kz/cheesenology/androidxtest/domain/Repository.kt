package kz.cheesenology.androidxtest.domain

import kz.cheesenology.androidxtest.room.tables.TestDao
import kz.cheesenology.androidxtest.room.tables.TestEntity
import javax.inject.Inject

class Repository @Inject constructor(private val dao: TestDao) {

    fun insertTest() {
        dao.insert(TestEntity(testNum = 12, testName = "testname"))
    }
}