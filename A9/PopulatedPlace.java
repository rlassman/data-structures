/* Name: Rebecca Lassman
 * File: PopulatedPlace.java
 * Desc:
 *
 * This program defines a PopulatedPlace object, which is a subclass of LocatedPlace.
 *
 */

public class PopulatedPlace extends LocatedPlace {

    private int population; //population of the place

    /** Constructs a PopulatedPlace object
     * @param zip 5-digit zipcode
     * @param town Name of town
     * @param state Two letter state name abbreviation
     * @param lat Latitude of the location
     * @param lon Longitude of the location
     * @param pop Population of the location
     */
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

    /** Creates a string containing data about the place
     * @return A string containing a place's town, state, latitude,
     * longitude, and population
     */
    public String getData() {
	return  super.getData() + " " + population;
    }

}
