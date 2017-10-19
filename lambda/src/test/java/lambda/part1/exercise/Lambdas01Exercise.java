package lambda.part1.exercise;

import static com.google.common.collect.ImmutableList.*;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;


import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;

import data.Person;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;


public class Lambdas01Exercise {

    @Test
    public void sortPersonsByAge() {
        Person[] persons = {new Person("name 3", "lastName 3", 20),
                new Person("name 1", "lastName 2", 40),
                new Person("name 2", "lastName 1", 30)
        };

        // TODO use Arrays.sort
        Arrays.sort(persons, (o1, o2) -> Integer.compare(o1.getAge(), o2.getAge()));

        assertArrayEquals(persons, new Person[]{
                new Person("name 3", "lastName 3", 20),
                new Person("name 2", "lastName 1", 30),
                new Person("name 1", "lastName 2", 40),}
        );
    }


    @Test
    public void findFirstWithAge30() {
        List<Person> persons = of(
                new Person("name 3", "lastName 3", 20),
                new Person("name 1", "lastName 2", 30),
                new Person("name 2", "lastName 1", 30)
        );

        Person person = null;

        // TODO use FluentIterable
        Optional<Person> firstPerson30yearsOld = FluentIterable.from(persons)
                                                                .firstMatch(new Predicate<Person>() {
                                                                    @Override
                                                                    public boolean apply(Person personInput) {
                                                                        return personInput.getAge() == 30;
                                                                    }
                                                                });
        assertEquals(firstPerson30yearsOld.get(), new Person("name 1", "lastName 2", 30));


    }


}
