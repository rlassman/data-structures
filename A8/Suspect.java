/* Name: Rebecca Lassman
 * File: Suspect.java
 * Desc:
 *
 * This class creates an instance of a suspect using some of
 * the data from a row of an SQF data file.
 *
 */

public class Suspect implements Comparable<Suspect> {
    //file's placeholders for missing data
    public static final String defaultDOB = "12311900", blank = "Z", unknown = "U";
    //incremented & added to suspect with missing data's toString to prevent equality
    public static int randInt = 0;
    
    private String sex; //suspect's race, one letter abbreviation
    private String race; //suspect's sex (M or F)
    private String birthday; //suspect's date of birth
    private String heightFt; //feet part of suspect's height
    private String heightIn; //inches part of suspect's height
    private String weight; //suspect's weight
    private String hairColor; //suspect's hair color, two letter abbreviation
    private String eyeColor; //suspect's eye color, two letter abbreviation
    private String build; //suspect's build, one letter abbreviation

    /** Constructs a suspect object
     * @param s Suspect's sex
     * @param r Suspect's race
     * @param dob Suspect's date of birth
     * @param ft Feet part of suspect's height
     * @param in Inches part of suspect's height
     * @param w Suspect's weight
     * @param hair Suspect's hair color
     * @param eye Suspect's eye color
     * @param b Suspect's build
     */
    public Suspect(String s, String r, String dob, String ft, String in, String w, String hair, String eye, String b) {
	sex = s;
	race = r;
	birthday = dob;
	heightFt = ft;
	heightIn = in;
	weight = w;
	hairColor = hair;
	eyeColor = eye;
	build = b;
    }

    /** Returns the suspect's sex
     * @return The value of the sex instance variable
     */
    public String getSex() {
	return sex;
    }

    /** Returns the suspect's race
     * @return The value of the race instance variable
     */
    public String getRace() {
	return race;
    }

    /** Returns the suspect's date of birth
     * @return The value of the birthday instance variable
     */
    public String getBirthday() {
	return birthday;
    }

    /** Returns the suspect's feet part of height
     * @return The value of the heightFt instance variable
     */
    public String getHeightFt() {
	return heightFt;
    }

    /** Returns the suspect's inches part of height
     * @return The value of the heightIn instance variable
     */
    public String getHeightIn() {
	return heightIn;
    }

    /** Returns the suspect's weight
     * @return The value of the weight instance variable
     */
    public String getWeight() {
	return weight;
    }

    /** Returns the suspect's hair color
     * @return The value of the hairColor instance variable
     */
    public String getHair() {
	return hairColor;
    }

    /** Returns the suspect's eye color
     * @return The value of the eyeColor instance variable
     */
    public String getEye() {
	return eyeColor;
    }

    /** Returns the suspect's build
     * @return The value of the build instance variable
     */
    public String getBuild() {
	return build;
    }

    /** Determines if two suspects are the same person or their ordering
     * @param other The suspect being compared to
     * @return 0 if they are the same person, -1 if other's toString alphabetically
     * proceeds the suspect, or 1 otherwise
     */
    public int compareTo(Suspect other) {
	if (this.toString().equals(other.toString())) {
	   return 0;
	} else if (this.toString().compareTo(other.toString()) < 0) {
	    return -1;
	} else {
	    return 1;
	}
    }

    /** Creates a string containing the attributes of comparison for a suspect
     * @return A string containing the attributes of comparison (and a unique integer
     * if the suspect is missing data)
     */
    public String toString() { 
	String s = birthday + sex + race + heightFt + heightIn + weight + hairColor + eyeColor + build;
	if (!birthday.equals(defaultDOB) && !race.equals(blank) && !sex.equals(blank) && !race.equals(unknown)) {
	    return s;
	} else { //makes sure suspects with missing data can't be considered duplicates
	    return s + randInt++;
	}
    }
}
