// Liam Major 30223023

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

}
