package bernardo.bernardinhio.kotlinclasstypesInbankingapp.view

import android.app.AlertDialog
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

import bernardo.bernardinhio.kotlinclasstypesInbankingapp.R
import bernardo.bernardinhio.kotlinclasstypesInbankingapp.data.SystemData
import bernardo.bernardinhio.kotlinclasstypesInbankingapp.logic.*

class ActivityBankDashboard : ActivityDashboard() {

    var areFieldsClientFilled = false
    var areFieldsAccountFilled = false

    lateinit var etGlobalInterestRate : EditText
    lateinit var btnChangeGlobalInterest : Button
    lateinit var btnCreateClient : Button
    lateinit var btnCreateAccount : Button

    var newOwner : Owner = Owner()
    lateinit var newAccount : Account

    val fragmentCreateClient: FragmentCreateClient = FragmentCreateClient()
    val fragmentCreateAccount: FragmentCreateAccount = FragmentCreateAccount()

    var processAborted : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_bank_dashboard)
        setActivityDimensions()

        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        findViewById<TextView>(R.id.page_title).text = "The Bank"
        etGlobalInterestRate =  findViewById(R.id.et_global_yearly_interest_rate)

        btnChangeGlobalInterest = findViewById<Button>(R.id.bt_change_bank_yearly_interest_rate)
        btnCreateClient = findViewById<Button>(R.id.create_client)
        btnCreateAccount = findViewById<Button>(R.id.create_account)

    }

    override fun confirmLeavingActivity(): Boolean {
        processAborted = super.confirmLeavingActivity()
        return processAborted
    }

    fun changeBankYearlyInterestRate(view : View){
        if (!etGlobalInterestRate.text.isEmpty()){
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Current Interest yearlyRate is: ${Account.standardYearlyInterestRate}\nSure change the bank Interest yearlyRate?")
                    .setMessage("This will affect all Accounts!")
                    .setPositiveButton("YES") { dialog, which ->
                        Account.standardYearlyInterestRate = etGlobalInterestRate.text.toString().toDouble()
                        etGlobalInterestRate.isEnabled = false
                    }
                    .setNeutralButton("CANCEL") {
                        dialog, which ->
                        builder.setCancelable(true)
                    }
                    .show()
        } else Toast.makeText(this, "You didn't enter new Yearly Interest yearlyRate", Toast.LENGTH_LONG).show()
    }

    fun createClient(view : View){
        // if use has pressed on create client first,
        // then get entered data and update the Owner object
        if (areFieldsAccountFilled) {
            setupAccount()
            if (fragmentCreateAccount.radioGroupAccountType.checkedRadioButtonId != -1){
                updateAppAccountsAndOwners()
            } else Toast.makeText(this, "Select account Type", Toast.LENGTH_LONG).show()
        }
        if (!areFieldsClientFilled){
            hideButtonsBankMain()
            val fragmentManager: FragmentManager? = this.supportFragmentManager
            val fragmentTransaction: FragmentTransaction? = fragmentManager!!.beginTransaction()
            fragmentTransaction!!.replace(R.id.container_bank_input, fragmentCreateClient)
            fragmentTransaction.commit()
            areFieldsClientFilled = true
        }
    }

    fun createAccount(view : View){
        // if use has pressed on create account first,
        // then get entered data and update the Account Object
        if (areFieldsClientFilled){
            setupClient()
            updateAppAccountsAndOwners()
        }
        if (!areFieldsAccountFilled){
            hideButtonsBankMain()
            val fragmentManager: FragmentManager? = this.supportFragmentManager
            val fragmentTransaction: FragmentTransaction? = fragmentManager!!.beginTransaction()
            fragmentTransaction!!.replace(R.id.container_bank_input, fragmentCreateAccount)
            fragmentTransaction.commit()
            areFieldsAccountFilled = true
        }
    }

    private fun hideButtonsBankMain() {
        btnCreateClient.visibility = View.GONE
        btnCreateAccount.visibility = View.GONE
        etGlobalInterestRate.visibility = View.GONE
        btnChangeGlobalInterest.visibility = View.GONE
    }

    private fun setupClient() {
        // setters
        newOwner.firstName = getNonEmptyEnteredFirstName()
        newOwner.lastName = getNonEmptyEnteredLastName()
        newOwner.dateOfBirth = fragmentCreateClient.etDateOfBirth.text.toString()
        newOwner.nationality = fragmentCreateClient.etNationality.text.toString()
        newOwner.address = fragmentCreateClient.etAddress.text.toString()
        newOwner.occupation = fragmentCreateClient.etOccupation.text.toString()
    }

    private fun getNonEmptyEnteredFirstName() : String{
        return if(fragmentCreateClient.etFirstName.text.isEmpty()){
            "John*"
        } else fragmentCreateClient.etFirstName.text.toString()
    }

    private fun getNonEmptyEnteredLastName() : String{
        return if(fragmentCreateClient.etLastName.text.isEmpty()){
            "Denver*"
        } else fragmentCreateClient.etLastName.text.toString()
    }

    private fun setupAccount() {
        // we keep it not initialized (or abstract) until we know teh type
        // that the bank employee wishes
        when(fragmentCreateAccount.radioGroupAccountType.checkedRadioButtonId){
            R.id.rb_type_checking -> setupCheckingAccount()
            R.id.rb_type_savings -> setupSavingsAccount()
            R.id.rb_type_checking_and_savings -> setupCheckingSavingsAccount()
        }
    }

    private fun setupCheckingAccount() {
        newAccount = CheckingAccount(
                getNonEmptyEnteredCheckingBalance(),
                getNonEmptyEnteredOverdraftLimit(),
                getNonEmptyEnteredOverdraftLimit() // type // default is CHECKING
        )
        //setters
        newAccount.owner = newOwner
        newOwner.accountType = AccountType.CHECKING
        newOwner.account = newAccount
    }

    private fun setupSavingsAccount() {
        newAccount = SavingsAccount(
                getNonEmptyEnteredSavingsBalance(),
                Account.standardYearlyInterestRate // type // default is SAVINGS
        )
        // setters
        newAccount.owner = newOwner
        newOwner.accountType = AccountType.SAVINGS
        newOwner.account = newAccount
    }

    private fun setupCheckingSavingsAccount() {
        val checkingAccount : CheckingAccount
        val savingsAccount : SavingsAccount

        // create CheckingAccount of type CHECKING_AND_SAVINGS
        checkingAccount = CheckingAccount(
                getNonEmptyEnteredCheckingBalance(),
                getNonEmptyEnteredOverdraftLimit(),
                getNonEmptyEnteredOverdraftLimit(),
                AccountType.CHECKING_AND_SAVINGS, // default is CHECKING
                SavingsAccount()
        )
        // create SavingsAccount of type CHECKING_AND_SAVINGS
        savingsAccount = SavingsAccount(
                getNonEmptyEnteredSavingsBalance(),
                fragmentCreateAccount.etYearlyInterestRate.text.toString().toDouble(),
                AccountType.CHECKING_AND_SAVINGS, // default is SAVINGS
                checkingAccount
        )

        // each of checking and Savings accounts has dofferent IDs
        checkingAccount.owner = this.newOwner
        savingsAccount.owner = this.newOwner

        newAccount = checkingAccount
        //newAccount = savingsAccount // we can also because both are CHECKING_AND_SAVINGS

        newOwner.account = newAccount

        newOwner.accountType = AccountType.CHECKING_AND_SAVINGS
    }

    private fun getNonEmptyEnteredCheckingBalance() : Double{
        return if (fragmentCreateAccount.etCheckingBalance.text.isEmpty()){
            0.0
        } else fragmentCreateAccount.etCheckingBalance.text.toString().toDouble()
    }

    private fun getNonEmptyEnteredOverdraftLimit() : Double{
        return if (fragmentCreateAccount.etOverdraftLimit.text.isEmpty()){
            OverdraftLimitType.LIMIT_1000.limit
        } else fragmentCreateAccount.etOverdraftLimit.text.toString().toDouble()
    }

    private fun getNonEmptyEnteredSavingsBalance() : Double{
        return if (fragmentCreateAccount.etSavingsBalance.text.isEmpty()){
            0.0
        } else fragmentCreateAccount.etSavingsBalance.text.toString().toDouble()
    }

    private fun updateAppAccountsAndOwners(){
        if (areFieldsClientFilled && areFieldsAccountFilled && !processAborted) {
            when(newOwner.accountType){
                AccountType.CHECKING -> {
                    SystemData.ownerOnlyCheckingAccount = newOwner
                    SystemData.accountOnlyChecking = newAccount as CheckingAccount
                    SystemData.accountModified = AccountType.CHECKING
                }
                AccountType.SAVINGS -> {
                    SystemData.ownerOnlySavingsAccount = newOwner
                    SystemData.accountOnlySavings = newAccount as SavingsAccount
                    SystemData.accountModified = AccountType.SAVINGS
                }
                AccountType.CHECKING_AND_SAVINGS -> {
                    SystemData.ownerBothCheckingAndSavingsAccount = newOwner
                    SystemData.accountBothCheckingAndSavings = newAccount as CheckingAccount
                    SystemData.accountModified = AccountType.CHECKING_AND_SAVINGS
                }
            }
            this.finish()
        }
    }
}
