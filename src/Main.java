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
import java.io.*;
import java.util.*;
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
        String paymentType = scnr.next();   // Accept user input
        if(paymentType.equals("EXIT")){
          break;  // break out of program if they say "exit"
        }
        String paymentNumber = scnr.next();
        String paymentAmount = scnr.next();
        Payment userPayment = handleInput(paymentType, paymentNumber, paymentAmount);   // Throw user input to handleInput
        if(userPayment != null){
          System.out.println(userPayment.toString());   // Handle error of no input given
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
      inputAmount = Double.valueOf(paymentAmount);  // Convert payment amount from string to double
      if(inputAmount != (double)inputAmount){   // Check that payment amount is a valid number
        System.out.println(paymentAmount + " is not a valid amount.");
      }
      if(!paymentType.equals("CREDITCARD") && !paymentType.equals("CASH")){
        System.out.println(paymentType + " is not a valid payment type.");  // Handle invalid payment type
        return null;
      }else if(paymentType.equals("CREDITCARD")){
        int[] ccNum = stringToCreditCardNumber(paymentNumber);  // Convert card number string to int array
        System.out.println("Main 122 ccNum handleInput: " + Arrays.toString(ccNum));
        CreditCard userCredit = new CreditCard(inputAmount, ccNum);   // Create new credit card
        return userCredit;
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
      }else if (inputAmount == null|| inputAmount != (double)inputAmount ){
        System.out.println(paymentAmount + " is not a valid amount.");
      }else {
        System.out.println(paymentNumber + " is not a valid card/serial number.");
      }
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
    int[] creditCardArr = new int[creditCardNumber.length()];   // Create new array
    for(int i=0; i<creditCardNumber.length(); i++){
      creditCardArr[i] = Integer.parseInt(String.valueOf(creditCardNumber.charAt(i)));  // Fill arrray
    }
    return creditCardArr;
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
