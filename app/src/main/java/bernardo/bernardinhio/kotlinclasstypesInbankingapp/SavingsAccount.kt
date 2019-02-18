package bernardo.bernardinhio.kotlinclasstypesInbankingapp

class SavingsAccount : Account {

    var savingAmount : Double = 0.0
        set(value) {field = if (value <= totalBalance) value else 0.0}

    constructor(owner: Owner, savingAmount: Double, type: AccountType, balance: Double, savingsBalance: Double, checkingBalance: Double) : super() {
        // if owner was initialized already then don't initialize it again
        super.owner = if (super.owner.name.isEmpty())owner else super.owner
        this.savingAmount = if(savingAmount <= balance) savingAmount else 0.0
        this.type = getAccountTypeToSet(type)
        this.totalBalance = balance
        this.savingsBalance = savingsBalance
        this.checkingBalance = checkingBalance
    }

    /**
     * When trying to open a new account type, we have to check
     */
    private fun getAccountTypeToSet(newType : AccountType) : AccountType{
        var accountType = AccountType.UNDEFINED
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