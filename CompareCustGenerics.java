/* 
 * CompareCustGenerics.java 
 * 
 * Version: 
 *     $Id$
 * 
 * Revisions: Initial Version
 *    
 */
/**
 * CompareCustGenerics class implements the comparator interface and
 * defines a custom method compare.
 *
 * @author Sanket Agarwal
 * 
 */
import java.util.Comparator;

class CompareCustGenerics<T extends Comparable<T>> implements Comparator<T> {

	public int compare(T left, T right) {

		return left.compareTo(right);

	}
}
