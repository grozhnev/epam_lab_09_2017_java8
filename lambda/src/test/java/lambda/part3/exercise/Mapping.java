package lambda.part3.exercise;

import data.Employee;
import data.JobHistoryEntry;
import data.Person;
import org.junit.Test;

import java.util.*;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;

@SuppressWarnings({"WeakerAccess"})
public class Mapping {

    private static class MapHelper<T> {

        private final List<T> list;

        public MapHelper(List<T> list) {
            this.list = list;
        }

        public List<T> getList() {
            return list;
        }

        // ([T], T -> R) -> [R]
        public <R> MapHelper<R> map(Function<T, R> f) {
            // TODO
            //throw new UnsupportedOperationException();
            List<R> rList = new ArrayList<>(list.size());
            list.forEach(t -> rList.add(f.apply(t)));
            return new MapHelper<>(rList);
        }

        // ([T], T -> [R]) -> [R]
        public <R> MapHelper<R> flatMap(Function<T, List<R>> f) {
            // TODO
            //throw new UnsupportedOperationException();
            List<R> rList = new ArrayList<>(list.size());
            list.forEach(t -> rList.addAll(f.apply(t)));
            return new MapHelper<>(rList);
        }
    }

    @Test
    public void mapping() {
        List<Employee> employees = Arrays.asList(
            new Employee(new Person("a", "Galt", 30),
                Arrays.asList(
                        new JobHistoryEntry(2, "dev", "epam"),
                        new JobHistoryEntry(1, "dev", "google")
                )),
            new Employee(new Person("b", "Doe", 40),
                Arrays.asList(
                        new JobHistoryEntry(3, "qa", "yandex"),
                        new JobHistoryEntry(1, "qa", "epam"),
                        new JobHistoryEntry(1, "dev", "abc")
                )),
            new Employee(new Person("c", "White", 50),
                Collections.singletonList(
                        new JobHistoryEntry(5, "qa", "epam")
                ))
        );

        List<Employee> mappedEmployees = new MapHelper<>(employees)
                /*
                .map(TODO) // Изменить имя всех сотрудников на John .map(e -> e.withPerson(e.getPerson().withFirstName("John")))
                .map(TODO) // Добавить всем сотрудникам 1 год опыта .map(e -> e.withJobHistory(addOneYear(e.getJobHistory())))
                .map(TODO) // Заменить все qa на QA
                * */

                .map(e -> e.withPerson(e.getPerson().withFirstName("John"))) // Изменить имя всех сотрудников на John
                .map(e -> e.withJobHistory(addOneYear(e.getJobHistory()))) // Добавить всем сотрудникам 1 год опыта
                .map(e -> e.withJobHistory(qaChangeOnQA(e.getJobHistory()))) // Заменить все qa на QA

                .getList();

        List<Employee> expectedResult = Arrays.asList(
            new Employee(new Person("John", "Galt", 30),
                Arrays.asList(
                        new JobHistoryEntry(3, "dev", "epam"),
                        new JobHistoryEntry(2, "dev", "google")
                )),
            new Employee(new Person("John", "Doe", 40),
                Arrays.asList(
                        new JobHistoryEntry(4, "QA", "yandex"),
                        new JobHistoryEntry(2, "QA", "epam"),
                        new JobHistoryEntry(2, "dev", "abc")
                )),
            new Employee(new Person("John", "White", 50),
                Collections.singletonList(
                        new JobHistoryEntry(6, "QA", "epam")
                ))
        );

        assertEquals(mappedEmployees, expectedResult);
    }

    private List<JobHistoryEntry>  qaChangeOnQA(List<JobHistoryEntry> jobHistory) {
        return new MapHelper<>(jobHistory).map(e -> e.getPosition().equals("qa") ? e.withPosition("QA") : e).getList();
    }

    private List<JobHistoryEntry> addOneYear(List<JobHistoryEntry> jobHistory) {
        return new MapHelper<>(jobHistory).map(e -> e.withDuration(e.getDuration() + 1)).getList();
    }

    private static class LazyMapHelper<T, R> {
        private List<T> list;
        private Function<T, R> function;

        public LazyMapHelper(List<T> list, Function<T, R> function) {
            this.list = list;
            this.function =function;
        }

        public static <T> LazyMapHelper<T, T> from(List<T> list) {
            return new LazyMapHelper<>(list, Function.identity());
        }

        public List<R> force() {
            // TODO
            //throw new UnsupportedOperationException();
            return new MapHelper<>(list).map(function).getList();
        }

        public <R2> LazyMapHelper<T, R2> map(Function<R, R2> f) {
            // TODO
            //throw new UnsupportedOperationException();
            return new LazyMapHelper<>(list, function.andThen(f));
        }
    }

    // TODO * LazyFlatMapHelper

    @Test
    public void lazyMapping() {
        List<Employee> employees = Arrays.asList(
            new Employee(
                new Person("a", "Galt", 30),
                Arrays.asList(
                        new JobHistoryEntry(2, "dev", "epam"),
                        new JobHistoryEntry(1, "dev", "google")
                )),
            new Employee(
                new Person("b", "Doe", 40),
                Arrays.asList(
                        new JobHistoryEntry(3, "qa", "yandex"),
                        new JobHistoryEntry(1, "qa", "epam"),
                        new JobHistoryEntry(1, "dev", "abc")
                )),
            new Employee(
                new Person("c", "White", 50),
                Collections.singletonList(
                        new JobHistoryEntry(5, "qa", "epam")
                ))
        );

        List<Employee> mappedEmployees = LazyMapHelper.from(employees)
                /*
                .map(TODO) // Изменить имя всех сотрудников на John .map(e -> e.withPerson(e.getPerson().withFirstName("John")))
                .map(TODO) // Добавить всем сотрудникам 1 год опыта .map(e -> e.withJobHistory(addOneYear(e.getJobHistory())))
                .map(TODO) // Заменить все qu на QA
                */

                .map(e -> e.withPerson(e.getPerson().withFirstName("John"))) // Изменить имя всех сотрудников на John
                .map(e -> e.withJobHistory(addOneYear(e.getJobHistory()))) // Добавить всем сотрудникам 1 год опыта
                .map(e -> e.withJobHistory(qaChangeOnQA(e.getJobHistory()))) // Заменить все qu на QA
                .force();

        List<Employee> expectedResult = Arrays.asList(
            new Employee(new Person("John", "Galt", 30),
                Arrays.asList(
                        new JobHistoryEntry(3, "dev", "epam"),
                        new JobHistoryEntry(2, "dev", "google")
                )),
            new Employee(new Person("John", "Doe", 40),
                Arrays.asList(
                        new JobHistoryEntry(4, "QA", "yandex"),
                        new JobHistoryEntry(2, "QA", "epam"),
                        new JobHistoryEntry(2, "dev", "abc")
                )),
            new Employee(new Person("John", "White", 50),
                Collections.singletonList(
                        new JobHistoryEntry(6, "QA", "epam")
                ))
        );

        assertEquals(mappedEmployees, expectedResult);
    }
}
