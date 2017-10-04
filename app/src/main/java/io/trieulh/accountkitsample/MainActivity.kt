package io.trieulh.accountkitsample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.facebook.accountkit.AccountKit
import com.facebook.accountkit.ui.AccountKitActivity
import android.content.Intent
import android.util.Log
import com.facebook.accountkit.AccountKitLoginResult
import com.facebook.accountkit.ui.AccountKitConfiguration
import com.facebook.accountkit.ui.LoginType


class MainActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        val TAG = "ACCOUNTKIT-LOG"
        val APP_REQUEST_CODE = 99
    }

    private var mButtonLoginPhone: Button? = null
    private var mButtonLoginEmail: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        AccountKit.logOut()

        mButtonLoginPhone = findViewById(R.id.button_login_phone)
        mButtonLoginPhone?.setOnClickListener(this)

        mButtonLoginEmail = findViewById(R.id.button_login_email)
        mButtonLoginEmail?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.button_login_phone -> loginAKPhone()
            R.id.button_login_email -> loginAKEmail()
        }
    }

    private fun loginAKEmail() {
        val intent = Intent(this, AccountKitActivity::class.java)
        val configurationBuilder = AccountKitConfiguration.AccountKitConfigurationBuilder(
                LoginType.EMAIL,
                AccountKitActivity.ResponseType.TOKEN) // or .ResponseType.TOKEN
        // ... perform additional configuration ...
        intent.putExtra(
                AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,
                configurationBuilder.build())
        startActivityForResult(intent, APP_REQUEST_CODE)
    }

    private fun loginAKPhone() {
        val intent = Intent(this, AccountKitActivity::class.java)
        val configurationBuilder = AccountKitConfiguration.AccountKitConfigurationBuilder(
                LoginType.PHONE,
                AccountKitActivity.ResponseType.TOKEN) // or .ResponseType.TOKEN
        // ... perform additional configuration ...
        intent.putExtra(
                AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,
                configurationBuilder.build())
        startActivityForResult(intent, APP_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == APP_REQUEST_CODE) {
            val loginResult = data.getParcelableExtra<AccountKitLoginResult>(AccountKitLoginResult.RESULT_KEY)

            when {
                loginResult.error != null ->
                    Log.d(TAG, loginResult.error?.errorType?.message)
                loginResult.wasCancelled() ->
                    Log.d(TAG, "CLogin Cancelled")
                else ->
                    loginResult.accessToken?.let { it -> Log.d(TAG, "Access Token: " + it.token) } ?: Log.d(TAG, "Autho Token: " + loginResult.authorizationCode)
            }

        }
    }
}
