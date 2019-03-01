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

    override fun addMoney(money : Double, toTypeIfSecondAccount : AccountType){
        if (type.equals(AccountType.SAVINGS)){
            if (toTypeIfSecondAccount.equals(AccountType.SAVINGS))
                savingsBalance += money
            else if (toTypeIfSecondAccount.equals(AccountType.SAVINGS))
                println("You account is Savings can't add to Checking")
        }
        else if (type.equals(AccountType.CHECKING_AND_SAVINGS)){
            if (toTypeIfSecondAccount.equals(AccountType.SAVINGS))
                savingsBalance += money
            else if (toTypeIfSecondAccount.equals(AccountType.CHECKING))
                checkingAccount?.checkingBalance = money + checkingAccount?.checkingBalance!!
        }
    }

    override fun withdrawMoney(money : Double, fromTypeIfSecondAccount : AccountType): Boolean {
        var canWithdraw = false
        if (type.equals(AccountType.SAVINGS)) {
            if (fromTypeIfSecondAccount.equals(AccountType.SAVINGS)){
                canWithdraw = withdrawFromSavings(money)
            } else if (fromTypeIfSecondAccount.equals(AccountType.CHECKING)){
                canWithdraw = false
                println("Your account is Savings, you can't withdraw from Checking")
            }
        }
        else if (type.equals(AccountType.CHECKING_AND_SAVINGS)){
            if (fromTypeIfSecondAccount.equals(AccountType.SAVINGS))
                canWithdraw = withdrawFromSavings(money)
            else if (fromTypeIfSecondAccount.equals(AccountType.CHECKING))
                canWithdraw = withdrawFromChecking(money)
        }
        return canWithdraw
    }

    private fun withdrawFromChecking(money: Double): Boolean {
        var canWithdraw = false
        if (money <= checkingAccount?.checkingBalance!!){
            checkingAccount?.checkingBalance = checkingAccount?.checkingBalance!! - money
            canWithdraw = true
        } else if (money > checkingAccount?.checkingBalance!!
                && (money - checkingAccount?.checkingBalance!!) <= checkingAccount?.remainingOverdraft!!
        ){
            checkingAccount?.remainingOverdraft = checkingAccount?.remainingOverdraft!! - (money - checkingAccount?.checkingBalance!!)
            checkingAccount?.checkingBalance = 0.toDouble()
            canWithdraw = true
        }else if(money > checkingAccount?.checkingBalance!!.plus(checkingAccount?.remainingOverdraft!!)){
            canWithdraw = false
            println("cannot withdraw money > checkingBalance + remainingOverdraft")
        }
        return canWithdraw
    }

    private fun withdrawFromSavings(money: Double): Boolean {
        var canWithdraw = false
        if (money <= savingsBalance){
            savingsBalance -= money
            canWithdraw = true
        } else if (money > savingsBalance){
            canWithdraw = false
            println("cannot withdraw money > savingsBalance")
        }
        return canWithdraw
    }


    override fun transferMoneyToSomeone(money : Double, fromTypeIfSecondAccount : AccountType, receiverAccount : Account): Boolean {
        var canTransfer = false
        if (type.equals(AccountType.SAVINGS)){
            if (fromTypeIfSecondAccount.equals(AccountType.SAVINGS)){
                canTransfer = transferFromSavingsToReceiverAccount(money, receiverAccount)
            } else if (fromTypeIfSecondAccount.equals(AccountType.CHECKING))
                println("Your account is Savings, you can't transfer from Checking")
        }else if (type.equals(AccountType.CHECKING_AND_SAVINGS)){
            if (fromTypeIfSecondAccount.equals(AccountType.CHECKING))
                canTransfer = transferFromCheckingToReceiverAccount(money, receiverAccount)
            else if (fromTypeIfSecondAccount.equals(AccountType.SAVINGS))
                canTransfer = transferFromSavingsToReceiverAccount(money, receiverAccount)
        }
        return canTransfer
    }

    private fun transferFromSavingsToReceiverAccount(money: Double, receiverAccount: Account): Boolean {
        var canTransfer = false
        if (money <= savingsBalance){
            when (receiverAccount.type){
                AccountType.CHECKING -> {
                    (receiverAccount as CheckingAccount).checkingBalance =+ money
                    savingsBalance -= money
                    canTransfer = true
                }
                AccountType.SAVINGS -> {
                    (receiverAccount as SavingsAccount).savingsBalance += money
                    savingsBalance -= money
                    canTransfer = true
                }
                // receiving to CHECKING_AND_SAVINGS, receive on CHECKING
                AccountType.CHECKING_AND_SAVINGS -> {
                    (receiverAccount as CheckingAccount).checkingBalance += money
                    savingsBalance -= money
                    canTransfer = true
                }
                AccountType.UNDEFINED -> {}
            }
        } else if (money > savingsBalance){
            canTransfer = false
            println("cannot transfer money > savingsBalance")
        }
        return canTransfer
    }

    private fun transferFromCheckingToReceiverAccount(money: Double, receiverAccount: Account): Boolean {
        var canTransfer = false
        if (money <= checkingAccount?.checkingBalance!!){
            canTransfer = transferFromCheckingBalanceToReceiverAccount(money, receiverAccount)
        } else if (money > checkingAccount?.checkingBalance!! && (money - checkingAccount?.checkingBalance!!) <= checkingAccount?.remainingOverdraft!!){
            canTransfer = transferFromCheckingBalanceAndRemainingOverdraftToReceiverAccount(money, receiverAccount)
        } else if(money > checkingAccount?.checkingBalance!! + checkingAccount?.remainingOverdraft!!){
            canTransfer = false
            println("can't withdraw money > checkingBalance + remainingOverdraft")
        }
        return canTransfer
    }

    private fun transferFromCheckingBalanceToReceiverAccount(money: Double, receiverAccount: Account): Boolean {
        var canTransfer = false
        when (receiverAccount.type){
            AccountType.CHECKING -> {
                (receiverAccount as CheckingAccount).checkingBalance += money
                checkingAccount?.checkingBalance = checkingAccount?.checkingBalance!! - money
                canTransfer = true
            }
            AccountType.SAVINGS -> {
                (receiverAccount as SavingsAccount).savingsBalance += money
                checkingAccount?.checkingBalance = checkingAccount?.checkingBalance!! - money
                canTransfer = true
            }
            // receiving to CHECKING_AND_SAVINGS, receive on CHECKING
            AccountType.CHECKING_AND_SAVINGS -> {
                (receiverAccount as CheckingAccount).checkingBalance += money
                checkingAccount?.checkingBalance = checkingAccount?.checkingBalance!! - money
                canTransfer = true
            }
            AccountType.UNDEFINED -> {}
        }
        return canTransfer
    }


    private fun transferFromCheckingBalanceAndRemainingOverdraftToReceiverAccount(money: Double, receiverAccount: Account): Boolean {
        var canTransfer = false
        when (receiverAccount.type){
            AccountType.CHECKING -> {
                (receiverAccount as CheckingAccount).checkingBalance += money
                checkingAccount?.remainingOverdraft = checkingAccount?.remainingOverdraft!! - (money - checkingAccount?.checkingBalance!!)
                checkingAccount?.checkingBalance = 0.toDouble()
                canTransfer = true
            }
            AccountType.SAVINGS -> {
                (receiverAccount as SavingsAccount).savingsBalance += money
                checkingAccount?.remainingOverdraft = checkingAccount?.remainingOverdraft!! - (money - checkingAccount?.checkingBalance!!)
                checkingAccount?.checkingBalance = 0.toDouble()
                canTransfer = true
            }
            // receiving to CHECKING_AND_SAVINGS, receive on CHECKING
            AccountType.CHECKING_AND_SAVINGS -> {
                (receiverAccount as CheckingAccount).checkingBalance += money
                checkingAccount?.remainingOverdraft = checkingAccount?.remainingOverdraft!! - (money - checkingAccount?.checkingBalance!!)
                checkingAccount?.checkingBalance = 0.toDouble()
                canTransfer = true
            }
            AccountType.UNDEFINED -> {}
        }
        return canTransfer
    }

    fun convertMoneyToChecking(money : Double) : Boolean{
        var isConverted = false
        if (type.equals(AccountType.CHECKING_AND_SAVINGS)){
            if (money <= savingsBalance){
                isConverted = convertFromSavingsBalanceToSavingsBalance(money)
            } else println("can't convert money more than you have in checkingBalance")
        } else println("you don't even have a Checking Account")
        return isConverted
    }

    private fun convertFromSavingsBalanceToSavingsBalance(money: Double): Boolean {
        checkingAccount?.checkingBalance = money + checkingAccount?.checkingBalance!!
        savingsBalance -= money
        return true
    }

    fun getBenefitFromInterest(perPeriod : PeriodOfInterest) : Double{
        return when(perPeriod){
            PeriodOfInterest.MONTHS_1 -> savingsBalance*yearlyInterestRate/100/12
            PeriodOfInterest.MONTHS_12 -> savingsBalance*yearlyInterestRate/100
        }
    }
}