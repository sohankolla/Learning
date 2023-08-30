/* *****************************************************************************
 *  Name: Sohan Kolla
 *  Date: 08/10/2023
 *  Description: Double ended queue
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private int count = 0; // counter for size of the linked list
    private Node first, last; // pointers for the beginning and end of the queue

    private class Node { // doubly-linked nodes to keep track of next and prev for when we need to remove from the end of the queue
        Item item;
        Node next;
        Node prev;
    }

    private class LinkedIterator implements Iterator<Item> { // iterator class
        private Node current = first;

        public boolean hasNext() {
            if (isEmpty()) {
                return false;
            }
            return current != null;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // construct an empty deque
    public Deque() {
        first = new Node();
        // initializing the nodes and making them point to
        // the same spot since the queue is new/empty
        last = first;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size() == 0; // checks the counter to see if the queue is empty
    }

    // return the number of items on the deque
    public int size() {
        return count; // returns the counter which tells how many nodes in the queue
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();
        // corner case incase client sends in empty item

        if (isEmpty()) { // if adding to an empty queue...
            first = new Node();
            // re-initializes the first node incase this isn't the first time the queue has been
            // empty (empty but not new i.e., items have been added and then removed, so it is now
            // empty again, therefore we cant use .next and .prev since the list is empty)

            first.item = item; // sets value to client's value
            last = first; // since the queue is empty, sets the pointers back to the same spot
        }
        else {
            // if the queue is not empty, we can use normal node logic to add
            // a first node behind the current one.
            Node oldFirst = first; // temp saving the current first
            first = new Node(); // creating a new node for the new first
            first.item = item; // adding value to the new first

            first.next = oldFirst;
            // pointing the current first's next node to the old first to link it to the list
            oldFirst.prev = first;
            // linking the previous of the old first to the new first

        }
        count++; // incrementing the counter for total nodes in the list.
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();
        // corner case incase client sends in empty item

        if (isEmpty()) { // if adding to an empty queue...
            last = new Node();
            // re-initializes the last node incase this isn't the first time the queue has been
            // empty (empty but not new, i.e., items have been added and then removed, so it is now
            // empty again therefore we cant use .next and .prev since the list is empty)

            last.item = item; // sets the value to the client's value
            first = last; // sets the pointers to the same spot since the queue was empty.
        }
        else {
            Node oldLast = last; // temp saving the current last
            last = new Node(); // re-initializing the last pointer to a new node
            last.item = item; // giving the new node a value
            oldLast.next = last; // linking the old last to the new last
            last.prev = oldLast;
            // linking the new last to the old last for future removeLast() method calls
        }
        count++; // incrementing the counter for total nodes in the linked-list
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();
        // corner-case, if the list is already empty, we can't remove anymore.

        Item item = first.item; // saving the value of the first, so we can return it later
        if (first.next != null) { // if the queue is greater than size 1...
            first = first.next; // we can just make the first pointer point to the next node
            first.prev = null; // then unlink the old first from the list
        }
        else { // if the list is size 1, then we are just removing the only element
            first = null; // removing the only element makes both pointers point to nothing
            last = null;
        }
        count--; // decrementing the counter for the total nodes in the linked-list
        return item; // returning the value we saved earlier
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException();
        // corner-case, we can't remove anymore elements if the queue is already empty.

        Item item = last.item; // saving the value to return later

        if (last.prev != null) { // if the queue is greater than size 1...
            last = last.prev;
            // we can just use our node info and make the last pointer
            // point to the node before the last

            last.next = null; // then we can unlink the old last from the list
        }
        else { // if the queue is size 1, then we have just removed the only element
            last = null; // so we just empty the pointers and make them point to nothing
            first = null;
        }
        count--; // decrementing the total node counter
        return item; // returning the value we saved earlier
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new LinkedIterator(); // returning a new object of the inner class we declared above
        // makes it so that we can use for-each loops on our list of generics (Items)
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> d = new Deque<>();
        StdOut.println(d.size());
        StdOut.println(d.isEmpty());
        d.addFirst(1);
        StdOut.println(d.isEmpty());
        StdOut.println(d.removeFirst());
        d.addFirst(2);
        StdOut.println(d.removeLast());
        d.addLast(3);
        StdOut.println(d.removeFirst());
        d.addLast(4);
        StdOut.println(d.removeLast());

        d.addLast(5);
        d.addLast(6);
        d.addFirst(4);
        d.addLast(7);
        d.addFirst(3);
        d.addLast(8);

        for (Integer i : d) {
            StdOut.println(i);
        }

    }
}
