package bernardo.bernardinhio.kotlinclasstypesInbankingapp.logic

import org.junit.Assert.*

class CheckingAccountTest{

    val checkingAcc = CheckingAccount()

    val checkingAndSavingsAcc = CheckingAccount()

    val savingsAcc = SavingsAccount()

    fun reload(){
        checkingAcc.checkingBalance = 3000.0
        checkingAcc.overdraftLimit = 500.0
        checkingAcc.remainingOverdraft = 200.0

        checkingAndSavingsAcc.checkingBalance = 3000.0
        checkingAndSavingsAcc.overdraftLimit = 500.0
        checkingAndSavingsAcc.remainingOverdraft = 200.0
        checkingAndSavingsAcc.type = AccountType.CHECKING_AND_SAVINGS
        checkingAndSavingsAcc.savingsAccount = SavingsAccount(
                1500.0
        )

        savingsAcc.savingsBalance = 3000.0


    }



    // test getBalance()

    @org.junit.Test
    fun test1_getBalance() {
        reload()
        val balance = checkingAcc.getBalance()
        assertEquals(3000.0.toString(), balance.toString())
    }

    @org.junit.Test
    fun test2_getBalance() {
        reload()
        val balance = checkingAndSavingsAcc.getBalance()
        assertEquals(4500.0.toString(), balance.toString())
    }



    // test addMoney

    @org.junit.Test
    fun test1_addMoney() {
        reload()
        checkingAcc.addMoney(1000.0, AccountType.CHECKING)
        assertEquals(4000.0.toString(), checkingAcc.checkingBalance.toString())
    }

    @org.junit.Test
    fun test2_addMoney() {
        reload()
        checkingAcc.addMoney(1000.0, AccountType.SAVINGS)
        assertNotEquals(2500.0.toString(), checkingAcc.savingsAccount?.savingsBalance.toString())
    }

    @org.junit.Test
    fun test3_addMoney() {
        reload()
        checkingAndSavingsAcc.addMoney(1000.0, AccountType.CHECKING)
        assertEquals(4000.0.toString(), checkingAndSavingsAcc.checkingBalance.toString())
    }

    @org.junit.Test
    fun test4_addMoney() {
        reload()
        checkingAndSavingsAcc.addMoney(1000.0, AccountType.SAVINGS)
        assertEquals(2500.0.toString(), checkingAndSavingsAcc.savingsAccount?.savingsBalance.toString())
    }



    @org.junit.Test
    fun test1_withdrawMoney() {
        reload()
        checkingAcc.withdrawMoney(70.0, AccountType.SAVINGS)
        // saving account doesn't exist
        assertNull(checkingAcc.savingsAccount?.savingsBalance?.toString())
    }

    @org.junit.Test
    fun test2_withdrawMoney() {
        reload()
        checkingAcc.withdrawMoney(70.0, AccountType.CHECKING)
        assertEquals(2930.0.toString(), checkingAcc.checkingBalance.toString())
    }

    @org.junit.Test
    fun test3_withdrawMoney() {
        reload()
        checkingAcc.withdrawMoney(100.0, AccountType.CHECKING)
        assertEquals(2900.0.toString(), checkingAcc.checkingBalance.toString())
    }

    @org.junit.Test
    fun test4_withdrawMoney() {
        reload()
        checkingAcc.withdrawMoney(3100.0, AccountType.CHECKING)
        assertEquals(0.0.toString(), checkingAcc.checkingBalance.toString())
        reload()
        checkingAcc.withdrawMoney(3100.0, AccountType.CHECKING)
        assertEquals(100.0.toString(), checkingAcc.remainingOverdraft.toString())
    }

    @org.junit.Test
    fun test5_withdrawMoney() {
        reload()
        checkingAcc.withdrawMoney(3210.0, AccountType.CHECKING)
        assertEquals(3000.0.toString(), checkingAcc.checkingBalance.toString())
    }

    @org.junit.Test
    fun test6_withdrawMoney() {
        reload()
        checkingAndSavingsAcc.withdrawMoney(70.0, AccountType.CHECKING)
        assertEquals(2930.0.toString(), checkingAndSavingsAcc.checkingBalance.toString())
    }

