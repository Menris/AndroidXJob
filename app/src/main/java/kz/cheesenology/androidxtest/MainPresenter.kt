package kz.cheesenology.androidxtest

import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import kz.cheesenology.androidxtest.domain.MainInteractor
import kz.cheesenology.androidxtest.domain.Repository
import kz.cheesenology.androidxtest.work.SyncPeriodicWork
import kz.cheesenology.androidxtest.work.SyncWork
import java.util.Observer
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@InjectViewState
class MainPresenter @Inject constructor(private val interactor: MainInteractor) : MvpPresenter<MainView> (){

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showToast("Presenter Toast!!!")
    }

    fun testExactSchdule() {
        interactor.insertRow()
    }

    fun runJob() {
        //EXACT JOB
        /*val request = OneTimeWorkRequest.Builder(SyncWork::class.java).build()
        WorkManager.getInstance().enqueue(request)

        //EXACT JOB wit delay
        val requestDelay = OneTimeWorkRequest.Builder(SyncWork::class.java).setInitialDelay(3, TimeUnit.SECONDS).build()
        WorkManager.getInstance().enqueue(requestDelay)*/

        //PERIODIC UNIQUE REQUEST
        val requestUniquePeriodic = PeriodicWorkRequest.Builder(SyncPeriodicWork::class.java, 15, TimeUnit.MINUTES).build()
        WorkManager.getInstance().enqueueUniquePeriodicWork("PERIODIC", ExistingPeriodicWorkPolicy.KEEP, requestUniquePeriodic)
    }

    fun checkWorkManagerResult() {

    }
}
