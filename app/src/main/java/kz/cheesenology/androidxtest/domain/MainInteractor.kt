package kz.cheesenology.androidxtest.domain

import kz.cheesenology.androidxtest.room.tables.TestDao
import javax.inject.Inject

class MainInteractor @Inject constructor(private val repository: Repository) {

    fun insertRow() {
        repository.insertTest()
    }
}