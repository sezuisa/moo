package com.dhbw.heidenheim.haegele.moo

import android.app.Application
import io.realm.kotlin.mongodb.App
import io.realm.kotlin.mongodb.AppConfiguration

lateinit var app: App

class MooApp : Application() {

    override fun onCreate() {
        super.onCreate()
        app = App.create(
            AppConfiguration.Builder(getString(R.string.realm_app_id))
                .baseUrl(getString(R.string.realm_base_url))
                .build()
        )

    }

}