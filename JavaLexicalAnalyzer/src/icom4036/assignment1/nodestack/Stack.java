package icom4036.assignment1.nodestack;
/**
 * Interface for a stack, a collection of objects that are inserted and removed according to the
 * last-in first-out principle. This interface includes the main methods of java.util.Stack
 * Reference: "Data Structures and Algorithms" by Michael T. Goodrich & Roberto Tamassia
 * Original Authors: Michael T. Goodrich & Roberto Tamassia
 * @author Jose F. Martinez
 * 
 *
 * @param <E>
 */
public interface Stack<E> {
	/**
	 * Insert element e, to be the top of the stack
	 * @param element
	 */
	public void push(E element);
	
	/**
	 * Remove from the stack and return the top element on the stack; an error occurs
	 * if the stack is empty.
	 * @return
	 */
	public E pop() throws EmptyStackException;
	
	/**
	 * Return the number of elements in the stack.
	 * @return
	 */
	public int size();
	
	/**
	 * Return a Boolean indicating if the stack is empty.
	 * @return
	 */
	public boolean isEmpty();
	
	/**
	 * Return the top element in the stack, without removing it; an error occurs
	 * if the stack is empty.
	 * @return
	 */
	public E top() throws EmptyStackException;

}
