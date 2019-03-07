package kz.cheesenology.androidxtest

import com.arellomobile.mvp.MvpView

interface MainView : MvpView{
    fun showToast(s: String)

}
