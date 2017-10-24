package part1.exercise;

import java.util.Arrays;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.IntConsumer;
import java.util.function.Predicate;

public class RectangleSpliterator extends Spliterators.AbstractIntSpliterator {

    private final int[][] array;
    private long arrayBeginning;
    private final long arrayEnding;

    public RectangleSpliterator(int[][] array) {
        this(array, 0 , checkArrayAndCalcEstimatedSize(array));
    }

    private RectangleSpliterator(int[][] array, long begginning, long ending) {
        //super(checkArrayAndCalcEstimatedSize(array), 0);       // TODO заменить
        super((ending - begginning), Spliterator.IMMUTABLE
                          | Spliterator.ORDERED
                          | Spliterator.SIZED
                          | Spliterator.SUBSIZED
                          | Spliterator.NONNULL);
        this.array = array;
        this.arrayEnding = array.length;
        this.arrayBeginning = array.length - array[0].length;
    }

    private static long checkArrayAndCalcEstimatedSize(int[][] array) {
        // TODO
        if (Arrays.stream(array).anyMatch(eachArray -> eachArray.length == array[0].length)){
            return array.length * array[0].length;
        } else {
            throw  new ArrayIndexOutOfBoundsException();
        }
    }

    @Override
    public OfInt trySplit() {
        // TODO
        //throw new UnsupportedOperationException();
        long length = arrayEnding - arrayBeginning;
        if (length < 2) {
            return null;
        }

        long mid = arrayBeginning + length / 2;
        RectangleSpliterator result = new RectangleSpliterator(array, arrayBeginning, mid);
        arrayBeginning = mid;
        return result;
    }

    @Override
    public long estimateSize() {
        // TODO
        //throw new UnsupportedOperationException();
        return arrayEnding-arrayBeginning;
    }

    @Override
    public boolean tryAdvance(IntConsumer action) {
        // TODO
        //throw new UnsupportedOperationException();
        if (arrayBeginning < arrayEnding) {
            /*int arrayBegin = (int)arrayBeginning;
            if ((long)arrayBegin != arrayBeginning) {
                throw new IllegalArgumentException("Can't cast long to int");
            }
            int value = (int) array[arrayBegin];*/
            int value = array[Math.toIntExact(arrayBeginning / array[0].length)][Math.toIntExact(arrayBeginning % array[0].length)];
            arrayBeginning += 1;
            action.accept(value);
            return true;
        }
        return false;
    }


}
/*
// classwork example :

class A {

    protected String val;

    A() {
        setVal();
    }

    public void setVal() {
        val = "A";
    }
}

class B extends A {

    @Override
    public void setVal() {
        val = "B";
    }

    public static void main(String[] args) {
        System.out.println(new B().val);

    }
}*/
