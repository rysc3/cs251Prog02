* @param paymentAmount String from the user representing payment amount. It
*                      must be a double
* @return Validated payment
*/
private static Payment handleInput(String paymentType, String paymentNumber, String paymentAmount) {
 // TODO: Fill in the logic given above, replace return null with your code.

 // Convert payment amount to a double
 Double inputAmount = null;
 try{
   inputAmount = Double.valueOf(paymentAmount);
 }
 catch(InvalidAmountException e){
   System.out.println(paymentAmount + " is not a valid amount."); // If converting causes an error, print that.
 }

 // Check valid payment types
 if(!paymentType.equals("CREDITCARD") && !paymentType.equals("CASH")){
   System.out.println(paymentType + " is not a recognized payment type."); // Catch invalid payment type
 }else if(paymentType.equals("CREDITCARD")){
   try{
     int[] ccNum = stringToCreditCardNumber(paymentNumber); // Convert cc number to int array
     return new CreditCard(inputAmount, ccNum);
   }
   catch(NumberFormatException e){
     System.out.println(paymentNumber + " is not a valid card/serial number.");
   }

 }
}
