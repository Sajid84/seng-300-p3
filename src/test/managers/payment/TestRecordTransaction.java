// Liam Major 30223023
package test.managers.payment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.jjjwelectronics.card.Card.CardData;

import stubbing.StubbedCardData;
import stubbing.StubbedSystemManager;
import utils.Pair;

public class TestRecordTransaction {
	// vars
	private StubbedSystemManager sm;

	private CardData card;
	private Long hn;
	private Double a;

	@Before
	public void setup() {
		// creating the stubs
		sm = new StubbedSystemManager();

		card = new StubbedCardData();
		hn = (long) 1;
		a = (double) 1;
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRecordTransactionNullCard() {
		sm.recordTransaction(null, hn, a);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRecordTransactionNullHoldNumber() {
		sm.recordTransaction(card, null, a);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRecordTransactionNullAmount() {
		sm.recordTransaction(card, hn, null);
	}

	@Test
	public void tesTransactionPresent() {
		sm.recordTransaction(card, hn, a);

		// getting the records
		Map<String, List<Pair<Long, Double>>> records = sm.getRecords();

		// asserting
		assertTrue(records.size() == 1);
		
		List<Pair<Long, Double>> transactions = records.get(card.getNumber());
		
		assertNotNull(transactions);
		assertTrue(transactions.size() == 1);
		
		// getting pair of values
		Pair<Long, Double> pair = transactions.get(0);
		
		// asserting the pair
		assertNotNull(pair);
		
		// asserting values
		Long key = pair.getKey();
		Double val = pair.getValue();
		
		assertNotNull(key);
		assertNotNull(val);
		assertEquals(key, hn);
		assertEquals(val, a);
	}
}
