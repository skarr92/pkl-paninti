package com.sekar.paninti

import android.content.Intent
import android.graphics.Color
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
import com.sekar.paninti.databinding.FragmentLoginBinding

class FragmentLogin : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        val view = binding.root

        val spannable = SpannableStringBuilder(binding.tvDaftar.text.toString())
        val blueColor = ForegroundColorSpan(Color.parseColor("#4496B3"))
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val fragment = FragmentRegister()
                val transaction = fragmentManager?.beginTransaction()
                transaction?.replace(R.id.container,fragment)?.commit()
            }
        }
        spannable.setSpan(blueColor, 18, 33, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannable.setSpan(clickableSpan, 18, 33, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.tvDaftar.text = spannable
        binding.tvDaftar.movementMethod = LinkMovementMethod.getInstance()

        binding.etEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (s?.length ?: 0 >= 1) {
                    binding.tilEmail.isErrorEnabled = false
                } else {
                    binding.tilEmail.error = "Email atau Username wajib diisi"
                }
            }
        })

        binding.etPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (s?.length ?: 0 >= 1) {
                    binding.tilPassword.isErrorEnabled = false
                } else {
                    binding.tilPassword.error = "Password wajib diisi"
                }

            }
        })

        binding.btnMasuk.setOnClickListener{
            if(binding.etEmail.text.toString() == ""){
                binding.tilEmail.error = "Email atau Username wajib diisi"
            }
            if(binding.etPassword.text.toString() == ""){
                binding.tilPassword.error = "Pasword wajib diisi"
            }
            if (binding.tilEmail.isErrorEnabled == false && binding.tilPassword.isErrorEnabled == false){
                val intent = Intent (getActivity(), MainActivity::class.java)
                getActivity()?.startActivity(intent)
            } else {

            }
        }
        return view
    }
}