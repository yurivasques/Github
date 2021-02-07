package com.yurivasques.github.presentation

import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
    }

    fun search(view: View) {
        val userName = findViewById<EditText>(R.id.editTextUserName)?.text.toString()
        val intent = Intent(this, SecondFragment::class.java).apply{
            putExtra(EXTRA_MESSAGE, userName)
        }
        startActivity(intent)
    }
}