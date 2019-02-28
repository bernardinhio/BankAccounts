package bernardo.bernardinhio.kotlinclasstypesInbankingapp.logic

/**
 * By using a primary constructor in Kotlin with parameters
 * initialized by default values, then we don't need to
 * overload constructors so we can call all these:
 *
 * val checkingAccount = CheckingAccount()
 * val checkingAccount = CheckingAccount(123.5)
 * val checkingAccount = CheckingAccount(123.5, 4355.9)
 * val checkingAccount = CheckingAccount(123.5, 4355.9, 3339.6)
 * val checkingAccount = CheckingAccount(123.5, 4355.9, 3339.6, AccountType.UNDEFINED)
 * val checkingAccount = CheckingAccount(123.5, 4355.9, 3339.6, AccountType.UNDEFINED, SavingsAccount())
 */
class CheckingAccount(
        var checkingBalance: Double = 0.toDouble(),
        var overdraftLimit: Double = 0.toDouble(),
        var remainingOverdraft: Double = 0.toDouble(),
        override var type: AccountType = AccountType.CHECKING,
        var savingsAccount : SavingsAccount? = null
) : Account() {

    /**
     * we can create from the beginning an account that is
     * of type CHECKING_AND_SAVINGS, or just CHECKING
     * that later we can upgrade to CHECKING_AND_SAVINGS
     *
     * example:
     * val checkingAccount = CheckingAccount(AccountType.CHECKING_AND_SAVINGS)
     */
    constructor(type: AccountType) : this(){
        this.type = type
    }

    /**
     * All combination of parameters in the constructor
     * are allowed if we use the "named parameters"
     * technique from Kotlin. Such as:
     *
     * val checkingAccount = CheckingAccount(checkingBalance =  23415.7, type = AccountType.CHECKING)
     */

    override fun getBalance() : Double{
        return when(type){
            AccountType.CHECKING -> checkingBalance
            AccountType.CHECKING_AND_SAVINGS -> checkingBalance + savingsAccount?.savingsBalance!!
            else -> -1.toDouble()
        }
    }





    override fun addMoney(money : Double, toTypeIfSecondAccount : AccountType){
        if (type.equals(AccountType.CHECKING)) {
            if (toTypeIfSecondAccount.equals(AccountType.CHECKING))
                checkingBalance += money
            else if (toTypeIfSecondAccount.equals(AccountType.SAVINGS))
                println("You account is CHECKING can't add to SAVINGS")
        }
        else if (type.equals(AccountType.CHECKING_AND_SAVINGS)){
            if (toTypeIfSecondAccount.equals(AccountType.CHECKING))
                checkingBalance += money
            else if (toTypeIfSecondAccount.equals(AccountType.SAVINGS))
                savingsAccount?.savingsBalance = savingsAccount?.savingsBalance!! + money
        }
    }






    override fun withdrawMoney(money : Double, fromTypeIfSecondAccount : AccountType): Boolean {
        var canWithdraw = false
        if (type.equals(AccountType.CHECKING)){
            if (fromTypeIfSecondAccount.equals(AccountType.CHECKING)){
                canWithdraw = withdrawFromChecking(money)
            } else if (fromTypeIfSecondAccount.equals(AccountType.SAVINGS)){
                canWithdraw = false
                println("Your account is checking, you can't withdraw from savings")
            }
        } else if (type.equals(AccountType.CHECKING_AND_SAVINGS)){
            if (fromTypeIfSecondAccount.equals(AccountType.CHECKING))
                canWithdraw = withdrawFromChecking(money)
            else if (fromTypeIfSecondAccount.equals(AccountType.SAVINGS))
                canWithdraw = withdrawFromSavings(money)
        }
        return canWithdraw
    }




    private fun withdrawFromChecking(money: Double) : Boolean{
        var canWithdraw = false
        if (money <= checkingBalance){
            checkingBalance -= money
            canWithdraw = true
        } else if (money > checkingBalance && (money - checkingBalance) <= remainingOverdraft){
            remainingOverdraft = remainingOverdraft - (money - checkingBalance)
            checkingBalance = 0.toDouble()
            canWithdraw = true
        } else if(money > checkingBalance + remainingOverdraft){
            canWithdraw = false
            println("can't withdraw money > checkingBalance + remainingOverdraft")
        }
        return canWithdraw
    }


    private fun withdrawFromSavings(money: Double): Boolean {
        var canWithdraw = false
        if (money <= savingsAccount?.savingsBalance!!){
            savingsAccount?.savingsBalance = savingsAccount?.savingsBalance!! - money
            canWithdraw = true
        } else if (money > savingsAccount?.savingsBalance!!){
            canWithdraw = false
            println("cannot withhdraw money > savingsBalance")
        }
        return canWithdraw
    }




    override fun transferMoneyToSomeone(): Boolean {
        var canTransfer = false

        return canTransfer
    }

}
