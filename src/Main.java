/**
 * @author Ryan Scherbarth
 *          This class handles scanning user input then transforming
 *         that input using the other classes to determine if the given
 *         information is correct. It continues to read input until the
 *         user enters "EXIT". Each line of input should have the following
 *         structure:
 *
 *         PAYMENTTYPE PAYMENTNUMBER PAYMENTAMOUNT\n
 *         PAYMENTTYPE: "CREDITCARD" | "CASH"
 *         PAYMENTNUMBER: Integer
 *         PAYMENTAMOUNT: Double
 */
//import java.text.NumberFormat;


//                                Test Cases
//  CREDITCARD 123456 40.0
// You're card number's check digit is invalid.
// CREDITCARD 12345 40.25
// The card number must be exactly 6 digits
// CREDITCARD 123455 40.25
// Valid Credit Card Number: 123455, amount: 40.25
// CREDITCARD 123455 4.0.0
//  4.0.0 is not a valid amount.
// CASH 1 40.25
// You're cash's serial number is not between 1,000,000 and 10,000,000.
// CASH 1000000 40.25
// Valid Cash Serial Number: 1000000, amount: 40.25
// CASH 1000000 4.0.0
//  4.0.0 is not a valid amount.
// WHAT 123455 40.25
// WHAT is not a recognized payment type.
// CREDITCARD where 40.25
// where is not a valid card/serial number.
// EXIT

import java.util.Scanner;
public class Main {
  /**
   * This function contains the loop to continuously read in
   * user input from the command line. It then hands off
   * PAYMENTTYPE, PAYMENTNUMBER, and PAYMENTAMOUNT to
   * {@link #handleInput(String, String, String)}
   * to be processed. The resulting {@link Payment} is then printed unless
   * it is null. The loop then begins again, the loop can only be stopped if
   * the user inputs "EXIT".
   *
   * @param args Command line arguments
   */
  public static void main(String[] args) {
    while(true){   // Long loop lol
        Scanner scnr = new Scanner(System.in);
        String paymentType = scnr.next();
        if(paymentType.equals("EXIT")){
          break;  // break out of program if they say "exit"
        }
        String paymentNumber = scnr.next();
        String paymentAmount = scnr.next();
        Payment userPayment = handleInput(paymentType, paymentNumber, paymentAmount);
        if(userPayment != null){
          System.out.println(userPayment.toString());
        }
      }
    }

  /**
   * This function handles each line of input and returns a Payment. If successful
   * then Payment will either be Cash or CreditCard. However, if an exception is
   * thrown
   * Payment will be null. It first tries to convert paymentAmount to a double
   * using
   * {@link #stringToAmount(String)}. If this fails it catches the
   * {@link InvalidAmountException}
   * and prints out: "$paymentAmount is not a valid amount.".
   *
   * If it succeeds then it moves
   * on to checking if the paymentType is valid. The two valid inputs for this
   * String are:
   * "CREDITCARD" or "CASH". If it is neither of these options then it prints out:
   * "$paymentType is not a recognized payment type.".
   *
   * If it is "CREDITCARD" then it converts paymentNumber to an int[] by
   * using the {@link #stringToCreditCardNumber(String)} function. If this
   * fails then it catches the {@link NumberFormatException} and prints out the
   * following message:
   * "$paymentNumber is not a valid card/serial number.". If it succeeds then it
   * creates a new CreditCard
   * and assigns it to payment.
   *
   * If it is "CASH" then it converts the paymentNumber to an int by using
   * the {@link #stringToCashSerialNumber(String)} function. If this fails then
   * it catches the {@link NumberFormatException} and prints out the following
   * message:
   * "$paymentNumber is not a valid card/serial number.". If it succeds then it
   * creates a new Cash
   * and assigns it to payment.
   *
   * @param paymentType   String from the user representing the type of payment.
   *                      Valid entries are
   *                      "CREDITCARD" or "CASH"
   * @param paymentNumber String from the user representing a payment number of
   *                      some kind. Valid entry
   *                      depends on paymentType
   * @param paymentAmount String from the user representing payment amount. It
   *                      must be a double
   * @return Validated payment
   */
  private static Payment handleInput(String paymentType, String paymentNumber, String paymentAmount) {
    // Fill in the logic given above, replace return null with your code.
    Double inputAmount = null;
    try{
      inputAmount = stringToAmount(paymentAmount);  // Convert payment amount from string to double

      if(!paymentType.equals("CREDITCARD") && !paymentType.equals("CASH")){
        System.out.println(paymentType + " is not a valid payment type.");  // Handle invalid payment type
        return null;
      }else if(paymentType.equals("CREDITCARD")){
        int[] ccNum = stringToCreditCardNumber(paymentNumber);  // Convert card number string to int array
        CreditCard userCredit = new CreditCard(inputAmount, ccNum);   // Create new credit card
        return userCredit;
        // How to assign to type payment??

      }else {
        int serialNum = stringToCashSerialNumber(paymentNumber);   // Convert paymentAmount to int
        Cash userCash = new Cash(inputAmount, serialNum);   // Create new user cash item
        return userCash;  // Return cash
      }
    }
    catch(InvalidAmountException e){  // user enters invalid amount
      System.out.println(paymentAmount + " is not a valid amount.");
    }
    catch(NumberFormatException f){   // user enters invalid card number
      if(paymentNumber.length() != 6){
        System.out.println("The card number must be exactly 6 digits");
      }else {
        System.out.println(paymentNumber + " is not a valid card/serial number.");
      }
    }
    catch(NullPointerException g){
      System.out.println("Null bro");
    }
    return null;
  }

  /**
   * Converts the given string to a credit card number represented as an int[].
   * By converting each character of the String into an int and storing them
   * in an array which it then returns.
   *
   * @param creditCardNumber String from the user representing the credit card
   *                         number
   * @return int[] where each element corresponds to each character of the input
   *         String
   * @throws NumberFormatException Occurs if an element of creditCardNumber is not
   *                               a number
   */
  private static int[] stringToCreditCardNumber(String creditCardNumber) throws NumberFormatException {
    // Fill in the logic given above, replace return null with your code.
    int[] creditCardNum = new int[6];   // Valid credit card numbers must be length 6 in this program
    // Catch invalid length
    if(creditCardNumber.length() != 6){
      throw new NumberFormatException();
    }
    for(int i=0; i<6; i++){
      creditCardNum[i] = creditCardNumber.charAt(i);
    }
    return creditCardNum;
  }

  /**
   * Attempts to convert serialNumber to an int using Integer.parseInt.
   *
   * @param serialNumber String from the user representing the serial number
   * @return int converted from serialNumber
   * @throws NumberFormatException Occurs if serialNumber is not a valid int
   */
  private static int stringToCashSerialNumber(String serialNumber) throws NumberFormatException {
    return Integer.parseInt(serialNumber);
  }

  /**
   * Attempts to convert amount to a double using Double.parseDouble.
   * If it fails it catches the NumberFormatException and throws an
   * InvalidAmountException with
   * the NumberFormatException passed to it.
   *
   * @param amount String from the user representing the payment amount
   * @return double converted from amount
   * @throws InvalidAmountException Occurs if amount is not a valid double
   */
  private static double stringToAmount(String amount) throws InvalidAmountException {
    // Fill in the logic given above, replace return 0 with your code.
    return Double.valueOf(amount);
  }
}
