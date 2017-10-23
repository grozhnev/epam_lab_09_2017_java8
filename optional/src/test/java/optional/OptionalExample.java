package optional;

import org.junit.Test;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.function.Predicate;

import static org.junit.Assert.assertEquals;

@SuppressWarnings({"Convert2MethodRef", "ExcessiveLambdaUsage", "ResultOfMethodCallIgnored", "OptionalIsPresent"})
public class OptionalExample {

    @Test
    public void get() {
        Optional<String> o1 = Optional.empty();
        // i'm not sure that this is alright, but the test is passed
        if(o1.isPresent()){
            o1.get();
        }

        /*o1.ifPresent(s -> System.out.println(s));
        o1.orElse("t");
        o1.orElseGet(() -> "t");
        o1.orElseThrow(() -> new UnsupportedOperationException());*/
    }

    @Test
    public void ifPresent() {
        Optional<String> o1 = getOptional();

        o1.ifPresent(System.out::println);

        if (o1.isPresent()) {
            System.out.println(o1.get());
        }
    }

    @Test
    public void map() {
        Optional<String> o1 = getOptional();

        Function<String, Integer> getLength = String::length;

        Optional<Integer> expected = o1.map(getLength);

        Optional<Integer> actual;
        if (o1.isPresent()) {
            actual = Optional.ofNullable(getLength.apply(o1.get()));
        } else {
            actual = Optional.empty();
        }

        assertEquals(expected, actual);
    }

    @Test
    public void flatMap() {
        /*
        public <R> Optional<R> flatMap(Function<? super T, Optional<R>> mapper) {
            if (isPresent()) {
                return mapper.apply(value);
            } else {
                return empty();
            }
        }*/
        Optional<String> optional = getOptional();
        Function<String, Optional<Integer>> optionalFlatMappingFunction = str -> Optional.of(str.length());
        Optional<Integer> optional2;

        if(optional.isPresent()){
            optional2 = Optional.of(optional.get().length());
        } else {
            optional2 = Optional.empty();
        }


        assertEquals(optional.flatMap(optionalFlatMappingFunction), optional2);
        //throw new UnsupportedOperationException("Not implemented");
    }


    @Test
    public void filter() {
        /*
        public Optional<T> filter(Predicate<? super T> predicate) {
            if (isPresent()) {
                return predicate.test(value) ? this : empty();
            } else {
                return empty();
            }
        }*/

        Optional<String> optional = getOptional();
        Predicate<String> optionalPredicateStringHasLength = s -> s.length() != 0;
        Optional<String> optional2;

        if (optional.isPresent()){
            optional2 = optional.filter(optionalPredicateStringHasLength);
        } else {
            optional2 = Optional.empty();
        }

        assertEquals(optional, optional2);
        //throw new UnsupportedOperationException("Not implemented");
    }

    private Optional<String> getOptional() {
        return ThreadLocalRandom.current().nextBoolean() ? Optional.empty() : Optional.of("abc");
    }
}
