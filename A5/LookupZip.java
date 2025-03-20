/* Name: Rebecca Lassman
 * File: LookupZip.java
 * Desc:
 *
 * This program contains static methods that read two zipcodes files and combine the data
 * from both to create an ArrayList of Places, LocatedPlaces, and PopulatedPlaces.
 * It also finds a place in the ArrayList when given a zipcode.
 *
 */

import java.io.*;
import java.util.*;

public class LookupZip {
    //Indexes for data file lines
    public static final int ZIP = 0, TOWN = 1, STATE = 2, POP = 3, LAT = 5, LON = 6;

    /** Parses one line of input by creating a LocatedPlace or
     * PopulatedPlace that denotes the information in the given line
     * @param lineNumber The line number of this line
     * @param line One line from the zipcodes file
     * @return A place that contains the relevant information
     * (zipcode, town, state, and population if availible) from that line
     */
    public static LocatedPlace parseLine(int lineNumber, String line) {
	String[] tokens = line.split(",", 0); //stores the tokens from given line
       	String zip = tokens[ZIP]; //zipcode from given line
	String town = tokens[TOWN]; //town from given line
	String state = tokens[STATE]; //state from given line

	//checks if population is listed
	if (tokens.length > POP) {
	    //creates a PopulatedPlace with data from line
	    String popString = tokens[POP]; //population from given line
	    int pop = Integer.parseInt(popString);
	    PopulatedPlace popPlace  = new PopulatedPlace(zip, town, state, 0, 0, pop); //0 is a placeholder for latitude/longitude
	    return popPlace;
	} else {
	    //creates a LocatedPlace with data from line
	    LocatedPlace place = new LocatedPlace(zip, town, state, 0, 0); //0 is a placeholder for latitude/longitude
	    return place;
	}
    }

    /** Finds a place with the same zipcode in the given ArrayList 
     *  and adds the given longitude/latitude data to the place
     * @param places An ArrayList of LocatedPlaces with latitude/longitude data missing
     * @param zip The zip code being searched for
     * @param lat The latitude that corresponds to the zipcode
     * @param lon The longitude that corresponds to the zipcode 
     * @param low The lower boundary of the binary search
     * @param high The upper boundary of the binary search
     */
    public static void combineData(ArrayList<LocatedPlace> places, String zip, double lat, double lon, int low, int high) {
	int mid = (low + high)/2; //middle index of the section of the ArrayList being searched
        String otherZip = places.get(mid).getZip(); //zip code that zip is compared to
        if (zip.equals(otherZip)) {
	    places.get(mid).setLatitude(lat);
	    places.get(mid).setLongitude(lon);
        } else if (zip.compareTo(otherZip) < 0) {
	    combineData(places, zip, lat, lon, low, mid-1);
        } else {
	    combineData(places, zip, lat, lon, mid+1, high);
	}
    }

    /** Reads two zipcodes files, parsing every line, and combines the data from both files
     * @param file1Name The name of the zipcodes file containing zipcode, town, state, and population data
     * @param file2Name The name of the zipcodes file containing zipcode, latitude, and longitude data
     * @return The ArrayList of Places representing all the data in the files.
     */
    public static ArrayList<Place> readZipCodes(String file1Name, String file2Name) throws FileNotFoundException {
	Scanner input = new Scanner(new File(file1Name));
	int lineNum = 0; //current line being read from the file
	String line; //stores a line from the zipcode file
	ArrayList<LocatedPlace> places = new ArrayList<>(); //temporarily stores all LocatedPlaces
	ArrayList<Place> updatedPlaces = new ArrayList<>(); //stores all Places once data is updated

	//reads the first file and adds LocatedPlaces/PopulatedPlaces
	//(with 0 as a placeholder for latitude/longitude) to the ArrayList
	String firstLine = input.nextLine();
	while (input.hasNextLine()) {
	    line = input.nextLine();
	    places.add(parseLine(lineNum, line));
	    lineNum++;
	}
	input.close();

	//reads the second file and adds availible latitude/longitude data to the existing LocatedPlaces
	Scanner input2 = new Scanner(new File(file2Name));
	firstLine = input2.nextLine();
	while (input2.hasNextLine()) {
	    line = input2.nextLine();
	    //parses data from line
	    String[] tokens = line.split(",", 0); //stores tokens from given line
	    String zip = tokens[ZIP].replace("\"",""); //zip code from given line
	    String latString = tokens[LAT]; //latitude from given line
	    String lonString = tokens[LON]; //longitude from given line

	    //checks if latitude is missing from line
	    if (!latString.equals("")) {
		//stores latitude and longitude as doubles
		double lat = Double.parseDouble(latString);
		double lon = Double.parseDouble(lonString);
		int low = 0; //first index in the ArrayList
		int high = places.size(); //last index in the ArrayList
		combineData(places, zip, lat, lon, low, high);
	    }
	}
	input2.close();

	//changes LocatedPlaces without latitude/longitude data to Places
	for(int i=0; i<places.size(); i++) {
	    updatedPlaces.add(places.get(i));
	    if (places.get(i).getLatitude()==0) { //checks if latitude has the 0 placeholder
		Place place = new Place(places.get(i).getZip(), places.get(i).getTown(), places.get(i).getState());
		updatedPlaces.set(i, place);
	    }
	}
	
	return updatedPlaces;
    }


    /** Find a Place with a given zip code
     * @param places The ArrayList of places being searched
     * @param zip The zip code (as a String) to look up
     * @return A place that matches the given zip code,
     * or null if no such place exists.
     */
    public static Place lookupZip(ArrayList<Place> places, String zip) {
        int low = 0; //first index in the ArrayList
	int high = places.size()-1; //last index in the ArrayList
        return lookupZipRec(places, zip, low, high);
     }

    /** Uses a binary search to find a Place with a given zip code
     * @param places The ArrayList of places being searched
     * @param zip The zip code (as a String) to look up
     * @param low The lower boundary of the binary search
     * @param high The upper boundary of the binary search
     * @return A place that matches the given zip code,
     * or null if no such place exists.
     */
    public static Place lookupZipRec(ArrayList<Place> places, String zip, int low, int high) {
	if (low > high) { //true when zip is not in places
	    return null;
	} else {
	    int mid = (low + high)/2; //middle index of the section of the ArrayList being searched
	    String otherZip = places.get(mid).getZip(); //zip code that zip is compared to
	    if (zip.equals(otherZip))
		return places.get(mid);
	    else if (zip.compareTo(otherZip) < 0)
		return lookupZipRec(places, zip, low, mid-1);
	    else
		return lookupZipRec(places, zip, mid+1, high);
	}	    
    }
    
}  
