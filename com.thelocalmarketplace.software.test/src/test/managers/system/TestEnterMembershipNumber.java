// Liam Major			- 30223023
// Md Abu Sinan			- 30154627
// Ali Akbari			- 30171539
// Shaikh Sajid Mahmood	- 30182396
// Abdullah Ishtiaq		- 30153185
// Adefikayo Akande		- 30185937
// Alecxia Zaragoza		- 30150008
// Ana Laura Espinosa Garza - 30198679
// Anmol Bansal			- 30159559
// Emmanuel Trinidad	- 30172372
// Gurjit Samra			- 30172814
// Kelvin Jamila		- 30117164
// Kevlam Chundawat		- 30180662
// Logan Miszaniec		- 30156384
// Maleeha Siddiqui		- 30179762
// Michael Hoang		- 30123605
// Nezla Annaisha		- 30123223
// Nicholas MacKinnon	- 30172737
// Ohiomah Imohi		- 30187606
// Sheikh Falah Sheikh Hasan - 30175335
// Umer Rehman			- 30169819
package test.managers.system;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.math.BigDecimal;

import javax.naming.OperationNotSupportedException;

import org.junit.*;

import com.jjjwelectronics.card.Card;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.thelocalmarketplace.hardware.ISelfCheckoutStation;
import com.thelocalmarketplace.hardware.external.CardIssuer;

import enums.*;
import stubbing.*;
import utils.CardHelper;

public class TestEnterMembershipNumber {
	private StubbedPaymentManager pm;
	private StubbedSystemManager sm;
	private StubbedOrderManager om;
	private ISelfCheckoutStation machine;
	private Card msc;
	private CardIssuer issuer;
	private Card nonMSC;

	@Before
	public void setup() {
		// configuring the hardware
		StubbedStation.configure();

		// creating the hardware
		machine = new StubbedStation().machine;
		machine.plugIn(StubbedGrid.instance());
		machine.turnOn();

		// creating the stubs
		sm = new StubbedSystemManager(BigDecimal.ZERO);
		pm = sm.pmStub;
		om = sm.omStub;
		
		//creating the cards
		msc = CardHelper.createMembershipCard();
		issuer = CardHelper.createCardIssuer();
		nonMSC = CardHelper.createCard(issuer);

		// configuring the machine
		sm.configure(machine);

	}
	
	@Test
	public void testIsMembership() {
		assertTrue(CardHelper.isMembership(msc));
		assertFalse(CardHelper.isMembership(nonMSC));
	}
	
	
	@Test
	public void testInputMSNviaSwipe() throws IOException {
		pm.notifyCardDataRead(msc.swipe());
		assertEquals(sm.getMembershipData().getNumber(), msc.number);
		assertEquals(sm.getMembershipData().getType(), "membership");
	}
	
	@Test
	public void testInputMSNviaTap() throws IOException {
		pm.notifyCardDataRead(msc.tap());
		assertEquals(sm.getMembershipData().getNumber(), msc.number);
		assertEquals(sm.getMembershipData().getType(), "membership");
	}
		
}
