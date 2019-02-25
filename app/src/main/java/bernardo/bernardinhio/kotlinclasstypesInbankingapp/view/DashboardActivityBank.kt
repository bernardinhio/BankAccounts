package bernardo.bernardinhio.kotlinclasstypesInbankingapp.view

import android.app.AlertDialog
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText

import bernardo.bernardinhio.kotlinclasstypesInbankingapp.R
import bernardo.bernardinhio.kotlinclasstypesInbankingapp.data.SystemData
import bernardo.bernardinhio.kotlinclasstypesInbankingapp.logic.*

class DashboardActivityBank : DashboardActivity() {

    var areFieldsClientFilled = false
    var areFieldsAccountFilled = false

    lateinit var globalInterestRate : EditText
    lateinit var buttonChangeGlobalInterest : Button
    lateinit var buttonCreateClient : Button
    lateinit var buttonCreateAccount : Button

    var newOwner : Owner = Owner()
    lateinit var newAccount : Account

    val fragmentCreateClient: FragmentCreateClient = FragmentCreateClient()
    val fragmentCreateAccount: FragmentCreateAccount = FragmentCreateAccount()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        setContentView(R.layout.activity_dashboard_bank)
        setActivityDimensions()

        globalInterestRate =  findViewById(R.id.et_global_yearly_interest_rate)

        buttonChangeGlobalInterest = findViewById<Button>(R.id.bt_change_bank_yearly_interest_rate)
        buttonCreateClient = findViewById<Button>(R.id.create_client)
        buttonCreateAccount = findViewById<Button>(R.id.create_account)

    }

    fun changeBankYearlyInterestRate(view : View){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Current Interest rate is: ${SystemData.yearlyInterestRate}\nSure change the bank Interest rate?")
                .setMessage("This will affect all Accounts!")
                .setPositiveButton("YES") { dialog, which ->
                    if (!globalInterestRate.text.isEmpty()){
                        SystemData.yearlyInterestRate = globalInterestRate.text.toString().toDouble()
                        globalInterestRate.isEnabled = false
                    }
                }
                .setNeutralButton("CANCEL") {
                    dialog, which ->
                    builder.setCancelable(true)
                }
                .show()
    }

    fun createClient(view : View){
        // if use has pressed on create client first,
        // then get entered data and update the Owner object
        if (areFieldsAccountFilled) {
            setupAccount()
            updateAppAccountsAndOwners()
        }
        if (!areFieldsClientFilled){
            hideButtonsCreate()
            val fragmentManager: FragmentManager? = this.supportFragmentManager
            val fragmentTransaction: FragmentTransaction? = fragmentManager!!.beginTransaction()
            fragmentTransaction!!.replace(R.id.container_bank_input, fragmentCreateClient)
            fragmentTransaction.commit() // synchronized
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
            hideButtonsCreate()
            val fragmentManager: FragmentManager? = this.supportFragmentManager
            val fragmentTransaction: FragmentTransaction? = fragmentManager!!.beginTransaction()
            fragmentTransaction!!.replace(R.id.container_bank_input, fragmentCreateAccount)
            fragmentTransaction.commit() // synchronized
            areFieldsAccountFilled = true
        }
    }

    private fun hideButtonsCreate() {
        buttonCreateClient.visibility = View.GONE
        buttonCreateAccount.visibility = View.GONE
        globalInterestRate.visibility = View.GONE
        buttonChangeGlobalInterest.visibility = View.GONE
    }

    private fun setupClient() {
        // setters
        newOwner.firstName = fragmentCreateClient.etFirstName.text.toString()
        newOwner.lastName = fragmentCreateClient.etLastName.text.toString()
        newOwner.dateOfBirth = fragmentCreateClient.etDateOfBirth.text.toString()
        newOwner.nationality = fragmentCreateClient.etNationality.text.toString()
        newOwner.address = fragmentCreateClient.etAddress.text.toString()
        newOwner.occupation = fragmentCreateClient.etOccupation.text.toString()
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
                fragmentCreateAccount.etCheckingBalance.text.toString().toDouble(),
                fragmentCreateAccount.etOverdraftLimit.text.toString().toDouble()
        )
        //setters
        newAccount.type = AccountType.CHECKING
        newAccount.owner = this.newOwner
        newOwner.accountType = AccountType.CHECKING
        newOwner.account = this.newAccount
    }

    private fun setupSavingsAccount() {
        newAccount = SavingsAccount(
                fragmentCreateAccount.etSavingsBalance.text.toString().toDouble(),
                fragmentCreateAccount.etYearlyInterestRate.text.toString().toDouble()
        )
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
        newAccount.checkingBalance = fragmentCreateAccount.etCheckingBalance.text.toString().toDouble()
        newAccount.overdraftLimit = fragmentCreateAccount.etOverdraftLimit.text.toString().toDouble()
        newAccount.savingsBalance = fragmentCreateAccount.etSavingsBalance.text.toString().toDouble()
        // Theoretically we should not be able to set an interest rate because
        // banks have one Interest rate for all accounts, however for the
        // purpose of App demonstration we don't know when such interest rate
        // can be personalized according to customers wealth
        newAccount.yearlyInterestRate = fragmentCreateAccount.etYearlyInterestRate.text.toString().toDouble()
        newAccount.owner = this.newOwner
        newOwner.accountType = AccountType.CHECKING_AND_SAVINGS
        newOwner.account = this.newAccount
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

    /**
    override fun onDestroy() {
        super.onDestroy()

        MainActivity.startAnimationClientContainer(
                MainActivity.containerClientOnlyCheckingAccount
        )

        MainActivity.startAnimationClientContainer(
                MainActivity.containerClientOnlySavingsAccount
        )

        MainActivity.startAnimationClientContainer(
                MainActivity.containerClientBothCheckingAndSavingsAccount
        )

    }
     **/

}
