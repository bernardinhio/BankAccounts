package bernardo.bernardinhio.kotlinclasstypesInbankingapp.view

import android.os.Bundle
import android.view.View

import bernardo.bernardinhio.kotlinclasstypesInbankingapp.R

class DashboardActivityClientOnlyCheckingAccount : DashboardActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_client_only_checking_account)
        setActivityDimensions()
    }

    fun getBalance(view : View){}

    fun getOverdraftLimit(view : View){}

    fun getRemainingOverdraft(view : View){}

    fun withdrawMoney(view : View){}

    // add first to complete the missing Overdraft then when done
    // add to checking
    fun addMoney(view : View){}

    // to only to other account Checking type
    fun transferToSomeone(view : View){}
}
