package bernardo.bernardinhio.kotlinclasstypesInbankingapp.view

import android.app.Activity
import android.os.Bundle

import bernardo.bernardinhio.kotlinclasstypesInbankingapp.R

class DashboardActivityClientSavingsAndCheckingAccount : DashboardActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard_activity_client_savings_and_checking_account)
        setActivityDimensions()
    }
}
