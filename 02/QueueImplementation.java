import java.util.ArrayList;

public class QueueImplementation<E> implements Queue<E> {
	/**
     * <b>queue</b> stores the references of the elements
     * currently in the queue
     */
    private ArrayList<E> queue;

    /**
     * Constructor, initializes  <b>queue</b>
     */
    public QueueImplementation() {
    	queue = new ArrayList<E>();
    }

    /**
     * implementation of the method <b>enqueue</b> 
     * from the interface <b>SolutionQueue</b>.
     * @param value
     *      The reference to the new element
     */
    public void enqueue(E value) {
    	if(value== null) {
    		throw new NullPointerException();
    	}
    	queue.add(value);
    }


	/**
     * implementation of the method <b>dequeue</b> 
     * from the interface <b>SolutionQueue</b>.
     * @return 
     *      The reference to removed Solution
     */
    public E dequeue() {
    	if(isEmpty()) {
    		System.out.println("Empty queue!");
    	}
    	return queue.remove(0);
    }

    /**
     * implementation of the method <b>isEmpty</b> 
     * from the interface <b>SolutionQueue</b>.
     * @return 
     *      true if the queue is empty 
     */
    public boolean isEmpty() {
    	return queue.size()==0;
    }
}
