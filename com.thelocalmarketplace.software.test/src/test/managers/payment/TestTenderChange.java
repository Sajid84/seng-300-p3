// Liam Major 30223023
// Jason Very 30222040
package test.managers.payment;

import com.jjjwelectronics.scanner.BarcodedItem;
import com.tdc.CashOverloadException;
import com.tdc.DisabledException;
import com.tdc.NoCashAvailableException;
import com.tdc.banknote.Banknote;
import com.tdc.banknote.IBanknoteDispenser;
import com.tdc.coin.Coin;
import com.tdc.coin.ICoinDispenser;
import com.thelocalmarketplace.hardware.ISelfCheckoutStation;
import org.junit.Before;
import org.junit.Test;
import powerutility.PowerGrid;
import stubbing.StubbedOrderManager;
import stubbing.StubbedPaymentManager;
import stubbing.StubbedStation;
import stubbing.StubbedSystemManager;
import utils.DatabaseHelper;

import javax.naming.OperationNotSupportedException;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Some of the tests are meant to show that it is not the end of the world if a dispenser is disabled.
 * For example, if I gave $20 for something that's $10, it shouldn't matter if the $50 dispenser is disabled.
 */
public class TestTenderChange {
    public ISelfCheckoutStation machine;
    public StubbedSystemManager sm;
    public StubbedPaymentManager pm;
    public StubbedOrderManager om;
    private final BigDecimal fiveCentDenom = StubbedStation.coinDenominations[1];
    private final BigDecimal oneDollarDenom = StubbedStation.coinDenominations[4];
    private final BigDecimal twoDollarDenom = StubbedStation.coinDenominations[5];
    private final BigDecimal fiveDollarDenom = StubbedStation.banknoteDenominations[0];
    private final BigDecimal tenDollarDenom = StubbedStation.banknoteDenominations[1];
    private final BigDecimal twentyDollarDenom = StubbedStation.banknoteDenominations[2];
    private final BigDecimal fiftyDollarDenom = StubbedStation.banknoteDenominations[3];
    private final long PRICE = 10;

    private void loadCoinsIntoMachine(int amount, BigDecimal denomination) throws CashOverloadException {
        ICoinDispenser dispenser = this.machine.getCoinDispensers().get(denomination);

        // checking the dispenser
        if (dispenser == null) {
            throw new RuntimeException("Tried to access a coin dispenser for a denomination that doesn't exist: " + denomination);
        }

        // creating an array of coins to load into the machine
        Coin[] coins = new Coin[amount];
        for (int i = 0; i < amount; i++) {
            coins[i] = new Coin(denomination);
        }

        // loading the coins into the dispenser
        dispenser.load(coins);
    }

    private void loadBanknotesIntoMachine(int amount, BigDecimal denomination) throws CashOverloadException {
        IBanknoteDispenser dispenser = this.machine.getBanknoteDispensers().get(denomination);

        // checking the dispenser
        if (dispenser == null) {
            throw new RuntimeException("Tried to access a coin dispenser for a denomination that doesn't exist: " + denomination);
        }

        // creating an array of coins to load into the machine
        Banknote[] notes = new Banknote[amount];
        for (int i = 0; i < amount; i++) {
            notes[i] = new Banknote(Currency.getInstance(Locale.CANADA), denomination);
        }

        // loading the coins into the dispenser
        dispenser.load(notes);
    }

    private void receiveCoin(BigDecimal denomination) throws DisabledException, CashOverloadException {
        machine.getCoinSlot().receive(new Coin(denomination));
    }

    private void receiveBanknote(BigDecimal denomination) throws DisabledException, CashOverloadException {
        machine.getBanknoteInput().receive(new Banknote(Currency.getInstance(Locale.CANADA), denomination));
    }

    @Before
    public void setup() throws OperationNotSupportedException {
        // configuring the hardware
        StubbedStation.configure();

        // creating the hardware
        machine = new StubbedStation().machine;
        machine.plugIn(PowerGrid.instance());
        machine.turnOn();

        // creating the stubs
        sm = new StubbedSystemManager(BigDecimal.ZERO);
        om = sm.omStub;
        pm = sm.pmStub;

        // configuring the managers
        sm.configure(machine);

        // Add item to order to get total price
        // Price of item is $10
        BarcodedItem item = DatabaseHelper.createRandomBarcodedItem(PRICE);
        om.addItem(item);
    }

    // No Coins in machine
    @Test(expected = NoCashAvailableException.class)
    public void testEmptyMachine() throws RuntimeException, NoCashAvailableException, CashOverloadException {
        pm.setPayment(50.00);

        // asserting we get an error here
        this.pm.tenderChange();
    }

