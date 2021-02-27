import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] array;
    private int lastIndex;

    // Initialize randomized queue
    public RandomizedQueue() {
        Item[] a = (Item[]) new Object[1];
        array = a;
        lastIndex = -1;
    }

    // Check if the queue is empty.
    public boolean isEmpty() {
        return size() == 0;
    }

    // Return size of deque
    public int size() {
        return lastIndex + 1;
    }

    // Add an item
    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException("Element cannot be null.");
        }
        // If array is full, resize it.
        if (lastIndex + 1 == array.length) {
            resize(array.length * 2);
        }
        array[++lastIndex] = item;
    }

    // Remove and return random item.
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("RandomizedQueue is empty.");
        }
        int i = StdRandom.uniform(lastIndex + 1);
        Item removed = array[i];
        array[i] = array[lastIndex];
        array[lastIndex--] = null;
        // resize array if it is only 25% full
        if (size() > 0 && size() == array.length / 4) {
            resize(array.length / 2);
        }
        return removed;
    }

    // Return random item (without removal)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("RandomizedQueue is empty.");
        }
        Item sample = null;
        while (sample == null) {
            sample = array[StdRandom.uniform(lastIndex + 1)];
        }
        return sample;
    }

    private void resize(int newCapacity) {
        Item[] newArray = (Item[]) new Object[newCapacity];
        int i = 0, j = 0;
        while (i <= lastIndex) {
            newArray[j++] = array[i++];
        }
        array = newArray;
        lastIndex = j - 1;
    }

    // Return an iterator over items in queue in random order
    public Iterator<Item> iterator() {
        return new RandomizedIterator();
    }

    private class RandomizedIterator implements Iterator<Item> {

        private Item[] copiedArray;
        private int copiedLastIndex;

        RandomizedIterator() {
            Item[] a = (Item[]) new Object[lastIndex + 1];
            for (int i = 0; i <= lastIndex; i++) {
                a[i] = array[i];
            }
            copiedArray = a;
            copiedLastIndex = lastIndex;
        }

        @Override
        public boolean hasNext() {
            return copiedLastIndex >= 0;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more items.");
            }
            int i = StdRandom.uniform(copiedLastIndex + 1);
            Item item = copiedArray[i];
            copiedArray[i] = copiedArray[copiedLastIndex];
            copiedArray[copiedLastIndex--] = null;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not supported.");
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> q1 = new RandomizedQueue<>();
    }
}
