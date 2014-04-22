package danaus;

import java.text.SimpleDateFormat;
import java.util.Date;

/** A debugging tool shed, Debugger has many useful debugging tools that can 
 * help log a program, check for null parameters, etc.  
 */
public class Debugger {
	/** The time log formatting string. Each message is prefixed by a time log
	 * that is formatted by a SimpleDateFormat with the following pattern. */
	private static String DATE_FORMAT= "HH:mm:ss.SSSS";
	
	/**
	 * If DEBUG_ENABLED is true, prints a message logged with the current time.
	 * 
	 * @param message A debug message.
	 */
	public static void DEBUG(String message) {
		if (!Common.DEBUG_ENABLED) {
			return;
		}
		
		// Gather the time and format it nicely.
		Date date= new Date();
		SimpleDateFormat formatter= new SimpleDateFormat(DATE_FORMAT);
		
		System.out.println("[" + formatter.format(date) + "] DEBUG  : " + 
		                   message);
	}
	
	/** If WARNING_ENABLED is true, print message logged with the current time.
	 * 
	 * @param message A warning message. 
	 */
	public static void WARNING(String message) {
		if (!Common.WARNING_ENABLED) {
			return;
		}
		
		// Gather the time and format it nicely.
		Date date= new Date();
		SimpleDateFormat formatter= new SimpleDateFormat(DATE_FORMAT);
		
		System.out.println("[" + formatter.format(date) + "] WARNING: " + 
		                   message);
	}
	
	/** If ERROR_ENABLED is true, print message logged with the current time.
	 * 
	 * @param message An error message.
	 */
	public static void ERROR(String message) {
		if (!Common.ERROR_ENABLED) {
			return;
		}
		
		// Gather the time and format it nicely.
		Date date= new Date();
		SimpleDateFormat formatter= new SimpleDateFormat(DATE_FORMAT);
		
		System.out.println("[" + formatter.format(date) + "] ERROR  : " + 
		                   message);
	}
	
	/** If NULL_CHECK_ENABLED is true and obj is null, print message and
	 * throw a NullPointerException.
	 * 
	 * @param obj An object.
	 * @param message An error message to produce if obj is null.
	 */
	public static void NULL_CHECK(Object obj, String message) {
		if (!Common.NULL_CHECK_ENABLED) {
			return;
		}
		
		if (obj == null) {
			ERROR(message);
			throw new NullPointerException();
		}
	}
}
