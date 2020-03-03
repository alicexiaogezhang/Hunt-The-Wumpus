/*Alice Zhang
* CS 231 
* Lab#5
* 10/08/18
*/

import java.util.Iterator;
import java.util.ArrayList;
import java.util.Collections;


public class LinkedList<T> implements Iterable<T>{
    
    private int size;
    private Node head;
    
    /**
     * iterator subclass
     */
    private class LLIterator implements Iterator<T>{
        Node nextNode;
        public LLIterator(Node head){
            nextNode = head; 
        }
        public boolean hasNext(){
            return nextNode != null;
        }
        public T next(){
            if (hasNext()){
                T data = nextNode.getThing();
                nextNode = nextNode.getNext();
                return data;
            }
            return null;
        }
        public void remove(){}
    }

    /**
     *  returns a new LLIterator pointing to the head of the list
     */
    public Iterator<T> iterator() {
        return new LLIterator( this.head );
    }


    /**
     * a container class that holds a generic object and a next pointer
     */
    private class Node{
        private Node next;
        private T data;  
        public Node(T item){
            next = null;
            data = item;
        }
        /**
         * returns the value of the container field
         */
        public T getThing(){
            return data;
        }
        /**
         * sets next to the given node n
         */
        public void setNext(Node n){
            next = n;
        }
        /**
         * returns the next field
         */
        public Node getNext(){
            return next;
        }
    }
    
    /**
     * initializes an empty list
     */
    public LinkedList(){
        size = 0; 
        head = null;
    }
    
    /**
     * empties the list
     */
    public void clear(){
        size = 0;
        head = null;
    }
    
    /**
     * returns the size of the list
     */
    public int size(){
        return size;
    }

    /**
     * inserts the item at the beginning of the list
     */
    public void addFirst(T item){
        Node newNode = new Node(item);
        newNode.setNext(head);
        head = newNode;
        size++;
    }

    /**
     * appends the item at the end of the list
     */
    public void addLast(T item){
        Node newNode = new Node(item);
        if (head != null){
            Node curNode = head;
            while (curNode.getNext() != null){
                curNode = curNode.getNext();
            }
            curNode.setNext(newNode);
            newNode.setNext(null);
        }
        else {
            head = newNode;
            newNode.setNext(null);
        }
        size++;
    }

    /**
     * inserts the item at the specified index in the list
     */
    public void add(int index, T item){
        if (index<0 || index>size)
            throw new IndexOutOfBoundsException();
        if (index == 0)
            addFirst(item);
        else{
            Node newNode = new Node(item);
            Node curNode = head;
            for (int i = 1; i<index; i++){
                curNode = curNode.getNext();
            }
            newNode.setNext(curNode.getNext());
            curNode.setNext(newNode);
            size++;
        }
    }

    /**
     * removes the item at the specified index in the list and returns the value of the removed node
     */
    public T remove(int index){
        if (head == null || index >= size)
            throw new IndexOutOfBoundsException();
        Node removedNode = null; 
        if (index == 0){
            removedNode = head;
            head = head.getNext();
        }
        else{
            Node curNode = head;
            for (int i = 1; i<index; i++){
                curNode = curNode.getNext();
            }
            removedNode = curNode.getNext();
            curNode.setNext(removedNode.getNext());
        }
        size--;
        return removedNode.getThing();
    }

    /**
     * returns an ArrayList of the list contents in order
     */
    public ArrayList<T> toArrayList(){
        ArrayList<T> arr = new ArrayList<>();
        Iterator<T> itr = this.iterator();
        while (itr.hasNext())
            arr.add(itr.next());
        return arr; 
    }

    /**
     * returns an ArrayList of the list contents in shuffled order
     */
    public ArrayList<T> toShuffledList(){
        ArrayList<T> arr = new ArrayList<>();
        arr = this.toArrayList();
        Collections.shuffle(arr);
        return arr; 
    }
    


}