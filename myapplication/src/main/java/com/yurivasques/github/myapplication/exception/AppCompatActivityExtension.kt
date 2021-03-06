package com.yurivasques.github.myapplication.exception

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun AppCompatActivity.addFragment(containerViewId: Int, fragment: Fragment) {
    supportFragmentManager.beginTransaction().replace(containerViewId, fragment).commit()
}

fun AppCompatActivity.getLongExtra(key: String): Long = intent!!.extras!!.getLong(key)

fun AppCompatActivity.getBooleanExtra(key: String): Boolean = intent!!.extras!!.getBoolean(key)

fun AppCompatActivity.getStringExtra(key: String): String = intent!!.extras!!.getString(key)!!