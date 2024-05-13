package com.chacademy.android.Utils


expect class SPref() {
    fun getInt(key: String): Int
    fun setInt(key: String, value: Int)
    fun getBoolean(key: String): Boolean
    fun setBoolean(key: String, value: Boolean)
    fun getString(key: String): String
    fun setString(key: String, value: String)
}