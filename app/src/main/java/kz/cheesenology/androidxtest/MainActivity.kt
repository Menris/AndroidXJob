package kz.cheesenology.androidxtest

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kz.cheesenology.androidxtest.moxy.MvpAppCompatActivity
import java.util.concurrent.TimeUnit
import com.arellomobile.mvp.presenter.PresenterType
import com.arellomobile.mvp.presenter.ProvidePresenter
import javax.inject.Inject


class MainActivity : MvpAppCompatActivity(), MainView {

    @Inject lateinit var presenter: MainPresenter
    @InjectPresenter lateinit var moxyPresenter: MainPresenter
    @ProvidePresenter fun providePresenter() = presenter

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        XApplication[this].component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GlobalScope.launch(Dispatchers.Main) {
            presenter.checkWorkManagerResult()
        }

        btnRunWork.setOnClickListener{
            presenter.runJob()
        }

        btnTest.setOnClickListener {
            presenter.testExactSchdule()
        }
    }

    override fun showToast(s: String) {
        Toast.makeText(this@MainActivity, s, Toast.LENGTH_LONG).show()
    }
}
