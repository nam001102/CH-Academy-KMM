package com.chacademy.android.Utils


expect class SPref

expect fun SPref.getInt(key: String) : Int
expect fun SPref.setInt(key: String, value: Int)
expect fun SPref.getString(key: String) : String
expect fun SPref.setString(key: String, value: String)