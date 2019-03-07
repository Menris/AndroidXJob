package kz.cheesenology.androidxtest

import android.app.Activity
import android.app.Application
import com.facebook.stetho.Stetho
import kz.cheesenology.androidxtest.di.AppComponent
import kz.cheesenology.androidxtest.di.ApplicationModule
import kz.cheesenology.androidxtest.di.DaggerAppComponent
import kz.cheesenology.androidxtest.di.RoomModule

class XApplication : Application() {

    private lateinit var applicationComponent: AppComponent

    val component: AppComponent
        get() = applicationComponent

    override fun onCreate() {
        super.onCreate()

        Stetho.initializeWithDefaults(this)

        applicationComponent = DaggerAppComponent.builder()
            .applicationModule(ApplicationModule(applicationContext))
            .roomModule(RoomModule(applicationContext))
            .build()
    }

    companion object {
        operator fun get(activity: Activity): XApplication {
            return activity.application as? XApplication ?: throw IllegalStateException()
        }
        //var applicationComponent: AppComponent? = null
    }
}