    @org.junit.Test
    fun test7_withdrawMoney() {
        reload()
        checkingAndSavingsAcc.withdrawMoney(70.0, AccountType.SAVINGS)
        assertEquals(1430.0.toString(), checkingAndSavingsAcc.savingsAccount?.savingsBalance.toString())
    }

    @org.junit.Test
    fun test8_withdrawMoney() {
        reload()
        checkingAndSavingsAcc.withdrawMoney(1550.0, AccountType.SAVINGS)
        assertEquals(1500.0.toString(), checkingAndSavingsAcc.savingsAccount?.savingsBalance.toString())
    }




    // transferMoneyToSomeone

    @org.junit.Test
    fun test1_transferMoneyToSomeone() {
        reload()
        val checkingReceiver = CheckingAccount()
        checkingAcc.transferMoneyToSomeone(1000.0, AccountType.CHECKING, checkingReceiver)
        assertEquals(2000.0.toString(), checkingAcc.checkingBalance.toString())
        assertEquals(1000.0.toString(), checkingReceiver.checkingBalance.toString())
    }

    @org.junit.Test
    fun test2_transferMoneyToSomeone() {
        reload()
        val savingsReceiver = SavingsAccount()
        checkingAcc.transferMoneyToSomeone(1000.0, AccountType.CHECKING, savingsReceiver)
        assertEquals(2000.0.toString(), checkingAcc.checkingBalance.toString())
        assertEquals(1000.0.toString(), savingsReceiver.savingsBalance.toString())
    }

    @org.junit.Test
    fun test3_transferMoneyToSomeone() {
        reload()
        checkingAcc.transferMoneyToSomeone(1000.0, AccountType.CHECKING, savingsAcc)
        assertEquals(2000.0.toString(), checkingAcc.checkingBalance.toString())
        assertEquals(4000.0.toString(), savingsAcc.savingsBalance.toString())
    }

    @org.junit.Test
    fun test4_transferMoneyToSomeone() {
        reload()
        checkingAcc.transferMoneyToSomeone(1000.0, AccountType.CHECKING, checkingAndSavingsAcc)
        assertEquals(2000.0.toString(), checkingAcc.checkingBalance.toString())
        assertEquals(4000.0.toString(), checkingAndSavingsAcc.checkingBalance.toString())
    }

    @org.junit.Test
    fun test5_transferMoneyToSomeone() {
        reload()
        val checkingReceiver = CheckingAccount()
        checkingAcc.transferMoneyToSomeone(3100.0, AccountType.CHECKING, checkingReceiver)
        assertEquals(0.0.toString(), checkingAcc.checkingBalance.toString())
        assertEquals(100.0.toString(), checkingAcc.remainingOverdraft.toString())
        assertEquals(3100.0.toString(), checkingReceiver.checkingBalance.toString())
    }

    @org.junit.Test
    fun test6_transferMoneyToSomeone() {
        reload()
        val savingsReceiver = SavingsAccount()
        checkingAcc.transferMoneyToSomeone(3100.0, AccountType.CHECKING, savingsReceiver)
        assertEquals(0.0.toString(), checkingAcc.checkingBalance.toString())
        assertEquals(100.0.toString(), checkingAcc.remainingOverdraft.toString())
        assertEquals(3100.0.toString(), savingsReceiver.savingsBalance.toString())
    }

    @org.junit.Test
    fun test7_transferMoneyToSomeone() {
        reload()
        val checkingReceiver = CheckingAccount(AccountType.CHECKING_AND_SAVINGS)
        checkingAcc.transferMoneyToSomeone(3100.0, AccountType.CHECKING, checkingReceiver)
        assertEquals(0.0.toString(), checkingAcc.checkingBalance.toString())
        assertEquals(100.0.toString(), checkingAcc.remainingOverdraft.toString())
        assertEquals(3100.0.toString(), checkingReceiver.checkingBalance.toString())
    }

