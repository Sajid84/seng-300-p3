//Emmanuel Trinidad 30172372

package test.managers.payment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;
import java.util.Map;

import com.tdc.DisabledException;
import com.thelocalmarketplace.hardware.ISelfCheckoutStation;
import com.thelocalmarketplace.hardware.external.CardIssuer;

import ca.ucalgary.seng300.simulation.SimulationException;
import managers.enums.PaymentType;

import org.junit.Before;
import org.junit.Test;

import com.jjjwelectronics.EmptyDevice;
import com.jjjwelectronics.OverloadedDevice;
import com.jjjwelectronics.card.Card;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.tdc.CashOverloadException;
import com.tdc.banknote.Banknote;
import com.tdc.banknote.BanknoteDispenserBronze;
import com.tdc.banknote.IBanknoteDispenser;
import com.tdc.coin.Coin;
import com.tdc.coin.CoinDispenserBronze;
import com.tdc.coin.ICoinDispenser;
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.hardware.BarcodedProduct;

import powerutility.PowerGrid;
import stubbing.StubbedAttendantManager;
import stubbing.StubbedOrderManager;
import stubbing.StubbedPaymentManager;
import stubbing.StubbedStation;
import stubbing.StubbedSystemManager;
import utils.CardHelper;
import utils.DatabaseHelper;

public class TestPredictLow {

	public ISelfCheckoutStation machine;

	// vars
	private StubbedPaymentManager pm;
	private StubbedSystemManager sm;
	private StubbedAttendantManager am;
	private StubbedOrderManager om;
	private Map<BigDecimal, ICoinDispenser> disps;
	private ICoinDispenser disp;
	private IBanknoteDispenser bDisp;
	

	public Banknote fiveNote;
	public Coin coin;

	private CardIssuer issuer;
	private Card card;
	
	@Before
	public void setup() {
		// configuring the hardware
		StubbedStation.configure();

		// creating the hardware
		this.machine = new StubbedStation().machine;
		this.machine.plugIn(PowerGrid.instance());
		this.machine.turnOn();

		// creating the stubs
		issuer = CardHelper.createCardIssuer();
		sm = new StubbedSystemManager(issuer);
		pm = sm.pmStub;
		am = sm.amStub;
		om = sm.omStub;
		
		card = CardHelper.createCard(issuer);

		// configuring the machine
		sm.configure(machine);

		fiveNote = new Banknote(Currency.getInstance(Locale.CANADA), new BigDecimal(5.00));
		coin = new Coin(Currency.getInstance(Locale.CANADA), new BigDecimal(1.00));
	
		//get dispenser for testing predict low/notify empty
		disp = this.machine.getCoinDispensers().get(BigDecimal.valueOf(1));
		bDisp = this.machine.getBanknoteDispensers().get(new BigDecimal(5.00));
	}
	
	/**tests checking if ink is notified to be low when under 10%
	 * not sure why this isn't working, tried forcing device to start with under 10% ink
	 * 
	 * @throws OverloadedDevice
	 * @throws EmptyDevice
	 */
	@Test
	public void testNotifyInkLow() throws OverloadedDevice, EmptyDevice {
		
		//max ink is some extremely high amount
		this.machine.getPrinter().addInk(4000);
		this.machine.getPrinter().addPaper(1000);
		
		//print something and check variable
		StringBuilder sb = new StringBuilder();
		sb.append("x");
		
		pm.printLine(sb.toString());
		
		//not working for some reason??
		//when ink is low, variable should be true as ink is under 10%
		assertTrue(am.isInkLow());
	}
		
	/**tests checking if paper is notified to be low when under 10%
	 * not sure why not working, starts off with under 10% but after print 
	 * 
	 * @throws OverloadedDevice
	 */
	@Test
	public void testNotifyPaperLow() throws OverloadedDevice {
		
		this.machine.getPrinter().addInk(500000);
		
		//103 is (1 << 10) / 10
		this.machine.getPrinter().addPaper(100);
		
		BarcodedItem item = DatabaseHelper.createRandomBarcodedItem();
		BarcodedProduct prod = DatabaseHelper.get(item);
		om.addItem(item);
		pm.setPayment(prod.getPrice());

		// pay and check if printed
		pm.printReceipt(PaymentType.CARD, card);
		
		//check if paperLow is true after under 10% paper
		assertTrue(am.isPaperLow());
	}
	
	
	/**tests trying to notify state change in coin dispenser that is not related to machine
	 * 
	 * @throws IllegalArgumentException
	 */
	@Test (expected = IllegalArgumentException.class)
	public void testInvalidCoinDenom() {
		
		CoinDispenserBronze x = new CoinDispenserBronze(5);

		//pass in random coin dispenser not connected to machine
		am.notifyCoinDispenserStateChange(x);
		
	}
	
	
	/**tests if attendant is notified that coin dispenser is empty
	 * 
	 * @throws CashOverloadException
	 * @throws DisabledException
	 */
	@Test
	public void testNotifyEmptyDispenser() throws CashOverloadException, DisabledException {
		disp.unload();
		
		//upon unload should emit message that dispenser empty
		assertEquals("The 1 coin dispenser is empty.", am.getAttendantNotification());
		assertTrue(am.notifyAttendantCalled);
	}
	
