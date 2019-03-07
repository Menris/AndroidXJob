package kz.cheesenology.androidxtest.work

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import kz.cheesenology.androidxtest.XApplication
import kz.cheesenology.androidxtest.network.NetworkApi
import kz.cheesenology.androidxtest.room.tables.TestDao
import kz.cheesenology.androidxtest.room.tables.TestEntity
import kz.cheesenology.androidxtest.util.PreferenceHelper
import kz.cheesenology.androidxtest.util.PreferenceHelper.defaultPrefs
import kz.cheesenology.androidxtest.util.PreferenceHelper.get
import kz.cheesenology.androidxtest.util.PreferenceHelper.set
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SyncWork(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {

    @Inject
    lateinit var networkApi: NetworkApi

    @Inject
    lateinit var dao: TestDao

    var sp: SharedPreferences = defaultPrefs(applicationContext)

    override fun doWork(): Result {

        (applicationContext as? XApplication)!!.component.inject(this)
    
        return try {
            val previousSyncDate: Long? = sp[PreferenceHelper.SYNC_TIME]
            var diff: Long = 0

            if (previousSyncDate != null) {
                diff = TimeUnit.MILLISECONDS.toMinutes(Date().time - previousSyncDate)
            }

            if (diff > 0) {
                val response = networkApi.testRequest("kuan", "kuan").execute()
                if (response.isSuccessful) {
                    Log.e("WORK_MANAGER EXACT: ", "SUCCESS")
                    dao.insert(TestEntity(testName = "work", testNum = 505))
                    sp[PreferenceHelper.SYNC_TIME] = Date().time
                    Result.success()
                } else {
                    Log.e("WORK_MANAGER EXACT: ", "FAIL")
                    Result.retry()
                    //Result.failure()
                }
            } else {
                Log.e("WORK_MANAGER EXACT: ", "TIME DIFF")
                Result.failure()
            }
        } catch (th: Throwable) {
            th.printStackTrace()
            Result.failure()
        }
    }
}