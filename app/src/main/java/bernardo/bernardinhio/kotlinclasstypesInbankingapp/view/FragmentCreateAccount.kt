package bernardo.bernardinhio.kotlinclasstypesInbankingapp.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RadioGroup
import bernardo.bernardinhio.kotlinclasstypesInbankingapp.R
import bernardo.bernardinhio.kotlinclasstypesInbankingapp.data.SystemData
import bernardo.bernardinhio.kotlinclasstypesInbankingapp.logic.CheckingAccount

class FragmentCreateAccount : Fragment(){

    lateinit var radioGroupAccountType : RadioGroup
    lateinit var etCheckingBalance : EditText
    lateinit var etOverdraftLimit : EditText
    lateinit var etSavingsBalance : EditText
    lateinit var etYearlyInterestRate : EditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val viewInflated : View = inflater.inflate(R.layout.fragment_create_account, container, false)
        initializeViews(viewInflated)
        setUiDefaultValues()
        return viewInflated
    }

    private fun initializeViews(viewInflated: View) {
        radioGroupAccountType = viewInflated.findViewById(R.id.rg_account_type)
        etCheckingBalance = viewInflated.findViewById(R.id.et_checking_balance)
        etOverdraftLimit = viewInflated.findViewById(R.id.et_overdraft_limit)
        etSavingsBalance = viewInflated.findViewById(R.id.et_savings_balance)
        etYearlyInterestRate = viewInflated.findViewById(R.id.et_yearly_interest_rate)
    }

    private fun setUiDefaultValues() {
        val defaultCheckingAccount = CheckingAccount()
        // it can also be SavingsAccount because the default values are set in
        // the abstract super class which we cannot initialize
        etCheckingBalance.setText(defaultCheckingAccount.checkingBalance.toString())
        etOverdraftLimit.setText(defaultCheckingAccount.overdraftLimit.toString())
        etSavingsBalance.setText(defaultCheckingAccount.savingsBalance.toString())
        etYearlyInterestRate.setText(SystemData.yearlyInterestRate.toString())
    }
}