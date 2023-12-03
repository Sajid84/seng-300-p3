// Liam Major 30223023

package utils;

import ca.ucalgary.seng300.simulation.InvalidArgumentSimulationException;
import com.jjjwelectronics.Item;
import com.jjjwelectronics.Mass;
import com.jjjwelectronics.Numeral;
import com.jjjwelectronics.bag.ReusableBag;
import com.jjjwelectronics.scanner.Barcode;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.thelocalmarketplace.hardware.*;
import database.Database;

import java.math.BigDecimal;
import java.util.Random;

/**
 * all this does is populate the database with randomly generated products
 */
public class DatabaseHelper {

    public static final BigDecimal PRICE_OF_BAG = BigDecimal.ONE;
    private static final String[] names = {"Milk", "Bread", "Eggs", "Cheese", "Sugar", "Cereal", "Beef", "Abacus", "Butter"};

    private static final String[] adjectives = {"Inedible", "Tasty", "Pasty", "Wooden", "Super", "Proud", "Big", "Small",
            "Puny", "Dandriff-infused", "Feline", "Canine"};

    private static final Random random = new Random();

    public static final int DEFAULT_BARCODE_LENGTH = 10;


    /**
     * Creates a randomized mass between 0.1 (inclusive) and 1.1 (exclusive), scaled
     * by a factor of 25.
     *
     * @return the randomized mass
     */
    public static double createRandomMass() {
        return DatabaseHelper.random.nextDouble(0.1, 1.1) * 25;
    }

    /**
     * Creates a randomized price between 1 (inclusive) and 100 (exclusive).
     *
     * @return the randomized price
     */
    public static long createRandomPrice() {
        return DatabaseHelper.random.nextLong(1, 100);
    }

    public static long createRandomSignifier() {
        return DatabaseHelper.random.nextLong(100, 1_000);
    }

    /**
     * This creates a barcode of length {@code digits} for anything that needs a
     * {@link Barcode}.
     *
     * @param digits the number of digits for the {@link Barcode}
     * @return a randomized {@link Barcode}
     */
    public static Barcode createRandomBarcode(int digits) {
        // testing length
        if (digits < 1 || digits > 48) {
            throw new InvalidArgumentSimulationException("invalid number of digits, between 1 and 48 inclusive");
        }

        // creating a list
        Numeral[] code = new Numeral[digits];

        // creating as many digits that are put in
        for (int i = 0; i < digits; ++i) {
            byte num = (byte) (DatabaseHelper.random.nextInt(10));

            code[i] = Numeral.valueOf(num);
        }

        return new Barcode(code);
    }

    public static PriceLookUpCode createRandomPLUCode() {
        Integer code = random.nextInt(10_000, 100_000);
        String scode = String.valueOf(code);

        return new PriceLookUpCode(scode);
    }

    /**
     * Creates a randomized description for a {@link Product}.
     *
     * @return a description
     */
    public static String createRandomDescription() {
        String name;

        name = adjectives[DatabaseHelper.random.nextInt(DatabaseHelper.adjectives.length)];
        name += " ";
        name += names[DatabaseHelper.random.nextInt(DatabaseHelper.names.length)];

        return name;
    }

    /**
     * This is a shorthand to insert something into the database.
     *
     * @param item the barcoded item to insert
     * @param prod the barcoded product to insert
     * @return true if successful, false otherwise
     */
    private static boolean insertIntoDatabase(BarcodedItem item, BarcodedProduct prod) {
        if (item.getBarcode() != prod.getBarcode()) {
            throw new IllegalArgumentException("Both item and product have different barcodes");
        }

        if (Database.BARCODED_ITEM_DATABASE.containsKey(item.getBarcode()) ||
                Database.BARCODED_PRODUCT_DATABASE.containsKey(prod.getBarcode())) {
            return false;
        }

        Database.BARCODED_ITEM_DATABASE.put(item.getBarcode(), item);
        Database.BARCODED_PRODUCT_DATABASE.put(prod.getBarcode(), prod);
        return true;
    }

    /**
     * This is a shorthand to insert something into the database.
     *
     * @param item the PLU coded item to insert
     * @param prod the PLU coded product to insert
     * @return true if successful, false otherwise
     */
    private static boolean insertIntoDatabase(PLUCodedItem item, PLUCodedProduct prod) {
        if (item.getPLUCode() != prod.getPLUCode()) {
            throw new IllegalArgumentException("Both item and product have different barcodes");
        }

        if (Database.PLU_ITEM_DATABASE.containsKey(item.getPLUCode()) ||
                Database.PLU_PRODUCT_DATABASE.containsKey(prod.getPLUCode())) {
            return false;
        }

        Database.PLU_ITEM_DATABASE.put(item.getPLUCode(), item);
        Database.PLU_PRODUCT_DATABASE.put(prod.getPLUCode(), prod);
        return true;
    }

