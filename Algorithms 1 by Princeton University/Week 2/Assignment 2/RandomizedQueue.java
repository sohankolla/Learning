/* *****************************************************************************
 *  Name: Sohan Kolla
 *  Date: 08/10/2023
 *  Description: Randomized queue
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] queue;
    private int count; // counter for how many existing items are in the queue

    private class RandomIterator implements Iterator<Item> { // iterator class
        private int index; // counter for the current index when iterating through
        private Item[] random; // copy array for shuffling and random iteration

        public RandomIterator() {
            index = 0; // initializing index counter
            random = (Item[]) new Object[count];
            for (int i = 0; i < count; i++) {
                random[i] = queue[i]; // copying items over
            }
            StdRandom.shuffle(random); // shuffling the array
        }

        public boolean hasNext() {
            return index < count;
            // checks if the next index number (because we increment it in next())
            // is under the number of elements in the array.
            // When index == count then there is no next element.
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return random[index++];
            // returns the value of random at the current index then increments the index counter.
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // construct an empty randomized queue
    public RandomizedQueue() {
        queue = (Item[]) new Object[1]; // initializing the queue
        count = 0; // initializing the counter of elements

    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return count; // returns how many items exist in the queue
    }

    // grow or shrink the array
    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < count; i++) {
            // we can use a for loop to iterate through because dequeue() doesn't leave any gaps in
            // the array, so we can just iterate through it until all items are copied over
            // (item counter is count)

            copy[i] = queue[i];
        }
        queue = copy; // updating queue to be bigger or smaller
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
        // corner-case, can't add an empty item.

        if (count == queue.length) resize(queue.length * 2);
        // doubling the array capacity if the current array is full

        queue[count] = item; // adding the next element to the next index
        // since dequeue() automatically fills any gaps caused from removing items
        // we don't need to worry about filling them here, so we just put any new items to the right.

        count++; // incrementing the element counter
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();
        // corner-case, can't return a random item if there are no items.

        int i = StdRandom.uniformInt(count); // choosing a random item from the ones that exist
        Item item = queue[i]; // saving the value so we can return it later

        if (count == 1) { // when the array is only size 1
            queue[0] = null; // then we just set the only index to null
        }
        else if (i == count - 1) { // random index is also the most right index
            queue[i] = null; // just set it null as it won't create a gap in the array
        }
        else { // random index is not the last index and the array is bigger than size 1
            queue[i] = queue[count - 1];
            // move the value from the most right index in the array to the random
            // index we just emptied to fill the gap.
            queue[count - 1] = null;
            // make the most right index empty since we already moved its value.
            // this way, enqueue() won't have any problems continuing to add to the array
        }
        count--; // decrementing the element counter

        if (count > 0 && count == queue.length / 4) resize(queue.length / 2);
        // shrinking the array to a half when it only has 1/4th elements of it total capacity

        return item; // returning the removed element.
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();
        // corner-case, can't return a random item if there are no items.

        return queue[StdRandom.uniformInt(count)];
        // returns a random item between 0 and the number of existing elements
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomIterator();
        // creates a new object of the RandomIterator class we defined above
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> q = new RandomizedQueue<>();
        StdOut.println(q.size());
        StdOut.println(q.isEmpty());
        q.enqueue(1);
        StdOut.println(q.isEmpty());
        StdOut.println(q.dequeue());
        q.enqueue(2);
        q.enqueue(3);
        q.enqueue(4);
        q.enqueue(5);
        StdOut.println(q.sample());
        StdOut.println(q.dequeue());

        for (Integer i : q) {
            StdOut.println(i);
        }
    }

}
