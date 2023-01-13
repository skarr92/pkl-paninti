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

    private lateinit var binding: FragmentRegisterBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        val view = binding.root

        val spannable = SpannableStringBuilder(binding.tvMasuk.text.toString())
        val blueColor = ForegroundColorSpan(Color.parseColor("#4496B3"))
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val fragment = FragmentLogin()
                val transaction = fragmentManager?.beginTransaction()
                transaction?.replace(R.id.container,fragment)?.commit()
            }
        }
        spannable.setSpan(blueColor, 18, 32, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannable.setSpan(clickableSpan, 18, 32, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.tvMasuk.text = spannable
        binding.tvMasuk.movementMethod = LinkMovementMethod.getInstance()

        val minTwoCharRegex = "^.{2,}$"
        val minSixCharRegex = "^.{6,}$"
        val validPasswordRegex = "^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{6,}$"
        var validName = false
        var validUsername = false
        var validEmail = false
        var validPassword = false
        var validConfirmPassword = false

        binding.etNama.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (s.toString().matches(minTwoCharRegex.toRegex())){
                    binding.tilNama.isErrorEnabled = false
                    validName = true
                } else if (!(s?.length ?: 0 >= 1)){
                    binding.tilNama.error = "Nama lengkap wajib diisi"
                    validName = false
                } else {
                    binding.tilNama.error = "Nama lengkap minimal 2 karakter"
                    validName = false
                }
            }
        })

        binding.etUsername.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if(!(s?.length ?: 0 >= 1)){
                    binding.tilUsername.error = "Username wajib diisi"
                    validUsername = false
                } else  if (!(s.toString().matches("[a-zA-Z0-9._]+".toRegex()))) {
                    binding.tilUsername.error = "Username tidak bisa menggunakan simbol selain . dan _"
                    validUsername = false
                } else if (!(s.toString().matches(minSixCharRegex.toRegex()))){
                    binding.tilUsername.error = "Username minimal 6 karakter"
                    validUsername = false
                } else {
                    binding.tilUsername.isErrorEnabled = false
                    validUsername = true
                }
            }
        })

        binding.etEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (android.util.Patterns.EMAIL_ADDRESS.matcher(s).matches()) {
                    binding.tilEmail.isErrorEnabled = false
                    validEmail = true
                } else if (!(s?.length ?: 0 >= 1)){
                    binding.tilEmail.error = "Email wajib diisi"
                    validEmail = false
                } else {
                    binding.tilEmail.error = "Format email tidak sesuai"
                    validEmail = false
                }
            }
        })

        binding.etPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (!(s?.length ?: 0 >= 1)) {
                    binding.tilPassword.error = "Password wajib diisi"
                    validPassword = false
                } else if (!(s.toString().matches(minSixCharRegex.toRegex()))) {
                    binding.tilPassword.error = "Password minimal berisi berisi 6 karakter, 1 huruf kapital dan 1 angka"
                    validPassword = false
                } else if (!(s.toString().matches(validPasswordRegex.toRegex()))){
                    binding.tilPassword.error = "Password minimal 1 huruf kapital dan 1 angka"
                    validPassword = false
                } else {
                    binding.tilPassword.isErrorEnabled = false
                    validPassword = true
                }
            }
        })

            binding.etKonfirmasiPassword.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    if (s.toString() == binding.etPassword.text.toString()){
                        binding.tilKonfirmasiPassword.isErrorEnabled = false
                        validConfirmPassword = true
                    } else if (!(s?.length ?: 0 >= 1)) {
                        binding.tilKonfirmasiPassword.error = "Konfirmasi password wajib diisi"
                        validConfirmPassword = false
                    } else {
                        binding.tilKonfirmasiPassword.error = "Konfirmasi password tidak sesuai"
                        validConfirmPassword = false
                    }
                }
            })


        binding.btnDaftar.setOnClickListener{
            if(binding.etNama.text.toString() == ""){
                binding.tilNama.error = "Nama lengkap wajib diisi"
                validName = false
            }
            if(binding.etUsername.text.toString() == ""){
                binding.tilUsername.error = "Username wajib diisi"
                validUsername = false
            }
            if (binding.etEmail.text.toString() == ""){
                binding.tilEmail.error = "Email wajib diisi"
                validEmail = false
            }
            if (binding.etPassword.text.toString() == ""){
                binding.tilPassword.error = "Password wajib diisi"
                validPassword = false
            }
            if (binding.etKonfirmasiPassword.text.toString() == ""){
                binding.tilKonfirmasiPassword.error = "Konfirmasi password wajib diisi"
                validConfirmPassword = false
            }

            if(validName == true && validUsername == true && validEmail == true && validPassword == true && validConfirmPassword == true){
                val intent = Intent (getActivity(), MainActivity::class.java)
                getActivity()?.startActivity(intent)
            } else {

            }
        }
        return view
    }

    fun isValidEmail(target: CharSequence?): Boolean{
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

}
