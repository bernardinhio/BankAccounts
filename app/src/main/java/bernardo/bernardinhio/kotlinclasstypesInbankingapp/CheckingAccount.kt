package bernardo.bernardinhio.kotlinclasstypesInbankingapp

class CheckingAccount : Account() {

    // our Overdraft Limit is the maximum amount that your bank is prepared for you to borrow from your current account.
    var overdraftLimit : Double = 1000.0

    private fun transferMoney(money : Double, receiverCheckingAccount : CheckingAccount){
        receiverCheckingAccount.receivedMoney(money, this)
    }

    private fun receivedMoney(money : Double, senderCheckingAccount : CheckingAccount){
        checkingBalance += money
    }


    override var totalBalance: Double
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}
    override var savingsBalance: Double
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}
    override var checkingBalance: Double
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}

    override fun addCash(cash: Double) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun withdrawCash(cash: Double) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
