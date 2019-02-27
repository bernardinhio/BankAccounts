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
                startAnimationClientContainer(containerClientOnlyCheckingAccount!!)
            }
            AccountType.SAVINGS -> {
                updateUiSavingsOnlyAccount()
                startAnimationClientContainer(containerClientOnlySavingsAccount!!)
            }
            AccountType.CHECKING_AND_SAVINGS -> {
                updateUiBothCheckingAndSavingsAccount()
                startAnimationClientContainer(containerClientBothCheckingAndSavingsAccount!!)
            }
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
        tvOwnerInfoOnlyChecking.text = "Owner: ${SystemData.ownerOnlyCheckingAccount?.firstName} ${SystemData.ownerOnlyCheckingAccount?.lastName}"
        tvAccountInfoOnlyChecking.text =
                "Checking Balance: ${SystemData.accountOnlyChecking?.checkingBalance} €" +
                "\nOverdraft Limit: ${SystemData.accountOnlyChecking?.overdraftLimit} €"
        tvConsoleMessages.text = tvConsoleMessages.text.toString() + "\nBank created Checking-only account"
    }

    private fun showAccountOnlyCheckingDetails(show : Boolean){
        tvOwnerInfoOnlyChecking.visibility = if (show) View.VISIBLE else View.GONE
        tvAccountInfoOnlyChecking.visibility = if (show) View.VISIBLE else View.GONE
        btnActionsOnlyCheckingAccount.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun updateUiSavingsOnlyAccount() {
        showAccountOnlySavingsDetails(true)
        tvOwnerInfoOnlySavings.text = "Owner: ${SystemData.ownerOnlySavingsAccount?.firstName} ${SystemData.ownerOnlySavingsAccount?.lastName}"
        tvAccountInfoOnlySavings.text = "Savings Balance: ${SystemData.accountOnlySavings?.savingsBalance} €" +
                "\n Interest/year: ${SystemData.accountOnlySavings?.yearlyInterestRate}%"
        tvConsoleMessages.text = tvConsoleMessages.text.toString() + "\nBank created Savings-only account"
    }

    private fun showAccountOnlySavingsDetails(show : Boolean){
        tvOwnerInfoOnlySavings.visibility = if (show) View.VISIBLE else View.GONE
        tvAccountInfoOnlySavings.visibility = if (show) View.VISIBLE else View.GONE
        btnActionsOnlySavingsAccount.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun updateUiBothCheckingAndSavingsAccount() {
        showAccountBothCheckingAndSavingsDetails(true)
        tvOwnerInfoBothSavingsAndChecking.text = "Owner: ${SystemData.ownerBothCheckingAndSavingsAccount?.firstName} ${SystemData.ownerBothCheckingAndSavingsAccount?.lastName}"
        tvAccountInfoBothSavingsAndChecking.text =
                "Checking Balance: ${SystemData.accountBothCheckingAndSavings?.checkingBalance} €" +
                        "\nOverdraft Limit: ${SystemData.accountBothCheckingAndSavings?.overdraftLimit} €" +
                        "\nSavings Balance: ${SystemData.accountBothCheckingAndSavings?.savingsBalance} €" +
                        "\nInterest/year: ${SystemData.accountBothCheckingAndSavings?.yearlyInterestRate}%"
        tvConsoleMessages.text = tvConsoleMessages.text.toString() + "\nBank created both Checking & Savings account"
    }

    private fun showAccountBothCheckingAndSavingsDetails(show : Boolean){
        tvOwnerInfoBothSavingsAndChecking.visibility = if (show) View.VISIBLE else View.GONE
        tvAccountInfoBothSavingsAndChecking.visibility = if (show) View.VISIBLE else View.GONE
        btnActionsBothSavingsAndCheckingAccount.visibility = if (show) View.VISIBLE else View.GONE
    }

    // whe, the 3 Ui components and 3 types of accounts are initialized
    // then don't show the Bank dashboard on click again but restart
    private fun setButtonDashboardBankClickListener(){
        btnDashboardBank.setOnClickListener {
            if(btnDashboardBank.tag.equals("openDashboardBank")){
                startActivity(Intent(this, DashboardActivityBank::class.java))
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
        startActivity(Intent(this, DashboardActivityClientOnlySavingsAccount::class.java))
    }

    fun openDashboardCheckingAccount(view: View) {
        startActivity(Intent(this, DashboardActivityClientOnlyCheckingAccount::class.java))
    }

    fun openDashboardSavingsAndCheckingAccount(view: View) {
        startActivity(Intent(this, DashboardActivityClientBothSavingsAndCheckingAccount::class.java))
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
