package bernardo.bernardinhio.kotlinclasstypesInbankingapp.view

import android.os.Bundle
import android.view.WindowManager

import bernardo.bernardinhio.kotlinclasstypesInbankingapp.R
import bernardo.bernardinhio.kotlinclasstypesInbankingapp.data.SystemData

class ActivitySavingsAccountDashboard : ActivityDashboard() {

    var processAborted : Boolean = false

    val checkingAccount = SystemData.accountOnlyChecking
    val savingsAccount = SystemData.accountOnlySavings
    val checkingAndSavingsAccount = SystemData.accountBothCheckingAndSavings

    lateinit var ownerName : String
    lateinit var date : String
    lateinit var balance : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_savings_account_dashboard)
        setActivityDimensions()

        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        setupCheckingAccountFields()
    }

    override fun confirmLeavingActivity(): Boolean {
        processAborted = super.confirmLeavingActivity()
        return processAborted
    }

    private fun setupCheckingAccountFields() {
        savingsAccount?.run {
            ownerName = "$savingsAccount.owner.firstName ${savingsAccount.owner.lastName}"
            date = savingsAccount.dateCreated
            balance = savingsAccount.savingsBalance.toString()
        }
    }

    // TODO here access the SavingsAccount methods called by savingsAccount Object
}
