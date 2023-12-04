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

package utils;

/**
 * This is just a container that binds two values together. Basically a C-style
 * struct.
 * 
 * This class doesn't care whether the object is null or not.
 * 
 * @param <K> a class
 * @param <V> a class
 */

public class Pair<K, V> {

	K key;
	V val;

	public Pair(K k, V v) {
		key = k;
		val = v;
	}

	public K getKey() {
		return key;
	}

	public V getValue() {
		return val;
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) return true;

		if (!(other instanceof Pair<?,?>)) return false;

		Pair<K, V> p = (Pair<K, V>) other;

		if (p.getKey() != this.getKey()) return false;
		if (p.getValue() != this.getValue()) return false;

		return true;
	}

}
