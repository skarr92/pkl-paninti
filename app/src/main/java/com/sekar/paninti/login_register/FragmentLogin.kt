package com.sekar.paninti.login_register

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sekar.paninti.R
import com.sekar.paninti.forecast.ui.main.view.Weather
import com.sekar.paninti.databinding.FragmentLoginBinding

class FragmentLogin : Fragment() {

    private var isNullEmailOrUsername = false
    private var isNullPassword = false
    var validEmailOrUsername = false
    var validPassword = false

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view()
    }

    private fun view(){
        val spannable = SpannableStringBuilder(binding.tvSignUp.text.toString())
        val blueColor = ForegroundColorSpan(R.color.blue_500)
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val fragment = FragmentRegister()
                val transaction = fragmentManager?.beginTransaction()
                transaction?.replace(R.id.container,fragment)?.commit()
            }
        }
        spannable.setSpan(blueColor, 18, 33, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannable.setSpan(clickableSpan, 18, 33, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.tvSignUp.text = spannable
        binding.tvSignUp.movementMethod = LinkMovementMethod.getInstance()

        emailOrUsername()
        password()
        logIn()
    }

    private fun emailOrUsername(){
        binding.etEmailOrUsername.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (s?.length ?: 0 >= 1) {
                    clearEmailOrUsername()
                } else {
                    nullEmailOrUsername()
                }
            }
        })
    }

    private fun password(){
        binding.etPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (s?.length ?: 0 >= 1) {
                    clearPassword()
                } else {
                    nullPassword()
                }

            }
        })
    }

    private fun logIn(){
        binding.btnLogIn.setOnClickListener{
            validation()
        }
    }

    private fun validation(){
        nullCheck()
        validationTrue()
    }

    private fun validationTrue(){
        if(isNullEmailOrUsername() && isNullPassword() && validEmailOrUsername==true && validPassword==true) binding()
    }

    private fun binding(){
        val intent = Intent(activity, Weather::class.java)
        startActivity(intent)
    }

    private fun nullCheck(){
        isNullEmailOrUsername()
        isNullPassword()
    }

    private fun isNullEmailOrUsername(): Boolean{
        isNullEmailOrUsername = if (binding.etEmailOrUsername.length() == 0){
            nullEmailOrUsername()
            false
        } else {
            true
        }
        return isNullEmailOrUsername
    }

    private fun isNullPassword(): Boolean{
        isNullPassword = if (binding.etPassword.length() == 0){
            nullPassword()
            false
        } else {
            true
        }
        return isNullPassword
    }

    private fun nullEmailOrUsername(): Boolean {
        binding.tilEmailOrUsername.error = getString(R.string.null_email_username)
        binding.etEmailOrUsername.setBackgroundResource(R.drawable.bg_textbox_red)
        return false
    }

    private fun nullPassword(): Boolean{
        binding.tilPassword.error = getString(R.string.null_password)
        binding.etPassword.setBackgroundResource(R.drawable.bg_textbox_red)
        return false
    }

    private fun clearEmailOrUsername(){
        binding.tilEmailOrUsername.isErrorEnabled = false
        binding.etEmailOrUsername.setBackgroundResource(R.drawable.slr_textbox)
        validEmailOrUsername = true
    }

    private fun clearPassword(){
        binding.tilPassword.isErrorEnabled = false
        binding.etPassword.setBackgroundResource(R.drawable.slr_textbox)
        validPassword = true
    }
}
