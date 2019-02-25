package bernardo.bernardinhio.kotlinclasstypesInbankingapp.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import bernardo.bernardinhio.kotlinclasstypesInbankingapp.R
import bernardo.bernardinhio.kotlinclasstypesInbankingapp.data.SystemData
import bernardo.bernardinhio.kotlinclasstypesInbankingapp.logic.CheckingAccount

class FragmentCreateAccount : Fragment(){

    lateinit var radioGroupAccountType : RadioGroup
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
        return viewInflated
    }

    private fun initializeViews(viewInflated: View) {
        radioGroupAccountType = viewInflated.findViewById(R.id.rg_account_type)
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
        etCheckingBalance.setText(defaultCheckingAccount.checkingBalance.toString())
        etOverdraftLimit.setText(defaultCheckingAccount.overdraftLimit.toString())
        etSavingsBalance.setText(defaultCheckingAccount.savingsBalance.toString())
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

    private fun showCheckingFields(show: Boolean) {
        when(show){
            true -> {
                etCheckingBalance.visibility = View.VISIBLE
                etOverdraftLimit.visibility = View.VISIBLE
                tvLabelCheckingBalance.visibility = View.VISIBLE
                tvLabelOverdraftLimit.visibility = View.VISIBLE
            }
            false -> {
                etCheckingBalance.visibility = View.GONE
                etOverdraftLimit.visibility = View.GONE
                tvLabelCheckingBalance.visibility = View.GONE
                tvLabelOverdraftLimit.visibility = View.GONE
            }
        }
    }

    private fun showSavingsFields(show: Boolean) {
        when(show){
            true -> {
                etSavingsBalance.visibility = View.VISIBLE
                etYearlyInterestRate.visibility = View.VISIBLE
                tvLabelSavingsBalance.visibility = View.VISIBLE
                tvLabelYearlyInterestRate.visibility = View.VISIBLE
            }
            false -> {
                etSavingsBalance.visibility = View.GONE
                etYearlyInterestRate.visibility = View.GONE
                tvLabelSavingsBalance.visibility = View.GONE
                tvLabelYearlyInterestRate.visibility = View.GONE
            }
        }
    }

}