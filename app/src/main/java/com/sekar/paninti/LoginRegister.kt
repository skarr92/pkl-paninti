package com.sekar.paninti

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class LoginRegister : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_register)

        supportFragmentManager.beginTransaction().replace(R.id.container, FragmentLogin()).commit()
    }
}