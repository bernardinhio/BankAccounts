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

class MainActivity : AppCompatActivity() {

    private var animationClientContainer: Animation? = null
    private var containerClientOnlyCheckingAccount: ConstraintLayout? = null
    private var containerClientOnlySavingsAccount: ConstraintLayout? = null
    private var containerClientBothCheckingAndSavingsAccount: ConstraintLayout? = null

    private var ownerInfoOnlySavings: TextView? = null
    private var ownerInfoOnlyChecking: TextView? = null
    private var ownerInfoBothSavingsAndChecking: TextView? = null
    private var accountInfoOnlySavings: TextView? = null
    private var accountInfoOnlyChecking: TextView? = null
    private var accountInfoBothSavingsAndChecking: TextView? = null
    private var consoleMessages: TextView? = null

    private var buttonActionOnlySavings: Button? = null
    private var buttonActionOnlyChecking: Button? = null
    private var buttonActionBothSavingsAndChecking: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeView()
        setupClientsContainersAnimation()
    }

    private fun initializeView() {
        ownerInfoOnlySavings = findViewById(R.id.owner_only_savings)
        ownerInfoOnlyChecking = findViewById(R.id.owner_only_checking)
        ownerInfoBothSavingsAndChecking = findViewById(R.id.owner_both_savings_and_checking)
        accountInfoOnlySavings = findViewById(R.id.account_only_savings)
        accountInfoOnlyChecking = findViewById(R.id.account_only_checking)
        accountInfoBothSavingsAndChecking = findViewById(R.id.account_both_savings_and_checking)
        consoleMessages = findViewById(R.id.tv_result)
        buttonActionOnlySavings = findViewById(R.id.bt_dashboard_savings)
        buttonActionOnlyChecking = findViewById(R.id.bt_dashboard_checking)
        buttonActionBothSavingsAndChecking = findViewById(R.id.bt_dashboard_savings_and_checking)
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
        when (SystemData.accountModified.type) {
            "checking" -> {
                updateUiCheckingOnlyAccount()
                startAnimationClientContainer(containerClientOnlyCheckingAccount!!)
            }
            "savings" -> {
                updateUiSavingsOnlyAccount()
                startAnimationClientContainer(containerClientOnlySavingsAccount!!)
            }
            "checking_and_savings" -> {
                updateUiBothCheckingAndSavingsAccount()
                startAnimationClientContainer(containerClientBothCheckingAndSavingsAccount!!)
            }
            else -> {
            }
        }
    }

    private fun updateUiCheckingOnlyAccount() {
        ownerInfoOnlyChecking!!.visibility = View.VISIBLE
        accountInfoOnlyChecking!!.visibility = View.VISIBLE
        buttonActionOnlyChecking!!.visibility = View.VISIBLE
        ownerInfoOnlyChecking!!.text = "Owner: " + SystemData.ownerOnlyCheckingAccount!!.firstName + " " + SystemData.ownerOnlyCheckingAccount!!.lastName
        accountInfoOnlyChecking!!.text = "Checking Balance: " + SystemData.accountOnlyChecking!!.checkingBalance
        consoleMessages!!.text = consoleMessages!!.text.toString() + "\nBank created Checking only account"
    }

    private fun updateUiSavingsOnlyAccount() {
        ownerInfoOnlySavings!!.visibility = View.VISIBLE
        accountInfoOnlySavings!!.visibility = View.VISIBLE
        buttonActionOnlySavings!!.visibility = View.VISIBLE
        ownerInfoOnlySavings!!.text = "Owner: " + SystemData.ownerOnlySavingsAccount!!.firstName + " " + SystemData.ownerOnlySavingsAccount!!.lastName
        accountInfoOnlySavings!!.text = "Savings Balance: " + SystemData.accountOnlySavings!!.savingsBalance
        consoleMessages!!.text = consoleMessages!!.text.toString() + "\nBank created Savings only account"
    }

    private fun updateUiBothCheckingAndSavingsAccount() {
        ownerInfoBothSavingsAndChecking!!.visibility = View.VISIBLE
        accountInfoBothSavingsAndChecking!!.visibility = View.VISIBLE
        buttonActionBothSavingsAndChecking!!.visibility = View.VISIBLE
        ownerInfoBothSavingsAndChecking!!.text = "Owner: " + SystemData.ownerBothCheckingAndSavingsAccount!!.firstName + " " + SystemData.ownerBothCheckingAndSavingsAccount!!.lastName
        accountInfoBothSavingsAndChecking!!.text = "Checking Balance: " + SystemData.accountBothCheckingAndSavings!!.checkingBalance + "\n" + "Savings Balance: " + SystemData.accountBothCheckingAndSavings!!.savingsBalance
        consoleMessages!!.text = consoleMessages!!.text.toString() + "\nBank created both Checking & Savings account"
    }


    fun openDashboardBank(view: View) {
        startActivity(Intent(this, DashboardActivityBank::class.java))
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
