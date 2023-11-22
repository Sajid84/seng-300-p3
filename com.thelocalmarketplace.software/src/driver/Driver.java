// Jun Heo - 30173430
// Brandon Smith - 30141515
// Katelan Ng - 30144672
// Muhib Qureshi - 30076351
// Liam Major - 30223023
// André Beaulieu - 30174544

package driver;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import javax.naming.OperationNotSupportedException;

import com.jjjwelectronics.Item;
import com.jjjwelectronics.OverloadedDevice;
import com.jjjwelectronics.card.Card;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.tdc.CashOverloadException;
import com.tdc.NoCashAvailableException;
import com.tdc.banknote.Banknote;
import com.tdc.banknote.IBanknoteDispenser;
import com.tdc.coin.Coin;
import com.tdc.coin.ICoinDispenser;
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.hardware.BarcodedProduct;
import com.thelocalmarketplace.hardware.Product;
import com.thelocalmarketplace.hardware.external.CardIssuer;

import ca.ucalgary.seng300.simulation.NullPointerSimulationException;
import ca.ucalgary.seng300.simulation.SimulationException;
import managers.SystemManager;
import managers.enums.PaymentType;
import managers.enums.ScanType;
import managers.enums.SelfCheckoutTypes;
import managers.enums.SessionStatus;
import powerutility.PowerGrid;
import utils.CardHelper;
import utils.DatabaseHelper;
import utils.DriverHelper;

// start session use case

public class Driver {

	// hardware references
	protected AbstractSelfCheckoutStation machine;

	// object references
	protected static Scanner scanner = new Scanner(System.in);

	// object ownership
	protected SystemManager system;
	protected CardIssuer cardIssuer;

	// vars
	protected List<Item> items;
	protected BigDecimal leniency = BigDecimal.ONE;
	protected Card card;

	// denominations
	protected final BigDecimal[] coinDenominations = new BigDecimal[] { new BigDecimal(0.01), new BigDecimal(0.05),
			new BigDecimal(0.10), new BigDecimal(0.25), new BigDecimal(1), new BigDecimal(2) };
	protected final BigDecimal[] banknoteDenominations = new BigDecimal[] { new BigDecimal(5), new BigDecimal(10),
			new BigDecimal(20), new BigDecimal(50) };

	public Driver(SelfCheckoutTypes type) {
		// configuring the machine (need to do this before we initialize it)
		DriverHelper.configureMachine(coinDenominations, banknoteDenominations, 100, 1000);

		// create the machine itself
		this.machine = DriverHelper.createMachine(type);

		// creating the vars for the system manager
		cardIssuer = CardHelper.createCardIssuer();
		card = CardHelper.createCard(cardIssuer);

		// creating the system manager
		this.system = new SystemManager(cardIssuer, leniency);
	}

	protected void setup() {
		// configuring the system
		this.system.configure(this.machine);

		// so that no power surges happen
		PowerGrid.engageUninterruptiblePowerSource();

		// plug in and turn on the machine
		this.machine.plugIn(PowerGrid.instance());
		this.machine.turnOn();

		// creating list of items
		this.items = new ArrayList<Item>();

		// loading the coin dispensers
		for (int i = 0; i < coinDenominations.length; ++i) {
			ICoinDispenser cd = machine.coinDispensers.get(coinDenominations[i]);
			for (int j = 0; j < cd.getCapacity(); ++j) {
				try {
					cd.load(new Coin(coinDenominations[i]));
				} catch (SimulationException | CashOverloadException e) {
					// shouldn't happen
				}
			}
		}

		// loading the banknote dispensers
		for (int i = 0; i < banknoteDenominations.length; ++i) {
			IBanknoteDispenser abd = machine.banknoteDispensers.get(banknoteDenominations[i]);
			for (int j = 0; j < abd.getCapacity(); ++j) {
				try {
					abd.load(new Banknote(Currency.getInstance(Locale.CANADA), banknoteDenominations[i]));
				} catch (SimulationException | CashOverloadException e) {
					// shouldn't happen
				}
			}
		}

		// adding paper and ink to the printer
		try {
			machine.printer.addInk(1 << 20);
			machine.printer.addPaper(1 << 10);
		} catch (OverloadedDevice e) {
			// shouldn't happen
		}
	}

	protected boolean yesnoprompt(String prompt) {
		return DriverHelper.yesnoprompt(prompt, scanner);
	}

	protected int getChoice(int lower, int upper) {
		return DriverHelper.getChoice(lower, upper, ">> ", scanner);
	}

