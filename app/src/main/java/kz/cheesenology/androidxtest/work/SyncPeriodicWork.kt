package kz.cheesenology.androidxtest.work

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import kz.cheesenology.androidxtest.XApplication
import kz.cheesenology.androidxtest.network.NetworkApi
import kz.cheesenology.androidxtest.room.tables.TestDao
import kz.cheesenology.androidxtest.room.tables.TestEntity
import kz.cheesenology.androidxtest.util.PreferenceHelper
import kz.cheesenology.androidxtest.util.PreferenceHelper.set
import java.util.*
import javax.inject.Inject

class SyncPeriodicWork(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {

    @Inject
    lateinit var networkApi: NetworkApi

    @Inject
    lateinit var dao: TestDao

    override fun doWork(): Result {

        (applicationContext as? XApplication)!!.component.inject(this)

        return try {
            val response = networkApi.testRequest("kuan", "kuan").execute()
            if (response.isSuccessful) {
                Log.e("WORK_MANAGER PERIODIC: ", "SUCCESS")
                dao.insert(TestEntity(testName = "work", testNum = 505))
                val sp = PreferenceHelper.defaultPrefs(applicationContext)
                sp[PreferenceHelper.SYNC_PERIODIC_TIME] = Date().toString()
                Result.success()
            } else {
                Log.e("WORK_MANAGER PERIODIC: ", "FAIL")
                Result.retry()
                //Result.failure()
            }
        } catch (th: Throwable) {
            th.printStackTrace()
            Result.failure()
        }
    }
}