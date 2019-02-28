package bernardo.bernardinhio.kotlinclasstypesInbankingapp.logic

/**
 * By using a primary constructor in Kotlin with parameters
 * initialized by default values, then we don't need to
 * overload constructors so we can call all these:
 *
 * val savingsAccount = SavingsAccount()
 * val savingsAccount = SavingsAccount(12345.4)
 * val savingsAccount = SavingsAccount(12345.4, 3452.5)
 * val savingsAccount = SavingsAccount(12345.4, 3452.5, AccountType.CHECKING_AND_SAVINGS)
 * val savingsAccount = SavingsAccount(12345.4, 3452.5, AccountType.CHECKING_AND_SAVINGS, CheckingAccount())
 */
class SavingsAccount(
        var savingsBalance : Double = 0.toDouble(),
        var yearlyInterestRate : Double = 0.toDouble(),
        override var type: AccountType = AccountType.SAVINGS,
        var checkingAccount: CheckingAccount? = null
) : Account(){

    /**
     * we can create from the beginning an account that is
     * of type CHECKING_AND_SAVINGS, or just SAVINGS
     * that later we can upgrade to CHECKING_AND_SAVINGS
     *
     * example:
     * val savingsAccount = CheckingAccount(AccountType.CHECKING_AND_SAVINGS)
     */
    constructor(type: AccountType) : this(){
        this.type = type
    }

    /**
     * All combination of parameters in the constructor
     * are allowed if we use the "named parameters"
     * technique from Kotlin. Such as:
     *
     * val savingsAccount = SavingsAccount(savingsBalance = 12345.5, type = AccountType.SAVINGS)
     */

    override fun getBalance() : Double{
        return when(type){
            AccountType.SAVINGS -> savingsBalance
            AccountType.CHECKING_AND_SAVINGS -> savingsBalance + checkingAccount?.checkingBalance!!
            else -> -1.toDouble()
        }
    }

    // TODO make unit test to check if working as in CheckingAccount
    override fun addMoney(money : Double, toTypeIfSecondAccount : AccountType){
        if (type.equals(AccountType.SAVINGS))
            savingsBalance += money
        else if (type.equals(AccountType.CHECKING_AND_SAVINGS)){
            if (toTypeIfSecondAccount.equals(AccountType.SAVINGS))
                savingsBalance += money
            else if (toTypeIfSecondAccount.equals(AccountType.CHECKING))
                checkingAccount?.checkingBalance!!.plus(money)
        }
    }

    override fun withdrawMoney(money : Double, fromTypeIfSecondAccount : AccountType): Boolean {
        var canWithdraw = false
        if (type.equals(AccountType.SAVINGS))
            canWithdraw = widrawFromSavings(money)
        else if (type.equals(AccountType.CHECKING_AND_SAVINGS)){
            if (fromTypeIfSecondAccount.equals(AccountType.SAVINGS))
                canWithdraw = widrawFromSavings(money)
            else if (fromTypeIfSecondAccount.equals(AccountType.CHECKING))
                canWithdraw = widrawFromChecking(money)
        }
        return canWithdraw
    }

    private fun widrawFromChecking(money: Double): Boolean {
        var canWithdraw = false
        if (money <= checkingAccount?.checkingBalance!!){
            checkingAccount?.checkingBalance!!.minus(money)
            canWithdraw = true
            println("SUCCESS: money <= checkingBalance")
        } else if (money > checkingAccount?.checkingBalance!!){
            checkingAccount?.checkingBalance!!.minus(checkingAccount?.checkingBalance!!)
            checkingAccount?.remainingOverdraft!!.minus(money)
            canWithdraw = true
            println("SUCCESS: money <= checkingBalance + remainingOverdraft")
        }else if(money > checkingAccount?.checkingBalance!!.plus(checkingAccount?.remainingOverdraft!!)){
            canWithdraw = false
            println("FAILED: money > checkingBalance + remainingOverdraft")
        }
        return canWithdraw
    }



    private fun widrawFromSavings(money: Double): Boolean {
        var canWithdraw = false
        if (money <= savingsBalance){
            savingsBalance -= money
            canWithdraw = true
            println("SUCCESS: money <= savingsBalance")
        } else if (money > savingsBalance){
            canWithdraw = false
            println("FAILED: money > savingsBalance")
        }
        return canWithdraw
    }


    // TODO make unit test to check if working as in CheckingAccount
    override fun transferMoneyToSomeone(money : Double, fromTypeIfSecondAccount : AccountType, receiverAccount : Account): Boolean {
        var canTransfer = false
        // TODO
        return canTransfer
    }




    // TODO Unit test
    fun getBenefitPerYear(perYearOrMonth : Int) : Double{
        return when(perYearOrMonth){
            1 -> savingsBalance*yearlyInterestRate/100/12
            12 -> savingsBalance*yearlyInterestRate/100
            else -> 0.0
        }
    }




    fun convertMoneyToChecking(money : Double) : Boolean{
        var isConverted = false
        if (type.equals(AccountType.CHECKING_AND_SAVINGS)){
            // TODO check if amount is smaller
            isConverted = true
        } else if (type.equals(AccountType.SAVINGS)){

        }
        return isConverted
    }
}