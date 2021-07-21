package com.karis.facebooklogin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import java.util.*


class MainActivity : AppCompatActivity() {

    private val callbackManager = CallbackManager.Factory.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val loginButton = findViewById<LoginButton>(R.id.login_button);
        loginButton.setReadPermissions(listOf(Companion.EMAIL))

        FacebookSdk.setApplicationId(resources.getString(R.string.facebook_app_id))
        FacebookSdk.sdkInitialize(this)

        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult?> {
                override fun onSuccess(loginResult: LoginResult?) {
                    // App code
                    val accessToken = loginResult?.accessToken?.token
                    Log.i(Companion.TAG, "onSuccess: $accessToken")

                }

                override fun onCancel() {
                    // App code
                }

                override fun onError(exception: FacebookException) {
                    // App code
                }
            })

        //This is just to attempt a login when oncreate is called, you can also call this in a button onclickListener
        launchLogin()

    }

    private fun launchLogin() {

        if (!isLoggedIn()) {
            login()
        }
    }

    private fun login() {
        LoginManager.getInstance().logInWithReadPermissions(this, listOf("public_profile", "instagram_basic", "pages_show_list"))
    }

    private fun isLoggedIn(): Boolean {
        val accessToken = getCurrentAccessToken()
        return accessToken != null && !accessToken.isExpired
    }

    private fun getCurrentAccessToken(): AccessToken? {
        return  AccessToken.getCurrentAccessToken()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    companion object {
        private const val TAG = "MainActivity"
        private const val EMAIL = "email"
    }


}