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

package stubbing;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.jjjwelectronics.card.Card.CardData;
import com.thelocalmarketplace.hardware.ISelfCheckoutStation;
import com.thelocalmarketplace.hardware.external.CardIssuer;

import managers.SystemManager;
import enums.SessionStatus;
import utils.Pair;

public class StubbedSystemManager extends SystemManager {

	public static final CardIssuer issuer = new CardIssuer("testing", 10);
	public static final BigDecimal leniency = BigDecimal.ZERO;

	public StubbedPaymentManager pmStub;
	public StubbedOrderManager omStub;
	public StubbedAttendantManager amStub;
	private String attendantNotification;

	public boolean notifyAttendantCalled;
	public boolean blockSessionCalled;
	public boolean unblockSessionCalled;
	public boolean getStateCalled;
	public boolean recordTransactionCalled;

	public StubbedSystemManager() {
		this(issuer, leniency);
	}

	public StubbedSystemManager(CardIssuer i) {
		this(i, leniency);
	}

	public StubbedSystemManager(BigDecimal l) {
		this(issuer, l);
	}

	public StubbedSystemManager(CardIssuer i, BigDecimal l) {
		super(i, l);

		// creating stubbed managers
		omStub = new StubbedOrderManager(this, l);
		pmStub = new StubbedPaymentManager(this, i);
		amStub = new StubbedAttendantManager(this);

		// injecting stubbed managers for testing purposes
		this.om = omStub;
		this.pm = pmStub;
		this.am = amStub;

		// variables to track if a function was called or not
		notifyAttendantCalled = false;
		blockSessionCalled = false;
		unblockSessionCalled = false;
		getStateCalled = false;
		recordTransactionCalled = false;
	}

	public void setIssuer(CardIssuer i) {
		super.issuer = i;
		pmStub.setIssuer(i);
	}

    public ISelfCheckoutStation getMachine() {
		return machine;
	}

	@Override
	public void notifyAttendant(String reason) {
		notifyAttendantCalled = true;
		this.attendantNotification = reason;
		super.notifyAttendant(reason);
	}

	@Override
	public void blockSession() {
		blockSessionCalled = true;
		super.blockSession();
	}

	@Override
	public void unblockSession() {
		unblockSessionCalled = true;
		super.unblockSession();
	}

	@Override
	public SessionStatus getState() {
		getStateCalled = true;
		return super.getState();
	}

	public String getAttendantNotification() {
		return this.attendantNotification;
	}

	@Override
	public void setState(SessionStatus s) {
		super.setState(s);
	}

	public Map<String, List<Pair<Long, Double>>> getRecords() {
		return super.records;
	}

	@Override
	public void recordTransaction(CardData card, Long holdnumber, Double amount) {
		recordTransactionCalled = true;
		super.recordTransaction(card, holdnumber, amount);
	}

	public boolean getDisabledRequest() {
		return super.disabledRequest;
	}
}
