package bernardo.bernardinhio.kotlinclasstypesInbankingapp

import bernardo.bernardinhio.kotlinclasstypesInbankingapp.logic.AccountType
import bernardo.bernardinhio.kotlinclasstypesInbankingapp.logic.CheckingAccount
import bernardo.bernardinhio.kotlinclasstypesInbankingapp.logic.Owner
import bernardo.bernardinhio.kotlinclasstypesInbankingapp.logic.SavingsAccount

fun main(args : Array<String>){

    /**
     * --- Steps---
     *
     * --- Preparation ---
     * I created 2 utility functions to reuse in this program
     * to handle inputs from users:
     * bernardo.bernardinhio.kotlinclasstypesInbankingapp.getStringInputNotNullNotEmpty()
     * bernardo.bernardinhio.kotlinclasstypesInbankingapp.chooseBetweenTwo()
     *
     * --- Scenario ---
     * The program starts by either the creation of bernardo.bernardinhio.kotlinclasstypesInbankingapp.logic.Account or
     * the creation of Client (account owner)
     */

    /**
    val welcomeAction = chooseBetweenTwo("Hello Employee\nCreate Client (c) or create bank account (b)?", "c", "b")

    when(welcomeAction){
        "c" -> println("client creation...")
        "b" -> println("bank-account creation...")
    }

    **/

    val checking = CheckingAccount(12345.6, 5438.9, 54.9)
    println(checking.checkingBalance)
    println(checking.overdraftLimit)

    val savings = SavingsAccount(5666777.7, 3.43, 0.0)
    println(savings.savingsBalance)
    println(savings.yearlyInterestRate)


    fun createNewClient() : Owner = Owner()

    Owner()
    Owner("Bernard")
    Owner("Bernard", "Karam")
    Owner("Bernard", "Karam", "date")
    Owner("Bernard", "Karam", "date", "some nation")
    Owner("Bernard", "Karam", "date", "some nation", "any address")
    Owner("Bernard", "Karam", "date", "some nation", "any address", "any occupation")


    // with 1 parameter = 9
    // with 2 parameters = 9 * (9-1)
    // with 3 parameters = 9 * (9-1) * (9-2)
    // with 4 parameters = 9 * (9-1) * (9-2) * (9-3)
    // with 5 parameters = 9 * (9-1) * (9-2) * (9-3) * (9-4)
    // with 6 parameters = 9 * (9-1) * (9-2) * (9-3) * (9-4) * (9-5)
    // with 7 parameters = 9 * (9-1) * (9-2) * (9-3) * (9-4) * (9-5) * (9-6)
    // with 8 parameters = 9 * (9-1) * (9-2) * (9-3) * (9-4) * (9-5) * (9-6) * (9-7)
    // with 9 parameters = 9 * (9-1) * (9-2) * (9-3) * (9-4) * (9-5) * (9-6) * (9-7) * (9-8=1)

    // TEMPLATE fun createNewClient20() : bernardo.bernardinhio.kotlinclasstypesInbankingapp.logic.Owner = bernardo.bernardinhio.kotlinclasstypesInbankingapp.logic.Owner(firstName = "Bernard", lastName = "Karam", dateOfBirth = "date", nationality = "some nation")

    //with 1 parameter
    fun createNewClient2() : Owner = Owner("Bernard")
    fun createNewClient3() : Owner = Owner(lastName = "Karam")
    fun createNewClient4() : Owner = Owner(dateOfBirth = "date")
    fun createNewClient5() : Owner = Owner(nationality = "some nation")
    fun createNewClient6() : Owner = Owner(address = "any address")
    fun createNewClient7() : Owner = Owner(occupation = "any occupation")
    fun createNewClient8() : Owner = Owner(account = CheckingAccount())
    fun createNewClient9() : Owner = Owner(accountType = AccountType.CHECKING)





    val owner = Owner()
    val owner1 = Owner("", "")
    val owner2 = Owner(lastName = "")



    val checking2 : CheckingAccount = CheckingAccount()
    val checking1 : CheckingAccount = CheckingAccount(2000.0)
    val checking3 : CheckingAccount = CheckingAccount(2000.0, 1000.0, 34.8)

    // access superclass properties
    checking2.id
    checking2.dateCreated
    checking2.owner

    // access superclass fields
    checking2.type  // yes modified in superclass init
    checking2.checkingBalance  // yes modified in superclass init

    // access specific subclass fields
    checking2.overdraftLimit





    val savings1 : SavingsAccount = SavingsAccount()
    val savings2 : SavingsAccount = SavingsAccount(200.0)
    val savings3 : SavingsAccount = SavingsAccount(200.0, 5.0, 0.0)

    // access superclass properties
    savings2.id
    savings2.dateCreated
    savings2.owner

    // access superclass fields
    savings2.type  // yes modified in superclass init
    savings2.checkingBalance  // yes modified in superclass init

    // access specific subclass fields
    savings2.savingsBalance
    savings2.yearlyInterestRate






}


private fun getStringInputNotNullNotEmpty(message : String) : String{
    var input : String?
    do {
        println(message)
        println()
        input  = readLine()
    } while (input != null && !input.isEmpty())
    return input!!.toLowerCase()
}

private fun chooseBetweenTwo(message : String, option1 : String, option2 : String) : String{
    var input : String?
    do {
        println(message)
        println()
        println()
        input  = readLine()
    } while (
            input.isNullOrBlank()
            || (!input.toLowerCase().equals(option1.toLowerCase())
                    && !input.toLowerCase().equals(option2.toLowerCase())))
    return input.toLowerCase()
}