// Liam Major 30223023
package stubbing;

import com.jjjwelectronics.card.Card.CardData;

public class StubbedCardData implements CardData {

	public static final String TYPE = "DEBIT";
	public static final String NUMBER = "0123456789";
	public static final String HOLDER = "Ryan Gosling";
	public static final String CVV = "000";

	private String type;
	private String number;
	private String holder;
	private String cvv;

	public StubbedCardData() {
		this(TYPE, NUMBER, HOLDER, CVV);
	}

	public StubbedCardData(String t, String n, String h, String c) {
		type = t;
		number = n;
		holder = h;
		cvv = c;
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public String getNumber() {
		return number;
	}

	@Override
	public String getCardholder() {
		return holder;
	}

	@Override
	public String getCVV() {
		return cvv;
	}

}
