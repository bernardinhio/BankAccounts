package bernardo.bernardinhio.kotlinclasstypesInbankingapp.logic

import org.junit.Assert.*

class CheckingAccountTest{

    val checking = CheckingAccount()

    val checkingAndSavings = CheckingAccount()

    fun reload(){
        checking.checkingBalance = 3000.0
        checking.overdraftLimit = 500.0
        checking.remainingOverdraft = 200.0
        checking.type = AccountType.CHECKING

        checkingAndSavings.checkingBalance = 3000.0
        checkingAndSavings.overdraftLimit = 500.0
        checkingAndSavings.remainingOverdraft = 200.0
        checkingAndSavings.type = AccountType.CHECKING_AND_SAVINGS
        checkingAndSavings.savingsAccount = SavingsAccount(1500.0)
    }



    // test getBalance()

    @org.junit.Test
    fun test1_getBalance() {
        reload()
        val balance = checking.getBalance()
        assertEquals(3000.0.toString(), balance.toString())
    }

    @org.junit.Test
    fun test2_getBalance() {
        reload()
        val balance = checkingAndSavings.getBalance()
        assertEquals(4500.0.toString(), balance.toString())
    }



    // test addMoney

    @org.junit.Test
    fun test1_addMoney() {
        reload()
        checking.addMoney(1000.0, AccountType.CHECKING)
        assertEquals(4000.0.toString(), checking.checkingBalance.toString())
    }

    @org.junit.Test
    fun test2_addMoney() {
        reload()
        checking.addMoney(1000.0, AccountType.SAVINGS)
        assertNotEquals(2500.0.toString(), checking.savingsAccount?.savingsBalance.toString())
    }

    @org.junit.Test
    fun test3_addMoney() {
        reload()
        checkingAndSavings.addMoney(1000.0, AccountType.CHECKING)
        assertEquals(4000.0.toString(), checkingAndSavings.checkingBalance.toString())
    }

    @org.junit.Test
    fun test4_addMoney() {
        reload()
        checkingAndSavings.addMoney(1000.0, AccountType.SAVINGS)
        assertEquals(2500.0.toString(), checkingAndSavings.savingsAccount?.savingsBalance.toString())
    }



    @org.junit.Test
    fun test1_withdrawMoney() {
        reload()
        checking.withdrawMoney(70.0, AccountType.SAVINGS)
        // saving account doesn't exist
        assertNull(checking.savingsAccount?.savingsBalance?.toString())
    }

    @org.junit.Test
    fun test2_withdrawMoney() {
        reload()
        checking.withdrawMoney(70.0, AccountType.CHECKING)
        assertEquals(2930.0.toString(), checking.checkingBalance.toString())
    }

    @org.junit.Test
    fun test3_withdrawMoney() {
        reload()
        checking.withdrawMoney(100.0, AccountType.CHECKING)
        assertEquals(2900.0.toString(), checking.checkingBalance.toString())
    }

    @org.junit.Test
    fun test4_withdrawMoney() {
        reload()
        checking.withdrawMoney(3100.0, AccountType.CHECKING)
        assertEquals(0.0.toString(), checking.checkingBalance.toString())
        reload()
        checking.withdrawMoney(3100.0, AccountType.CHECKING)
        assertEquals(100.0.toString(), checking.remainingOverdraft.toString())
    }

    @org.junit.Test
    fun test5_withdrawMoney() {
        reload()
        checking.withdrawMoney(3210.0, AccountType.CHECKING)
        assertEquals(3000.0.toString(), checking.checkingBalance.toString())
    }

    @org.junit.Test
    fun test6_withdrawMoney() {
        reload()
        checkingAndSavings.withdrawMoney(70.0, AccountType.CHECKING)
        assertEquals(2930.0.toString(), checkingAndSavings.checkingBalance.toString())
    }

    @org.junit.Test
    fun test7_withdrawMoney() {
        reload()
        checkingAndSavings.withdrawMoney(70.0, AccountType.SAVINGS)
        assertEquals(1430.0.toString(), checkingAndSavings.savingsAccount?.savingsBalance.toString())
    }

    @org.junit.Test
    fun test8_withdrawMoney() {
        reload()
        checkingAndSavings.withdrawMoney(1550.0, AccountType.SAVINGS)
        assertEquals(1500.0.toString(), checkingAndSavings.savingsAccount?.savingsBalance.toString())
    }
}