//мой двухмесячный проект имеет 1000+ строчек. писать ЭТИ 600 БЫЛО НЕ ВЕСЕЛО

import java.util.Iterator;

interface MyList<E> extends Iterable<E> {
    void add(E element);
    void add(int index, E element);
    E get(int index);
    E set(int index, E element);
    E remove(int index);
    boolean remove(E element);
    int size();
    boolean isEmpty();
    void clear();
}

class MyArrayList<E> implements MyList<E> {

    private Object[] elements;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;

    public MyArrayList() {
        elements = new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    private void ensureCapacity() {
        if (size >= elements.length) {
            Object[] newArr = new Object[elements.length * 2];
            System.arraycopy(elements, 0, newArr, 0, size);
            elements = newArr;
        }
    }

    @Override
    public void add(E element) {
        ensureCapacity();
        elements[size++] = element;
    }

    @Override
    public void add(int index, E element) {
        checkPositionIndex(index);
        ensureCapacity();
        System.arraycopy(elements, index, elements, index + 1, size - index);
        elements[index] = element;
        size++;
    }

    @Override
    public E get(int index) {
        checkElementIndex(index);
        return elementData(index);
    }

    @Override
    public E set(int index, E element) {
        checkElementIndex(index);
        E old = elementData(index);
        elements[index] = element;
        return old;
    }

    @Override
    public E remove(int index) {
        checkElementIndex(index);
        E old = elementData(index);
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(elements, index + 1, elements, index, numMoved);
        }
        elements[--size] = null; // clear to let GC do its work
        return old;
    }

    @Override
    public boolean remove(E element) {
        for (int i = 0; i < size; i++) {
            if ((element == null && elements[i] == null) ||
                    (element != null && element.equals(elements[i]))) {
                remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }


    private E elementData(int index) {
        @SuppressWarnings("unchecked")
        E e = (E) elements[index];
        return e;
    }

    private void checkElementIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index = " + index + ", Size = " + size);
        }
    }

    private void checkPositionIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index = " + index + ", Size = " + size);
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            int cursor = 0;
            @Override
            public boolean hasNext() {
                return cursor < size;
            }

            @Override
            public E next() {
                return elementData(cursor++);
            }
        };
    }
}

class MyLinkedList<E> implements MyList<E> {

    private class MyNode {
        E element;
        MyNode next;
        MyNode prev;

        MyNode(E element) {
            this.element = element;
        }
    }

    private MyNode head;
    private MyNode tail;
    private int size;

    public MyLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public void add(E element) {
        MyNode newNode = new MyNode(element);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    @Override
    public void add(int index, E element) {
        checkPositionIndex(index);
        if (index == size) {

            add(element);
            return;
        }
        MyNode newNode = new MyNode(element);
        if (index == 0) {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        } else {
            MyNode current = getNode(index);
            MyNode prevNode = current.prev;
            newNode.next = current;
            newNode.prev = prevNode;
            prevNode.next = newNode;
            current.prev = newNode;
        }
        size++;
    }

    @Override
    public E get(int index) {
        checkElementIndex(index);
        return getNode(index).element;
    }

    @Override
    public E set(int index, E element) {
        checkElementIndex(index);
        MyNode node = getNode(index);
        E oldValue = node.element;
        node.element = element;
        return oldValue;
    }

    @Override
    public E remove(int index) {
        checkElementIndex(index);
        MyNode toRemove = getNode(index);
        E oldValue = toRemove.element;
        MyNode prevNode = toRemove.prev;
        MyNode nextNode = toRemove.next;

        if (prevNode == null) {
            head = nextNode;
        } else {
            prevNode.next = nextNode;
        }

        if (nextNode == null) {
            tail = prevNode;
        } else {
            nextNode.prev = prevNode;
        }
        size--;
        return oldValue;
    }

    @Override
    public boolean remove(E element) {
        MyNode current = head;
        for (int i = 0; i < size; i++) {
            if ((element == null && current.element == null) ||
                    (element != null && element.equals(current.element))) {
                remove(i);
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        MyNode current = head;
        while (current != null) {
            MyNode next = current.next;
            current.prev = null;
            current.next = null;
            current.element = null;
            current = next;
        }
        head = null;
        tail = null;
        size = 0;
    }

    private MyNode getNode(int index) {
        if (index < size / 2) {
            MyNode current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current;
        } else {
            MyNode current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
            return current;
        }
    }

    private void checkElementIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index = " + index + ", Size = " + size);
        }
    }

    private void checkPositionIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index = " + index + ", Size = " + size);
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private MyNode current = head;
            @Override
            public boolean hasNext() {
                return current != null;
            }
            @Override
            public E next() {
                E val = current.element;
                current = current.next;
                return val;
            }
        };
    }
}

class MyStack<E> {
    private MyArrayList<E> list;

    public MyStack() {
        list = new MyArrayList<>();
    }

    public void push(E element) {
        list.add(element);
    }