    @Test(expected = NoCashAvailableException.class)
    public void testNotEnoughChangeInMachine()
            throws CashOverloadException, DisabledException, RuntimeException, NoCashAvailableException {
        // Load machine with 2 dollar coin
        loadCoinsIntoMachine(1, twoDollarDenom);

        // receiving payment
        receiveBanknote(fiftyDollarDenom);

        // this should fail
        this.pm.tenderChange();
    }

    @Test
    public void testPaymentEqualsTotal() throws CashOverloadException,
            DisabledException, RuntimeException, NoCashAvailableException {
        // Load machine with 2 dollar coins
        loadCoinsIntoMachine(10, twoDollarDenom);

        // Receives $10 from customer
        for (int i = 0; i < 5; i++) {
            receiveCoin(twoDollarDenom);
        }

        // No Change to give back, should return true
        assertTrue("No change needed", this.pm.tenderChange());
    }

    // tests if tenderChnage returns true when
    // change is returned
    @Test
    public void testReturnChange() throws CashOverloadException, DisabledException, RuntimeException, NoCashAvailableException {
        // Load machine with 2 dollar coins
        loadCoinsIntoMachine(10, twoDollarDenom);

        // receive payment
        receiveBanknote(twentyDollarDenom);

        // Change should be dispensed
        assertTrue("Change was dispensed", this.pm.tenderChange());
    }

    @Test(expected = RuntimeException.class)
    public void testPaymentLessThanPrice() throws CashOverloadException, DisabledException, RuntimeException, NoCashAvailableException {
        // loading coins into the machine
        loadCoinsIntoMachine(10, twoDollarDenom);

        // receiving payment
        receiveBanknote(fiveDollarDenom);

        // this should fail as we have not provided enough payment
        this.pm.tenderChange();
    }

    @Test
    public void disabledCoinDispenserHasEnoughMoney() throws CashOverloadException, DisabledException, RuntimeException, NoCashAvailableException {
        // loading the machine with loonies and toonies
        loadCoinsIntoMachine(10, oneDollarDenom);
        loadCoinsIntoMachine(5, twoDollarDenom);

        // disabling the fifty dollar denomination
        machine.getCoinDispensers().get(twoDollarDenom).disable();

        // receiving payment
        receiveBanknote(twentyDollarDenom); // making $10 in change

        // change should be emitted
        assertTrue(this.pm.tenderChange());

        // the attendant should be called that there was a disabled dispenser
        assertTrue("attendant was called", this.sm.notifyAttendantCalled);
    }

    @Test
    public void disabledCoinDispenserNotEnoughMoney() throws CashOverloadException, DisabledException, RuntimeException, NoCashAvailableException {
        // loading the machine with loonies and toonies
        loadCoinsIntoMachine(1, oneDollarDenom);
        loadCoinsIntoMachine(5, twoDollarDenom);

        // disabling the fifty dollar denomination
        machine.getCoinDispensers().get(twoDollarDenom).disable();

        // receiving payment
        receiveBanknote(twentyDollarDenom); // making $10 in change

        // change should be emitted
        assertFalse(this.pm.tenderChange());

        // the attendant should be called that there was a disabled dispenser
        assertTrue("attendant was called", this.sm.notifyAttendantCalled);
    }

    @Test
    public void disabledBanknoteDispsenserHasEnoughMoney() throws CashOverloadException, DisabledException, RuntimeException, NoCashAvailableException {
        // loading the machine with five dollar bills and ten dollar bills
        loadBanknotesIntoMachine(4, fiveDollarDenom);
        loadBanknotesIntoMachine(4, tenDollarDenom);

        // disabling the fifty dollar denomination
        machine.getBanknoteDispensers().get(tenDollarDenom).disable();

        // receiving payment
        receiveBanknote(twentyDollarDenom); // making $10 in change

        // change should be emitted
        assertTrue(this.pm.tenderChange());

        // the attendant should be called that there was a disabled dispenser
        assertTrue("attendant was called", this.sm.notifyAttendantCalled);
    }

    @Test
    public void disabledBanknoteDispsenserNotEnoughMoney()
            throws CashOverloadException, DisabledException, RuntimeException, NoCashAvailableException {
        // loading the machine with five dollar bills and ten dollar bills
        loadBanknotesIntoMachine(1, fiveDollarDenom);
        loadBanknotesIntoMachine(4, tenDollarDenom);

        // disabling the fifty dollar denomination
        machine.getBanknoteDispensers().get(tenDollarDenom).disable();

        // receiving payment
        receiveBanknote(twentyDollarDenom); // making $10 in change

        // only a five dollar bill should be emitted
        assertFalse(this.pm.tenderChange());

        // the attendant should be called that there was a disabled dispenser
        assertTrue("attendant was called", this.sm.notifyAttendantCalled);
    }

}
