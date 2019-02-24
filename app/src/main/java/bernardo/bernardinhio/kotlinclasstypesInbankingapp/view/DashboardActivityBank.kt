package bernardo.bernardinhio.kotlinclasstypesInbankingapp.view

import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.view.View
import android.widget.Button

import bernardo.bernardinhio.kotlinclasstypesInbankingapp.R
import bernardo.bernardinhio.kotlinclasstypesInbankingapp.logic.*

class DashboardActivityBank : DashboardActivity() {

    var areFieldsClientFilled = false
    var areFieldsAccountFilled = false

    lateinit var buttonCreateClient : Button
    lateinit var buttonCreateAccount : Button

    lateinit var owner : Owner
    lateinit var account : Account

    val fragmentCreateClient: FragmentCreateClient = FragmentCreateClient()
    val fragmentCreateAccount: FragmentCreateAccount = FragmentCreateAccount()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard_activity_bank)
        setActivityDimensions()

        buttonCreateClient = findViewById<Button>(R.id.create_client)
        buttonCreateAccount = findViewById<Button>(R.id.create_account)

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
    }

    private fun setupClient() {
        owner = Owner(
                fragmentCreateClient.etFirstName.text.toString(),
                fragmentCreateClient.etLastName.text.toString(),
                fragmentCreateClient.etDateOfBirth.text.toString(),
                fragmentCreateClient.etNationality.text.toString(),
                fragmentCreateClient.etAddress.text.toString(),
                fragmentCreateClient.etOccupation.text.toString()
        )
    }

    private fun setupAccount() {
        when(fragmentCreateAccount.radioGroupAccountType.checkedRadioButtonId){
            R.id.rb_type_checking ->
                setupCheckingAccount()
            R.id.rb_type_savings ->
                setupSavingsAccount()
            R.id.rb_type_checking_and_savings ->
                setupCheckingSavingsAccount()
        }
    }


    private fun setupCheckingAccount() {
        account = CheckingAccount(
                fragmentCreateAccount.etCheckingBalance.text.toString().toDouble(),
                fragmentCreateAccount.etOverdraftLimit.text.toString().toDouble()
        )
        //setters
        account.type = AccountType.CHECKING
    }

    private fun setupSavingsAccount() {
        account = SavingsAccount(
                fragmentCreateAccount.etSavingsBalance.text.toString().toDouble(),
                fragmentCreateAccount.etYearlyInterestRate.text.toString().toDouble()
        )
        // setters
        account.type = AccountType.SAVINGS
    }

    private fun setupCheckingSavingsAccount() {
        // we can initialize using either CheckingAccount or SavingsAccount
        account = CheckingAccount()
        account.type = AccountType.SAVINGS_AND_CHECKING
        account.checkingBalance = fragmentCreateAccount.etCheckingBalance.text.toString().toDouble()
        account.overdraftLimit = fragmentCreateAccount.etOverdraftLimit.text.toString().toDouble()
        account.savingsBalance = fragmentCreateAccount.etSavingsBalance.text.toString().toDouble()
        account.yearlyInterestRate = fragmentCreateAccount.etYearlyInterestRate.text.toString().toDouble()
    }


    private fun updateAppAccountsAndOwners(){
        this.finish()
    }

}
