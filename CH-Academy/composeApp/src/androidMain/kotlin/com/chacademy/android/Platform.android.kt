package com.chacademy.android

import android.os.Build

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
    override val shortname: String = "Android"
}

actual fun getPlatform(): Platform = AndroidPlatform()