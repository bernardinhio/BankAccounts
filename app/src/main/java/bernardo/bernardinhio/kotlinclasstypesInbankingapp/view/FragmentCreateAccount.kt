package bernardo.bernardinhio.kotlinclasstypesInbankingapp.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import bernardo.bernardinhio.kotlinclasstypesInbankingapp.R
import bernardo.bernardinhio.kotlinclasstypesInbankingapp.data.SystemData
import bernardo.bernardinhio.kotlinclasstypesInbankingapp.logic.CheckingAccount
import bernardo.bernardinhio.kotlinclasstypesInbankingapp.logic.OverdraftLimitType

class FragmentCreateAccount : Fragment(){

    lateinit var radioGroupAccountType : RadioGroup
    lateinit var rbTypeChecking : RadioButton
    lateinit var rbTypeSavings: RadioButton
    lateinit var rbTypeCheckingAndSavings : RadioButton
    lateinit var etCheckingBalance : EditText
    lateinit var etOverdraftLimit : EditText
    lateinit var etSavingsBalance : EditText
    lateinit var etYearlyInterestRate : EditText
    lateinit var tvLabelCheckingBalance : TextView
    lateinit var tvLabelOverdraftLimit : TextView
    lateinit var tvLabelSavingsBalance : TextView
    lateinit var tvLabelYearlyInterestRate : TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val viewInflated : View = inflater.inflate(R.layout.fragment_create_account, container, false)
        initializeViews(viewInflated)
        setUiDefaultValues()
        setRadioGroupCheckedListener()
        showOnlyNonAlreadyCreatedAccountTypes()
        return viewInflated
    }

    private fun initializeViews(viewInflated: View) {
        radioGroupAccountType = viewInflated.findViewById(R.id.rg_account_type)
        rbTypeChecking = viewInflated.findViewById(R.id.rb_type_checking)
        rbTypeSavings = viewInflated.findViewById(R.id.rb_type_savings)
        rbTypeCheckingAndSavings = viewInflated.findViewById(R.id.rb_type_checking_and_savings)
        etCheckingBalance = viewInflated.findViewById(R.id.et_checking_balance)
        etOverdraftLimit = viewInflated.findViewById(R.id.et_overdraft_limit)
        etSavingsBalance = viewInflated.findViewById(R.id.et_savings_balance)
        etYearlyInterestRate = viewInflated.findViewById(R.id.et_yearly_interest_rate)
        tvLabelCheckingBalance = viewInflated.findViewById(R.id.label_checking_balance)
        tvLabelOverdraftLimit = viewInflated.findViewById(R.id.label_overdraft_limit)
        tvLabelSavingsBalance = viewInflated.findViewById(R.id.label_savings_balance)
        tvLabelYearlyInterestRate = viewInflated.findViewById(R.id.label_yearly_interest_rate)
    }

    private fun setUiDefaultValues() {
        val defaultCheckingAccount = CheckingAccount()
        // it can also be SavingsAccount because the default values are set in
        // the abstract super class which we cannot initialize
        etOverdraftLimit.setText(OverdraftLimitType.LIMIT_1000.limit.toString())
        etYearlyInterestRate.setText(SystemData.yearlyInterestRate.toString())
    }

    private fun setRadioGroupCheckedListener() {
        radioGroupAccountType.setOnCheckedChangeListener(
                RadioGroup.OnCheckedChangeListener {
                    group, checkedId ->
                    when(checkedId){
                        R.id.rb_type_savings -> {
                            showSavingsFields(true)
                            showCheckingFields(false)
                        }
                        R.id.rb_type_checking -> {
                            showCheckingFields(true)
                            showSavingsFields(false)
                        }
                        R.id.rb_type_checking_and_savings -> {
                            showSavingsFields(true)
                            showCheckingFields(true)
                        }
                    }
                })
    }

    // just hide the types of radioButtons that was not initialized
    // and that represent the type of account to be created
    // for the UI, so it won't bhe created
    private fun showOnlyNonAlreadyCreatedAccountTypes(){
        SystemData.accountOnlyChecking?.run {
            rbTypeChecking.visibility = View.GONE
            rbTypeChecking.isChecked = false
        }
        SystemData.accountOnlySavings?.run {
            rbTypeSavings.visibility = View.GONE
            rbTypeSavings.isChecked = false
        }
        SystemData.accountBothCheckingAndSavings?.run {
            rbTypeCheckingAndSavings.visibility = View.GONE
            rbTypeCheckingAndSavings.isChecked = false
        }
    }

    private fun showCheckingFields(show: Boolean) {
        etCheckingBalance.visibility = if (show) View.VISIBLE else View.GONE
        etOverdraftLimit.visibility = if (show) View.VISIBLE else View.GONE
        tvLabelCheckingBalance.visibility = if (show) View.VISIBLE else View.GONE
        tvLabelOverdraftLimit.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun showSavingsFields(show: Boolean) {
        etSavingsBalance.visibility = if (show) View.VISIBLE else View.GONE
        etYearlyInterestRate.visibility = if (show) View.VISIBLE else View.GONE
        tvLabelSavingsBalance.visibility = if (show) View.VISIBLE else View.GONE
        tvLabelYearlyInterestRate.visibility = if (show) View.VISIBLE else View.GONE
    }

}