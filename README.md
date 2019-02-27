In this App I show 2 major types of bank accounts: Checking account and Savings account, also a 3rd kind of types that is a combination of both.

The “concrete” 2 classes are “CheckingAccount” and “SavingsAccount”, they do different things  but they share common functionalities. Also a bank client with a “CheckingAccount” can one day upgrade to have a way to have a Checking and a Saving accounts or a client with a Savings account can one day also upgrade to have a Savings and Checking accounts.

This is why I decided the use the concept of abstract class to implement the logic of bank accounts. The reason is that when I make an abstract superclass “Account” that I use later to create subclasses from it “CheckingAccount” and “SavingsAccount”, then I can put in this abstract superclass some methods that have implementation and that will be called automatically by any object of any subclass. Meanwhile abstract class allows me to force all subclass to override the methods that are marked “abstract”.

In my program, I created an abstract class “Account” and 2 subclasses from it: “CheckingAccount” and “SavingsAccount”.

These 2 subclasses should override some methods that are marked “abstract” for example getBalance() but will do completely different things because the “CheckingAccount” will return the “checkingBalance” and the “SavingsAccount” will return the “savingBalance”.

But what if the account is at the same time “Checking” and “Savings”? This is why I decided to add a property to the abstract superclass “Account” that indicates the type of the account and according to that types, particularly when the account is at the same times “Checking” and ‘Saving”, to behave differently such as when getBalance() it will return the sum of “checkingBalance” and “savingsBalance”.

This allows me to always create as in real life as such “at the same time Checking and Savings” account using any of the constructors of “CheckingAccount” or “SavingsAccount” classes, then upgrading that account at any time when the client wants to become both types “Checking” and “Savings”

For this scenario, I designed the abstract superclass “Account” to hold all the fields (or properties) existing in the both classes “CheckingAccount” and “SavingsAccount”, but that are initially set with default values such as  “checkingBalance” and “savingsBalance” equals to Zero. This allows me to be able later to convert any account of one type, to become both types at the same time.

The constructors of each of these subclasses initialize the concerning fields of the abstract superclass, then when needed by the client, we can initialize the fields related to the other type of subclass. When this is the case, then I update the type of the account from “Checking” or “Savings” to “CheckingAndSavings”. So every account instance created using whether the constructor of  “CheckingAccount” or the constructor of “SavingsAccount” will be one day of types “Checking” or “Savings” or a combination of both when the account is upgraded to be “CheckingAndSavings”.

In kotlin it is much easier to create instances using a set of parameters from a parent superclass and then later update or set other parameters if we need. The power of Kotlin over Java are so many, one of them is called “constructor with “default values of parameters”, this allows you to call all the constructors possible with any combination of parameters and number of parameters by just reducing one or more parameters from the end, or by using the other powerful concept in Kotlin which is “named parameters” and this allows you to combine the first concept concept “constrictors with default values” and “named parameters” to put in your constructor call any order of parameters and any number of parameters 

My software design approach in this very specific case of being able to convert from one type of account to include both types, I didn’t add specific properties to the subclasses in my app any extra properties, but I just allowed the constructors of subclasses to initialize the properties of superclass. This is how I can offer the possibility to convert, or to “add” a Checking” account to an existing “Savings” account, or a “Savings” account to an existing “Checking” account. 

As in real life when we go to the bank we most probably open an account of type “CheckingAccount”, then maybe at some point of time we say that we would like to start “saving money” and get benefits from them. So in that case we need to create a “Saving” account using our current “Checking” account. In that case I make 2 method that is allows the conversion by just updating the type of account in superclass and initializing the related properties.

To convert from a “CheckingAccount” to a “both checking & saving” account, I update the type of account in superclass “Account” and then I initialize the savingBalance of the superclass by taken money from the checkingbalance (or by taking extra money) to add to the savingsBalance and I set the yearlyInterestRate so I can later get / calculate the yearlyBenefit and monthlyBenefit

To convert from a “SavingsAccount” to a “both savings and checking” account, I update the type of account in the superclass “Account” and then I initialize the checkingBalance of the superclass by taking money from the savingsBalance (or by taking extra money) to add to the checkingBalance and I set the overdraftLimit for that client (usually according to his salary range) and I set the remainingOverdraft that tells how much yet the account owner can withdraw from the overdraftLimit

How I proceeded and decided which methods a shared and do the same for any type of account and which have same name but do different things and which have different names but exist only for a certain type of accounts? I started by writing all the possible methods that can exist, then I found out for each method which type of account is concerned. Here is how I did:

Checking = C
Savings = S
Checking/Savings = CS


getBalance()   for C  S  CS
addMoney()   for C  S  CS
withdrawMoney()   for C  S  CS
transferMoneyToSomeone()   for C  S  CS

getOverdraftLimit()   for C  CS
getRemainingOverdraft()   for C  CS

