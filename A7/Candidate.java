/* Name: Rebecca Lassman
 * File: Candidate.java
 * Desc:
 *
 * This class creates an instance of a candidate and stores
 * the data associated with that candidate.
 *
 */

public class Candidate implements Comparable<Candidate> {

    private String lastName; //candidate's last name
    private String fullName; //candidate's full name given in file
    private double percent; //candidate's polling percent

    /** Constructs a candidate object containing a last name, full name, and percent
     * @param last The candidate's last name
     * @param full The candidate's full name given in file
     * @param perc The candidate's polling percent
     */
    public Candidate(String last, String full, double perc) {
	lastName = last;
	fullName = full;
	percent = perc;
    }

    /** Returns the value of lastName
     * @return The value of the lastName instance variable
     */
    public String getLastName() {
	return lastName;
    }

    /** Returns the value of fullName
     * @return The value of the fullName instance variable
     */
    public String getFullName() {
	return fullName;
    }

    /** Returns the value of percent
     * @return The value of the percent instance variable
     */
    public double getPercent() {
	return percent;
    }

    /** Sets the value of percent
     * @param p The new value for percent
     */
    public void setPercent(double p) {
	percent = p;
    }

    /** Compares two candidates based on polling percentage or last name
     * @param other The candidate being compared to
     * @return 0 if the candidates have the same last name, -1 if the
     * candidate has a higher polling percentage, 1 if it has a lower 
     * percentage, or 2 if they have the same polling percentage.
     */
    public int compareTo(Candidate other) {
	if (lastName.equals(other.getLastName()))
	    return 0;
	else if (percent > other.getPercent())
	    return -1;
	else if (percent == other.getPercent())
	    return 2;
	else
	    return 1;
    }

    /** Formats how a candidate object is printed
     * @return The candidate's full name and polling percent
     */
    public String toString() {
	return fullName + ":" + percent;
    }    
}
