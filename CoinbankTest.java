package common;
/**
 * JUnit test class. Use these tests as models for your own.
 *
 * Tests for Coinbank class's methods
 */
import org.junit.*;
import org.junit.rules.Timeout;
import static org.junit.Assert.*;

import proj1.Coinbank;

public class CoinbankTest {

	@Rule // a test will fail if it takes longer than 1/10 of a second to run
	public Timeout timeout = Timeout.millis(100);

	/**
	 * Sets up a bank with the given coins
	 *
	 * @param pennies  number of pennies you want
	 * @param nickels  number of nickels you want
	 * @param dimes    number of dimes you want
	 * @param quarters number of quarters you want
	 * @return the Coinbank filled with the requested coins of each type
	 */
	private Coinbank makeBank(int pennies, int nickels, int dimes, int quarters) {
		Coinbank c = new Coinbank();
		int[] money = new int[]{pennies, nickels, dimes, quarters};
		int[] denom = new int[]{1, 5, 10, 25};
		for (int index = 0; index < money.length; index++) {
			int numCoins = money[index];
			for (int coin = 0; coin < numCoins; coin++) {
				c.insert(denom[index]);
			}
		}
		return c;
	}

	@Test // bank should be empty upon construction
	public void testConstruct() {
		Coinbank emptyDefault = new Coinbank();
		assertEquals(0, emptyDefault.get(1));
		assertEquals(0, emptyDefault.get(5));
		assertEquals(0, emptyDefault.get(10));
		assertEquals(0, emptyDefault.get(25));
	}


	@Test // inserting nickel should return true & one nickel should be in bank
	public void testInsertNickel_return() {
		Coinbank c = new Coinbank();
		assertTrue(c.insert(5));
		assertEquals(1, c.get(5));
	}

	@Test // inserting invalid coin, should return false and no coins added to bank
	public void testInsertInvalidCoin() {
		Coinbank c = new Coinbank();
		assertFalse(c.insert(19));
		assertEquals(0, c.get(1));
		assertEquals(0, c.get(5));
		assertEquals(0, c.get(10));
		assertEquals(0, c.get(25));
	}

	@Test // getter should return correct values
	public void testGet() {
		Coinbank c = makeBank(0, 2, 15, 1);
		assertEquals(0, c.get(1));
		assertEquals(2, c.get(5));
		assertEquals(15, c.get(10));
		assertEquals(1, c.get(25));
	}

	@Test // getter should return -1 because of invalid coinType
	public void testGet_invalidCoinType() {
		Coinbank c = makeBank(0, 2, 15, 1);
		assertEquals(-1, c.get(19));
	}

	@Test // getter should not alter the bank
	public void testGet_contents() {
		Coinbank c = makeBank(0, 2, 15, 1);
		c.get(1);
		c.get(5);
		c.get(10);
		c.get(25);
		String expected = "The bank currently holds $1.85 consisting of \n0 pennies\n2 nickels\n15 dimes\n1 quarters\n";
		assertEquals(expected, c.toString());
	}

	@Test // test of remove
	public void testRemove_justEnough() {
		Coinbank c = makeBank(4, 1, 3, 5);
		assertEquals(5, c.remove(25, 5));
		String expected = "The bank currently holds $0.39 consisting of \n4 pennies\n1 nickels\n3 dimes\n0 quarters\n";
		assertEquals(expected, c.toString());
	}

	@Test // remove should not do anything if a 3-cent coin is requested
	public void testRemove_invalidCoin() {
		Coinbank c = makeBank(4, 1, 3, 5);
		assertEquals(0, c.remove(3, 1));
	}

	@Test // remove should not do anything if number of coins requested is -19
	public void testRemove_invalidRequestedCoins() {
		Coinbank c = makeBank(4, 1, 3, 5);
		assertEquals(0, c.remove(5, -19));
		String expected = "The bank currently holds $1.64 consisting of \n4 pennies\n1 nickels\n3 dimes\n5 quarters\n";
		assertEquals(expected, c.toString());
	}

	@Test // remove should return all the coin of the requestedCoin type in
		// bank if requested coins are more than number of requestedCoin in bank
	public void testRemove_moreThanBankContains()
	{
		Coinbank c = makeBank(4,1,3,5);
		assertEquals(4,c.remove(1,19));
		String expected = "The bank currently holds $1.6 consisting of \n0 pennies\n1 nickels\n3 dimes\n5 quarters\n";
		assertEquals(expected,c.toString());
	}

	@Test // remove should return the exact requested coins if requested
		// coins are less than number of requestedCoin type in bank
	public void testRemove_lessThanBankContains()
	{
		Coinbank c = makeBank(4,1,3,5);
		assertEquals(2,c.remove(10,2));
		String expected = "The bank currently holds $1.44 consisting of \n4 pennies\n1 nickels\n1 dimes\n5 quarters\n";
		assertEquals(expected,c.toString());
	}
	
	@Test // test toString method
	public void testToString()
	{
		Coinbank c = makeBank(4,1,3,5);
		String expected = "The bank currently holds $1.64 consisting of \n4 pennies\n1 nickels\n3 dimes\n5 quarters\n";
		assertEquals(expected,c.toString());
	}
}
