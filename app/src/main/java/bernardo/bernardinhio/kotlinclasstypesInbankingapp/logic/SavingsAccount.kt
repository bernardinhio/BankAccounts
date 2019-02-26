package bernardo.bernardinhio.kotlinclasstypesInbankingapp.logic

class SavingsAccount() : Account() {

    constructor(savingsBalance : Double) : this(){
        this.savingsBalance = savingsBalance
    }

    constructor(savingsBalance : Double, yearlyInterestRate : Double) : this() {
        this.savingsBalance = savingsBalance
        this.yearlyInterestRate = yearlyInterestRate
    }

    private var monthlyInterestRate : Double = yearlyInterestRate /12
    private var monthlyBenefit : Double = super.totalBalance * monthlyInterestRate

    init {
        super.type = checkIfTypeNeedsModification()
        super.checkingBalance = super.totalBalance - savingsBalance
    }

    private fun checkIfTypeNeedsModification() : AccountType {
        val returnedType = when(super.type){
            AccountType.UNDEFINED -> AccountType.SAVINGS
            AccountType.CHECKING -> AccountType.CHECKING_AND_SAVINGS
            AccountType.CHECKING_AND_SAVINGS -> AccountType.CHECKING_AND_SAVINGS
            AccountType.SAVINGS -> AccountType.SAVINGS
        }
        return returnedType
    }

    override fun getBalance(): Double {
        return savingsBalance
    }

    override fun addMoney(amount: Double) {
        savingsBalance += amount
        totalBalance += amount
    }

    // for accountBothCheckingAndSavings
    // AND for accountOnlySavings
    override fun withdrawMoney(amount: Double) {
        when(this.type){
            AccountType.CHECKING_AND_SAVINGS -> {
                if (amount <= savingsBalance){
                    savingsBalance -= amount
                    totalBalance -= amount
                } else{
                    println("You can't withdraw $amount, it's more than your entire savings: $savingsBalance !")
                    println("Do you want to withdraw from your Checking balance? sure? You have there: $checkingBalance !")
                    if (confirmWithdrawFromSavingsAccount()){
                        withdrawFromCheckingAccount(amount)
                    }
                }
            }
            AccountType.SAVINGS -> {
                if (amount <= savingsBalance){
                    savingsBalance -= amount
                    totalBalance -= amount
                } else{
                    println("You can't withdraw $amount, it's more than your entire savings: $savingsBalance !")
                }
            }
        }

    }

    private fun confirmWithdrawFromSavingsAccount() : Boolean{
        val confirmed : Boolean = true
        return confirmed
    }

    // only for accountBothCheckingAndSavings
    private fun withdrawFromCheckingAccount(amount: Double) {
        if (this.type == AccountType.CHECKING_AND_SAVINGS){
            if (amount <= checkingBalance + overdraftLimit){
                checkingBalance -= amount
                totalBalance -= amount
            } else println("Sorry, your checking-balance $checkingBalance aren't enough for withdrawing $amount")
        }
    }

    // only for accountBothCheckingAndSavings
    fun convertToCheckingAccount(amount : Double){
        if (this.type == AccountType.CHECKING_AND_SAVINGS){
            if (amount <= savingsBalance){
                savingsBalance -= amount
                checkingBalance += amount
            } else println("Sorry, you can't convert $amount from Savings to checking-account, it's more than you actually have only: $savingsBalance")
        }
    }


}