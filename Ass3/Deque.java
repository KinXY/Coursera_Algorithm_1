import java.util.Iterator;

public class Deque<Item> implements Iterable<Item>{

    private Node first;
    private Node last;
    private int mySize;

    private class Node {
        Item item;
        Node next = null;
        Node prev = null;
    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        mySize = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return mySize == 0;
    }

    // return the number of items on the deque
    public int size() {
        return mySize;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        if (oldFirst != null) {
            oldFirst.prev = first;
        }

        if (last == null) {
            last = first;
        }

        // update the metadata
        mySize++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (isEmpty()) {
            addFirst(item);
            return;
        }

        last.next = new Node();
        Node oldLast = last;
        last = last.next;
        last.item = item;
        last.prev = oldLast;

        // update the metadata
        mySize++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }

        Item result = first.item;
        first = first.next;
        if (first != null) {
            first.prev = null;  
        }
        // update the metadata
        mySize--;
        return result;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }

        Item result = last.item;
        last = last.prev;
        if (last != null) {
            last.next = null;
        }

        // update the metadata
        mySize--;
        return result;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    // implementing the iterator
    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }

            Item result = current.item;
            current = current.next;
            return result;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> myDeque = new Deque<String>();
        // test the add functions
        myDeque.addFirst("second");
        myDeque.addFirst("first");
        myDeque.addLast("second last");
        myDeque.addLast("last");
        for (String i : myDeque) {
            System.out.println(i);
        }
        // test the remove functions
        myDeque.removeFirst();
        myDeque.removeLast();
        for (String i : myDeque) {
            System.out.println(i);
        }
        myDeque.removeFirst();
        myDeque.removeLast();
        for (String i : myDeque) {
            System.out.println(i);
        }
    }

}