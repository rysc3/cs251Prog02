/**
 * @author Ryan Scherbarth
 *         This class represents a credit card payment.
 *         It holds the card number as an int[].
 *         It verifies three properties (in this order):
 *         1. That the int[] only holds ints in the following range: [0,9]
 *         2. That the length of the int[] is exactly 6
 *         3. That the check digit is correct
 *
 *         The check digit is the last digit of the card number that verifies
 *         that the other 5 digits are correct. This number is computed with
 *         the Luhn algorithm (https://en.wikipedia.org/wiki/Luhn_algorithm).
 */
public class CreditCard extends Payment {
  private final int[] cardNumber;
  public CreditCard(double amount, int[] cardNumber) throws IllegalArgumentException {
    super(amount);
    this.cardNumber = cardNumber;
    // Perform the following verification:
    // 1. Use verifyCardDigits to check that all ints in card number
    // are in the following range: [0,9]. If not then throw an
    // IllegalArgumentException
    // with the following String: "The card number must consist of numbers in the
    // following range: [0,9]"
    // 2. Check that the card number length is exactly 6. If not then throw an
    // IllegalArgumentException
    // with the following String: "The card number must be exactly 6 digits"
    // 3. Use verify to check that the check digit is correct in the card number. If
    // it is not VALID then
    // throw an IllegalArgumentException with the toString of verification.
  }
  /**
   * This function verifies that the cardNumber only has
   * ints in the following range: [0,9].
   *
   * @return True if all ints are in the above range false otherwise.
   */
  private boolean verifyCardDigits() {
    // Fill in the logic given above, replace return false with your code.
    if(this.cardNumber.length != 6){  // Throw error if length isn't exactly 6
      System.out.println("The card number must be exactly 6 digits.");
      return false;
    }
    for (int i = 0; i < cardNumber.length; i++) {   // Loop through cardNumber array
      if(cardNumber[i] != (int)cardNumber[i]){    // Make sure each digit is actually an int
        return false;
      }
      if (cardNumber[i] < 0 || cardNumber[i] > 9 || cardNumber.length != 6) { // Each digit between 0 & 9, length == 6
        System.out.println("VerifyCardDigits False 2");
        return false;
      }
    }
    return true;    // If no errors are caught then we gucci
  }
  /**
   * This function sums the digits of the given int.
   * For example if given 123 it would return 6.
   *
   * @param num Int to sum the digits of
   * @return Sum of the digits of the given int
   */
  private int sumDigits(int num) {
    if(num < 4){
      return num;
    }else{
      num += num;
      String digits = String.valueOf(num);    // Comically long way of converting int to int array etc.
      System.out.println(digits);
      int[] nums = {Character.getNumericValue(digits.charAt(0)), Character.getNumericValue(digits.charAt(1))};
      System.out.println("num1: " + nums[0]);
      System.out.println("num 2: " + nums[1]);
      return (nums[0] + nums[1]);
    }
  }
  /**
   * This function verifies that cardNumber's check
   * digit is correct using the Luhn algorithm.
   *
   * @return VALID if the check digit is correct, INVALIDCARDNUMBER otherwise
   */
  @Override
  protected PaymentVerification verify() {
    verifyCardDigits();   // Check that digits are all valid
    int checkDigit = this.cardNumber[this.cardNumber.length-1];   // Establish check digit
    int[] newArr = new int[this.cardNumber.length];   // New arr is the card num without check digit
    newArr[newArr.length-1] = checkDigit;   // Set check digit
    int newArrSum = 0;  // Used to calculate checkdigit
    int i=this.cardNumber.length-2;   // Used to create while loop to loop backwards through the cardNum
    while(i > -1){
      System.out.println(" IGNORE Card Number: " + this.cardNumber[i]);
      if(i % 2 == 0){   // Values that are in the even positions (to be multiplied by 2)
        newArr[i] = sumDigits(this.cardNumber[i] * 2);  // send every value to sumDigits after doubling
        newArrSum += newArr[i];
        System.out.println(i + " sum arr: " + newArr[i]);
      }else{  // Handle the odd positions
        newArr[i] = this.cardNumber[i];   // do nothing if it is in an odd position
        newArrSum += newArr[i];
        System.out.println(i + " non sum Arr: " + newArr[i]);
      }
      i--;
    }
    // Check if the sum matches check digit
    System.out.println("Check digit: " + checkDigit);
    System.out.println("Arr Sum: " + newArrSum);
    if(((10 - (newArrSum % 10)) % 10) == checkDigit){   // Rest of luhm's algo
      return PaymentVerification.VALID;
    }else {
      return PaymentVerification.INVALIDCARDNUMBER;
    }
  }
  /**
   * This function checks if the cardNumber of this matches the card
   * number of o.
   *
   * @param o Other CreditCard to check if equal to this
   * @return True if o's cardNumber is equal to this.cardNumber
   */
  @Override
  public boolean equals(Object o) {
    // Fill in the logic given above, replace return false with your code.
    if(this.cardNumber != o){
      return false;
    }
    return true;
  }
  /**
   * This function converts this to a string
   *
   * @return "Valid Credit Card Number: $CARDNUMBER, amount: {@link #amount}"
   */
  @Override
  public String toString() {
    String cardNum = "";  // Use string for cardNumber since its easier to concatenate
    for(int i=0; i<cardNumber.length; i++){
      cardNum += cardNumber[i];   // Build new cardnumber string (probably could have done this in an easier way idfk)
    }
    if(this.verify() == PaymentVerification.VALID){
      return "Valid Credit Card Number: " + cardNum + ", amount: " + this.amount;   // Say SUCCESS if valid
    }else{
      return PaymentVerification.INVALIDCARDNUMBER.toString();  // Say not success if not valid lol
    }
  }
}
