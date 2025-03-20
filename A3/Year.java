/* Name: Rebecca Lassman
 * File: Year.java
 * Desc:
 *
 * This class creates an instance of a year and stores
 * the data associated with that year.
 *
 */

public class Year {

    private int year; //the numerical year (i.e. 1990)
    private int rank; //a name's rank based on the number of babies
    private int numOfBabies; //how many babies have the name

    /** Constructs a Year object containing a year, rank, and number of babies
     * @param year The numerical year
     * @param rank The rank based on the number of babies
     * @param num The number of babies
     */
    public Year(int year, int rank, int num) {
	this.year = year;
	this.rank = rank;
	numOfBabies = num;
    }

    /** Returns the value of year
     * @return The value of the year instance variable
     */
    public int getYear() {
	return year;
    }

    /** Returns the value of rank
     * @return The value of the rank instance variable
     */
    public int getRank() {
	return rank;
    }

    /** Returns the value of numOfBabies
     * @return The value of the numOfBabies instance variable
     */
    public int getNumOfBabies() {
	return numOfBabies;
    }

    /** Formats how a Year is printed
     * @return The rank and number of babies
     */
    public String toString() {
	return rank + ", " + numOfBabies;
    }
    
}
