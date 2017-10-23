package part1.exercise;

import data.Employee;
import data.JobHistoryEntry;
import data.Person;
import org.junit.Test;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

/**
 * @see <a href="https://youtu.be/kxgo7Y4cdA8">Через тернии к лямбдам, часть 1</a>
 * @see <a href="https://youtu.be/JRBWBJ6S4aU">Через тернии к лямбдам, часть 2</a>
 *
 * @see <a href="https://youtu.be/O8oN4KSZEXE">Stream API, часть 1</a>
 * @see <a href="https://youtu.be/i0Jr2l3jrDA">Stream API, часть 2</a>
 */
public class StreamsExercise2 {

    /**
     * Преобразовать список сотрудников в отображение [компания -> множество людей, когда-либо работавших в этой компании].
     *
     * Пример.
     *
     * Входные данные:
     * Employees = [
     *    Employee_1 = {
     *        Person = {"Alex", "Doe", 32},
     *        JobHistory = [
     *           {2, "dev", "epam"},
     *           {3, "dev", "yandex"},
     *           {1, "dev", "mail"},
     *        ]
     *    },
     *    Employee_2 = {
     *        Person = {"Adam", "Smith", 25},
     *        JobHistory = [
     *           {1, "QA", "yandex"},
     *           {2, "QA", "mail"},
     *        ]
     *    },
     *    Employee_3 = {
     *        Person = {"John", "Galt", 27},
     *        JobHistory = [
     *           {1, "QA", "epam"},
     *           {3, "dev", "epam"},
     *        ]
     *    }
     * ]
     *
     * Выходные данные:
     * Result = [
     *    "epam" -> [
     *       {"Alex", "Doe", 32},
     *       {"John", "Galt", 27}
     *    ],
     *    "yandex" -> [
     *       {"Alex", "Doe", 32},
     *       {"Adam", "Smith", 25}
     *    ],
     *    "mail" -> [
     *       {"Alex", "Doe", 32},
     *       {"Adam", "Smith", 25}
     *    ]
     * ]
     */
    @Test
    public void employersStuffList() {
        List<Employee> employees = getEmployees();
        Map<String, Set<Person>> result; // = null; // TODO

        /*
        Решение:
            1. Взять список всех компаний ()
                1.1 пройтись по списку сотрудников.списку компаний
                    сложить все компании в distinct() list String'ов
            2. Пройтись по списку сотрудников.компани в которых он работал
                добавить сотрудника в Set каждой компании
        */

        /* all unique employees */
        /*Set<Person> allEmployeesAsPersons = employees.stream()
                .map(Employee::getPerson)
                .collect(Collectors.toSet());*/

        /* all unique companies */
        /*Set<String> allCompanies = employees.stream()
                .flatMap(employee -> employee.getJobHistory().stream())
                .map(JobHistoryEntry::getEmployer)
                .collect(Collectors.toSet());*/

         /* fill map of companies by each person */
        result = employees.stream()
                        .flatMap(employee -> employee.getJobHistory().stream().map(JobHistoryEntry::getEmployer) // get employers map by employees
                        .map(employer -> new AbstractMap.SimpleEntry<>(employer, employee.getPerson())))         // get map of persons by employers
                        .collect(Collectors.groupingBy(AbstractMap.SimpleEntry::getKey,                          // collect persons by employers
                                                        Collectors.mapping(Map.Entry::getValue,
                                                        Collectors.toSet()))
                        );


        Map<String, Set<Person>> expected = new HashMap<>();
        expected.put("epam", new HashSet<>(Arrays.asList(
                new Person("John", "Galt", 20),
                new Person("John", "Doe", 21),
                new Person("John", "White", 22),
                new Person("John", "Galt", 23),
                new Person("John", "Doe", 24),
                new Person("John", "White", 25),
                new Person("John", "Galt", 26),
                new Person("Bob", "Doe", 27),
                new Person("John", "White", 28),
                new Person("John", "Galt", 29),
                new Person("John", "Doe", 30),
                new Person("Bob", "White", 31))));
        expected.put("google", new HashSet<>(Arrays.asList(
                new Person("John", "Galt", 20),
                new Person("John", "Galt", 23),
                new Person("John", "Galt", 26),
                new Person("John", "Galt", 29)
        )));
        expected.put("yandex", new HashSet<>(Arrays.asList(
                new Person("John", "Doe", 21),
                new Person("John", "Doe", 24),
                new Person("Bob", "Doe", 27),
                new Person("John", "Doe", 30)
        )));
        expected.put("abc", new HashSet<>(Arrays.asList(
                new Person("John", "Doe", 21),
                new Person("John", "Doe", 24),
                new Person("Bob", "Doe", 27),
                new Person("John", "Doe", 30)
        )));
        assertEquals(expected, result);
    }