	/**tests if attendant is notified coin dispenser is almost empty
	 * 
	 * @throws DisabledException
	 * @throws CashOverloadException
	 */
	@Test
	public void testAlmostEmptyDispenser() throws DisabledException, CashOverloadException {
		disp.receive(coin);

		assertEquals("The 1 coin dispenser is less than 10% full.", am.getAttendantNotification());
		assertTrue(am.notifyAttendantCalled);

	}
	/**
	 * tests trying to notify a state change in a banknote dispenser that is not related to the machine
	 * 
	 * @Throws IllegalArgumentException
	 */
	@Test (expected = IllegalArgumentException.class)
	public void testInvalidBanknoteDenom() {
		
		BanknoteDispenserBronze x = new BanknoteDispenserBronze();
		
		am.notifyBanknoteDispenserStateChange(x);
	}
	
	/**tests if attendant notified that banknote dispenser is empty
	 * 
	 * 
	 * @throws DisabledException
	 * @throws CashOverloadException
	 */
	@Test
	public void testNoBanknote() throws DisabledException, CashOverloadException {

		bDisp.load(fiveNote);
		bDisp.unload();
		
		//adding and unloading a banknote should trigger empty
		//gets memory address of dispenser each time so just checks if string contains word "empty"
		assertTrue(am.getAttendantNotification().contains("empty"));
		assertTrue(am.notifyAttendantCalled);

	}
	
	/**tests if attendant notified that banknote dispenser is lessthan 10% full
	 * 
	 * @throws SimulationException
	 * @throws CashOverloadException
	 * @throws DisabledException
	 */
	@Test
	public void testLowBanknote() throws SimulationException, CashOverloadException, DisabledException {
		//only 1 banknote in storage - should say storage low
		bDisp.load(fiveNote);
		
		//cannot get actual denomination name of banknote dispenser - gives random letters
		assertTrue(am.getAttendantNotification().contains("10% full"));
		assertTrue(am.notifyAttendantCalled);
	}
	
	
	/**tests if attendant notified that banknote dispenser is full
	 * 
	 * @throws SimulationException
	 * @throws CashOverloadException
	 * @throws DisabledException
	 */
	@Test
	public void testFullBanknote() throws SimulationException, CashOverloadException, DisabledException {
		//fill storage
		for (int i = 0; i < (this.machine.getBanknoteStorage().getCapacity()); i++) {
			this.machine.getBanknoteStorage().load(fiveNote);
		}
				
		//test for if notified full
		assertEquals("The banknote storage unit is full.", am.getAttendantNotification());
		assertTrue(am.notifyAttendantCalled);

	}
	
	/**tests if attendant notified banknote dispenser is more than 90% full
	 * 
	 * @throws DisabledException
	 * @throws CashOverloadException
	 */
	@Test
	public void testHighBanknote() throws DisabledException, CashOverloadException {
		for (int i = 0; i < (this.machine.getBanknoteStorage().getCapacity() * 0.90); i++) {
			// capacity - 2 should return higher than 90%
			this.machine.getBanknoteStorage().load(fiveNote);
		}
						
		//test for if notified full - attendant should be called and notify should be true??
		assertEquals("The banknote storage unit is over 90% full.", am.getAttendantNotification());
		assertTrue(am.notifyAttendantCalled);

	}
	
	/**tests if atttendant notified coin dispenser is full
	 * 
	 * @throws SimulationException
	 * @throws CashOverloadException
	 */
	@Test
	public void testFullCoin() throws SimulationException, CashOverloadException {
		//fill strage
		for (int i = 0; i < this.machine.getCoinStorage().getCapacity(); i++) {
			this.machine.getCoinStorage().load(coin);
		}
		
		//attendant should be called, checks notifyAttendantCalled
		assertEquals("The coin storage unit is full.", am.getAttendantNotification());
		assertTrue(am.notifyAttendantCalled);
		
	}
	
	/**tests if attendant notified coin dispenser is 90% full or higher
	 * 
	 * @throws SimulationException
	 * @throws CashOverloadException
	 */
	@Test
	public void testHighCoin() throws SimulationException, CashOverloadException {
		//inserts coins to almost max capacity
		for (int i = 0; i < (this.machine.getCoinStorage().getCapacity() * 0.90); i++) {
			this.machine.getCoinStorage().load(coin);
		}
		
		//should give this message
		assertEquals("The coin storage unit is over 90% full.", am.getAttendantNotification());
		assertTrue(am.notifyAttendantCalled);

	}
	
}
