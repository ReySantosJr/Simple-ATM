package atm;

/**
 * Program Name: ATM Machine
 * File ATMmachine.java
 * File Type: Contains Constructor and Main Method. 
 * Purpose: A mini-program that recreates the basic functions of an ATM machine.
 * 
 * @author Reynaldo Santos
 * @since 2018-11-17
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class ATMmachine extends JFrame {
    /*
     * Fields 
     */
     /* JFrame Components */
    // Buttons for program
    private JButton withdrawButton = new JButton("Withdraw");
    private JButton depositButton = new JButton("Deposit");
    private JButton transferToButton = new JButton("Transfer to");
    private JButton balanceButton = new JButton("Balance");

    // Radio buttons
    private JRadioButton checkingRadio = new JRadioButton("Checking");
    private JRadioButton savingsRadio = new JRadioButton("Savings");

    // Text label & field for money input
    private JTextField entry = new JTextField(20);

    // Button group
    private ButtonGroup radios = new ButtonGroup();
    private JOptionPane frame = new JOptionPane();

    // Checking and savings account
    private static Account checking = new Account().new Checking();
    private static Account savings = new Account().new Savings();

    // Format needed for money transfer
    private static DecimalFormat df = new DecimalFormat("$0.00");

    /*
     * Constructor
     */
    public ATMmachine(double checkingStartingBalance, double savingsStartingBalance) {
	// Window Dimensions
	super("Santos Bank - ATM");
	setSize(350, 250);
	setLocationRelativeTo(null);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	// Layout setup
	setLayout(new GridBagLayout());

	// Other GUI vars
	JPanel buttonPanel = new JPanel();
	JPanel textEntryPanel = new JPanel();
	Box vertBox = Box.createVerticalBox();

	// Var setups
	buttonPanel.setLayout(new GridLayout(3, 2));
	vertBox.setPreferredSize(new Dimension(300, 150));
	textEntryPanel.setBorder(BorderFactory.createTitledBorder("Enter amount:"));
	checkingRadio.setSelected(true);
	radios.add(checkingRadio);
	radios.add(savingsRadio);

	// Add vars to panels
	textEntryPanel.add(entry);
	buttonPanel.add(checkingRadio);
	buttonPanel.add(savingsRadio);
	buttonPanel.add(withdrawButton);
	buttonPanel.add(depositButton);
	buttonPanel.add(transferToButton);
	buttonPanel.add(balanceButton);

	// Add frames to main JFrame. then add main JFrame to window
	vertBox.add(textEntryPanel);
	vertBox.add(buttonPanel);
	add(vertBox);

	// Makes checking and savings accounts
	makeAccounts(checkingStartingBalance, savingsStartingBalance);

	// Action listeners established
	withdrawButton.addActionListener(new WithdrawButtonListener());
	depositButton.addActionListener(new DepositButtonListener());
	transferToButton.addActionListener(new TransferToButtonListener());
	balanceButton.addActionListener(new BalanceButtonListener());

	// Display default checking and saving account balance
	JOptionPane.showMessageDialog(frame,
		"Checking account: " + checkingStartingBalance + "\n" + "Savings account: " + savingsStartingBalance,
		"CURRENT BALANCE", JOptionPane.INFORMATION_MESSAGE);

    }// END OF Constructor

    // Method for check and saving
    public static void makeAccounts(double checkingStartingBalance, double savingsStartingBalance) {
	checking.setBalance(checkingStartingBalance);
	savings.setBalance(savingsStartingBalance);
    }

    // Error for invalid input other than a positive numerical value
    public void errorValidNumber() {
	JOptionPane.showMessageDialog(frame, "Invalid Entry. Withdraw, use $20 increments.");
    }

    /*
     * Action Listeners
     */
    // Withdraw button, ActionListener
    class WithdrawButtonListener implements ActionListener {
	public void actionPerformed(ActionEvent e) {
	    try {
		// to check for negative number & increments of 20
		if (recievedValue() > 0 && recievedValue() % 20 == 0) {
		    // to check radio button choice
		    if (checkingRadio.isSelected()) {
			checking.withdraw(recievedValue());
			JOptionPane.showMessageDialog(frame, df.format(recievedValue()) + " withdrawn from Checking.");
		    }

		    else if (savingsRadio.isSelected()) {
			savings.withdraw(recievedValue());
			JOptionPane.showMessageDialog(frame, df.format(recievedValue()) + " withdrawn from Savings.");
		    }

		    clearEntryValue();
		}

		else
		    errorValidNumber();
		clearEntryValue();

	    } catch (InsufficientFunds insufficientFunds) {
		System.out.println("Error");
	    }
	}

    } // END OF class WithdrawButtonListener

    /*
     * Deposit Button Class, ActionListener
     */
    class DepositButtonListener implements ActionListener {
	public void actionPerformed(ActionEvent e) {
	    // To check for a positive number
	    if (recievedValue() > 0) {

		// To check for radio button choice
		if (checkingRadio.isSelected()) {
		    checking.deposit(recievedValue());
		    JOptionPane.showMessageDialog(frame, df.format(recievedValue()) + " deposited in Checking.");
		}

		else if (savingsRadio.isSelected()) {
		    savings.deposit(recievedValue());
		    JOptionPane.showMessageDialog(frame, df.format(recievedValue()) + " deposited in Savings.");
		}

		clearEntryValue();
	    }

	    else
		errorValidNumber();
	    clearEntryValue();
	}

    } // END OF class DepositButtonListener

    /*
     * Transfer Button Class, ActionListener
     */
    class TransferToButtonListener implements ActionListener {
	public void actionPerformed(ActionEvent e) {
	    try {
		// To check for a positive number
		if (recievedValue() > 0) {
		    // Checks for radio selection
		    if (checkingRadio.isSelected()) {
			// Methods for transferFrom and transferTo
			savings.transferFrom(recievedValue());
			checking.transferTo(recievedValue());
			JOptionPane.showMessageDialog(frame,
				df.format(recievedValue()) + " transferred from Savings to Checking.");
		    }

		    else if (savingsRadio.isSelected()) {
			checking.transferFrom(recievedValue());
			savings.transferTo(recievedValue());
			JOptionPane.showMessageDialog(frame,
				df.format(recievedValue()) + " transferred from Checking to Savings.");
		    }

		    clearEntryValue();
		}

		else
		    errorValidNumber();
		clearEntryValue();

	    } catch (InsufficientFunds insufficientFunds) {
		System.out.println("Error");
	    }
	}

    } // END OF class TransferToButtonListener

    /*
     * Balance Button Class, Action listener
     */
    class BalanceButtonListener implements ActionListener {
	public void actionPerformed(ActionEvent e) {
	    // To check radio selection
	    if (checkingRadio.isSelected()) {
		JOptionPane.showMessageDialog(frame, "Checking account balance: \n" + df.format(checking.getBalance()));
	    }

	    else if (savingsRadio.isSelected()) {
		JOptionPane.showMessageDialog(frame, "Savings account balance: \n" + df.format(savings.getBalance()));
	    }

	    else
		errorValidNumber();
	    clearEntryValue();
	}

    } // END OF class BalanceButtonListener

    /*
     * Methods Needed
     */
    // This method returns the text in the text entry field
    public double recievedValue() {
	try {
	    return Double.parseDouble(entry.getText());
	} catch (NumberFormatException e) {
	    System.out.println("Invalid Input.");
	    clearEntryValue();
	    return 0;
	}
    }

    // Clears the text entry field
    public void clearEntryValue() {
	entry.setText("");
    }

    // Displays window
    public void display() {
	setVisible(true);
    }

    /*
     * MAIN METHOD
     */
    public static void main(String[] args) {
	// $340 in checking, 120 in savings
	ATMmachine frame = new ATMmachine(340, 120);
	frame.display();
    }

} // END OF class ATMmachine
