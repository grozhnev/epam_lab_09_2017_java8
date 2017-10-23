package part2.exercise;

import data.Employee;
import data.JobHistoryEntry;
import data.Person;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

public class CollectorsExercise1 {

    private static class PersonPositionDuration {
        private final Person person;
        private final String position;
        private final int duration;

        PersonPositionDuration(Person person, String position, int duration) {
            this.person = person;
            this.position = position;
            this.duration = duration;
        }

        Person getPerson() {
            return person;
        }

        String getPosition() {
            return position;
        }

        int getDuration() {
            return duration;
        }
    }


    // "epam" -> "Alex Ivanov 23, Semen Popugaev 25, Ivan Ivanov 33"

    private static class EmployerPersonPair {
        private final String person;
        private final String employer;

        EmployerPersonPair(String employer, String person) {
            this.employer = employer;
            this.person = person;
        }

        String getPerson() {
            return person;
        }

        String getEmployer() {
            return employer;
        }
    }


    @Test
    public void getEmployeesByEmployer() {
       Map<String, String> result = //null;
                getEmployees().stream()
                .flatMap(employee -> employee.getJobHistory().stream()
                .map(JobHistoryEntry::getEmployer)
                .map(employer -> new EmployerPersonPair(employer, employee.getPerson().toString())))
                .collect(Collectors.groupingBy(EmployerPersonPair::getEmployer,
                        Collectors.mapping(EmployerPersonPair::getPerson, Collectors.joining(", "))));
    }


    @Test
    public void getTheCoolestOne() {
        Map<String, Person> coolestByPosition = getCoolestByPosition(getEmployees());

        coolestByPosition.forEach((position, person) -> System.out.println(position + " -> " + person));
    }

    private Map<String, Person> getCoolestByPosition(List<Employee> employees) {
        // First option
        // Collectors.maxBy
        // Collectors.collectingAndThen
        // Collectors.groupingBy

        // Second option
        // Collectors.toMap
        // iterate twice: stream...collect(...).stream()...
        // TODO
        //throw new UnsupportedOperationException();

        return employees.stream()
                .flatMap(employee -> employee.getJobHistory().stream()                  //get job history of all employees
                        .collect(Collectors.groupingBy(JobHistoryEntry::getPosition,
                                Collectors.summingInt(JobHistoryEntry::getDuration)))   // group job history by positions and sum duration
                        .entrySet().stream()
                        .map(pair -> new PersonPositionDuration(employee.getPerson(), pair.getKey(), pair.getValue())))
                .collect(Collectors.groupingBy(PersonPositionDuration::getPosition,     // group pair of person-position-duration by position
                        Collectors.collectingAndThen(Collectors.maxBy(Comparator.comparing(PersonPositionDuration::getDuration)),
                                personWithMaxDuration -> personWithMaxDuration.get().getPerson())));                      // find person with max duration and get them.
    }

    private List<Employee> getEmployees() {
        return Arrays.asList(
                new Employee(
                        new Person("John", "Galt", 20),
                        Arrays.asList(
                                new JobHistoryEntry(3, "dev", "epam"),
                                new JobHistoryEntry(2, "dev", "google")
                        )),
                new Employee(
                        new Person("John", "Doe", 21),
                        Arrays.asList(
                                new JobHistoryEntry(4, "BA", "yandex"),
                                new JobHistoryEntry(2, "QA", "epam"),
                                new JobHistoryEntry(2, "dev", "abc")
                        )),
                new Employee(
                        new Person("John", "White", 22),
                        Collections.singletonList(
                                new JobHistoryEntry(6, "QA", "epam")
                        )),
                new Employee(
                        new Person("John", "Galt", 23),
                        Arrays.asList(
                                new JobHistoryEntry(3, "dev", "epam"),
                                new JobHistoryEntry(2, "dev", "google")
                        )),
                new Employee(
                        new Person("John", "Doe", 24),
                        Arrays.asList(
                                new JobHistoryEntry(4, "QA", "yandex"),
                                new JobHistoryEntry(2, "BA", "epam"),
                                new JobHistoryEntry(2, "dev", "abc")
                        )),
                new Employee(
                        new Person("John", "White", 25),
                        Collections.singletonList(
                                new JobHistoryEntry(6, "QA", "epam")
                        )),
                new Employee(
                        new Person("John", "Galt", 26),
                        Arrays.asList(
                                new JobHistoryEntry(3, "dev", "epam"),
                                new JobHistoryEntry(1, "dev", "google")
                        )),
                new Employee(
                        new Person("Bob", "Doe", 27),
                        Arrays.asList(
                                new JobHistoryEntry(4, "QA", "yandex"),
                                new JobHistoryEntry(2, "QA", "epam"),
                                new JobHistoryEntry(2, "dev", "abc")
                        )),
                new Employee(
                        new Person("John", "White", 28),
                        Collections.singletonList(
                                new JobHistoryEntry(6, "BA", "epam")
                        )),
                new Employee(
                        new Person("John", "Galt", 29),
                        Arrays.asList(
                                new JobHistoryEntry(3, "dev", "epam"),
                                new JobHistoryEntry(1, "dev", "google")
                        )),
                new Employee(
                        new Person("John", "Doe", 30),
                        Arrays.asList(
                                new JobHistoryEntry(4, "QA", "yandex"),
                                new JobHistoryEntry(2, "QA", "epam"),
                                new JobHistoryEntry(5, "dev", "abc")
                        )),
                new Employee(
                        new Person("Bob", "White", 31),
                        Collections.singletonList(
                                new JobHistoryEntry(6, "QA", "epam")
                        ))
        );
    }

}
