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