	/**
	 * Adds a random item to the order.
	 * 
	 * @throws OperationNotSupportedException this should never happen
	 */
	protected void scanItem() {
		ScanType method = ScanType.MAIN;

		// printing
		System.out.println("How would you like to scan your item?");
		System.out.println("1) By main scanner");
		System.out.println("2) By handheld");

		switch (getChoice(1, 2)) {
		case 1:
			break;
		case 2:
			method = ScanType.HANDHELD;
			break;
		default:
			break;
		}

		// creating new item
		BarcodedItem newItem = DatabaseHelper.createRandomBarcodedItem();

		// printing for the user
		BarcodedProduct prod = DatabaseHelper.get(newItem);
		System.out.printf("Randomly added '%s' to the cart.\n", prod.getDescription());

		if (!yesnoprompt("Would you like to bag this item?")) {
			System.out.printf("Not bagging '%s'.\n", prod.getDescription());
			this.system.onDoNotBagRequest(newItem);
		}

		// adding the item
		addItem(newItem, method);
	}

	protected void addItem(Item item, ScanType type) {
		// adding the item to the order
		try {
			this.system.addItemToOrder(item, type);
		} catch (OperationNotSupportedException e) {
			// this should never happen
		}

		// externally tracking the items added to the cart
		this.items.add(item);
	}

	protected void causeWeightDiscrepancy() {
		BarcodedItem badItem = DatabaseHelper.createWeightDiscrepancy();

		addItem(badItem, ScanType.MAIN);
	}

