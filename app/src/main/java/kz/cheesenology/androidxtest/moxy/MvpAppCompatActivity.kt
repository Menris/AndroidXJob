package kz.cheesenology.androidxtest.moxy

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.impl.WorkManagerImpl
import com.arellomobile.mvp.MvpDelegate
import com.google.android.material.snackbar.Snackbar
import kz.cheesenology.androidxtest.R
import kz.cheesenology.androidxtest.network.NetworkChangeReceiver
import kz.cheesenology.androidxtest.work.SyncWork

open class MvpAppCompatActivity : AppCompatActivity(),  NetworkChangeReceiver.ConnectivityReceiverListener {

    private var mMvpDelegate: MvpDelegate<out MvpAppCompatActivity>? = null

    private var mSnackBar: Snackbar? = null

    var receiver: BroadcastReceiver? = null

    val mvpDelegate: MvpDelegate<*>
        get() {
            if (mMvpDelegate == null) {
                mMvpDelegate = MvpDelegate(this)
            }
            return mMvpDelegate!!
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mvpDelegate.onCreate(savedInstanceState)

        receiver = NetworkChangeReceiver()

        registerReceiver(receiver,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
    }

    override fun onStart() {
        super.onStart()

        mvpDelegate.onAttach()
    }

    override fun onResume() {
        super.onResume()

        mvpDelegate.onAttach()

        NetworkChangeReceiver.connectivityReceiverListener = this

        val request = OneTimeWorkRequest.Builder(SyncWork::class.java).build()
        WorkManager.getInstance().enqueue(request)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        mvpDelegate.onSaveInstanceState(outState)
        mvpDelegate.onDetach()
    }

    override fun onStop() {
        super.onStop()

        mvpDelegate.onDetach()
    }

    override fun onDestroy() {
        super.onDestroy()

        mvpDelegate.onDestroyView()

        if (isFinishing) {
            mvpDelegate.onDestroy()
        }

        unregisterReceiver(receiver)
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        showMessage(isConnected)
    }

    @SuppressLint("WrongConstant")
    private fun showMessage(isConnected: Boolean) {
        if (!isConnected) {
            val messageToUser = "Сеть недоступна. Вы в режиме оффлайн"
            mSnackBar = Snackbar.make(findViewById(R.id.rootLayout), messageToUser, Snackbar.LENGTH_LONG)
            mSnackBar?.duration = Snackbar.LENGTH_INDEFINITE
            mSnackBar?.setAction("Закрыть") {
                mSnackBar?.dismiss()
            }
            mSnackBar?.show()
        } else {
            mSnackBar?.dismiss()
        }
    }
}
