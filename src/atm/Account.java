package atm;

/**
 * Program Name: ATM Machine
 * File ATMmachine.java
 * File Type: Contains methods needed for the program
 * Purpose: A mini-program that recreates the basic functions of an ATM machine.
 * 
 * @author Reynaldo Santos
 * @since 2018-11-17
 */

import javax.swing.JOptionPane;

public class Account {
    /*
     * Fields
     */
    private double balance;
    private int count;
    private double charge = 1.50;

    /*
     * Set & Get methods
     */
    public void setBalance(double balance) {
	this.balance = balance;
    }

    public double getBalance() {
	return this.balance;
    }

    // Checking account, subclass
    public class Checking extends Account {
	public Checking() {}
    }

    // Savings accounts, subclass
    public class Savings extends Account {
	public Savings() {}
    }

    /*
     * Additional Methods
     */
    // Withdraw action listener
    public void withdraw(double withdrawAmount) throws InsufficientFunds {
	// If amount input in balance becomes less than
	if (balance - withdrawAmount < 0) {
	    throw new InsufficientFunds();
	}

	balance = balance - withdrawAmount;
	count++;

	if (count >= 4) {
	    JOptionPane warning = new JOptionPane();
	    JOptionPane.showMessageDialog(warning,
		    "Exceeded free transactions. A $1.50 service will be applied to further withdrawals.");
	}

	if (count >= 5) {
	    balance = balance - charge;
	}
    }

    // Deposit action listener
    public void deposit(double depositAmount) {
	balance = balance + depositAmount;
    }

    // Transfer To action listener and adds input transfer amount.
    public void transferTo(double transferAmount) {
	balance = balance + transferAmount;
    }

    // Transfer To action listener and removes input transfer amount.
    public void transferFrom(double transferAmount) throws InsufficientFunds {
	// Checks to ensure sufficient funds
	if (balance - transferAmount < 0) {
	    throw new InsufficientFunds();
	}

	balance = balance - transferAmount;
    }
}
