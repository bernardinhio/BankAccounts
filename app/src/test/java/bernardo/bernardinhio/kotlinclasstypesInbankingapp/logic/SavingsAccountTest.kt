package bernardo.bernardinhio.kotlinclasstypesInbankingapp.logic

import org.junit.Assert.*

class SavingsAccountTest{

    val savings = SavingsAccount()

    val savingsAndChecking = SavingsAccount()

    fun reload(){
        savings.savingsBalance = 3000.0
        savings.yearlyInterestRate = 5.2

        savingsAndChecking.savingsBalance = 3000.0
        savingsAndChecking.yearlyInterestRate = 5.2
        savingsAndChecking.type = AccountType.CHECKING_AND_SAVINGS
        savingsAndChecking.checkingAccount = CheckingAccount(
                3000.0,
                500.0,
                200.0
        )
    }



    // test getBalance()

    @org.junit.Test
    fun test1_getBalance() {
        reload()
        val balance = savings.getBalance()
        assertEquals(3000.0.toString(), balance.toString())
    }

}