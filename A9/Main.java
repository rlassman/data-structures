/* Name: Rebecca Lassman
 * File: Main.java
 * Desc:
 *
 * The main driver program for Assignment 9.
 *
 * This program continuously prompts the user for a zipcode until they enter 00000
 * to quit. It prints the data stored in the Place object from an AVLTree of places with the
 * corresponding zipcode to the one entered by the user. It tells the user that there
 * is no such zipcode if it is not found in the tree.
 *
 */

import java.io.*;
import java.util.*;

public class Main {
    public static final String quitCode = "00000"; //terminates program when entered
    public static final String iFlag = "-i", dFlag = "-d"; //command-line flags
    
    public static void main(String[] args) throws FileNotFoundException {
	//checks for flags
	int iIndex = -1;
	int dIndex = -1;
	for (int i=0; i<args.length; i++) {
	    if (args[i].equals(iFlag))
	       iIndex = i;
	    else if (args[i].equals(dFlag))
		dIndex = i;
	}
	if (iIndex == -1) {
	    System.out.println("No input was given.");
	    System.exit(0);
	}
	
       	//stores all places created from the files' data
	AVLTree<Place> places = new AVLTree<Place>();
	try {
	    places = LookupZip.readZipCodes(args[iIndex+1],args[iIndex+2]);
	} catch (FileNotFoundException e) {
	    System.out.println("File not found.");
	    System.exit(0);
	} catch (ArrayIndexOutOfBoundsException e) {
	    System.out.println("No files were given.");
	    System.exit(0);
	}

	//prints debugging info if necessary
	if (dIndex != -1) {
	    System.out.println(places.height());
	    System.out.println(places);
	}

       	//continuously prompts user for zipcodes and prints corresponding data
	Scanner input = new Scanner(System.in);
	String userZip = "";
	while (!userZip.equals(quitCode)) {
	    System.out.print("zipcode: ");
	    userZip = input.next();	    
	    Place place = LookupZip.lookupZip(places, userZip);
	    if (place == null) {
		System.out.println("No such zipcode\n");
	    } else {
		System.out.println(place.getData()+"\n");
	    }
	}
	System.out.println("Good Bye!");
    }
}
