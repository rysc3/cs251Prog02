/**
 * This class represents a cash payment.
 * It holds a serial number that must be in the
 * following range: [1000000, 10000000].
 */
public class Cash extends Payment {
    private final int serialNumber;

    public Cash(double amount, int serialNumber) throws IllegalArgumentException {
        super(amount);
        this.serialNumber = serialNumber;

        PaymentVerification verification = verify();
        // TODO: If verification is not VALID then throw an IllegalArgumentException
        //       with the toString of verification
    }

    /**
     * This function verifies that the serial number is in the following
     * range: [1000000, 10000000]
     * @return VALID if in range, INVALIDSERIALNUMBER if not
     */
    @Override
    protected PaymentVerification verify() {
        //TODO: Fill in the logic given above, replace return null with your code.
        return null;
    }

    // TODO: Override equals from Object. It should return true if
    //       both serial numbers are equal, e.g. this.serialNumber == o.serialNumber
    //       where o is the other Cash object.

    /**
     * This function converts this to a string
     * @return "Valid Cash Serial Number: SERIALNUMBER, amount: {@link #amount}"
     */
    @Override
    public String toString() {
        // TODO: Fill in the logic given above, replace return null with your code.
        return null;
    }
}
