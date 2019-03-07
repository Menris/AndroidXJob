package kz.cheesenology.androidxtest.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import dagger.Module
import dagger.Provides
import kz.cheesenology.androidxtest.room.AppDatabase
import kz.cheesenology.androidxtest.room.tables.TestDao
import javax.inject.Singleton

@Module
class RoomModule(context: Context) {

    private val appDatabase: AppDatabase
    private val DB_NAME = "smremont"

    init {
        appDatabase = Room.databaseBuilder(
            context, AppDatabase::class.java,
            DB_NAME
        )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                }

                override fun onOpen(db: SupportSQLiteDatabase) {
                    super.onOpen(db)
                }
            })
            .build()
    }

    @Singleton
    @Provides
    internal fun providesRoomDatabase(): AppDatabase {
        return appDatabase
    }

    @Singleton
    @Provides
    internal fun provideTestDao(appDatabase: AppDatabase): TestDao {
        return appDatabase.testDao()
    }
}