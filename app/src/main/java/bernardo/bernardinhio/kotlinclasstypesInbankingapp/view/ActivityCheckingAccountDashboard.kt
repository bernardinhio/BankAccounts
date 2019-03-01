package bernardo.bernardinhio.kotlinclasstypesInbankingapp.view

import android.os.Bundle
import android.view.View
import android.view.WindowManager

import bernardo.bernardinhio.kotlinclasstypesInbankingapp.R
import bernardo.bernardinhio.kotlinclasstypesInbankingapp.data.SystemData

class ActivityCheckingAccountDashboard : ActivityDashboard() {

    var processAborted : Boolean = false

    val checkingAccount = SystemData.accountOnlyChecking
    val savingsAccount = SystemData.accountOnlySavings
    val checkingAndSavingsAccount = SystemData.accountBothCheckingAndSavings

    lateinit var ownerName : String
    lateinit var date : String
    lateinit var balance : String
    lateinit var remainingMoney : String
    lateinit var withdrawLimit : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checking_account_dashboard)
        setActivityDimensions()

        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        setupCheckingAccountFields()
    }

    private fun setupCheckingAccountFields() {
        checkingAccount?.run {
            ownerName = "$checkingAccount.owner.firstName ${checkingAccount.owner.lastName}"
            date = checkingAccount.dateCreated
            balance = checkingAccount.checkingBalance.toString()
            remainingMoney = checkingAccount.remainingOverdraft.toString()
            withdrawLimit = checkingAccount.overdraftLimit.toString()
        }
    }

    override fun confirmLeavingActivity(): Boolean {
        processAborted = super.confirmLeavingActivity()
        return processAborted
    }

    // TODO here access the CheckingAccount methods called by checkingAccount Object
}