getInterestRatePerYear()    for S  CS
getBenefitPerYear ()    for S  CS
getBenefitPerMonth ()    for S  CS

convertMoneyToChecking()   for CS
convertMoneyToSavings()    for CS


----- Methods design -----

getBalance()  

It is for all the types of accounts C  S  CS
- When it is called by a type c, then it returns the checkingBalance
- When it is called by a type s, then it returns the savingsBalance
- When it is called by a type c, then it returns the checkingBalance + savingsBalance
*** So it’s abstract written in abstract superclass “Account” then overridden in “CheckingAccount” and “SavingsAccount”

addMoney()  

It is for all the types of accounts C  S  CS
- When it is called by a type c, then it will add money to the checkingBalance
- When it is called by a type s, then it will add money to the savingsBalance
- When it is called by a type c, then it will add money to the checkingBalance only
*** So it’s abstract written in abstract superclass “Account” then overridden in “CheckingAccount” and “SavingsAccount”

withdrawMoney()

It is for all the types of accounts C  S  CS
- When it is called by a type c, then it will withdraw money from the (checkingBalance + remainingOverdraft). The overdraftLimit is what the bank allows to withdraw when our checkingBalance reached 0
- When it is called by a type s, then it will withdraw money from the savingsBalance till it finishes and if savingsBalance is not enough, then don’t complete the withdrawal 
- When it is called by a type c, then it will withdraw money from the checkingBalance till it finishes and if checkingBalance not enough, then withdraw from the remainingOverdraft and if remainingOverdraft is not enough, then withdraw from the savingsBalance 
*** So it’s abstract written in abstract superclass “Account” then overridden in “CheckingAccount” and “SavingsAccount”

transferMoneyToSomeone()  

It is for all the types of accounts C  S  CS
- When it is called by a type c, then it will transfer to another person account money from the (checkingBalance + remainingOverdraft). The overdraftLimit is what the bank allows to withdraw when our checkingBalance reached 0
- When it is called by a type s, then it will transfer to another person account money from the savingsBalance till it finishes and if savingsBalance is not enough, then don’t complete the transfer
- When it is called by a type c, then it will transfer to another person account money from the checkingBalance till it finishes and if checkingBalance not enough, then withdraw from the remainingOverdraft and if remainingOverdraft is not enough, then withdraw from the savingsBalance 
*** So it’s abstract written in abstract superclass “Account” then overridden in “CheckingAccount” and “SavingsAccount”

.... more


------------ screenshots --------------

Homescreen to allow create 3 types of account

![screenshot_1551250383](https://user-images.githubusercontent.com/20923486/53471874-63fb4e80-3a66-11e9-903a-a08ab83742b9.png)


Bank Dashboard

![screenshot_1551250397](https://user-images.githubusercontent.com/20923486/53471938-a1f87280-3a66-11e9-85f8-66d8616e80f0.png)


Modify the global yearlyInterestRate

![screenshot_1551250418](https://user-images.githubusercontent.com/20923486/53471976-be94aa80-3a66-11e9-983c-c790e7c42228.png)


Create Owner

![screenshot_1551250452](https://user-images.githubusercontent.com/20923486/53472010-d79d5b80-3a66-11e9-95ba-61d78bcd36fb.png)


Create only CheckingAccount type for this App

![screenshot_1551250497](https://user-images.githubusercontent.com/20923486/53472056-edab1c00-3a66-11e9-8d77-a1e26c95f4b2.png)


When finish, update Homepage and show animation

![screenshot_1551250688](https://user-images.githubusercontent.com/20923486/53472095-04ea0980-3a67-11e9-81a7-c2983b07e60a.png)


Remaining options for creating accounts for the App

![screenshot_1551250772](https://user-images.githubusercontent.com/20923486/53472132-1b906080-3a67-11e9-93a7-a270cc1bbe85.png)


Create a “both Checking and Savings” account

![screenshot_1551250807](https://user-images.githubusercontent.com/20923486/53472157-32cf4e00-3a67-11e9-9a68-5301fd718014.png)


When finish, update Homepage and show animation

![screenshot_1551250825](https://user-images.githubusercontent.com/20923486/53472202-4975a500-3a67-11e9-86cc-9a53619c0816.png)


Remaining options for creating accounts for the App

![screenshot_1551250852](https://user-images.githubusercontent.com/20923486/53472223-5db9a200-3a67-11e9-9a40-7d9645d60fc2.png)


Create only SavingAccount for this App

![screenshot_1551250885](https://user-images.githubusercontent.com/20923486/53472270-7924ad00-3a67-11e9-9149-6107573824ce.png)


When finish, update Homepage and show animation and update console

<img width="720" alt="screenshot_1551250892" src="https://user-images.githubusercontent.com/20923486/53473200-02d57a00-3a6a-11e9-82b6-9e0f1d16f221.png">


