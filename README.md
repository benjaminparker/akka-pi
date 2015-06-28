Calculating PI using multiple akka actors
=========================================
This is an example of using many workers to calculate PI. The messaging is done using Akka


Introduction
------------
To calculate PI we use the following formula

π/4 = 1 - 1/3 + 1/5 - 1/7 + 1/9 ...

Each 'worker' is responsible for a section of this series and reports back to the 'boss'.  The 'boss'
accumulates all the calculated values to give an approximation of PI.


Build and Deploy
----------------
The project is built with SBT

To compile:
> sbt compile

To run:
> sbt run 