    /**
     * <p>
     * Creates a {@link BarcodedItem} with a randomized mass and barcode. This
     * method also creates a {@link BarcodedProduct} with a randomized barcode,
     * description, price and mass.
     * </p>
     * <p>
     * This method guarantees that the {@link Barcode}s of both item and product are
     * the same and that they are put into respective databases in {@link Database}.
     * </p>
     *
     * @return the {@link BarcodedItem}
     */
    public static BarcodedItem createRandomBarcodedItem() {
        // creating the barcode
        double mass = DatabaseHelper.createRandomMass();
        String desc = "B" + DatabaseHelper.createRandomSignifier() + " " + DatabaseHelper.createRandomDescription();
        long price = DatabaseHelper.createRandomPrice();

        // temp vars
        Barcode barcode;
        BarcodedItem item;
        BarcodedProduct prod;

        // trying to insert the pair into the database
        do {
            // creating the random barcode
            barcode = DatabaseHelper.createRandomBarcode(DEFAULT_BARCODE_LENGTH);

            // need to create the item
            item = new BarcodedItem(barcode, new Mass(mass));

            // need to create the corresponding product
            prod = new BarcodedProduct(barcode, desc, price, mass);
        } while (!DatabaseHelper.insertIntoDatabase(item, prod));

        // return item to caller
        return item;
    }

    /**
     * This method creates a weight discrepancy by generating a barcoded item and
     * product pair where the product is expected to weight more than the item
     * itself.
     * </p>
     * <p>
     * This function also guarantess that the product's expected mass is bigger than
     * the item's by at 1,000 units.
     *
     * @return the generated barcoded item
     */
    public static BarcodedItem createWeightDiscrepancy() {
        // creating the barcode
        double mass = DatabaseHelper.createRandomMass();
        String desc = "(DISCREP) " + DatabaseHelper.createRandomDescription();
        long price = DatabaseHelper.createRandomPrice();

        // temp vars
        Barcode barcode;
        BarcodedItem item;
        BarcodedProduct prod;

        // trying to insert the pair into the database
        do {
            // creating the random barcode
            barcode = DatabaseHelper.createRandomBarcode(DEFAULT_BARCODE_LENGTH);

            // need to create the item
            item = new BarcodedItem(barcode, new Mass(mass));

            // need to create the corresponding product
            prod = new BarcodedProduct(barcode, desc, price, mass + 1_000);
        } while (!DatabaseHelper.insertIntoDatabase(item, prod));

        // return item to caller
        return item;
    }

    /**
     * This creates a bad {@link BarcodedItem} that's too heavy for the scales. It will cause a scale overload.
     *
     * @return a barcoded item
     */
    public static BarcodedItem createItemTooHeavy() {
        // creating the barcode
        double mass = DatabaseHelper.createRandomMass();
        String desc = "(HEAVY) " + DatabaseHelper.createRandomDescription();
        long price = DatabaseHelper.createRandomPrice();

        // temp vars
        Barcode barcode;
        BarcodedItem item;
        BarcodedProduct prod;

        // trying to insert the pair into the database
        do {
            // creating the random barcode
            barcode = DatabaseHelper.createRandomBarcode(DEFAULT_BARCODE_LENGTH);

            // need to create the item
            item = new BarcodedItem(barcode, new Mass(mass + 999999999));

            // need to create the corresponding product
            prod = new BarcodedProduct(barcode, desc, price, mass);
        } while (!DatabaseHelper.insertIntoDatabase(item, prod));

        // return item to caller
        return item;
    }

    /**
     * This is just a shorthand to get the corresponding product from the database.
     *
     * @param item the product to search for
     * @return the corresponding product, if there is one
     */
    public static BarcodedProduct get(BarcodedItem item) {
        return Database.BARCODED_PRODUCT_DATABASE.get(item.getBarcode());
    }

    /**
     * This is just a shorthand to get the corresponding product from the database.
     *
     * @param item the product to search for
     * @return the corresponding product, if there is one
     */
    public static PLUCodedProduct get(PLUCodedItem item) {
        return Database.PLU_PRODUCT_DATABASE.get(item.getPLUCode());
    }

    /**
     * Creates a new randomly generated {@link PLUCodedItem} with a corresponding {@link PLUCodedProduct} in the database to look for.
     *
     * @return a new {@link PLUCodedItem}
     */
    public static PLUCodedItem createRandomPLUCodedItem() {
        // creating random fields
        long price = DatabaseHelper.createRandomPrice();
        String desc = "P" + DatabaseHelper.createRandomSignifier() + " " + DatabaseHelper.createRandomDescription();
        double weight = DatabaseHelper.createRandomMass();

        // temp vars
        PriceLookUpCode code;
        PLUCodedProduct prod;
        PLUCodedItem item;

        // trying to insert the pair into the database
        do {
            // creating the random barcode
            code = DatabaseHelper.createRandomPLUCode();

            // need to create the item
            item = new PLUCodedItem(code, new Mass(weight));

            // need to create the corresponding product
            prod = new PLUCodedProduct(code, desc, price);
        } while (!DatabaseHelper.insertIntoDatabase(item, prod));

        // returning the item
        return item;
    }

}
