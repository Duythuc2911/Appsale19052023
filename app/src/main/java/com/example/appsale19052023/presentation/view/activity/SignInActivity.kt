package com.example.appsale19052023.presentation.view.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appsale19052023.R
import com.example.appsale19052023.data.api.AppResource
import com.example.appsale19052023.presentation.viewmodel.SignInViewModel
import com.example.appsale19052023.util.SpannedUtils
import com.example.appsale19052023.util.ToastUtils
import com.google.android.material.textfield.TextInputEditText

class SignInActivity : AppCompatActivity() {

    private lateinit var signInViewModel: SignInViewModel
    private lateinit var ediTextEmail: TextInputEditText
    private lateinit var ediTextPassword: TextInputEditText
    private lateinit var buttonSignIn: LinearLayout
    private lateinit var tvRegister: TextView
    private lateinit var layoutLoading: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
        signInViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory{
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return SignInViewModel(this@SignInActivity) as T
            }
        })[SignInViewModel::class.java]

        initView()
        observerData()
        event()

    }

    private fun observerData() {
        signInViewModel.getLoading().observe(this) {
            layoutLoading.isGone = !it
        }

        signInViewModel.getUser().observe(this) {
            when (it) {
                is AppResource.Success -> {
                    ToastUtils.showToast(this, "Login success!!!")
                    startActivity(Intent(this, ProductActivity::class.java))
                    finish()
                }
                is AppResource.Error -> ToastUtils.showToast(this, it.error)
            }
        }
    }

    private fun event() {
        buttonSignIn.setOnClickListener {
            val email = ediTextEmail.text.toString()
            val password = ediTextPassword.text.toString()
            if (email.isEmpty() || password.isEmpty()) {
                ToastUtils.showToast(this, "Input invalid!!!")
                return@setOnClickListener
            }

            signInViewModel.executeSignIn(email, password, this)
        }

        displayTextViewRegister()
    }

    private fun displayTextViewRegister() {
        SpannableStringBuilder().apply {
            append("Don't have an account?")
            append(SpannedUtils.setClickColorLink(
                text = "Register",
                context = this@SignInActivity,
                onListenClick = onClickRegister
            ))
            tvRegister.text = this
            tvRegister.highlightColor = Color.TRANSPARENT
            tvRegister.movementMethod = LinkMovementMethod.getInstance()
        }
    }

    private fun initView() {
        ediTextEmail = findViewById(R.id.text_edit_email)
        ediTextPassword = findViewById(R.id.text_edit_password)
        buttonSignIn = findViewById(R.id.button_sign_in)
        tvRegister = findViewById(R.id.text_view_register)
        layoutLoading = findViewById(R.id.layout_loading)
    }

    private var onClickRegister =  {
        startActivity(Intent(this@SignInActivity, RegisterActivity::class.java))
    }
}