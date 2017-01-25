package org.gridkit.sketchbook;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Merges two {@link Iterator} into one.
 * <br/>
 * Each of nested {@link Iterator}s MUST produce ordered, duplicate free
 * sequence of values.
 * <br/>
 * Result would be ordered, duplicate free sequence of values present 
 * in either iterator.
 * 
 * @author Alexey Ragozin (alexey.ragozin@gmail.com)
 */
public class MergeIterator<T> implements Iterator<T> {

    @SuppressWarnings("rawtypes")
    private static final Comparator NATURAL = new NaturalComaprator();
    
    @SuppressWarnings("unchecked")
    public static <T> Iterator<T> merge(Iterator<T> a, Iterator<T> b) {
        return merge(a, b, NATURAL);
    }
    
    public static <T> Iterator<T> merge(Iterator<T> a, Iterator<T> b, Comparator<T> cmp) {
        return new MergeIterator<T>(a, b, cmp);
    }

    @SuppressWarnings("unchecked")
    public static <T> Iterable<T> merge(final Iterable<T> a, final Iterable<T> b) {
        return merge(a, b, NATURAL);
    }

    public static <T> Iterable<T> merge(final Iterable<T> a, final Iterable<T> b, final Comparator<T> cmp) {
        return new Iterable<T>() {
            @Override
            public Iterator<T> iterator() {
                return merge(a.iterator(), b.iterator(), cmp);
            }
        };
    }
    
    private final Iterator<T> a;
    private final Iterator<T> b;
    private final Comparator<T> comparator;
    private T peekA;
    private T peekB;
    
    @SuppressWarnings("unchecked")
    public MergeIterator(Iterator<T> a, Iterator<T> b, Comparator<T> cmp) {
        this.a = a;
        this.b = b;
        this.comparator = cmp == null ? NATURAL : cmp;
        peekA = a.hasNext() ? next(a) : null;
        peekB = b.hasNext() ? next(b) : null;
    }

    private T next(Iterator<T> it) {
        T v = it.next();
        if (v == null) {
            throw new NullPointerException("null element is not allowed");
        }
        return v;
    }
    
    @Override
    public boolean hasNext() {
        return peekA != null || peekB != null;
    }

    @Override
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        T result;
        if (peekA == null && peekB != null) {
            result = peekB;
            peekB = b.hasNext() ? b.next() : null;
        }
        else if (peekB == null && peekA != null) {
            result = peekA;
            peekA = a.hasNext() ? a.next() : null;
        }
        else {
            int c = comparator.compare(peekA, peekB);
            if (c == 0) {
                result = peekA;
                peekA = a.hasNext() ? a.next() : null;
                peekB = b.hasNext() ? b.next() : null;
            }
            else if (c > 0) {
                result = peekB;
                peekB = b.hasNext() ? b.next() : null;
            }
            else {
                result = peekA;
                peekA = a.hasNext() ? a.next() : null;
            }
        }
        return result;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    private static class NaturalComaprator implements Comparator<Comparable<Object>> {

        @Override
        public int compare(Comparable<Object> o1, Comparable<Object> o2) {
            return o1.compareTo(o2);
        }
    }
}
