package org.example;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class SortedList<T extends Comparable<T>> {

    private final List<T> list;

    private final Queue<T> queue;

    public SortedList() {
        list = new LinkedList<>();
        queue = new PriorityQueue<>(10);
    }

    public T poll() {
        T temp = queue.poll();
        list.remove(temp);
        return temp;
    }

    public void offer(T object){
        list.add(object);
        queue.offer(object);
    }

    public boolean contains(T object) {
        return queue.contains(object);
    }

    public T get(T object){
        return list.get(list.indexOf(object));
    }

    public void remove(T object) {
        list.remove(object);
        queue.remove(object);
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
