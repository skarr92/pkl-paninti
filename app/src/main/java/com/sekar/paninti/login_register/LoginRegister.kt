package com.sekar.paninti.login_register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sekar.paninti.R

class LoginRegister : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_register)

        supportFragmentManager.beginTransaction().replace(R.id.container, FragmentLogin()).commit()
    }
}