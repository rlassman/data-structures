/* Name: Rebecca Lassman
 * File: Place.java
 * Desc:
 *
 * This program defines a Place object
 *
 */

public class Place implements Comparable<Place> {

    private String zipcode; //5-digit zipcode
    private String town; //name of town
    private String state; //two letter state name abbreviation

     /** Constructs a Place object containing a zipcode, town, and state
     * @param zip 5-digit zipcode
     * @param town Name of town
     * @param state Two letter state name abbreviation
     */
    public Place(String zip, String town, String state) {
	zipcode = zip;
	this.town = town;
	this.state = state;
    }

     /** Returns the value of zipcode
     * @return The value of the zipcode instance variable
     */
    public String getZip() {
	return zipcode;
    }

     /** Returns the value of town
     * @return The value of the town instance variable
     */
    public String getTown() {
	return town;
    }

     /** Returns the value of state
     * @return The value of the state instance variable
     */
    public String getState() {
	return state;
    }

     /** Creates a string containing data about the place
     * @return A string containing a place's town and state
     */
    public String getData() {
	return town+", "+state;
    }

    /** Formats how a place is printed
     * @return A string containing a place's zipcode
     */
    public String toString() {
	return zipcode;
    }

    /** Compares two places using their zipcodes
     * @return 0 if the zipcodes are equals, -1 if the other 
     * zipcode is larger, 1 if it's smaller
     */
    public int compareTo(Place other) {
	int result = zipcode.compareTo(other.getZip());
	if (result == 0)
	    return 0;
	else if (result < 0)
	    return -1;
	else
	    return 1;
    }
}
