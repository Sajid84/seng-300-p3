package database;

import com.jjjwelectronics.scanner.Barcode;
import com.jjjwelectronics.scanner.BarcodedItem;
import com.thelocalmarketplace.hardware.*;

import java.util.HashMap;
import java.util.Map;

public class Database {
    private Database() {}

    /**
     * The known PLU-coded products, indexed by PLU code.
     */
    public static final Map<PriceLookUpCode, PLUCodedProduct> PLU_PRODUCT_DATABASE = new HashMap<>();

    /**
     * The known barcoded products, indexed by barcode.
     */
    public static final Map<Barcode, BarcodedProduct> BARCODED_PRODUCT_DATABASE = new HashMap<>();

    /**
     * The known PLU-coded items, indexed by PLU code.
     */
    public static final Map<PriceLookUpCode, PLUCodedItem> PLU_ITEM_DATABASE = new HashMap<>();

    /**
     * The known barcoded items, indexed by barcode.
     */
    public static final Map<Barcode, BarcodedItem> BARCODED_ITEM_DATABASE = new HashMap<>();

    /**
     * A count of the items of the given product that are known to exist in the
     * store. Of course, this does not account for stolen items or items that were
     * not correctly recorded, but it helps management to track inventory.
     */
    public static final Map<Product, Integer> INVENTORY = new HashMap<>();
}
