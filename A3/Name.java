/* Name: Rebecca Lassman
 * File: Name.java
 * Desc:
 *
 * This class creates an instance of a name and stores
 * the data associated with that name.
 *
 */

import java.util.*;

public class Name {

    private String name; //the name (i.e. Mary)
    private int rank; //rank in list alphabetically
    private ArrayList<Year> years; //all year objects with data for name

    /** Constructs a Name object containing a name, rank, and ArrayList of years
     * @param name The name
     */
    public Name(String name) {
	this.name = name;
	rank = 0;
	years = new ArrayList<>();
    }

    /** Returns the value of name
     * @return The value of the name instance variable
     */
    public String getName() {
	return name;
    }

    /** Returns the value of rank
     * @return The value of the rank instance variable
     */
    public int getRank() {
	return rank;
    }

    /** Changes the value of the rank instance variable
     * @param r The new value for rank
     */
    public void setRank(int r) {
	rank = r;
    }

    /** Returns the years ArrayList 
     * @return The years ArrayList
     */
    public ArrayList<Year> getYears() {
	return years;
    }

    /** Returns the name's total number of babies for all years in the ArrayList
     * @return The total number of babies with the  name
     */
    public int nameTotal() {
	int total = 0;
	for(int i=0; i<years.size(); i++) {
	    total+=years.get(i).getNumOfBabies();
	}
	return total;
    }

    /** Formats how a Name is printed
     * @return The rank and name followed by a colon
     */
    public String toString() {
	return rank + " " + name;
    }
}