    public E pop() {
        if (list.isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        return list.remove(list.size() - 1);
    }

    public E peek() {
        if (list.isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        return list.get(list.size() - 1);
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public int size() {
        return list.size();
    }

    public void clear() {
        list.clear();
    }
}

class MyQueue<E> {
    private MyLinkedList<E> list;

    public MyQueue() {
        list = new MyLinkedList<>();
    }

    public void enqueue(E element) {
        list.add(element);
    }

    public E dequeue() {
        if (list.isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        return list.remove(0);
    }

    public E peek() {
        if (list.isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        return list.get(0);
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public int size() {
        return list.size();
    }

    public void clear() {
        list.clear();
    }
}

class MyMinHeap<E extends Comparable<E>> {
    private MyArrayList<E> heap;

    public MyMinHeap() {
        this.heap = new MyArrayList<>();
    }

    public void add(E element) {
        heap.add(element);
        siftUp(heap.size() - 1);
    }

    public E peek() {
        if (heap.isEmpty()) {
            throw new IllegalStateException("Heap is empty");
        }
        return heap.get(0);
    }

    public E poll() {
        if (heap.isEmpty()) {
            throw new IllegalStateException("Heap is empty");
        }
        E result = heap.get(0);
        E last = heap.remove(heap.size() - 1);
        if (!heap.isEmpty()) {
            heap.set(0, last);
            siftDown(0);
        }
        return result;
    }

    public int size() {
        return heap.size();
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    public void clear() {
        heap.clear();
    }

    private void siftUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            E current = heap.get(index);
            E parentVal = heap.get(parent);
            if (current.compareTo(parentVal) < 0) {
                swap(index, parent);
                index = parent;
            } else {
                break;
            }
        }
    }

    private void siftDown(int index) {
        int size = heap.size();
        while (true) {
            int left = 2 * index + 1;
            int right = 2 * index + 2;
            int smallest = index;

            if (left < size && heap.get(left).compareTo(heap.get(smallest)) < 0) {
                smallest = left;
            }
            if (right < size && heap.get(right).compareTo(heap.get(smallest)) < 0) {
                smallest = right;
            }
            if (smallest != index) {
                swap(index, smallest);
                index = smallest;
            } else {
                break;
            }
        }
    }

    private void swap(int i, int j) {
        E temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }
}

public class Main {
    public static void main(String[] args) {
        System.out.println("тестируем эту парашу");
        MyList<String> arrayList = new MyArrayList<>();
        arrayList.add("A");
        arrayList.add("B");
        arrayList.add("C");
        arrayList.add(1, "X");
        System.out.println("содержимое после добавлений: ");
        for (String s : arrayList) {
            System.out.print(s + " ");
        }
        System.out.println("\nget(2): " + arrayList.get(2));
        System.out.println("remove(1): " + arrayList.remove(1));
        System.out.println("remove(\"C\"): " + arrayList.remove("C"));
        System.out.println("содержимое после удалений: ");
        for (String s : arrayList) {
            System.out.print(s + " ");
        }
        System.out.println("\nРазмер: " + arrayList.size());
        arrayList.clear();
        System.out.println("пуст ли список после clear()? " + arrayList.isEmpty());

        System.out.println("\nтестируем линкедлист");
        MyList<Integer> linkedList = new MyLinkedList<>();
        linkedList.add(10);
        linkedList.add(20);
        linkedList.add(30);
        linkedList.add(1, 15);
        System.out.println("содержимое после добавлений: ");
        for (Integer i : linkedList) {
            System.out.print(i + " ");
        }
        System.out.println("\nget(2): " + linkedList.get(2));
        System.out.println("set(2, 25): " + linkedList.set(2, 25));
        System.out.println("remove(1): " + linkedList.remove(1));
        linkedList.remove((Integer)30);
        System.out.println("содержимое после удалений: ");
        for (Integer i : linkedList) {
            System.out.print(i + " ");
        }
        System.out.println("\nРазмер: " + linkedList.size());
        linkedList.clear();
        System.out.println("пуст ли список после clear()? " + linkedList.isEmpty());

        System.out.println("\nтест майстек....");
        MyStack<String> stack = new MyStack<>();
        stack.push("One");
        stack.push("Two");
        stack.push("Three");
        System.out.println("поп: " + stack.pop());
        System.out.println("пик: " + stack.peek());
        System.out.println("размер: " + stack.size());
        stack.clear();
        System.out.println("туста ли параша?? " + stack.isEmpty());

        System.out.println("тестируем дрисню куеуе");
        MyQueue<String> queue = new MyQueue<>();
        queue.enqueue("First");
        queue.enqueue("Second");
        queue.enqueue("Third");
        System.out.println("декью: " + queue.dequeue());
        System.out.println("пик: " + queue.peek());
        System.out.println("размер: " + queue.size());
        queue.clear();
        System.out.println("пусто? " + queue.isEmpty());

        System.out.println("еще один тест ура");
        MyMinHeap<Integer> minHeap = new MyMinHeap<>();
        minHeap.add(5);
        minHeap.add(3);
        minHeap.add(8);
        minHeap.add(1);
        System.out.println("пик (минимум): " + minHeap.peek());
        System.out.println("полл (извлекаем минимум): " + minHeap.poll());
        System.out.println("новый минимум: " + minHeap.peek());
        System.out.println("размер кучи: " + minHeap.size());
        minHeap.clear();
        System.out.println("пустота?" + minHeap.isEmpty());
    }
}


//это не смешная домашка. не надо так больше делать прошу