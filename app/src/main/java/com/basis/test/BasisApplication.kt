package com.basis.test

import android.app.Application
import io.realm.Realm

class BasisApplication:Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }
}