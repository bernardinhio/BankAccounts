package bernardo.bernardinhio.kotlinclasstypesInbankingapp.logic

class CheckingAccount() : Account() {

    constructor(checkingBalance : Double) : this()

    constructor(checkingBalance : Double, overdraftLimit : Double) : this()

    // asked by the sunhill
    var currentInterestPerYear : Double = 0.toDouble()
    var currentInterestPerMonth : Double = 0.toDouble()

    init {
        this.type = checkIfTypeNeedsModification()
    }

    private fun checkIfTypeNeedsModification() : AccountType {
        val returnedType = when(super.type){
            AccountType.UNDEFINED -> AccountType.CHECKING
            AccountType.SAVINGS -> AccountType.CHECKING_AND_SAVINGS
            AccountType.CHECKING_AND_SAVINGS -> AccountType.CHECKING_AND_SAVINGS
            AccountType.CHECKING -> AccountType.CHECKING
        }
        return returnedType
    }

    override fun getBalance(): Double {
        return checkingBalance
    }

    override fun addMoney(amount: Double) {
        checkingBalance += amount
        totalBalance += amount
    }

    override fun withdrawMoney(amount: Double) {
        if (amount <= checkingBalance + overdraftLimit){
            checkingBalance -= amount
            totalBalance -= amount
        } else {
            println("You can't withdraw $amount, it's more than you are allowed: your overdraft limit is $overdraftLimit and your checking balance is $checkingBalance you can maximum withdraw ${checkingBalance + overdraftLimit} !!")
            println("Do you want to withdraw from your Savings? You can't get interest on them later, sure?")
            if (confirmWithdrawFromSavingsAccount()){
                withdrawFromSavingsAccount(amount)
            } else println("Then you don't want to withdraw from your savings")
        }
    }

    private fun confirmWithdrawFromSavingsAccount() : Boolean{
        val confirmed : Boolean = true
        return confirmed
    }

    private fun withdrawFromSavingsAccount(amount: Double){
        if (amount <= savingsBalance){
            savingsBalance -= amount
            totalBalance -= amount
        } else println("Sorry, your savings $savingsBalance aren't enough for withdrawing $amount")
    }

    fun convertToSavingsAccount(amount : Double){
        if (amount <= checkingBalance){
            checkingBalance -= amount
            savingsBalance += amount
        } else println("Sorry, you can't convert $amount from Checking-account to Saving-account, it's more than you actually have only: $checkingBalance")
    }

}