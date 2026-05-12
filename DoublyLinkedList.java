/**
 * A basic doubly linked list implementation.
 *
 * @author Michael T. Goodrich
 * @author Roberto Tamassia
 * @author Michael H. Goldwasser
 */
public class DoublyLinkedList<E> {

  //---------------- nested Node class ----------------
  /**
   * Node of a doubly linked list, which stores a reference to its
   * element and to both the previous and next node in the list.
   */
  private static class Node<E> {

    /** The element stored at this node */
    private E element;               // reference to the element stored at this node

    /** A reference to the preceding node in the list */
    private Node<E> prev;            // reference to the previous node in the list

    /** A reference to the subsequent node in the list */
    private Node<E> next;            // reference to the subsequent node in the list

    /**
     * Creates a node with the given element and next node.
     *
     * @param e  the element to be stored
     * @param p  reference to a node that should precede the new node
     * @param n  reference to a node that should follow the new node
     */
    public Node(E e, Node<E> p, Node<E> n) {
      element = e;
      prev = p;
      next = n;
    }

    // public accessor methods
    /**
     * Returns the element stored at the node.
     * @return the element stored at the node
     */
    public E getElement() { 
    	return element; 
    }

    /**
     * Returns the node that precedes this one (or null if no such node).
     * @return the preceding node
     */
    public Node<E> getPrev() { return prev; }

    /**
     * Returns the node that follows this one (or null if no such node).
     * @return the following node
     */
    public Node<E> getNext() { return next; }

    // Update methods
    /**
     * Sets the node's previous reference to point to Node n.
     * @param p    the node that should precede this one
     */
    public void setPrev(Node<E> p) { prev = p; }

    /**
     * Sets the node's next reference to point to Node n.
     * @param n    the node that should follow this one
     */
    public void setNext(Node<E> n) { next = n; }
  } //----------- end of nested Node class -----------

  // instance variables of the DoublyLinkedList
  /** Sentinel node at the beginning of the list */
  private Node<E> header;                    // header sentinel

  /** Sentinel node at the end of the list */
  private Node<E> trailer;                   // trailer sentinel

  /** Number of elements in the list (not including sentinels) */
  private int size = 0;                      // number of elements in the list

  /** Constructs a new empty list. */
  public DoublyLinkedList() {
    header = new Node<>(null, null, null);      // create header
    trailer = new Node<>(null, header, null);   // trailer is preceded by header
    header.setNext(trailer);                    // header is followed by trailer
  }

  // public accessor methods
  /**
   * Returns the number of elements in the linked list.
   * @return number of elements in the linked list
   */
  public int size() { return size; }

  /**
   * Tests whether the linked list is empty.
   * @return true if the linked list is empty, false otherwise
   */
  public boolean isEmpty() { return size == 0; }

  /**
   * Returns (but does not remove) the first element of the list.
   * @return element at the front of the list (or null if empty)
   */
  public E first() {
    if (isEmpty()) return null;
    return header.getNext().getElement();   // first element is beyond header
  }

  /**
   * Returns (but does not remove) the last element of the list.
   * @return element at the end of the list (or null if empty)
   */
  public E last() {
    if (isEmpty()) return null;
    return trailer.getPrev().getElement();    // last element is before trailer
  }

  // public update methods
  /**
   * Adds an element to the front of the list.
   * @param e   the new element to add
   */
  public void addFirst(E e) {
    addBetween(e, header, header.getNext());    // place just after the header
  }

  /**
   * Adds an element to the end of the list.
   * @param e   the new element to add
   */
  public void addLast(E e) {
    addBetween(e, trailer.getPrev(), trailer);  // place just before the trailer
  }

  /**
   * Removes and returns the first element of the list.
   * @return the removed element (or null if empty)
   */
  public E removeFirst() {
    if (isEmpty()) return null;                  // nothing to remove
    return remove(header.getNext());             // first element is beyond header
  }

  /**
   * Removes and returns the last element of the list.
   * @return the removed element (or null if empty)
   */
  public E removeLast() {
    if (isEmpty()) return null;                  // nothing to remove
    return remove(trailer.getPrev());            // last element is before trailer
  }

  // private update methods
  /**
   * Adds an element to the linked list in between the given nodes.
   * The given predecessor and successor should be neighboring each
   * other prior to the call.
   *
   * @param predecessor   node just before the location where the new element is inserted
   * @param successor     node just after the location where the new element is inserted
   */
  private void addBetween(E e, Node<E> predecessor, Node<E> successor) {
    // create and link a new node
    Node<E> newest = new Node<>(e, predecessor, successor);
    predecessor.setNext(newest);
    successor.setPrev(newest);
    size++;
  }

