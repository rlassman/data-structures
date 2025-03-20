/* Name: Rebecca Lassman
 * File: Place.java
 * Desc:
 *
 * This program defines a Place object
 *
 */

public class Place {

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

     /** Formats how a Place is printed
     * @return A string containing a Place's town and state
     */
    public String toString() {
	return town+", "+state;
    }
}
