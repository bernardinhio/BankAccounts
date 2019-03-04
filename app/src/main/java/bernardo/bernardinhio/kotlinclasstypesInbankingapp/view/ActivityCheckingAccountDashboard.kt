package bernardo.bernardinhio.kotlinclasstypesInbankingapp.view

import android.os.Bundle
import android.text.Editable
import android.view.View
import android.view.WindowManager
import android.widget.*

import bernardo.bernardinhio.kotlinclasstypesInbankingapp.R
import bernardo.bernardinhio.kotlinclasstypesInbankingapp.data.SystemData
import bernardo.bernardinhio.kotlinclasstypesInbankingapp.logic.AccountType
import bernardo.bernardinhio.kotlinclasstypesInbankingapp.logic.CheckingAccount
import bernardo.bernardinhio.kotlinclasstypesInbankingapp.logic.SavingsAccount

class ActivityCheckingAccountDashboard : ActivityDashboard() {

    var processAborted : Boolean = false

    val thisCheckingAccount : CheckingAccount? = SystemData.accountOnlyChecking
    val appSavingsAccount : SavingsAccount? = SystemData.accountOnlySavings
    val appCheckingAndSavingsAccount : CheckingAccount? = SystemData.accountBothCheckingAndSavings

    // UI views
    lateinit var tvPageTitle : TextView
    lateinit var tvAccountInfo : TextView
    lateinit var tvBalance : TextView
    lateinit var tvDashboardFeedback : TextView
    lateinit var etInputMoneyToAdd : EditText
    lateinit var rgAddTo : RadioGroup
    lateinit var btConfirm_add : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checking_account_dashboard)
        setActivityDimensions()

        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        initializeUiView()
        setUiInitialData()
    }

    override fun confirmLeavingActivity(): Boolean {
        processAborted = super.confirmLeavingActivity()
        return processAborted
    }

    private fun initializeUiView(){
        tvPageTitle = findViewById(R.id.page_title)
        tvAccountInfo = findViewById(R.id.tv_account_info)
        tvBalance = findViewById(R.id.tv_balance)
        tvDashboardFeedback = findViewById(R.id.tv_dashboard_feedback)
        etInputMoneyToAdd = findViewById(R.id.et_input_money_to_add)
        rgAddTo = findViewById(R.id.rg_add_to)
        btConfirm_add = findViewById(R.id.bt_confirm_add)
    }

    private fun setUiInitialData(){
        tvPageTitle.text = "Checking Account"
        tvAccountInfo.text = "${thisCheckingAccount?.owner?.firstName} ${thisCheckingAccount?.owner?.lastName} \n ${thisCheckingAccount?.dateCreated}"
    }


    fun getBalance(view : View){
        if (tvBalance.visibility == View.GONE){
            tvBalance.visibility = View.VISIBLE
            tvBalance.text = "${thisCheckingAccount?.getBalance()}€"
            tvDashboardFeedback.text = tvDashboardFeedback.text.toString() + "-> Got balance "
        } else {
            tvBalance.setText("")
            tvBalance.visibility = View.GONE
        }
    }

    fun addMoney(view : View){
        if (etInputMoneyToAdd.visibility == View.GONE){
            showAddMoneyFields()
        } else {
            hideAddMoneyFields()
        }
    }

    private fun showAddMoneyFields(){
        etInputMoneyToAdd.visibility = View.VISIBLE
        rgAddTo.visibility = View.VISIBLE
        btConfirm_add.visibility = View.VISIBLE
    }

    private fun hideAddMoneyFields(){
        etInputMoneyToAdd.setText("")
        etInputMoneyToAdd.visibility = View.GONE
        rgAddTo.visibility = View.GONE
        btConfirm_add.visibility = View.GONE
    }

    fun confirmAddMoney(view : View){
        if (!etInputMoneyToAdd.text.isEmpty()){
            when(rgAddTo.checkedRadioButtonId){
                R.id.add_to_checking -> {
                    val isAdded = thisCheckingAccount?.addMoney(
                            etInputMoneyToAdd.text.toString().toDouble(),
                            AccountType.CHECKING
                    )
                    if (isAdded!!){
                        tvDashboardFeedback.text = tvDashboardFeedback.text.toString() + "-> Added money "
                        hideAddMoneyFields()
                    }
                }
                R.id.add_to_savings -> {
                    val isAdded = thisCheckingAccount?.addMoney(
                            etInputMoneyToAdd.text.toString().toDouble(),
                            AccountType.SAVINGS
                    )
                    if (isAdded!!) {
                        tvDashboardFeedback.text = tvDashboardFeedback.text.toString() + "-> Added money "
                        hideAddMoneyFields()
                    }
                }
                else -> Toast.makeText(this, "Select To account", Toast.LENGTH_LONG).show()
            }
        } else Toast.makeText(this, "Enter money", Toast.LENGTH_LONG).show()
    }


    /** INHERITANCE  STRATEGY

     --- common -- abstract
     getBalance
     addMoney
     withdrawMoney
     transferMoneyToSomeone
     convertMoneyFromMyCheckingToMySavings
     convertMoneyFromMySavingsToMyChecking

     --- common -- Non-abstract
    --> not overriden in CheckingAccount
    --> IS overriden in SavingsAccount
    getDetailsOfYearlyInterestRate

    ----- specific method > CheckingAccount
     getOverdraftStatus

     ----- specific method > SavingsAccount
     getBenefitFromInterest

     */


}
