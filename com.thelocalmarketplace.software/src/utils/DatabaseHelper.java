// Liam Major 30223023

package utils;

import ca.ucalgary.seng300.simulation.InvalidArgumentSimulationException;
import com.jjjwelectronics.Mass;
import com.jjjwelectronics.Numeral;
import com.jjjwelectronics.scanner.Barcode;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.thelocalmarketplace.hardware.*;
import com.thelocalmarketplace.hardware.external.ProductDatabases;

import java.util.Random;

/**
 * all this does is populate the database with randomly generated products
 */
public class DatabaseHelper {

    private static String[] names = {"Milk", "Bread", "Eggs", "Cheese", "Sugar", "Cereal", "Beef", "Abacus",};

    private static String[] adjectives = {"Inedible", "Tasty", "Pasty", "Wooden", "Super", "Proud", "Big", "Small",
            "Puny", "Dandriff-infused", "Feline", "Canine"};

    private static Random random = new Random();

    public static final int DEFAULT_BARCODE_LENGTH = 10;

    public static final int DEFAULT_PLU_CODE_LENGTH = 5;


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
        return DatabaseHelper.createRandomBarcodedItem(DEFAULT_BARCODE_LENGTH);
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
     * @param length how many digits to generate for the {@link Barcode}
     * @return the {@link BarcodedItem}
     */
    public static BarcodedItem createRandomBarcodedItem(int length) {
        // creating the barcode
        Barcode barcode = DatabaseHelper.createRandomBarcode(length);
        double mass = DatabaseHelper.createRandomMass();

        // need to create the item
        BarcodedItem item = new BarcodedItem(barcode, new Mass(mass));

        // need to create the corresponding product
        BarcodedProduct prod = new BarcodedProduct(barcode, DatabaseHelper.createRandomDescription(),
                DatabaseHelper.createRandomPrice(), mass);

        // add both to database
        ProductDatabases.BARCODED_PRODUCT_DATABASE.put(barcode, prod);

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
     * the item's by at least 10 units.
     *
     * @return the generated barcoded item
     */
    public static BarcodedItem createWeightDiscrepancy() {
        // creating the barcode
        Barcode barcode = DatabaseHelper.createRandomBarcode(DEFAULT_BARCODE_LENGTH);
        double mass = DatabaseHelper.createRandomMass();

        // need to create the item
        BarcodedItem item = new BarcodedItem(barcode, new Mass(mass + 100));

        // need to create the corresponding product
        BarcodedProduct prod = new BarcodedProduct(barcode, DatabaseHelper.createRandomDescription(),
                DatabaseHelper.createRandomPrice(), mass);

        // add both to database
        ProductDatabases.BARCODED_PRODUCT_DATABASE.put(barcode, prod);

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
        return ProductDatabases.BARCODED_PRODUCT_DATABASE.get(item.getBarcode());
    }

    /**
     * This is just a shorthand to get the corresponding product from the database.
     *
     * @param item the product to search for
     * @return the corresponding product, if there is one
     */
    public static PLUCodedProduct get(PLUCodedItem item) {
        return ProductDatabases.PLU_PRODUCT_DATABASE.get(item.getPLUCode());
    }

    /**
     * Creates a new randomly generated {@link PLUCodedItem} with a corresponding {@link PLUCodedProduct} in the database to look for.
     *
     * @return a new {@link PLUCodedItem}
     */
    public static PLUCodedItem createRandomPLUCodedItem() {
        // creating random fields
        PriceLookUpCode code = DatabaseHelper.createRandomPLUCode();
        long price = DatabaseHelper.createRandomPrice();
        String desc = DatabaseHelper.createRandomDescription();
        double weight = DatabaseHelper.createRandomMass();

        // creating product & item pair
        PLUCodedProduct prod = new PLUCodedProduct(code, desc, price);
        PLUCodedItem item = new PLUCodedItem(code, new Mass(weight));

        // putting prod in database
        ProductDatabases.PLU_PRODUCT_DATABASE.put(code, prod);

        // returning the item
        return item;
    }

}
