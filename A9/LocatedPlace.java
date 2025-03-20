/* Name: Rebecca Lassman
 * File: LocatedPlace.java
 * Desc:
 *
 * This program defines a LocatedPlace object, which is a subclass of Place.
 *
 */

public class LocatedPlace extends Place {

    private double latitude; //latitude of the place
    private double longitude; //longitude of the place

    /** Constructs a PopulatedPlace object
     * @param zip 5-digit zipcode
     * @param town Name of town
     * @param state Two letter state name abbreviation
     * @param lat Latitude of the location
     * @param lon Longitude of the location
     */
    public LocatedPlace(String zip, String town, String state, double lat, double lon) {
	super(zip, town, state);
	latitude = lat;
	longitude = lon;
    }

     /** Returns the value of latitude
     * @return The value of the latitude instance variable
     */
    public double getLatitude() {
	return latitude;
    }

     /** Returns the value of longitude
     * @return The value of the longitude instance variable
     */
    public double getLongitude() {
	return longitude;
    }

     /** Creates a string containing data about the place
     * @return A string containing a place's town, state, latitude,
     * and longitude
     */
    public String getData() {
	return super.getData() + " " + latitude + " " + longitude;
    }

     /** Changes the value of latitude
     * @param lat The new value for latitude
     */
    public void setLatitude(double lat) {
	latitude = lat;
    }

     /** Changes the value of longitude
     * @param lon The new value for longitude
     */
    public void setLongitude(double lon) {
	longitude = lon;
    }

}
