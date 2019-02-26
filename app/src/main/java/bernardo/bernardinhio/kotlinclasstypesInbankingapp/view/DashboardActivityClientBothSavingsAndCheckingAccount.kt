package bernardo.bernardinhio.kotlinclasstypesInbankingapp.view

import android.os.Bundle
import android.view.View

import bernardo.bernardinhio.kotlinclasstypesInbankingapp.R

class DashboardActivityClientBothSavingsAndCheckingAccount : DashboardActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_client_both_checking_and_savings_account)
        setActivityDimensions()
    }

    fun getBalance(view : View){}

    // both yearly or monthly (radio buttons)
    fun getInterest(view : View){}

    // both Checking or Savings (radio button)
    fun withdrawMoney(view : View){}

    // both Checking or Savings (radio button)
    fun addMoney(view : View){}

    fun convertMoneyToChecking(view : View){}

    fun convertMoneyToSavings(view : View){}

    // to either: the only Savings and the only Checking (radio buttons)
    fun transferToSomeone(view : View){}
}
