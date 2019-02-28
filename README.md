In this App I show 2 major types of bank accounts: CheckingAccount and SavingsAccount, also a 3rd kind of that is a combination of both for clients who have the 2 types. This App demonstrates how these different types of accounts are created and how they interact with each other’s.

I wanted to show how Kotlin is powerful when we use it in Android projects, especially how we can have implicitly contractors created for us when we use a primary constructor in the head of the class that has its parameters initialized by default values. This alone can allow us to call any constructor with any number of parameters without explicitly overloading constructors.

Another powerful point that goes hand in hand with the parameters initialized by default values is the concept of “named parameters”. This allows us to pass any order we want of parameters to a function without caring what is its original signature.

Both concepts allow us to save a lot of boilerplate code!

I wanted to show how the concept of abstract methods can be applied in my context, because the different types of bank accounts have some common functionalities that have the same implementations and some others methods that do different behavior but should necessary exist in all the classes. This is where the abstract class concept from Kotlin can help us. In fact Kotlin also has abstract variables which doesn’t exist in Java!

Concerning the App, I made a quite nice concept of interaction, I used Fragments inflations and animations and many SDK nice topics you can see in the screenshots

I made a bank component on the App represented by Square that will initialize / create the 3 types of Accounts


How I proceeded and decided which methods a shared and do the same for any type of account and which have same name but do different things and which have different names but exist only for a certain type of accounts? I started by writing all the possible methods that can exist, then I found out for each method which type of account is concerned. Here is how I did:

Checking = C
Savings = S
Checking/Savings = CS


// abstract methods in superclass
getBalance()  -- for C  S  CS
addMoney()  -- for C  S  CS
withdrawMoney()  -- for C  S  CS
transferMoneyToSomeone()  -- for C  S  CS

//in super and implemented
getInterestRatePerYear()   -- for   C S  CS

// by Kotlin
getOverdraftLimit()  -- for C  CS       
getRemainingOverdraft()  -- for C  CS   

// subclass CheckingAccount
getBenefitPerYear ()   -- for S  CS     
getBenefitPerMonth ()   -- for S  CS   
convertMoneyToSavings()   -- for CS

// subclass SavingsAccount
convertMoneyToChecking()  -- for CS



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


![screenshot_1651250885]( https://user-images.githubusercontent.com/20923486/53473423-a2930800-3a6a-11e9-9490-3726fa57d89f.png) 


