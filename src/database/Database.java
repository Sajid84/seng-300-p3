// Importing necessary packages
import com.jjjwelectronics.scanner.Barcode;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.thelocalmarketplace.hardware.*;

import java.util.HashMap;
import java.util.Map;

// Database class to manage products and inventory
public class Database {
    // Private constructor to prevent instantiation
    private Database() {}

    /**
     * The known PLU-coded products, indexed by PLU code.
     * PLU (Price Look Up) codes are used by stores to identify products.
     */
    public static final Map<PriceLookUpCode, PLUCodedProduct> PLU_PRODUCT_DATABASE = new HashMap<>();

    /**
     * The known barcoded products, indexed by barcode.
     * Barcodes are used in retail stores for inventory management and sales checkout.
     */
    public static final Map<Barcode, BarcodedProduct> BARCODED_PRODUCT_DATABASE = new HashMap<>();

    /**
     * The known PLU-coded items, indexed by PLU code.
     * These are the items in the store that have a PLU code.
     */
    public static final Map<PriceLookUpCode, PLUCodedItem> PLU_ITEM_DATABASE = new HashMap<>();

    /**
     * The known barcoded items, indexed by barcode.
     * These are the items in the store that have a barcode.
     */
    public static final Map<Barcode, BarcodedItem> BARCODED_ITEM_DATABASE = new HashMap<>();

    /**
     * A count of the items of the given product that are known to exist in the
     * store. Of course, this does not account for stolen items or items that were
     * not correctly recorded, but it helps management to track inventory.
     * This is a map where the key is the product and the value is the quantity in inventory.
     */
    public static final Map<Product, Integer> INVENTORY = new HashMap<>();
}
