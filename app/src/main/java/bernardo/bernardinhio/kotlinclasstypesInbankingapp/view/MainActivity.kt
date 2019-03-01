package bernardo.bernardinhio.kotlinclasstypesInbankingapp.view

import android.content.Intent
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView

import bernardo.bernardinhio.kotlinclasstypesInbankingapp.R
import bernardo.bernardinhio.kotlinclasstypesInbankingapp.data.SystemData
import bernardo.bernardinhio.kotlinclasstypesInbankingapp.logic.AccountType

class MainActivity : AppCompatActivity() {

    lateinit var btnDashboardBank : Button
    lateinit var animationClientContainer: Animation
    lateinit var containerClientOnlyCheckingAccount: ConstraintLayout
    lateinit var containerClientOnlySavingsAccount: ConstraintLayout
    lateinit var containerClientBothCheckingAndSavingsAccount: ConstraintLayout

    lateinit var tvOwnerInfoOnlySavings: TextView
    lateinit var tvOwnerInfoOnlyChecking: TextView
    lateinit var tvOwnerInfoBothSavingsAndChecking: TextView
    lateinit var tvAccountInfoOnlySavings: TextView
    lateinit var tvAccountInfoOnlyChecking: TextView
    lateinit var tvAccountInfoBothSavingsAndChecking: TextView
    lateinit var tvConsoleMessages: TextView
    lateinit var btnActionsOnlySavingsAccount: Button
    lateinit var btnActionsOnlyCheckingAccount: Button
    lateinit var btnActionsBothSavingsAndCheckingAccount: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeView()
        setButtonDashboardBankClickListener()
        setupClientsContainersAnimation()
    }

    private fun initializeView() {
        btnDashboardBank = findViewById(R.id.bt_dashboard_bank)
        tvOwnerInfoOnlySavings = findViewById(R.id.owner_only_savings)
        tvOwnerInfoOnlyChecking = findViewById(R.id.owner_only_checking)
        tvOwnerInfoBothSavingsAndChecking = findViewById(R.id.owner_both_savings_and_checking)
        tvAccountInfoOnlySavings = findViewById(R.id.account_only_savings)
        tvAccountInfoOnlyChecking = findViewById(R.id.account_only_checking)
        tvAccountInfoBothSavingsAndChecking = findViewById(R.id.account_both_savings_and_checking)
        tvConsoleMessages = findViewById(R.id.tv_result)
        btnActionsOnlySavingsAccount = findViewById(R.id.bt_dashboard_savings)
        btnActionsOnlyCheckingAccount = findViewById(R.id.bt_dashboard_checking)
        btnActionsBothSavingsAndCheckingAccount = findViewById(R.id.bt_dashboard_savings_and_checking)
    }

    /**
     * when any of the 4 dashboard Activities are launched while the
     * parent activity MainActivity is partially visible, then the
     * life-cycle method "onPause()" is launched, the when the daughter
     * Activity is destroyed (of of the 4 Activities) then the "onResume()"
     * of the MainActivity is launched again.
     * So here is the place to use to get the data modified in the data
     * provider which is the static class SystemData
     */
    override fun onResume() {
        super.onResume()
        when (SystemData.accountModified) {
            AccountType.CHECKING -> {
                updateUiCheckingOnlyAccount()
                SystemData.accountModified = AccountType.UNDEFINED
                startAnimationClientContainer(containerClientOnlyCheckingAccount)
            }
            AccountType.SAVINGS -> {
                updateUiSavingsOnlyAccount()
                SystemData.accountModified = AccountType.UNDEFINED
                startAnimationClientContainer(containerClientOnlySavingsAccount)
            }
            AccountType.CHECKING_AND_SAVINGS -> {
                updateUiBothCheckingAndSavingsAccount()
                SystemData.accountModified = AccountType.UNDEFINED
                startAnimationClientContainer(containerClientBothCheckingAndSavingsAccount)
            }
            else -> {}
        }
        // if all 3 components are created then don't allow Bank dashboard
        // to create new ones but instead allow her to rest all data
        if(SystemData.accountOnlyChecking != null && SystemData.accountOnlySavings != null && SystemData.accountBothCheckingAndSavings != null){
            btnDashboardBank.tag = "restartActivity"
            btnDashboardBank.text = "Restart"
        }
    }

    private fun updateUiCheckingOnlyAccount() {
        showAccountOnlyCheckingDetails(true)
        tvOwnerInfoOnlyChecking.text = "${SystemData.ownerOnlyCheckingAccount?.firstName} ${SystemData.ownerOnlyCheckingAccount?.lastName}"
        tvAccountInfoOnlyChecking.text =
                "Checking: ${SystemData.accountOnlyChecking?.checkingBalance}€" +
                "\nLimit: ${SystemData.accountOnlyChecking?.overdraftLimit}€"
        tvConsoleMessages.text = "\nBank created Checking-only account --> at: ${SystemData.accountOnlyChecking?.dateCreated}"
    }

    private fun showAccountOnlyCheckingDetails(show : Boolean){
        tvOwnerInfoOnlyChecking.visibility = if (show) View.VISIBLE else View.INVISIBLE
        tvAccountInfoOnlyChecking.visibility = if (show) View.VISIBLE else View.INVISIBLE
        btnActionsOnlyCheckingAccount.visibility = if (show) View.VISIBLE else View.INVISIBLE
    }

    private fun updateUiSavingsOnlyAccount() {
        showAccountOnlySavingsDetails(true)
        tvOwnerInfoOnlySavings.text = "${SystemData.ownerOnlySavingsAccount?.firstName} ${SystemData.ownerOnlySavingsAccount?.lastName}"
        tvAccountInfoOnlySavings.text = "Savings: ${SystemData.accountOnlySavings?.savingsBalance}€" +
                "\n Interest/year: ${SystemData.accountOnlySavings?.yearlyInterestRate}%"
        tvConsoleMessages.text = "\nBank created Savings-only account --> at: ${SystemData.accountOnlySavings?.dateCreated}"
    }

    private fun showAccountOnlySavingsDetails(show : Boolean){
        tvOwnerInfoOnlySavings.visibility = if (show) View.VISIBLE else View.INVISIBLE
        tvAccountInfoOnlySavings.visibility = if (show) View.VISIBLE else View.INVISIBLE
        btnActionsOnlySavingsAccount.visibility = if (show) View.VISIBLE else View.INVISIBLE
    }

    private fun updateUiBothCheckingAndSavingsAccount() {
        showAccountBothCheckingAndSavingsDetails(true)
        tvOwnerInfoBothSavingsAndChecking.text = "${SystemData.ownerBothCheckingAndSavingsAccount?.firstName} ${SystemData.ownerBothCheckingAndSavingsAccount?.lastName}"
        tvAccountInfoBothSavingsAndChecking.text =
                "Checking: ${SystemData.accountBothCheckingAndSavings?.checkingBalance}€" +
                        "\nLimit: ${SystemData.accountBothCheckingAndSavings?.overdraftLimit}€" +
                        "\nSavings: ${SystemData.accountBothCheckingAndSavings?.savingsAccount?.savingsBalance}€" +
                        "\nInterest/year: ${SystemData.accountBothCheckingAndSavings?.savingsAccount?.yearlyInterestRate}%"
        tvConsoleMessages.text = "\nBank created both Checking & Savings account --> at: ${SystemData.accountBothCheckingAndSavings?.dateCreated}"
    }

    private fun showAccountBothCheckingAndSavingsDetails(show : Boolean){
        tvOwnerInfoBothSavingsAndChecking.visibility = if (show) View.VISIBLE else View.INVISIBLE
        tvAccountInfoBothSavingsAndChecking.visibility = if (show) View.VISIBLE else View.INVISIBLE
        btnActionsBothSavingsAndCheckingAccount.visibility = if (show) View.VISIBLE else View.INVISIBLE
    }

    // whe, the 3 Ui components and 3 types of accounts are initialized
    // then don't show the Bank dashboard on click again but restart
    private fun setButtonDashboardBankClickListener(){
        btnDashboardBank.setOnClickListener {
            if(btnDashboardBank.tag.equals("openDashboardBank")){
                startActivity(Intent(this, ActivityBankDashboard::class.java))
            } else if(btnDashboardBank.tag.equals("restartActivity")){
                restartAppSimulation()
            }
        }
    }

    private fun restartAppSimulation() {
        containerClientOnlySavingsAccount.clearAnimation()
        containerClientOnlyCheckingAccount.clearAnimation()
        containerClientBothCheckingAndSavingsAccount.clearAnimation()
        btnDashboardBank.tag = "openDashboardBank"
        btnDashboardBank.text = "Start"
        SystemData.resetData()
        showAccountOnlyCheckingDetails(false)
        showAccountOnlySavingsDetails(false)
        showAccountBothCheckingAndSavingsDetails(false)
        tvConsoleMessages.text = ""
    }

    fun openDashboardSavingsAccount(view: View) {
        startActivity(Intent(this, ActivitySavingsAccountDashboard::class.java))
    }

    fun openDashboardCheckingAccount(view: View) {
        startActivity(Intent(this, ActivityCheckingAccountDashboard::class.java))
    }

    fun openDashboardSavingsAndCheckingAccount(view: View) {
        startActivity(Intent(this, ActivitySavingsAndCheckingAccountDashboard::class.java))
    }

    fun setupClientsContainersAnimation() {
        containerClientOnlyCheckingAccount = findViewById(R.id.client_with_only_checking_account)
        containerClientOnlySavingsAccount = findViewById(R.id.client_with_only_savings_account)
        containerClientBothCheckingAndSavingsAccount = findViewById(R.id.client_with_both_savings_and_checking_account)

        animationClientContainer = AnimationUtils.loadAnimation(
                applicationContext,
                R.anim.anim_notify_container_changed)
    }

    private fun startAnimationClientContainer(container: ConstraintLayout) {
        container.startAnimation(animationClientContainer)
    }
}
