public interface Queue<E> {

	/**
	 * Returns true if the Queue is currently empty
	 * 
	 * @return true if the queue is empty
	 */
	public boolean isEmpty();

	/**
	 * Add the reference to Object at the rear of the queue. Assumes s is not null
	 * 
	 * @param s The (non null) reference to the new element
	 */
	public void enqueue(E s);

	/**
	 * Removes the reference to the Object at the front of the queue. Assumes the
	 * queue is not empty
	 * 
	 * @return The reference to removed Object
	 */
	public E dequeue();

}
