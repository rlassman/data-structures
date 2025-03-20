/* Name: Rebecca Lassman
 * File: PopulatedPlace.java
 * Desc:
 *
 * This program defines a PopulatedPlace object, which is a subclass of LocatedPlace.
 *
 */

public class PopulatedPlace extends LocatedPlace {

    private int population; //population of the place

    public PopulatedPlace(String zip, String town, String state, double lat, double lon, int pop) {
	super(zip, town, state, lat, lon);
	population = pop;
    }

    /** Returns the value of population
     * @return The value of the population instance variable
     */
    public int getPopulation() {
	return population;
    }

    /** Formats how a PopulatedPlace is printed
     * @return A String containing a PopulatedPlace's town, state, latitude, longitude, and population
     */
    public String toString() {
	return  super.toString() + " " + population;
    }

}
