/* Name: Rebecca Lassman
 * File: IllegalArgument.java
 * Desc:
 *
 * This exception checks if the user's command-line argument
 * is legal for its intended use in the program.
 *
 */

public class IllegalArgument extends RuntimeException {

    /** Constructs an IllegalArgument excpetion
     * @param errorMsg What is printed when the excpetion is thrown
     */
     public IllegalArgument(String errorMsg) {
	super(errorMsg);
    }
}
