package lambda.part2.exercise;

import data.Person;
import org.junit.Test;

import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;

public class ArrowNotationExercise {

    @Test
    public void getAge() {
        // Person -> Integer
        final Function<Person, Integer> getAge = Person::getAge;//null; // TODO

        assertEquals(Integer.valueOf(33), getAge.apply(new Person("", "", 33)));
    }

    @Test
    public void compareAges() {
        // TODO use BiPredicate
        // compareAges: (Person, Person) -> boolean
        final BiPredicate<Person, Person> compareAges = (o, o2) -> o.getAge() == o2.getAge();
        //throw new UnsupportedOperationException("Not implemented");
        assertEquals(true, compareAges.test(new Person("a", "b", 22), new Person("c", "d", 22)));
    }

    // TODO
    // getFullName: Person -> String
    @Test
    public void getFullName() {
        final Function<Person, String> fullName = person -> person.getFirstName() + " " + person.getLastName();
        assertEquals("John Doe", fullName.apply(new Person("John", "Doe", 33)));
    }

    // TODO
    // ageOfPersonWithTheLongestFullName: (Person -> String) -> ((Person, Person) -> int)
    @Test
    public void getAgeOfPersonWithTheLongestFullName() {

        final Function<Person, String> getFullName = o -> o.getFirstName() + " " + o.getLastName();

        final BiFunction<Person, Person, Integer> ageOfPersonWithTheLongestFullName =
                (o, o2) -> getFullName.apply(o).length() > getFullName.apply(o2).length() ? o.getAge() : o2.getAge();
        assertEquals(
                Integer.valueOf(1),
                ageOfPersonWithTheLongestFullName.apply(
                        new Person("a", "b", 2),
                        new Person("aa", "b", 1)));
    }
}
