package bernardo.bernardinhio.kotlinclasstypesInbankingapp.view

import android.os.Bundle
import android.view.View

import bernardo.bernardinhio.kotlinclasstypesInbankingapp.R
import bernardo.bernardinhio.kotlinclasstypesInbankingapp.data.SystemData
import bernardo.bernardinhio.kotlinclasstypesInbankingapp.logic.Account

class DashboardActivityClientOnlySavingsAccount : DashboardActivity() {

    private val accountOnlySavings : Account = SystemData.accountOnlySavings!!
    private val accountBothCheckingAndSavings : Account = SystemData.accountBothCheckingAndSavings!!
    private val yearlyInterestRate : Double = accountOnlySavings.yearlyInterestRate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_client_savings_account)
        setActivityDimensions()
    }

    fun getBalance(view : View){}

    // both yearly or monthly (radio buttons)
    fun getInterest(view : View){}

    fun withdrawMoney(view : View){}

    fun addMoney(view : View){}

    // to only to other account Savings type
    fun transferToSomeone(view : View){}

}