	/**
	 * Removes the specifed item from the order.
	 * 
	 * @throws OperationNotSupportedException this should never happen
	 */
	protected void removeItem() {
		String msg = new String("Please Select Item to Remove:");
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i) instanceof BarcodedItem) {
				BarcodedItem bi = (BarcodedItem) items.get(i);
				BarcodedProduct b = DatabaseHelper.get(bi);

				// if product is null, throw error
				if (b == null)
					throw new NullPointerSimulationException("could not find");

				msg += "\n" + (i + 1) + ") " + b.getDescription();
			}
		}
		System.out.println(msg);

		// store the choice of the user
		Integer choice = getChoice(1, items.size());

		// removing
		removeItem(items.get(choice - 1));
	}

	protected void removeItem(Item item) {
		try {
			this.system.removeItemFromOrder(item);
		} catch (OperationNotSupportedException e) {
			// this should never happen
		}

		this.items.remove(item);
	}

	protected boolean payForOrderByCard() {
		System.out.println("Swipe your card: (press enter)");
		scanner.nextLine();

		do {
			try {
				system.swipeCard(card);
				break;
			} catch (IOException e) {
				System.out.println("Looks like there was an error swiping your card, please try again");
				scanner.nextLine();
			}
		} while (true);

		return system.getState() == SessionStatus.PAID;
	}

	protected boolean payForOrderByCash() {
		System.out.println("Total price: " + system.getTotalPrice() + " cents");
		System.out.println("Valid coins/cash:");

		// printing all valid denominations
		for (BigDecimal denom : machine.coinDenominations) {
			System.out.println(denom.setScale(2, RoundingMode.HALF_EVEN));
		}
		for (BigDecimal denom : machine.banknoteDenominations) {
			System.out.println(denom.setScale(2, RoundingMode.HALF_EVEN));
		}

		// loop to receive cash
		while (system.getRemainingBalance().compareTo(BigDecimal.ZERO) >= 0) {
			// printing
			System.out.println("\nOutstanding balance: $" + system.getRemainingBalance().toString());
			System.out.print("Insert cash: ");
			BigDecimal value = scanner.nextBigDecimal();

			// temporary vars
			Coin ctemp = null;
			Banknote btemp = null;

			// in case of bad value
			if (value.compareTo(BigDecimal.ZERO) <= 0) {
				continue;
			}
			if (DriverHelper.contains(coinDenominations, value)) {
				ctemp = new Coin(value);
			}
			if (DriverHelper.contains(banknoteDenominations, value)) {
				btemp = new Banknote(Currency.getInstance(Locale.CANADA), value);
			}
			if (ctemp == null && btemp == null) {
				continue;
			}

			// inserting payment
			if (ctemp != null) {
				system.insertCoin(ctemp);
			}
			if (btemp != null) {
				system.insertBanknote(btemp);
			}
		}

		// try to tender change
		try {
			return system.tenderChange();
		} catch (RuntimeException e) {
			return false;
		} catch (NoCashAvailableException e) {
			System.out.println("Sorry, but not enough change was dispensed. An attendant will help you shortly.");
			return false;
		}
	}

	/*
	 * This is the method for the payment process. While (remaining balance - price)
	 * > 0, prompts user to insert coins. Otherwise, asks if user wants a receipt,
	 * then ends session.
	 */
	protected boolean payForOrder() {
		// ask the user how they would like to pay
		System.out.println("How would you like to pay?");
		System.out.println("1) By cash");
		System.out.println("2) By card");

		// setting the method of payment
		PaymentType method = PaymentType.CASH;

		// switching on choice
		switch (getChoice(1, 2)) {
		case 1:
			method = PaymentType.CASH;
			if (!payForOrderByCash()) {
				return false;
			}
			// banknotes for the customer
			machine.banknoteOutput.removeDanglingBanknotes();
			break;
		case 2:
			method = PaymentType.CARD;
			if (!payForOrderByCard()) {
				System.out.println(
						"Looks like something went wrong when processing your card. Please try another method of payment");
				return false;
			}
			break;
		default:
			break;
		}

		// user has paid, ask if the user wants a receipt
		if (yesnoprompt("Do you want your receipt?")) {
			system.printReceipt(method, card);

			machine.printer.cutPaper();

			String receipt = machine.printer.removeReceipt();

			System.out.println(receipt);
		}

		return true;
	}

	/**
	 * This displays the items in the order.
	 */
	protected void displayItems() {
		if (system.getProducts().size() == 0) {
			System.out.println("No products have been scanned yet!");
			return;
		}

		// there are items, we need to print them
		String msg = new String("----- ORDER -----");
		for (Product p : system.getProducts()) {
			if (p instanceof BarcodedProduct) {
				// casting
				BarcodedProduct t = (BarcodedProduct) p;

				// adding the description
				msg += "\n- " + t.getDescription();
				continue;
			}

			// invalid items should be caught by the system
			continue;
		}
		msg += "\n----- END -----\n";

		System.out.println(msg);
	}

	/**
	 * This is the main loop for a non-blocked state, displaying options
	 * accordingly.
	 * 
	 * @return
	 */
	protected boolean nonBlockedState() {
		System.out.println("1) Add an item to cart");
		System.out.println("2) Remove an item to cart");
		System.out.println("3) Cause weight discrepancy");
		System.out.println("4) View Order");

		// should only be able to pay when there is more than 1 item in the cart
		if (items.size() > 0) {
			System.out.println("5) Pay (Insert cash) and Checkout)");
		}

		System.out.println("6) Cancel Session Early");

		// switching based on choice
		switch (getChoice(1, 6)) {
		case 1: // add item
			scanItem();
			break;
		case 2: // remove item
			removeItem();
			break;
		case 3: // cause discrepancy
			causeWeightDiscrepancy();
			break;
		case 4:
			displayItems();
			break;
		case 5: // display total balance, update when coin is inserted
			if (items.size() > 0) {
				if (!payForOrder()) {
					return false;
				}
			}
			break;
		case 6: // user wants to quit session early
			system.notifyPaid();
			break;
		default:
			break;
		}

		// returning, nothing went wrong
		return true;
	}

	/**
	 * This is the main loop for a non-blocked state, displaying options
	 * accordingly.
	 * 
	 * @return
	 */
	protected void blockedState() {
		// printing
		System.out.println("Something went wrong, please remove the item from the bagging area");
		System.out.println("or call the attendant.\n");

		while (system.getState() == SessionStatus.BLOCKED) {
			System.out.println("Total Price of Order: " + system.getTotalPrice().toPlainString());

			if (items.size() > 0) {
				System.out.println("1) Remove last added item.");
				System.out.println("2) Display your items.");
			}
			System.out.println("3) Call over an attendant.");

			// switching based on choice
			switch (getChoice(1, 3)) {
			case 1: // remove item
				if (items.size() > 0) {
					removeItem(items.get(items.size() - 1));
				}
				break;
			case 2:
				if (items.size() > 0) {
					displayItems();
				}
				break;
			case 3:
				system.onAttendantOverride();
				System.out.println("[ATTENDANT]: Sorry about that, let me fix this for you.");
				break;
			default:
				break;
			}
		}

	}

	/*
	 * Main method of program
	 */
	public static void main(String[] args) throws OperationNotSupportedException {
		// create driver class
		Driver d = new Driver(DriverHelper.chooseMachineType());

		// setup driver class
		d.setup();

		// logging the state of the system
		if (!d.system.ready()) {
			d.system.notifyAttendant(
					"not all of the device components are setup correctly,\n\tthis might cause issues during the use of this system");
		}

		// ready for customer input
		while (d.system.getState() != SessionStatus.PAID) {
			// in case something went wrong
			boolean flag = true;

			System.out.println("Total Price of Order: $" + d.system.getTotalPrice().toString());

			switch (d.system.getState()) {
			case NORMAL:
				flag = d.nonBlockedState();
				break;
			case BLOCKED:
				d.blockedState();
				break;
			case PAID:
				// should never actually reach here
				break;
			}

			// something went wrong, break out of the main loop and exit
			if (!flag) {
				break;
			}

			// flushing the output and printing padding
			System.out.print("\n\n");
			System.out.flush();
		}

		// posting the credit card transactions
		d.system.postTransactions();

		System.out.println("Session ended. Have a nice day!");

		// exiting
		System.exit(0);
	}
}
