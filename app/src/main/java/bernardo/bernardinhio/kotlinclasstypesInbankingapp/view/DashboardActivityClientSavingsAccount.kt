package bernardo.bernardinhio.kotlinclasstypesInbankingapp.view

import android.os.Bundle

import bernardo.bernardinhio.kotlinclasstypesInbankingapp.R

class DashboardActivityClientSavingsAccount : DashboardActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_client_savings_account)
        setActivityDimensions()
    }
}
