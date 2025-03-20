/* Name: Rebecca Lassman
 * File: DoubleHashMap.java
 * Desc:
 *
 * This class creates a hash table (using the textbook's implementation)
 * that uses double hashing.
 *
 */

import java.util.ArrayList;

public class DoubleHashMap <K,V> extends AbstractHashMap<K,V> {
    private MapEntry<K,V>[] table;        // a fixed array of entries (all initially null)
    private MapEntry<K,V> DEFUNCT = new MapEntry<>(null, null);   //sentinel

    private double maxProbes = 0; //longest sequence of probes
    private double totalProbes = 0; //total number of probes   
    public static final int q = 500009; //prime number for double hash function

    // provide same constructors as base class
    /** Creates a hash table with capacity 17 and prime factor 109345121. */
    public DoubleHashMap() { super(); }

    /** Creates a hash table with given capacity and prime factor 109345121. */
    public DoubleHashMap(int cap) { super(cap); }

    /** Creates a hash table with the given capacity and prime factor. */
    public DoubleHashMap(int cap, int p) { super(cap, p); }

    /** Creates an empty table having length equal to current capacity. */
    @Override
    @SuppressWarnings({"unchecked"})
    protected void createTable() {
	table = (MapEntry<K,V>[]) new MapEntry[capacity];   // safe cast
    }

    /** Returns true if location is either empty or the "defunct" sentinel. */
    private boolean isAvailable(int j) {
	return (table[j] == null || table[j] == DEFUNCT);
    }

    /** Returns the total number of probes
     * @return The value of the totalProbes instance variable
     */
    public double getTotalProbes() {
	return totalProbes;
    }

    /** Returns the maximum number of probes
     * @return The value of the maxProbes instance variable
     */
    public double getMaxProbes() {
	return maxProbes;
    }

    /**
     * Searches for an entry with key equal to k (which is known to have
     * hash value h), returning the index at which it was found, or
     * returning -(a+1) where a is the index of the first empty or
     * available slot that can be used to store a new such entry.
     *
     * @param h the precalculated hash value of the given key
     * @param k the key
     * @return index of found entry or if not found, value -(a+1) where a is index of first available slot
     */
    private int findSlot(int h, K k) {
      int avail = -1;                               // no slot available (thus far)
      int j = h;                                    // index while scanning table
      double probes = 0; //number of probes in this execution
      int i = 1; //index for double hash function
      do {
	  //updates total & max probes
	  probes++;
	  totalProbes ++;
	  if (probes > maxProbes)
	      maxProbes = probes;
	  
	  if (isAvailable(j)) {                       // may be either empty or defunct
	      if (avail == -1) avail = j;               // this is the first available slot!
	      if (table[j] == null) break;              // if empty, search fails immediately
	  } else if (table[j].getKey().equals(k))
	      return j;                                 // successful match                      
	  j = (h + i*(q-k.hashCode()%q)) % capacity; //searches via double hashing
	  i++;
      } while (j != h);                             // stop if we return to the start

      //in case j returns to start without finding an availible spot
      while (avail == -1) {
	  if(isAvailable(j))
	      avail = j;
	j = (j+1) % capacity;
      }
      
      return -(avail + 1);                          // search has failed
    }

    /**
     * Returns value associated with key k in bucket with hash value h.
     * If no such entry exists, returns null.
     * @param h  the hash value of the relevant bucket
     * @param k  the key of interest
     * @return   associate value (or null, if no such entry)
     */
    @Override
    protected V bucketGet(int h, K k) {
	int j = findSlot(h, k);
	if (j < 0) return null;                   // no match found
	return table[j].getValue();
    }

    /**
     * Associates key k with value v in bucket with hash value h, returning
     * the previously associated value, if any.
     * @param h  the hash value of the relevant bucket
     * @param k  the key of interest
     * @param v  the value to be associated
     * @return   previous value associated with k (or null, if no such entry)
     */
    @Override
    protected V bucketPut(int h, K k, V v) {
	int j = findSlot(h, k);
	if (j >= 0) {                              // this key has an existing entry
	    return table[j].setValue(v);
	}
	table[-(j+1)] = new MapEntry<>(k, v);     // convert to proper index
	n++;
	return null;
    }

    /**
     * Removes entry having key k from bucket with hash value h, returning
     * the previously associated value, if found.
     * @param h  the hash value of the relevant bucket
     * @param k  the key of interest
     * @return   previous value associated with k (or null, if no such entry)
     */
    @Override
    protected V bucketRemove(int h, K k) {
	int j = findSlot(h, k);
	if (j < 0) return null;                   // nothing to remove
	V answer = table[j].getValue();
	table[j] = DEFUNCT;                       // mark this slot as deactivated
	n--;
	return answer;
    }

    /**
     * Returns an iterable collection of all key-value entries of the map.
     *
     * @return iterable collection of the map's entries
     */
    @Override
    public Iterable<Entry<K,V>> entrySet() {
	ArrayList<Entry<K,V>> buffer = new ArrayList<>();
	for (int h=0; h < capacity; h++)
	    if (!isAvailable(h)) buffer.add(table[h]);
	return buffer;
    }
}