    @org.junit.Test
    fun test8_transferMoneyToSomeone() {
        reload()
        val checkingReceiver = CheckingAccount()
        checkingAcc.transferMoneyToSomeone(3250.0, AccountType.CHECKING, checkingReceiver)
        assertEquals(3000.0.toString(), checkingAcc.checkingBalance.toString())
        assertEquals(200.0.toString(), checkingAcc.remainingOverdraft.toString())
        assertEquals(0.0.toString(), checkingReceiver.checkingBalance.toString())
    }

    @org.junit.Test
    fun test9_transferMoneyToSomeone() {
        reload()
        val checkingReceiver = CheckingAccount()
        checkingAcc.transferMoneyToSomeone(100.0, AccountType.SAVINGS, checkingReceiver)
        assertEquals(3000.0.toString(), checkingAcc.checkingBalance.toString())
        assertNotEquals(100.0.toString(), checkingReceiver.checkingBalance.toString())
    }

    @org.junit.Test
    fun test10_transferMoneyToSomeone() {
        reload()
        val checkingReceiver = CheckingAccount()
        checkingAndSavingsAcc.transferMoneyToSomeone(1000.0, AccountType.SAVINGS, checkingReceiver)
        assertEquals(1000.0.toString(), checkingReceiver.checkingBalance.toString())
        assertEquals(500.0.toString(), checkingAndSavingsAcc.savingsAccount?.savingsBalance.toString())
    }

    @org.junit.Test
    fun test11_transferMoneyToSomeone() {
        reload()
        val savingsReceiver = SavingsAccount()
        checkingAndSavingsAcc.transferMoneyToSomeone(1000.0, AccountType.SAVINGS, savingsReceiver)
        assertEquals(1000.0.toString(), savingsReceiver.savingsBalance.toString())
        assertEquals(500.0.toString(), checkingAndSavingsAcc.savingsAccount?.savingsBalance.toString())
    }

    @org.junit.Test
    fun test12_transferMoneyToSomeone() {
        reload()
        val checkingAndSavingsReceiver = CheckingAccount()
        checkingAndSavingsAcc.transferMoneyToSomeone(1000.0, AccountType.SAVINGS, checkingAndSavingsReceiver)
        assertEquals(1000.0.toString(), checkingAndSavingsReceiver.checkingBalance.toString())
        assertEquals(500.0.toString(), checkingAndSavingsAcc.savingsAccount?.savingsBalance.toString())
    }

    @org.junit.Test
    fun test13_transferMoneyToSomeone() {
        reload()
        val checkingReceiver = CheckingAccount()
        checkingAndSavingsAcc.transferMoneyToSomeone(1550.0, AccountType.SAVINGS, checkingReceiver)
        assertNotEquals(1000.0.toString(), checkingReceiver.checkingBalance.toString())
        assertNotEquals(500.0.toString(), checkingAndSavingsAcc.savingsAccount?.savingsBalance.toString())
    }




    // convertMoneyToSavings

    @org.junit.Test
    fun test1_convertMoneyToSavingse() {
        reload()
        checkingAndSavingsAcc.convertMoneyToSavings(2000.0)
        assertEquals(3500.0.toString(), checkingAndSavingsAcc.savingsAccount?.savingsBalance!!.toString())
        assertEquals(1000.0.toString(),checkingAndSavingsAcc.checkingBalance.toString())
    }

    @org.junit.Test
    fun test2_convertMoneyToSavingse() {
        reload()
        checkingAndSavingsAcc.convertMoneyToSavings(3500.0)
        assertNotEquals(3500.0.toString(), checkingAndSavingsAcc.savingsAccount?.savingsBalance!!.toString())
        assertNotEquals(1000.0.toString(),checkingAndSavingsAcc.checkingBalance.toString())
    }

    @org.junit.Test
    fun test3_convertMoneyToSavingse() {
        reload()
        checkingAcc.convertMoneyToSavings(3500.0)
        assertNotEquals(3500.0.toString(), checkingAndSavingsAcc.savingsAccount?.savingsBalance!!.toString())
        assertNotEquals(1000.0.toString(),checkingAndSavingsAcc.checkingBalance.toString())
    }
}