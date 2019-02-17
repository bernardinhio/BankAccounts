package bernardo.bernardinhio.kotlinclasstypesInbankingapp

import android.text.format.DateFormat
import java.util.*

abstract class Account{

    val id : Long = System.currentTimeMillis()
    val dateCreated : String = getFormattedTime(System.currentTimeMillis())

    var owner : Owner = Owner()
    var type : AccountType = AccountType.UNDEFINED

    abstract var totalBalance : Double
    abstract var savingsBalance : Double
    abstract var checkingBalance : Double


    abstract fun addCash(cash : Double)
    abstract fun withdrawCash(cash : Double)

    private fun getFormattedTime(timeStamp: Long): String {
        val calendar = Calendar.getInstance(Locale.ENGLISH)
        calendar.timeInMillis = timeStamp
        return DateFormat.format("'Created: 'EEE dd, MMM yyyy'  /  at: 'HH:mm:ss'/  Zone: 'z", calendar).toString()
    }
}




enum class AccountType(type : String){
    SAVINGS("savings"),
    CHECKING("checking"),
    SAVINGS_AND_CHECKING("savings_and_checking"),
    UNDEFINED("")
}




class SavingsAccount : Account{

    var savingAmount : Double = 0.0
        set(value) {field = if (value <= totalBalance) value else 0.0}


    constructor(owner: Owner, savingAmount: Double, type: AccountType, balance: Double, SavingsBalance: Double, checkingBalance: Double) : super() {
        // if owner was initialized already then don't initialize it again
        super.owner = if (super.owner.name.isEmpty())owner else super.owner
        this.savingAmount = if(savingAmount <= balance) savingAmount else 0.0
        this.type = getAccountTypeToSet(type)
        this.totalBalance = balance
        this.savingsBalance = SavingsBalance
        this.checkingBalance = checkingBalance
    }

    private fun getAccountTypeToSet(newType : AccountType) : AccountType{
        var accountType = AccountType.UNDEFINED

        // we can create 3 concrete types of Accounts; Savings // Checking and SavingsAndChecking
        when(newType){
            AccountType.SAVINGS -> {
                when(super.type){
                    AccountType.CHECKING -> accountType = AccountType.SAVINGS_AND_CHECKING
                    AccountType.UNDEFINED -> accountType = AccountType.SAVINGS
                }
            }
            AccountType.CHECKING -> {
                when(super.type){
                    AccountType.SAVINGS -> accountType = AccountType.SAVINGS_AND_CHECKING
                    AccountType.UNDEFINED -> accountType = AccountType.CHECKING
                }
            }
            AccountType.SAVINGS_AND_CHECKING -> accountType = AccountType.SAVINGS_AND_CHECKING
        }
        return accountType
    }

    // static to all SavingsAccount owners
    companion object {
        private var annualInterestRate : Double = 3.34

        private fun provideInterestToAllUsers(newInterestRate : Double){
            annualInterestRate = newInterestRate
        }
    }

    val monthlyInterest: Double
        get() = totalBalance * (annualInterestRate / 12)




    // we can set the totalBalance of the SavingAccount from the original account
    override var totalBalance : Double
        get() = savingAmount
        set(value) {
            // TODO here
            field = value
            checkingBalance = value - savingAmount}

    override var savingsBalance: Double
        get() = savingAmount
        set(value) { field = value - savingAmount}

    override var checkingBalance: Double
        get() = totalBalance - savingsBalance
        set(value) {field = totalBalance - savingsBalance}


    override fun addCash(cash: Double) {
        totalBalance += cash
    }

    override fun withdrawCash(cash: Double) {
        // we can't withdraw from the savingAmount only from the remaining
        if (cash <= totalBalance - savingAmount) totalBalance -= cash
    }
}


class CheckingAccount{

}