    /**
     * Преобразовать список сотрудников в отображение [компания -> множество людей, начавших свою карьеру в этой компании].
     *
     * Пример.
     *
     * Входные данные:
     * Employees = [
     *    Employee_1 = {
     *        Person = {"Alex", "Doe", 32},
     *        JobHistory = [
     *           {2, "dev", "epam"},
     *           {3, "dev", "yandex"},
     *           {1, "dev", "mail"},
     *        ]
     *    },
     *    Employee_2 = {
     *        Person = {"Adam", "Smith", 25},
     *        JobHistory = [
     *           {1, "QA", "yandex"},
     *           {2, "QA", "mail"},
     *        ]
     *    },
     *    Employee_3 = {
     *        Person = {"John", "Galt", 27},
     *        JobHistory = [
     *           {1, "QA", "epam"},
     *           {3, "dev", "epam"},
     *        ]
     *    }
     * ]
     *
     * Выходные данные:
     * Result = [
     *    "epam" -> [
     *       {"Alex", "Doe", 32},
     *       {"John", "Galt", 27}
     *    ],
     *    "yandex" -> [
     *       {"Adam", "Smith", 25}
     *    ]
     * ]
     */
    @Test
    public void indexByFirstEmployer() {
        Map<String, Set<Person>> result ;//= null; // TODO
        List<Employee> employees = getEmployees();

        /*we just need to add filter */
        result = employees.stream()
                .flatMap(employee -> employee.getJobHistory().stream()
                        .limit(1)
                        .map(JobHistoryEntry::getEmployer) // get employers map by employees
                        .map(employer -> new AbstractMap.SimpleEntry<>(employer, employee.getPerson()))) // get map of persons by employers
                .collect(Collectors.groupingBy(AbstractMap.SimpleEntry::getKey,                          // collect persons by employers
                        Collectors.mapping(Map.Entry::getValue,
                                Collectors.toSet()))
                );


        Map<String, Set<Person>> expected = new HashMap<>();
        expected.put("epam", new HashSet<>(Arrays.asList(
                new Person("John", "Galt", 20),
                new Person("John", "White", 22),
                new Person("John", "Galt", 23),
                new Person("John", "White", 25),
                new Person("John", "Galt", 26),
                new Person("John", "White", 28),
                new Person("John", "Galt", 29),
                new Person("Bob", "White", 31)
        )));
        expected.put("yandex", new HashSet<>(Arrays.asList(
                new Person("John", "Doe", 21),
                new Person("John", "Doe", 24),
                new Person("Bob", "Doe", 27),
                new Person("John", "Doe", 30)
        )));
        assertEquals(expected, result);
    }

    /**
     * Преобразовать список сотрудников в отображение [компания -> сотрудник, суммарно проработавший в ней наибольшее время].
     * Гарантируется, что такой сотрудник будет один.
     */
    @Test
    public void greatestExperiencePerEmployer() {
        Map<String, Person> result = null;// TODO

        Map<String, Person> expected = new HashMap<>();
        expected.put("epam", new Person("John", "White", 28));
        expected.put("google", new Person("John", "Galt", 29));
        expected.put("yandex", new Person("John", "Doe", 30));
        expected.put("abc", new Person("John", "Doe", 30));
        assertEquals(expected, result);
    }


    private List<Employee> getEmployees() {
        return Arrays.asList(
            new Employee("John", "Galt", 20,
                    Arrays.asList(
                            new JobHistoryEntry(3, "dev", "epam"),
                            new JobHistoryEntry(2, "dev", "google")
                    )),
            new Employee("John", "Doe", 21,
                    Arrays.asList(
                            new JobHistoryEntry(4, "BA", "yandex"),
                            new JobHistoryEntry(2, "QA", "epam"),
                            new JobHistoryEntry(2, "dev", "abc")
                    )),
            new Employee("John", "White", 22,
                    Collections.singletonList(
                            new JobHistoryEntry(6, "QA", "epam")
                    )),
            new Employee("John", "Galt", 23,
                    Arrays.asList(
                            new JobHistoryEntry(3, "dev", "epam"),
                            new JobHistoryEntry(2, "dev", "google")
                    )),
            new Employee("John", "Doe", 24,
                    Arrays.asList(
                            new JobHistoryEntry(4, "QA", "yandex"),
                            new JobHistoryEntry(2, "BA", "epam"),
                            new JobHistoryEntry(2, "dev", "abc")
                    )),
            new Employee("John", "White", 25,
                    Collections.singletonList(
                            new JobHistoryEntry(6, "QA", "epam")
                    )),
            new Employee("John", "Galt", 26,
                    Arrays.asList(
                            new JobHistoryEntry(3, "dev", "epam"),
                            new JobHistoryEntry(1, "dev", "google")
                    )),
            new Employee("Bob", "Doe", 27,
                    Arrays.asList(
                            new JobHistoryEntry(4, "QA", "yandex"),
                            new JobHistoryEntry(2, "QA", "epam"),
                            new JobHistoryEntry(2, "QA", "abc"),
                            new JobHistoryEntry(2, "dev", "abc")
                    )),
            new Employee("John", "White", 28,
                    Collections.singletonList(
                            new JobHistoryEntry(8, "BA", "epam")
                    )),
            new Employee("John", "Galt", 29,
                    Arrays.asList(
                            new JobHistoryEntry(3, "dev", "epam"),
                            new JobHistoryEntry(3, "dev", "google")
                    )),
            new Employee("John", "Doe", 30,
                    Arrays.asList(
                            new JobHistoryEntry(5, "QA", "yandex"),
                            new JobHistoryEntry(2, "QA", "epam"),
                            new JobHistoryEntry(5, "dev", "abc")
                    )),
            new Employee("Bob", "White", 31,
                    Collections.singletonList(
                            new JobHistoryEntry(6, "QA", "epam")
                    ))
        );
    }

}
