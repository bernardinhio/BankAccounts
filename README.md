This App shows how we can benefit from the concept of abstract classes with Kotlin. I developed the program about bank accounts and I created different types that can also change the type if they are upgraded.

I tried to make a nice UI to simulate the creation of 3 sorts of Accounts:
Checking account
Savings account 
Checking-Savings account

I simulate the creation of Owner and account and then adding data to a main page where the container is an account that will “shake” or make animation when it is created. I tried to make a reactive approach for the programing by hiding / unhiding data fields, also by make floating activities that act as a popup but are in fact activities when we can write complex code. All this is done with Kotlin which is my preferable language at the present.

The logic of the Accounts system and the operations methods are all written and tested which a Unit test that I created. I started using this logic in one of the “Dashboard” activities that represents the CheckingAccount, but didn’t finish yet implementing all the App dashboards.

The following is a brief listing of the methods I wrote to deal with the different sorts of Accounts:

  --- common -- abstract
     getBalance
     addMoney
     withdrawMoney
     transferMoneyToSomeone
     convertMoneyFromMyCheckingToMySavings
     convertMoneyFromMySavingsToMyChecking

     --- common -- Non-abstract
    --> not overriden in CheckingAccount
    --> IS overriden in SavingsAccount
    getDetailsOfYearlyInterestRate

    ----- specific method > CheckingAccount
     getOverdraftStatus

     ----- specific method > SavingsAccount
     getBenefitFromInterest

This App demonstrates how these different types of bank accounts are created and how they interact with each other’s. I created an abstract superclass “Account” that offers some properties inherited by its concrete subclasses CheckingAccount and SavingsAccount who have their own extra properties. Also the same for the methods, the abstract class offers some methods implemented that are inherited by the subclasses “CheckingAccount” and “SavingsAccount”. Who themselves have their own specialized methods.

The reason of using abstract class for “Account” is to force all the subclasses that inherit from it, to override and offer implementation to a certain number of methods, but that those methods do different things when they are called by different types of instance.

For example, the abstract class “Account” forces all its subclasses “ChecingAccount” and “SavingsAccount” to override these methods:

     getBalance
     addMoney
     withdrawMoney
     transferMoneyToSomeone
     convertMoneyFromMyCheckingToMySavings
     convertMoneyFromMySavingsToMyChecking

All of these functions can be called by any instance of any subclass, but do different things

The abstract superclass “Account” offers one implemented method that has the same behavior in the subclasses, so I don’t need to re-write it again in every subclass, so I write it in the abstract superclass. The method is:

getDetailsOfYearlyInterestRate ()

this is needed when any instance of the subclass CheckingAccount wants to convert its type to “Savings and Checking” and needs that information that is global for all kinds of accounts. Also this information is needed by any instance of the subclass “SavingsAccount” if the client needs to know the interest rate of the bank.

Each of the subclasses “CheckingAccount” and “SavingsAccount” can convert money from its checkingBalance to its savingsBalance and vice versa, ONLY if that account is of type “Checking and Savings” at the same time

I created many enum classes in Kotlin, one of the strong concepts is to add methods to any or all enum Items, or even a static method to the enu class itself.

In Kotlin we can put so many classes together in one file on the root, so I put all the enum classes related to Account inside the Account abstract class.

I used many powerful concepts of Kotlin such as primary constructors that have the fields initialized by default values which will allow calling all the combination of constructor’s whether by number of parameters or the order of types of parameters.


--------- Some screenshots ---------

Homepage

![screenshot_1551662521](https://user-images.githubusercontent.com/20923486/53705374-50643500-3e24-11e9-9b15-12aeed225210.png) 


Dashboard Bank, floating Activity and Fragments

![screenshot_1551662560](https://user-images.githubusercontent.com/20923486/53705385-670a8c00-3e24-11e9-8a8b-a147addfcb7e.png) 


Create Account Fragment

![screenshot_1551662613](https://user-images.githubusercontent.com/20923486/53705403-87d2e180-3e24-11e9-8f57-b1eb9f176d4e.png) 


Create Account Fragment

![screenshot_1551662729](https://user-images.githubusercontent.com/20923486/53705441-cb2d5000-3e24-11e9-8c23-8d4d8035a05e.png)


Reactive data display for each type of account

![screenshot_1551662776](https://user-images.githubusercontent.com/20923486/53705463-ed26d280-3e24-11e9-8e56-1dbd98d550a2.png)


Animate – shaking when an account is created

![screenshot_1551662865](https://user-images.githubusercontent.com/20923486/53705481-219a8e80-3e25-11e9-8db2-a99d3458cba4.png)


Three types of accounts created and added with animation and console update

![screenshot_1551663052](https://user-images.githubusercontent.com/20923486/53705527-92da4180-3e25-11e9-8a0c-2cde6a84d719.png)


Interactive Items that expand on click

![screenshot_1551663217](https://user-images.githubusercontent.com/20923486/53705595-ee0c3400-3e25-11e9-8b8a-0dfa4808d755.png) 


This App needs more work to be finalized, but I hope very soon I can do that!
