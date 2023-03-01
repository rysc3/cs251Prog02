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

    // TODO: Perform the following verification:
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
    for (int i = 0; i < cardNumber.length; i++) {
      if (cardNumber[i] < 0 || cardNumber[i] > 9 || cardNumber.length != 6) { // Each digit between 0 & 9, length == 6
        return false;
      }
    }
    return true;
  }

  /**
   * This function sums the digits of the given int.
   * For example if given 123 it would return 6.
   *
   * @param num Int to sum the digits of
   * @return Sum of the digits of the given int
   */
  private int sumDigits(int num) {
    // Fill in the logic given above, replace return 0 with your code.
    String original = Integer.toString(num);  // Convert to string
    int[] retArr = new int[original.length()];  // convert from string to array of digits
    int retVal = 0;
    for(int i=0; i<retArr.length; i++){
      retVal += retArr[i];    // Loop through each digit in array and add the next value
    }
    return retVal;  // Return value
  }

  /**
   * This function verifies that cardNumber's check
   * digit is correct using the Luhn algorithm.
   *
   * @return VALID if the check digit is correct, INVALIDCARDNUMBER otherwise
   */
  @Override
  protected PaymentVerification verify() {
    // Loop through entire card number
    int SOE = 0; // Sum of even
    int SOO = 0; // Sum of odd
    for(int i=0; i<cardNumber.length; i++){
      if(i % 2 == 0){   // Even positions
        if(cardNumber[i] < 5){
          SOE += (2 * cardNumber[i]);   // multiply by 2 and add to total if single digit
        }else {
          int temp = (2 * cardNumber[i]);   // Multiply by 2
          SOE += sumDigits(temp);   // Add sum of digits to running total
        }
      }else {   //Odd positions
        if(cardNumber[i] < 5){  // if single digit
          SOO += (2 * cardNumber[i]);
        }else {
          int temp = (2 * cardNumber[i]);
          SOO += sumDigits(temp);
        }
      }
    }
    // if total sum == 100, valid, else input is invalid
    if((SOE + SOO) % 10 == 0){
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
    // Fill in the logic given above, replace return null with your code.
    return "Valid Credit Card Number: " + this.cardNumber + ", ammount: " + this.amount;
  }
}
