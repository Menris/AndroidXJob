package kz.cheesenology.androidxtest.di

import android.content.Context
import dagger.Component
import kz.cheesenology.androidxtest.MainActivity
import kz.cheesenology.androidxtest.room.AppDatabase
import kz.cheesenology.androidxtest.room.tables.TestDao
import kz.cheesenology.androidxtest.work.SyncPeriodicWork
import kz.cheesenology.androidxtest.work.SyncWork
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, RetrofitModule::class, RoomModule::class])
interface AppComponent {

    //APP
    fun getContext(): Context
    fun getAppDatabase(): AppDatabase

    fun testDao(): TestDao

    fun inject(mainActivity: MainActivity)
    fun inject(mainActivity: SyncWork)
    fun inject(syncPeriodicWork: SyncPeriodicWork)
}