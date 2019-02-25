package bernardo.bernardinhio.kotlinclasstypesInbankingapp.view

import android.os.Bundle
import android.view.View

import bernardo.bernardinhio.kotlinclasstypesInbankingapp.R

class DashboardActivityClientCheckingAccount : DashboardActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_client_checking_account)
        setActivityDimensions()
    }

}
