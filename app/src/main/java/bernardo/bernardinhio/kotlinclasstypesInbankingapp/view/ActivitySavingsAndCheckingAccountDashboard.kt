package bernardo.bernardinhio.kotlinclasstypesInbankingapp.view

import android.os.Bundle
import android.view.View
import android.view.WindowManager

import bernardo.bernardinhio.kotlinclasstypesInbankingapp.R
import bernardo.bernardinhio.kotlinclasstypesInbankingapp.data.SystemData

class ActivitySavingsAndCheckingAccountDashboard : ActivityDashboard() {

    var processAborted : Boolean = false
    val checkingAndSavingsAccount = SystemData.accountBothCheckingAndSavings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checking_and_savings_account_dashboard)
        setActivityDimensions()
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }

    override fun confirmLeavingActivity(): Boolean {
        processAborted = super.confirmLeavingActivity()
        return processAborted
    }

    // TODO here access the SavingsAccount and CheckingAccount methods
    // called by savingsAccount and checkingAccount Object
}
