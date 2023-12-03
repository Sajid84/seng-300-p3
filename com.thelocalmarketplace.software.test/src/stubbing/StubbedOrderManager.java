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

import com.jjjwelectronics.Item;
import com.jjjwelectronics.scale.IElectronicScale;
import com.thelocalmarketplace.hardware.ISelfCheckoutStation;

import managers.OrderManager;
import enums.ScanType;
import enums.SessionStatus;
import managers.interfaces.IOrderManagerNotify;
import observers.order.BarcodeScannerObserver;
import observers.order.ScaleObserver;
import utils.Pair;

public class StubbedOrderManager extends OrderManager {
	public StubbedSystemManager smStub;
	public boolean addItemToOrderCalled;
	public boolean addCustomerBagsCalled;
	public boolean getTotalPriceCalled;
	public boolean getExpectedMassCalled;
	public boolean getProductsCalled;
	public boolean onAttendantOverrideCalled;
	public boolean onDoNotBagRequestCalled;
	public boolean removeItemFromOrderCalled;

	public StubbedOrderManager(StubbedSystemManager sm, BigDecimal leniency) {
		super(sm, leniency);

		smStub = sm;

		addItemToOrderCalled = false;
		addCustomerBagsCalled = false;
		getTotalPriceCalled = false;
		getExpectedMassCalled = false;
		getProductsCalled = false;
		onAttendantOverrideCalled = false;
		onDoNotBagRequestCalled = false;
		removeItemFromOrderCalled = false;
	}

	public BarcodeScannerObserver getMainBarcodeObserver() {
		return super.main_bso;
	}

	public BarcodeScannerObserver getHandheldBarcodeObserver() {
		return super.handheld_bso;
	}

	public ScaleObserver getBaggingAreaObserver() {
		return super.baggingarea_so;
	}

	public List<IElectronicScale> getOverloadedScales() {
		return super.overloadedScales;
	}

	public List<IOrderManagerNotify> getListeners() {
		return super.listeners;
	}

	public BigDecimal getLeniency() {
		return super.leniency;
	}

	public void setActualWeight(BigDecimal a) {
		super.actualWeight = a;
	}

	public BigDecimal getActualWeight() {
		return super.actualWeight;
	}

	@Override
	public BigDecimal getWeightAdjustment() {
		return super.getWeightAdjustment();
	}

	@Override
	public void setWeightAdjustment(BigDecimal a) {
		super.setWeightAdjustment(a);
	}
	
	public void addItem(Item i) {
		addItem(i, true);
	}

	public void addItem(Item i, boolean donotbag) {
		super.items.add(new Pair<>(i, donotbag));
	}

	public void setState(SessionStatus s) {
		smStub.setState(s);
	}

	@Override
	public void checkWeightDifference(BigDecimal difference) {
		super.checkWeightDifference(difference);
	}

	public boolean getBagItem() {
		return super.bagItem;
	}

	public ISelfCheckoutStation getMachine() {
		return super.machine;
	}

	private boolean configured;

	@Override
	public void configure(ISelfCheckoutStation machine) {
		super.configure(machine);

		configured = true;
	}

	public boolean getConfigured() {
		return configured;
	}

	@Override
	public void addItemToOrder(Item item, ScanType method) {
		addItemToOrderCalled = true;
		super.addItemToOrder(item, method);
	}

	@Override
	public void addCustomerBags(Item bags) throws IllegalArgumentException {
		addCustomerBagsCalled = true;
		super.addCustomerBags(bags);
	}

	@Override
	public BigDecimal getTotalPrice() {
		getTotalPriceCalled = true;
		return super.getTotalPrice();
	}

	@Override
	public BigDecimal getExpectedMass() {
		getExpectedMassCalled = true;
		return super.getExpectedMass();
	}

	@Override
	public List<Pair<Item, Boolean>> getItems() {
		getProductsCalled = true;
		return super.getItems();
	}

	@Override
	public void onAttendantOverride() {
		onAttendantOverrideCalled = true;
		super.onAttendantOverride();
	}

	@Override
	public void doNotBagRequest(boolean bagRequest) {
		onDoNotBagRequestCalled = true;
		super.doNotBagRequest(bagRequest);
	}

	public void removeItemFromOrder(Item item) {
		removeItemFromOrder(new Pair<>(item, false));
	}

	@Override
	public void removeItemFromOrder(Pair<Item, Boolean> pair) {
		removeItemFromOrderCalled = true;
		super.removeItemFromOrder(pair);
	}

}
