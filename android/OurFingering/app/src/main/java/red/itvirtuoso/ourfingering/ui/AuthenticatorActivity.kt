package red.itvirtuoso.ourfingering.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.amazonaws.mobile.auth.core.IdentityHandler
import com.amazonaws.mobile.auth.core.IdentityManager
import com.amazonaws.mobile.auth.ui.SignInUI
import com.amazonaws.mobile.client.AWSMobileClient
import com.amazonaws.mobileconnectors.pinpoint.PinpointConfiguration
import com.amazonaws.mobileconnectors.pinpoint.PinpointManager

class AuthenticatorActivity : Activity() {
    companion object {
        private var pinpointManager: PinpointManager? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AWSMobileClient.getInstance().initialize(this) {
            val signInUI = AWSMobileClient.getInstance().getClient(
                    this@AuthenticatorActivity,
                    SignInUI::class.java) as SignInUI?
            signInUI?.login(
                    this@AuthenticatorActivity,
                    MainActivity::class.java)?.execute()
        }.execute()
//
//        with (AWSMobileClient.getInstance()) {
//            val config = PinpointConfiguration(this@AuthenticatorActivity, credentialsProvider, configuration)
//            pinpointManager = PinpointManager(config)
//        }
//
//        pinpointManager?.sessionClient?.startSession()
//        pinpointManager?.analyticsClient?.submitEvents()
//
        finish()
    }
}
