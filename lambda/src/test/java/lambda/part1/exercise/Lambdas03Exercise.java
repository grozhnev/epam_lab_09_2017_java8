package lambda.part1.exercise;

import org.junit.Test;

import java.util.StringJoiner;

import static org.junit.Assert.assertEquals;

public class Lambdas03Exercise {

    private interface GenericProduct<T> {
        T prod(T a, int i);

        default T twice(T t) {
            return prod(t, 2);
        }
    }

    @Test
    public void generic0() {
        final GenericProduct<Integer> prod = //null; // Use anonymous class
                new GenericProduct<Integer>() {
                    @Override
                    public Integer prod(Integer p, int q) {
                        return p * q;
                    }
                };
        assertEquals(prod.prod(3, 2), Integer.valueOf(6));
    }

    @Test
    public void generic1() {
        final GenericProduct<Integer> prod = //null; // Use statement lambda
                (Integer p, int q) -> { return Integer.valueOf( p * q); };
        assertEquals(prod.prod(3, 2), Integer.valueOf(6));
    }

    @Test
    public void generic2() {
        final GenericProduct<Integer> prod = //null; // Use expression lambda
                (Integer p, int q) -> p * q;
        assertEquals(prod.prod(3, 2), Integer.valueOf(6));
    }

    private static String stringProd(String s, int i) {
        final StringBuilder sb = new StringBuilder();
        for (int j = 0; j < i; j++) {
            sb.append(s);
        }
        return sb.toString();
    }

    @Test
    public void strSum() {
        final GenericProduct<String> prod = //null; // use stringProd;
                Lambdas03Exercise::stringProd;
        assertEquals(prod.prod("a", 2), "aa");
    }

    private final String delimeter = "-";

    private String stringSumWithDelimeter(String s, int i) {
        final StringJoiner sj = new StringJoiner(delimeter);
        for (int j = 0; j < i; j++) {
            sj.add(s);
        }
        return sj.toString();
    }


    @Test
    public void strSum2() {
        final GenericProduct<String> prod = (GenericProduct<String>) (s, m) -> stringSumWithDelimeter(String.valueOf(s),m);//null; // use stringSumWithDelimeter;
        assertEquals(prod.prod("a", 3), "a-a-a");
    }
}