  /**
   * Removes the given node from the list and returns its element.
   * @param node    the node to be removed (must not be a sentinel)
   */
  private E remove(Node<E> node) {
    Node<E> predecessor = node.getPrev();
    Node<E> successor = node.getNext();
    predecessor.setNext(successor);
    successor.setPrev(predecessor);
    size--;
    return node.getElement();
  }

  /**
   * Produces a string representation of the contents of the list.
   * This exists for debugging purposes only.
   */
  public String toString() {
    StringBuilder sb = new StringBuilder("(");
    Node<E> walk = header.getNext();
    while (walk != trailer) {
      sb.append(walk.getElement());
      walk = walk.getNext();
      if (walk != trailer)
        sb.append(", ");
    }
    sb.append(")");
    return sb.toString();
  }
  
  // This method is used specifically to remove a watcher from the list
  public void findAndRemove(String name) {
      if (this.isEmpty()) { // In case list is empty
          System.out.println("No record on list\n");
      }
      Node<E> walk = this.header.getNext();
      while (walk != trailer) { // Traverse through the whole list
          Watcher watcher = (Watcher) walk.getElement();
          if (watcher.getName().equals(name)) { // Check if the current node equals the needed name
              remove(walk);
              System.out.println(name + " is removed from the watcher-list\n");
              return;
          }
          walk = walk.getNext(); // Continue traversing
      }
      System.out.println("Watcher not found\n"); // In case the name was not found
  }
  
  // This method is used specfically to find the earthquake with the biggest magnitude
  public void queryLargest() {
      if (this.isEmpty()) { // In case list is empty
          System.out.println("No record on list\n");
          return;
      }
      Node<E> walk = this.header.getNext();
      Earthquake currentMax = null; // Default value
      double maxMagnitude = 0.0; // Default value
      while (walk != trailer) { // Traverse through the whole list
          Earthquake current = (Earthquake) walk.getElement();
          if (current.getMagnitude() >= maxMagnitude) { // Check if the current node's magnitude is bigger than the currentmax
              maxMagnitude = current.getMagnitude();
              currentMax = current; // Change if it's bigger
          }
          walk = walk.getNext(); // Continue traversing
      }
      System.out.println("Largest earthquake in the past 6 hours:");
      System.out.println("Magnitude " + currentMax.getMagnitude() + " at " + currentMax.getPlace()+"\n");
  }
  
  // This method is used specifically to notify the watchers that are close to the added earhtquake
  public void closeWatchers(Earthquake earthquake) {
      if (this.isEmpty()) { // In case the list is empty
          System.out.println("No watchers to notify");
          return; 
      }

      Node<E> walk = header.getNext();
      while (walk != trailer) { // Traverse through the whole list
          Watcher watcher = (Watcher) walk.getElement();
          double longitudeDiffernece = watcher.getLongitude() - earthquake.getLongitude(); // To calculate the distance
          double latitudeDifference = watcher.getLatitude() - earthquake.getLatitude(); // To calculate the distance
          double distance = Math.sqrt((longitudeDiffernece*longitudeDiffernece) + (latitudeDifference*latitudeDifference));
          double formula = 2*earthquake.getMagnitude()*earthquake.getMagnitude()*earthquake.getMagnitude(); // This is the formula provided in the assignment file used to determine if the watcher is close or not
          if (distance < formula) { // Check if the watcher is close enough
              System.out.println(earthquake + " is close to " + watcher);
          }
          walk = walk.getNext(); // Continue traversing
      }
      System.out.println("");
      
  }
  
  // This method is used specifically to remove earthquakes that were added over 6 hours ago
  public void removeOld(int time) {
      if (this.isEmpty()) { // In case list is empty
          return;
      }
      Node<E> walk = this.header.getNext();
      while (walk != trailer) { // Traverse through the whole list
          Earthquake current = (Earthquake) walk.getElement();
          if ((time - current.getTime() > 6)) { // Check if more than 6 hours has passed since this earthquake was added
              remove(walk); // Remove it
              walk = walk.getNext(); // Continue traversing
          }
          else {
              break; // Break the loop if its less than 6 hours since all the next earthquakes are also less than 6 hours
          }
      }
  }
} //----------- end of DoublyLinkedList class -----------
