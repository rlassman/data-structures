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

     /** Formats how a LocatedPlace is printed
     * @return A String containing a LocatedPlace's town, state, latitude, and longitude
     */
    public String toString() {
	return super.toString() + " " + latitude + " " + longitude;
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
