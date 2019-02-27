package bernardo.bernardinhio.kotlinclasstypesInbankingapp.view

import android.app.AlertDialog
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import bernardo.bernardinhio.kotlinclasstypesInbankingapp.R
import bernardo.bernardinhio.kotlinclasstypesInbankingapp.data.SystemData
import bernardo.bernardinhio.kotlinclasstypesInbankingapp.logic.*

class DashboardActivityBank : DashboardActivity() {

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_dashboard_bank)
        setActivityDimensions()

        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        etGlobalInterestRate =  findViewById(R.id.et_global_yearly_interest_rate)

        btnChangeGlobalInterest = findViewById<Button>(R.id.bt_change_bank_yearly_interest_rate)
        btnCreateClient = findViewById<Button>(R.id.create_client)
        btnCreateAccount = findViewById<Button>(R.id.create_account)

    }

    fun changeBankYearlyInterestRate(view : View){
        if (!etGlobalInterestRate.text.isEmpty()){
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Current Interest rate is: ${Account.yearlyInterestRate}\nSure change the bank Interest rate?")
                    .setMessage("This will affect all Accounts!")
                    .setPositiveButton("YES") { dialog, which ->
                        Account.yearlyInterestRate = etGlobalInterestRate.text.toString().toDouble()
                        etGlobalInterestRate.isEnabled = false
                    }
                    .setNeutralButton("CANCEL") {
                        dialog, which ->
                        builder.setCancelable(true)
                    }
                    .show()
        } else Toast.makeText(this, "You didn't enter new Yearly Interest rate", Toast.LENGTH_LONG).show()
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
                getNonEmptyEnteredOverdraftLimit()
        )
        //setters
        newAccount.type = AccountType.CHECKING
        newAccount.owner = this.newOwner
        newOwner.accountType = AccountType.CHECKING
        newOwner.account = this.newAccount
    }

    private fun setupSavingsAccount() {
        newAccount = SavingsAccount(
                getNonEmptyEnteredSavingsBalance(),
                Account.yearlyInterestRate)
        // setters
        newAccount.type = AccountType.SAVINGS
        newAccount.owner = this.newOwner
        newOwner.accountType = AccountType.SAVINGS
        newOwner.account = this.newAccount
    }

    private fun setupCheckingSavingsAccount() {
        // we can initialize using either CheckingAccount or SavingsAccount
        newAccount = CheckingAccount()
        newAccount.type = AccountType.CHECKING_AND_SAVINGS
        newAccount.checkingBalance = getNonEmptyEnteredCheckingBalance()
        newAccount.overdraftLimit = getNonEmptyEnteredOverdraftLimit()
        newAccount.remainingOverdraft = getNonEmptyEnteredOverdraftLimit()
        newAccount.savingsBalance = getNonEmptyEnteredSavingsBalance()
        // Theoretically we should not be able to set an interest rate because
        // banks have one Interest rate for all accounts, however for the
        // purpose of App demonstration we don't know when such interest rate
        // can be personalized according to customers wealth
        newAccount.yearlyInterestRate = fragmentCreateAccount.etYearlyInterestRate.text.toString().toDouble()
        newAccount.owner = this.newOwner
        newOwner.accountType = AccountType.CHECKING_AND_SAVINGS
        newOwner.account = this.newAccount
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
        if (areFieldsClientFilled && areFieldsAccountFilled) {
            when(newOwner.accountType){
                AccountType.CHECKING -> {
                    SystemData.ownerOnlyCheckingAccount = newOwner
                    SystemData.accountOnlyChecking = newAccount
                    SystemData.accountModified = AccountType.CHECKING
                }
                AccountType.SAVINGS -> {
                    SystemData.ownerOnlySavingsAccount = newOwner
                    SystemData.accountOnlySavings = newAccount
                    SystemData.accountModified = AccountType.SAVINGS
                }
                AccountType.CHECKING_AND_SAVINGS -> {
                    SystemData.ownerBothCheckingAndSavingsAccount = newOwner
                    SystemData.accountBothCheckingAndSavings = newAccount
                    SystemData.accountModified = AccountType.CHECKING_AND_SAVINGS
                }
            }
            this.finish()
        }
    }
}
