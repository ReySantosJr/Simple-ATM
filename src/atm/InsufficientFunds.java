package atm;

/**
 * Program Name: ATM Machine
 * File ATMmachine.java
 * File Type: Let's user know of "insufficient funds"
 * Purpose: A mini-program that recreates the basic functions of an ATM machine.
 * 
 * @author Reynaldo Santos
 * @since 2018-11-17
 */
 
import javax.swing.*;

// If the result would be negative
public class InsufficientFunds extends Exception {
    public InsufficientFunds() {
        JOptionPane frame = new JOptionPane();
        JOptionPane.showMessageDialog(frame, "Insufficient Funds.");
    }
}

