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
