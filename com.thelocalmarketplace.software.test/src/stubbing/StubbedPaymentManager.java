// Liam Major 30223023

package stubbing;

import java.math.BigDecimal;

import com.jjjwelectronics.EmptyDevice;
import com.jjjwelectronics.card.Card;
import com.tdc.CashOverloadException;
import com.tdc.DisabledException;
import com.tdc.NoCashAvailableException;
import com.tdc.banknote.Banknote;
import com.tdc.coin.Coin;
import com.thelocalmarketplace.hardware.AbstractSelfCheckoutStation;
import com.thelocalmarketplace.hardware.ISelfCheckoutStation;
import com.thelocalmarketplace.hardware.external.CardIssuer;

import managers.PaymentManager;
import managers.enums.PaymentType;
import observers.payment.BanknoteCollector;
import observers.payment.CardReaderObserver;
import observers.payment.CoinCollector;
import observers.payment.ReceiptPrinterObserver;

public class StubbedPaymentManager extends PaymentManager {

	public StubbedSystemManager smStub;

	public BigDecimal notifyBalanceAddedValue;
	public boolean insertCoinCalled;
	public boolean insertBanknoteCalled;
	public boolean tenderChangeCalled;
	public boolean getCustomerPaymentCalled;
	public boolean printReceiptCalled;

	public StubbedPaymentManager(StubbedSystemManager sm, CardIssuer issuer) {
		super(sm, issuer);

		smStub = sm;

		insertCoinCalled = false;
		insertBanknoteCalled = false;
		tenderChangeCalled = false;
		getCustomerPaymentCalled = false;
		printReceiptCalled = false;
	}
	
	public CoinCollector getCoinCollector() {
		return super.cc;
	}
	
	public BanknoteCollector getBanknoteCollector() {
		return super.bc;
	}
	
	public CardReaderObserver getCardReaderObserver() {
		return super.cro;
	}
	
	public ReceiptPrinterObserver getReceiptPrinterObserver() {
		return super.rpls;
	}
	
	public BigDecimal getPayment() {
		return super.payment;
	}
	
	public void setPayment(double value) {
		super.payment = new BigDecimal(value);
	}

	@Override
	public void notifyBalanceAdded(BigDecimal value) {
		notifyBalanceAddedValue = value;
		super.notifyBalanceAdded(value);
	}

	public CardIssuer getIssuer() {
		return super.issuer;
	}
	
	public void setIssuer(CardIssuer i) {
		super.issuer = i;
	}

	public ISelfCheckoutStation getMachine() {
		return machine;
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
	public void insertCoin(Coin coin) throws DisabledException, CashOverloadException {
		insertCoinCalled = true;
		super.insertCoin(coin);
	}

	@Override
	public void insertBanknote(Banknote banknote) throws DisabledException, CashOverloadException {
		insertBanknoteCalled = true;
		super.insertBanknote(banknote);
	}

	@Override
	public boolean tenderChange() throws RuntimeException, NoCashAvailableException {
		tenderChangeCalled = true;
		return super.tenderChange();
	}

	@Override
	public BigDecimal getCustomerPayment() {
		getCustomerPaymentCalled = true;
		return super.getCustomerPayment();
	}

  @Override
	public boolean canTenderChange(BigDecimal value) {
		return super.canTenderChange(value);
  }

	@Override
	public void printReceipt(PaymentType type, Card card) {
		printReceiptCalled = true;
		super.printReceipt(type, card);
	}
	
	@Override
	public void printLine(String s) throws EmptyDevice {
		super.printLine(s);
	}
}
