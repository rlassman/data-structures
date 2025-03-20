/* Name: Rebecca Lassman
 * File: Main.java
 * Desc:
 *
 * The main driver program for Assignment 2.
 *
 * This program continuously prompts the user for a zipcode until they enter 00000
 * to quit. It prints the data stored in the Place object from an ArrayList of places with the
 * corresponding zipcode to the one entered by the user. It tells the user that there
 * is no such zipcode if it is not found in the ArrayList.
 *
 */

import java.io.*;
import java.util.*;

public class Main {
    public static final String quitCode = "00000"; //terminates program when entered
    
    //see description above
    public static void main(String[] args) throws FileNotFoundException {
	//prompts user for the first zipcode
	Scanner input = new Scanner(System.in);
	System.out.print("zipcode: ");
	String userZip = input.next(); //zipcode entered by user

	//stores all places created from the files' data
	ArrayList<Place> places = LookupZip.readZipCodes("uszipcodes.csv", "ziplocs.csv");
	//continuously prompts user for zipcodes and prints corresponding data
	while (!userZip.equals(quitCode)) {
	    Place place = LookupZip.lookupZip(places, userZip);
	    if (place == null) {
		System.out.println("No such zipcode\n");
	    } else {
		System.out.println(place+"\n");
	    }
	    //prompts user for the next zipcode
	    System.out.print("zipcode: ");
	    userZip = input.next();
	}
	System.out.println("Good Bye!");
    }

}
