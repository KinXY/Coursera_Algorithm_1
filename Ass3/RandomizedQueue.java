import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int m_size;
    private int m_capacity;
    private int m_topIndex;
    private int m_bottomIndex;
    private Item[] m_items;

    // construct an empty randomized queue
    public RandomizedQueue() {
        m_items = (Item[]) new Object[4];
        m_size = 0;
        m_capacity = 4;
        m_topIndex = 1;
        m_bottomIndex = 2;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return m_size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return m_size;
    }

    // when the array hits the capacity, resize it
    private void resize(int newCapacity) {
        Item[] newArray = (Item[]) new Object[newCapacity];
        // the index for the new element to be put
        int count = newCapacity / 4;
        for (int i = m_topIndex; i < m_bottomIndex; i++) {
            newArray[count] = m_items[i];
            count++;
        }
        m_items = newArray;
        m_topIndex = newCapacity / 4;
        m_bottomIndex = m_topIndex + m_size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        m_topIndex--;
        m_items[m_topIndex] = item;
        m_size++;

        // resize the array if necessary
        if (m_topIndex == 0) {
            resize(m_capacity * 2);
            m_capacity *= 2;
        }
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }

        // by the resizing, the array will not be too empty, so the
        // random algorithm will not take a lot tries
        Item result;
        while (true) {
            int randomIndex = StdRandom.uniformInt(m_topIndex, m_bottomIndex + 1);
            if (m_items[randomIndex] != null) {
                result = m_items[randomIndex];
                m_items[randomIndex] = null;
                m_size--;

                // prevent iterator bugs
                if (randomIndex == m_bottomIndex - 1) {
                    m_bottomIndex--;
                }
                break;
            }
        }

        // resize the array if necessary
        if (m_size == m_capacity / 4) {
            resize(m_capacity / 2);
            m_capacity /= 2;
        }

        return result;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }

        // by the resizing, the array will not be too empty, so the
        // random algorithm will not take a lot tries
        while (true) {
            int randomIndex = StdRandom.uniformInt(m_topIndex, m_bottomIndex + 1);
            if (m_items[randomIndex] != null) {
                return m_items[randomIndex];
            }
        }
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RQIterator();
    }

    private class RQIterator implements Iterator<Item> {
        private int currentIndex = m_topIndex;

        public boolean hasNext() {
            return !(currentIndex == m_bottomIndex);
        }

        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }

            // iterate until the element is not null
            while (true) {
                if (m_items[currentIndex] != null) {
                    break;
                }
                currentIndex++;
            }
            Item result = m_items[currentIndex];
            currentIndex++;
            return result;
        }

        public void remove() {
            // remove the element from the queue
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        // unit test
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        // enqueue
        rq.enqueue("first");
        rq.enqueue("second");
        rq.enqueue("third");
        rq.enqueue("fourth");
        for (String i : rq) {
            System.out.println(i);
        }
        // dequeue
        System.out.println(rq.dequeue());
        System.out.println(rq.dequeue());
        for (String i : rq) {
            System.out.println(i);
        }
    }

}