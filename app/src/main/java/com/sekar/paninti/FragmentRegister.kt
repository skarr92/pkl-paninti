package com.sekar.paninti

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.*
import android.text.Selection.setSelection
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.sekar.paninti.databinding.FragmentRegisterBinding

class FragmentRegister : Fragment() {

    private var isNullFullName = false
    private var isNullUsername = false
    private var isNullEmail = false
    private var isNullPassword = false
    private var isNullConfirmPassword = false

    val minTwoCharRegex = "^.{2,}$"
    val minSixCharRegex = "^.{6,}$"
    val passwordRegex ="^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{6,}$"
    val fullNameRegex = "[A-Za-z '-]+"
    val usernameRegex = "[a-zA-Z0-9._]+"
    var validName = false
    var validUsername = false
    var validEmail = false
    var validPassword = false
    var validConfirmPassword = false


    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view()
    }

    private fun view(){
        val spannable = SpannableStringBuilder(binding.tvLogin.text.toString())
        val blueColor = ForegroundColorSpan(R.color.blue_500)

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val fragment = FragmentLogin()
                val transaction = fragmentManager?.beginTransaction()
                transaction?.replace(R.id.container, fragment)?.commit()
            }
        }
        spannable.setSpan(blueColor, 18, 32, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannable.setSpan(clickableSpan, 18, 32, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.tvLogin.text = spannable
        binding.tvLogin.movementMethod = LinkMovementMethod.getInstance()

        fullName()
        username()
        email()
        password()
        confirmPassword()
        signUp()
    }

    private fun fullName(){
        binding.etFullName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (!(s?.length ?: 0 >= 1)) {
                    nullFullName()
                } else if (!(s.toString().matches(fullNameRegex.toRegex()))) {
                    regexFullName()
                } else if (!(s.toString().matches(minTwoCharRegex.toRegex()))) {
                    regexMinFullname()
                } else {
                    clearFullName()
                }
            }
        })
    }

    private fun username(){
        binding.etUsername.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (!(s?.length ?: 0 >= 1)) {
                    nullUsername()
                } else if (!(s.toString().matches(usernameRegex.toRegex()))) {
                    regexUsername()
                } else if (!(s.toString().matches(minSixCharRegex.toRegex()))) {
                    regexMinUsername()
                } else {
                    clearUsername()
                }
            }
        })
    }

    private fun email(){
        binding.etEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (regexEmail(s)) {
                    clearEmail()
                } else if (!(s?.length ?: 0 >= 1)) {
                    nullEmail()
                } else {
                    regexEmailResult()
                }
            }
        })
    }

    private fun password(){
        binding.etPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (!(s?.length ?: 0 >= 1)) {
                    nullPassword()
                } else if (!(s.toString().matches(minSixCharRegex.toRegex()))) {
                    regexMinPassword()
                } else if (!(s.toString().matches(passwordRegex.toRegex()))) {
                    regexPassword()
                } else {
                    clearPassword()
                }
            }
        })
    }

    private fun confirmPassword(){
        binding.etConfirmPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (s.toString() == binding.etPassword.text.toString()) {
                    clearConfirmPassword()
                } else if (!(s?.length ?: 0 >= 1)) {
                    nullConfirmPassword()
                } else {
                    regexConfirmPassword()
                }
            }
        })

    }

    private fun signUp(){
        binding.btnSignUp.setOnClickListener{
            validation()
        }
    }

    private fun validation(){
        nullCheck()
        validationTrue()
    }

    private fun validationTrue(){
        if(isNullFullName() && isNullUsername() && isNullEmail() && isNullPassword() && isNullConfirmPassword()
            && validName==true && validUsername==true && validEmail==true && validPassword==true && validConfirmPassword==true)
            binding()
    }

    private fun binding(){
        val intent = Intent(activity, Weather::class.java)
        startActivity(intent)
    }

    private fun nullCheck(){
        isNullFullName()
        isNullUsername()
        isNullEmail()
        isNullPassword()
        isNullConfirmPassword()
    }

    private fun isNullFullName(): Boolean{
        isNullFullName = if (binding.etFullName.length() == 0){
            nullFullName()
            false
        } else {
            true
        }
        return isNullFullName
    }

    private fun isNullUsername(): Boolean{
        isNullUsername = if (binding.etUsername.length() == 0){
            nullUsername()
            false
        } else {
            true
        }
        return isNullUsername
    }

    private fun isNullEmail(): Boolean{
        isNullEmail = if (binding.etEmail.length() == 0){
            nullEmail()
            false
        } else {
            true
        }
        return isNullEmail
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

    private fun isNullConfirmPassword(): Boolean{
        isNullConfirmPassword = if (binding.etConfirmPassword.length() == 0){
            nullConfirmPassword()
            false
        } else {
            true
        }
        return isNullConfirmPassword
    }


    private fun nullFullName(): Boolean {
        binding.tilFullName.error = getString(R.string.null_full_name)
        binding.etFullName.setBackgroundResource(R.drawable.bg_textbox_red)
        return false
    }

    private fun nullUsername(): Boolean{
        binding.tilUsername.error = getString(R.string.null_username)
        binding.etUsername.setBackgroundResource(R.drawable.bg_textbox_red)
        return false
    }

    private fun nullEmail(): Boolean{
        binding.tilEmail.error = getString(R.string.null_email)
        binding.etEmail.setBackgroundResource(R.drawable.bg_textbox_red)
        return false
    }

    private fun nullPassword(): Boolean{
        binding.tilPassword.error = getString(R.string.null_password)
        binding.etPassword.setBackgroundResource(R.drawable.bg_textbox_red)
        return false
    }

    private fun nullConfirmPassword(): Boolean{
        binding.tilConfirmPassword.error = getString(R.string.null_confirm_password)
        binding.etConfirmPassword.setBackgroundResource(R.drawable.bg_textbox_red)
        return false
    }

    private fun regexMinFullname(){
        binding.tilFullName.error = getString(R.string.min_full_name)
        binding.etFullName.setBackgroundResource(R.drawable.bg_textbox_red)
        validName = false
    }

    private fun regexFullName(){
        binding.tilFullName.error = getString(R.string.valid_full_name)
        binding.etFullName.setBackgroundResource(R.drawable.bg_textbox_red)
        validName = false
    }

    private fun regexMinUsername(){
        binding.tilUsername.error = getString(R.string.min_username)
        binding.etUsername.setBackgroundResource(R.drawable.bg_textbox_red)
        validUsername = false
    }

    private fun regexUsername(){
        binding.tilUsername.error = getString(R.string.valid_username)
        binding.etUsername.setBackgroundResource(R.drawable.bg_textbox_red)
        validUsername = false
    }

    private fun regexEmail(target: CharSequence?): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    private fun regexEmailResult(){
        binding.tilEmail.error = getString(R.string.valid_email)
        binding.etEmail.setBackgroundResource(R.drawable.bg_textbox_red)
        validEmail = false
    }

    private fun regexMinPassword(){
        binding.tilPassword.error = getString(R.string.min_password)
        binding.etPassword.setBackgroundResource(R.drawable.bg_textbox_red)
        validPassword = false
    }

    private fun regexPassword(){
        binding.tilPassword.error = getString(R.string.valid_password)
        binding.etPassword.setBackgroundResource(R.drawable.bg_textbox_red)
        validPassword = false
    }

    private fun regexConfirmPassword(){
        binding.tilConfirmPassword.error = getString(R.string.valid_confirm_password)
        binding.etConfirmPassword.setBackgroundResource(R.drawable.bg_textbox_red)
        validConfirmPassword = false
    }

    private fun clearFullName(){
        binding.tilFullName.isErrorEnabled = false
        binding.etFullName.setBackgroundResource(R.drawable.slr_textbox)
        validName = true
    }

    private fun clearUsername(){
        binding.tilUsername.isErrorEnabled = false
        binding.etUsername.setBackgroundResource(R.drawable.slr_textbox)
        validUsername = true
    }

    private fun clearEmail(){
        binding.tilEmail.isErrorEnabled = false
        binding.etEmail.setBackgroundResource(R.drawable.slr_textbox)
        validEmail = true
    }

    private fun clearPassword(){
        binding.tilPassword.isErrorEnabled = false
        binding.etPassword.setBackgroundResource(R.drawable.slr_textbox)
        validPassword = true
    }

    private fun clearConfirmPassword(){
        binding.tilConfirmPassword.isErrorEnabled = false
        binding.etConfirmPassword.setBackgroundResource(R.drawable.slr_textbox)
        validConfirmPassword = true
    }

}
