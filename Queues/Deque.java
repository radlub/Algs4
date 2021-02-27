import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node head;
    private Node tail;
    private int size;

    // Create a private Node class
    private class Node {

        Item item;
        Node prev;
        Node next;

        Node(Item item) {
            this.item = item;
        }
    }


    // Initialize and empty queue
    public Deque() {
        head = new Node(null);  // dummy head
        tail = new Node(null);  // dummy tail
        head.next = tail;
        tail.prev = head;
    }

    // Check if deque is empty.
    public boolean isEmpty() {
        return size == 0;
    }

    // Return size of deque
    public int size() {
        return size;
    }

    // Add and item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new NullPointerException("Element cannot be null.");
        }
        Node node = new Node(item);
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
        size++;
    }

    // Add an item to the tail
    public void addLast(Item item) {
        if (item == null) {
            throw new NullPointerException("Element cannot be null.");
        }
        Node node = new Node(item);
        node.next = tail;
        node.prev = tail.prev;
        tail.prev.next = node;
        tail.prev = node;
        size++;
    }

    // Pop first element
    public Item removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException("Deque is empty.");
        }
        Node node = head.next;
        head.next = node.next;
        head.next.prev = head;
        size--;
        return node.item;
    }

    // Pop last element
    public Item removeLast() {
        if (size == 0) {
            throw new NoSuchElementException("Deque is empty.");
        }
        Node node = tail.prev;
        tail.prev = node.prev;
        tail.prev.next = tail;
        size--;
        return node.item;
    }

    // Return an iterator over elements in the structure
    @Override
    public Iterator<Item> iterator() {
        return new HeadFirstIterator();
    }

    private class HeadFirstIterator implements Iterator<Item> {

        private Node curr = head;

        @Override
        public boolean hasNext() {
            return curr.next != tail;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more items.");
            }
            curr = curr.next;
            return curr.item;
        }
    }

    public static void main(String[] args) {

        Deque<Integer> d1 = new Deque<>();
    }
}
