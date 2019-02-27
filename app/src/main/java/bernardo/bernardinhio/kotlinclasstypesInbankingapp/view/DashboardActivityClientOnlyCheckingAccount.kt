package bernardo.bernardinhio.kotlinclasstypesInbankingapp.view

import android.os.Bundle
import android.view.View
import android.view.WindowManager

import bernardo.bernardinhio.kotlinclasstypesInbankingapp.R

class DashboardActivityClientOnlyCheckingAccount : DashboardActivity() {

    var processAborted : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_client_only_checking_account)
        setActivityDimensions()
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }

    override fun confirmLeavingActivity(): Boolean {
        processAborted = super.confirmLeavingActivity()
        return processAborted
    }

    // abstract
    fun getBalance(view : View){}

    // abstract // add to checking balance
    fun addMoney(view : View){}

    // superclass
    // according to type if only Checking then
    // withdraw from checking, if type is CheckingAndSavings
    // then offer possibility if not exist to withdraw from Savings
    fun withdrawMoney(view : View){}

    // superclass
    fun getOverdraftLimit(view : View){}

    // superclass
    fun getRemainingOverdraft(view : View){}

    // subclass transfer to only Account of type:
    // checking or CheckingAndSavings but to checking balance
    fun transferToSomeone(view : View){}
}
