Assignment - ATM Java Program
=================================

Instructions
-------------

This assignment will require You to create an application that addresses
the problem described below.

Your application must be written in Java,

Your application may have a Web UI, or it can run on the Command Line.

This assignment is focused on the Backend, so any Frontend "sugar" won't be
considered as beneficial in this assignment.

All source code must be delivered as part of the solution, as well as clear
instructions on how to build and run your application.

Your application and instructions must be targeted at either the Linux or Mac
platform (not Windows).


If you are not able to provide a completely working solution for this assignment,
you will still gain credit points based on your code, and the approach that you
have taken as the critical part of the assessment.

Evidence of a true Test Driven Development approach will earn you extra credit
points.

Correct use of appropriate design patterns will also ern you extra credit points.

Incorrect use of design patterns, or tests that add no value will be considered
a negative.


If you are using libraries and/or frameworks, please provide a small
explanation of the library/framework, and why they added value to your
solution.





Requirements:
-------------

Write an application that simulates the Backend logic of a cash dispensing
Automatic Teller Machine (ATM).

Of course the application is not required to distribute money, but it should be
able to simulate and report the outcome of people requesting money.

This simulation will not require any authentication or PIN to access the ATM.

Rather it is to be focused on keeping track of the current cash of the ATM,
and dispensing only the notes available.

It should be possible to tell it that it has so many of each type of note during
initialisation. After initialisation, it is only possible to remove notes.

It must know how many of each type of bank note it has and it should be able
to report back how much of each note it has.

It must support $20 and $50 notes.

It should be able to dispense only legal combinations of notes. For example, a
request for $100 can be satisfied by either five $20 notes or two $50 notes. It
is not required to present a list of options.

If a request cannot be satisfied due to failure to find a suitable combination of
notes, it should report an error condition in some fashion. For example, in an
ATM with only $20 and $50 notes, it is not possible to dispense $30.

Dispensing money should reduce the amount of available cash in the
machine.

Failure to dispense money due to an error should not reduce the amount of
available cash in the machine.




BUILD
-----

To build and install into local repo ...

    $> mvn clean install

To run tests ...

    $> mvn clean test

To run from console ...

    $> mvn exec:java




PROGRAMMATIC USAGE
------------------

Build an ATM

    Atm.Builder builder = new Atm.Builder();
	builder.addNotes(Note.$10).withQuantityOf(1);
    builder.addNotes(Note.$20).withQuatityOf(10);
    builder.addNotes(Note.$50).withQuantityOf(20);
    builder.singleThreadCapable();
    Atm atm = builder.build();


To withdraw from ATM

    Withdrawal withdrawal = atm.withdraw(30);
    withdrawal.isSuccessful(); // tells if ATM has enough notes to return the withdrawal amount
    System.out.println(withdrawal); // print withdrawal details

An example of ``Withdrawal``'s ``toString()`` is as follows

	Attempting to retrieve 100 
	Retrieval outome success
	Notes 
	 $50 x 1
	 $10 x 5
	Outstanding 0

To print summary of ATM

    Summary summary = atm.summary();
    System.out.println(summary); // print summary of atm

An example of ``Summary``'s ``toString()``  is as follows

	Notes currently in atm 
     $10 x 0 
     $20 x 3 
     $50 x 0 


	

CONSOLE USAGE
-------------

To run program in console

    $> mvn exec:java

be sure to make sure the artifact (atm jar file) is in your local repository. You could do so 
by running

	$> mvn clean install

which should run the tests as well, if you wish to skip the tests, run

	$> mvn clean install -Dmaven.test.skip=true


Example of one execution ...

    $> mvn exec:java
    [INFO] Scanning for projects...
    [INFO]
    [INFO] ------------------------------------------------------------------------
    [INFO] Building ATM 1.0.0-SNAPSHOT
    [INFO] ------------------------------------------------------------------------
    [INFO]
    [INFO] --- exec-maven-plugin:1.3:java (default-cli) @ atm ---
    [WARNING] Warning: killAfter is now deprecated. Do you need it ? Please comment on MEXEC-6.
    creating ATM ...
    quantities of $10 (enter an integer followed by enter): 0
    quantities of $20 (enter an integer followed by enter): 5
    quantities of $50 (enter an integer followed by enter): 2
    multithread (y/n followed by enter)? y
    created atm ... ok
    s for atm summary, w for withdrawal, e to end followed by enter: s
    Notes currently in atm
     $10 x 0
     $20 x 5
     $50 x 2
    s for atm summary, w for withdrawal, e to end followed by enter: w
    sum to withdraw (an integer followed by enter)? 100
    Attempting to retrieve 100
    Retrieval outome success
    Notes
     $50 x 2
    Outstanding 0
    s for atm summary, w for withdrawal, e to end followed by enter: s
    Notes currently in atm
     $10 x 0
     $20 x 5
     $50 x 0
    s for atm summary, w for withdrawal, e to end followed by enter: w
    sum to withdraw (an integer followed by enter)? 10
    Attempting to retrieve 10
    Retrieval outome failed
    Notes
      -- none --
    Outstanding 10
    s for atm summary, w for withdrawal, e to end followed by enter: w
    sum to withdraw (an integer followed by enter)? 1
    Attempting to retrieve 1
    Retrieval outome failed
    Notes
      -- none --
    Outstanding 1
    s for atm summary, w for withdrawal, e to end followed by enter: s
    Notes currently in atm
     $10 x 0
     $20 x 5
     $50 x 0
    s for atm summary, w for withdrawal, e to end followed by enter: w
    sum to withdraw (an integer followed by enter)? 40
    Attempting to retrieve 40
    Retrieval outome success
    Notes
     $20 x 2
    Outstanding 0
    s for atm summary, w for withdrawal, e to end followed by enter: s
    Notes currently in atm
     $10 x 0
     $20 x 3
     $50 x 0
    s for atm summary, w for withdrawal, e to end followed by enter: 60
    invalid input must be either [s, w, e]
    s for atm summary, w for withdrawal, e to end followed by enter: w
    sum to withdraw (an integer followed by enter)? 60
    Attempting to retrieve 60
    Retrieval outome success
    Notes
     $20 x 3
    Outstanding 0
    s for atm summary, w for withdrawal, e to end followed by enter: s
    Notes currently in atm
     $10 x 0
     $20 x 0
     $50 x 0
    s for atm summary, w for withdrawal, e to end followed by enter: e
    Bye! ...
    [INFO] ------------------------------------------------------------------------
    [INFO] BUILD SUCCESS
    [INFO] ------------------------------------------------------------------------
    [INFO] Total time: 1:30.855s
    [INFO] Final Memory: 9M/305M
    [INFO] ------------------------------------------------------------------------

