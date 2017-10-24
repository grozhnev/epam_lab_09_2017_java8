package part2.exercise;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;

public class ZipWithIndexDoubleSpliterator extends Spliterators.AbstractSpliterator<IndexedDoublePair> {


    private final OfDouble inner;
    private int currentIndex;

    public ZipWithIndexDoubleSpliterator(OfDouble inner) {
        this(0, inner);
    }

    private ZipWithIndexDoubleSpliterator(int firstIndex, OfDouble inner) {
        super(inner.estimateSize(), inner.characteristics());
        currentIndex = firstIndex;
        this.inner = inner;
    }

    @Override
    public int characteristics() {
        // TODO
        //throw new UnsupportedOperationException();
        return inner.characteristics();
    }

    @Override
    public boolean tryAdvance(Consumer<? super IndexedDoublePair> action) {
        // TODO
        //throw new UnsupportedOperationException();
        boolean result;
        if (currentIndex < inner.estimateSize()) {
            if (inner.tryAdvance((double value) -> action.accept(new IndexedDoublePair(currentIndex, value)))) {
                currentIndex++;
            }
            result = true;
        } else {
            result = false;
        }
        return result;
    }

    @Override
    public void forEachRemaining(Consumer<? super IndexedDoublePair> action) {
        // TODO
        //throw new UnsupportedOperationException();
        inner.forEachRemaining((double value) -> action.accept(new IndexedDoublePair(currentIndex, value)));
    }

    @Override
    public Spliterator<IndexedDoublePair> trySplit() {
        // TODO
        // if (inner.hasCharacteristics(???)) {
        //   use inner.trySplit
        // } else
        //return super.trySplit();

        if (inner.hasCharacteristics(Spliterator.SIZED | Spliterator.SUBSIZED)) {
            OfDouble trySplit = inner.trySplit();
            if (trySplit == null) {
                return null;
            }
            currentIndex += trySplit.estimateSize();
            return new ZipWithIndexDoubleSpliterator(currentIndex, inner.trySplit());
        } else {
            return super.trySplit();
        }
    }

    @Override
    public long estimateSize() {
        // TODO
        //throw new UnsupportedOperationException();
        return inner.estimateSize();
    }
}
