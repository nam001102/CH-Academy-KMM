package com.chacademy.android

interface Platform {
    val name: String
    val shortname: String
}

expect fun getPlatform(): Platform
