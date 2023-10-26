package by.clevertec;

import by.clevertec.model.Animal;
import by.clevertec.model.Car;
import by.clevertec.model.Examination;
import by.clevertec.model.Flower;
import by.clevertec.model.House;
import by.clevertec.model.Person;
import by.clevertec.model.Student;
import by.clevertec.util.Util;

import java.time.LocalDate;
import java.util.AbstractMap;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        task1();
        task2();
        task3();
        task4();
        task5();
        task6();
        task7();
        task8();
        task9();
        task10();
        task11();
        task12();
        task13();
        task14();
        task15();
        task16();
        task17();
        task18();
        task19();
        task20();
        task21();
        task22().forEach((s, i) -> System.out.printf("%s: %d\n", s, i));
    }

    public static void task1() {
        List<Animal> animals = Util.getAnimals();
        AtomicInteger counter = new AtomicInteger(1);
        Map<Integer, List<Animal>> zooAnimalMap = animals.stream()
                .filter(a -> a.getAge() >= 10 && a.getAge() <= 20)
                .sorted(Comparator.comparingInt(Animal::getAge))
                .collect(Collectors.groupingByConcurrent(a -> counter.getAndIncrement() / 7));
        System.out.println("Animals in 3 zoo: " + zooAnimalMap.get(3));

    }

    public static void task2() {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .filter(a -> a.getOrigin().equals("Japanese"))
                .map(a -> {
                    if (a.getGender().equals("Female")) {
                        return a.getBread().toLowerCase();
                    } else {
                        return a.getBread().toUpperCase();
                    }
                }).forEach(System.out::println);
    }

    public static void task3() {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .filter(a -> a.getAge() > 30)
                .map(Animal::getOrigin)
                .distinct()
                .filter(c -> c.startsWith("A"))
                .forEach(System.out::println);
    }

    public static void task4() {
        List<Animal> animals = Util.getAnimals();
        long count = animals.stream()
                .filter(a -> a.getGender().equals("Female"))
                .count();
        System.out.println("Females: " + count);
    }

    public static void task5() {
        List<Animal> animals = Util.getAnimals();
        boolean isHungarian = animals.stream()
                .filter(a -> a.getAge() >= 20 && a.getAge() <= 30)
                .anyMatch(a -> a.getOrigin().equals("Hungarian"));
        System.out.println("Is there anyone Hungarian: " + isHungarian);
    }

    public static void task6() {
        List<Animal> animals = Util.getAnimals();
        boolean allMatch = animals.stream()
                .allMatch(a -> a.getGender().equals("Male") || a.getGender().equals("Female"));
        System.out.println("Is there all male and female: " + allMatch);
    }

    public static void task7() {
        List<Animal> animals = Util.getAnimals();
        boolean noneOceania = animals.stream()
                .noneMatch(a -> a.getOrigin().equals("Oceania"));
        System.out.println("None Oceania: " + noneOceania);
    }

    public static void task8() {
        List<Animal> animals = Util.getAnimals();
        int older = animals.stream()
                .sorted(Comparator.comparing(Animal::getBread))
                .limit(100)
                .mapToInt(Animal::getAge)
                .max()
                .orElseThrow();
        System.out.println("Older animal: " + older);
    }

    public static void task9() {
        List<Animal> animals = Util.getAnimals();
        int minLength = animals.stream()
                .map(Animal::getBread)
                .map(String::toCharArray)
                .mapToInt(arr -> arr.length)
                .min()
                .orElseThrow();
        System.out.println("Min array length: " + minLength);
    }

    public static void task10() {
        List<Animal> animals = Util.getAnimals();
        int sum = animals.stream()
                .mapToInt(Animal::getAge)
                .sum();
        System.out.println("Sum of ages: " + sum);
    }

    public static void task11() {
        List<Animal> animals = Util.getAnimals();
        double average = animals.stream()
                .filter(a -> a.getOrigin().equals("Indonesian"))
                .mapToInt(Animal::getAge)
                .average()
                .orElseThrow();
        System.out.println("Average age in Indonesia: " + average);
    }

    public static void task12() {
        List<Person> persons = Util.getPersons();
        persons.stream()
                .filter(p -> p.getGender().equals("Male"))
                .filter(p -> (LocalDate.now().getYear() - p.getDateOfBirth().getYear()) >= 18
                             && (LocalDate.now().getYear() - p.getDateOfBirth().getYear()) <= 27)
                .sorted(Comparator.comparing(Person::getRecruitmentGroup))
                .limit(200)
                .forEach(System.out::println);
    }

    public static void task13() {
        List<House> houses = Util.getHouses();
        houses.stream()
                .filter(h -> h.getBuildingType().equals("Hospital"))
                .map(h -> h.getPersonList().stream())
                .flatMap(h -> Stream.concat(h, houses.stream()
                        .flatMap(house -> house.getPersonList().stream())
                        .sorted(Comparator.comparing(Person::getDateOfBirth, (d1, d2) -> {
                            if ((LocalDate.now().getYear() - d1.getYear()) <= 18
                                || (LocalDate.now().getYear() - d1.getYear()) >= 65) {
                                return -1;
                            } else if ((LocalDate.now().getYear() - d2.getYear()) <= 18
                                       || (LocalDate.now().getYear() - d2.getYear()) >= 65) {
                                return 1;
                            } else {
                                return 0;
                            }
                        }))))
                .distinct()
                .limit(500)
                .forEach(System.out::println);
    }

    public static void task14() {
        List<Car> cars = Util.getCars();
        Map<String, Predicate<Car>> predicates = getIntegerPredicateMap();
        Map<String, Double> costs = cars.stream()
                .flatMap(car -> predicates.entrySet().stream()
                        .filter(entry -> entry.getValue().test(car))
                        .map(entry -> new AbstractMap.SimpleEntry<>(entry.getKey(), car))
                )
                .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.mapping(Map.Entry::getValue, Collectors.toList())))
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .mapToDouble(car -> car.getMass() * 7.14 / 1000)
                                .sum()
                ));
        double sum = costs.values().stream().mapToDouble(Double::doubleValue).sum();
        costs.forEach((path, cost) -> System.out.printf("%s - %.2f\n", path, cost));
        System.out.printf("Sum: %.2f", sum);
    }

    private static Map<String, Predicate<Car>> getIntegerPredicateMap() {
        Predicate<Car> first = car -> car.getCarMake().equals("Jaguar") || car.getColor().equals("White");
        Predicate<Car> second = car -> car.getMass() < 1500 && (car.getCarMake().equals("BMW")
                                                                || car.getCarMake().equals("Lexus")
                                                                || car.getCarMake().equals("Chrysler")
                                                                || car.getCarMake().equals("Toyota"));
        Predicate<Car> third = car -> (car.getColor().equals("Black") && car.getMass() > 4000)
                                      || car.getCarMake().equals("GMC") || car.getCarMake().equals("Dodge");
        Predicate<Car> fourth = car -> car.getReleaseYear() < 1982
                                       || car.getCarModel().equals("Civic")
                                       || car.getCarModel().equals("Cherokee");
        Predicate<Car> fifth = car -> !(car.getColor().equals("Yellow")
                                        || car.getColor().equals("Red")
                                        || car.getColor().equals("Green")
                                        || car.getColor().equals("Blue"))
                                      || car.getPrice() > 40000;
        Predicate<Car> sixth = car -> car.getVin().contains("59");
        return Map.of(
                "Туркменистан", first, "Узбекистан", second, "Казахстан", third,
                "Кыргызстан", fourth, "Россия", fifth, "Монголия", sixth);
    }

    public static void task15() {
        List<Flower> flowers = Util.getFlowers();
        double sum = flowers.stream()
                .sorted(Comparator.comparing(Flower::getOrigin).reversed().
                        thenComparing(Flower::getPrice).reversed().
                        thenComparing(Flower::getWaterConsumptionPerDay).reversed())
                .filter(f -> f.getCommonName().charAt(0) <= 'S' && f.getCommonName().charAt(0) >= 'C')
                .filter(Flower::isShadePreferred)
                .mapToDouble(f -> f.getPrice() + (f.getWaterConsumptionPerDay() * 5 * 365 * 1.39))
                .sum();
        System.out.printf("Sum: %.2f$", sum);
    }

    public static void task16() {
        List<Student> students = Util.getStudents();
        students.stream()
                .filter(s -> s.getAge() < 18)
                .sorted(Comparator.comparing(Student::getSurname))
                .forEach(s -> System.out.println(s.getSurname() + " " + s.getAge()));
    }

    public static void task17() {
        List<Student> students = Util.getStudents();
        students.stream()
                .map(Student::getFaculty)
                .distinct()
                .forEach(System.out::println);
    }

    public static void task18() {
        List<Student> students = Util.getStudents();
        students.stream()
                .collect(Collectors.groupingBy(Student::getFaculty, Collectors.averagingDouble(Student::getAge)))
                .entrySet().stream()
                .sorted(Comparator.comparingDouble((Map.Entry<String, Double> entry) -> entry.getValue()).reversed())
                .forEach(entry -> System.out.printf("%s: %.3f\n", entry.getKey(), entry.getValue()));
    }

    public static void task19() {
        List<Examination> examinations = Util.getExaminations();
        List<Student> students = Util.getStudents();
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        students.stream()
                .filter(student -> student.getFaculty().equals(input))
                .filter(s -> examinations.stream()
                        .filter(e -> e.getStudentId() == s.getId())
                        .allMatch(e -> e.getExam3() > 4))
                .forEach(System.out::println);
    }

    public static void task20() {
        List<Student> students = Util.getStudents();
        List<Examination> examinations = Util.getExaminations();
        students.stream()
                .collect(Collectors.groupingBy(Student::getFaculty, Collectors.averagingDouble(
                        student -> examinations.stream()
                                .filter(examination -> examination.getStudentId() == student.getId())
                                .mapToDouble(Examination::getExam1)
                                .findFirst()
                                .orElse(0.0)
                )))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .stream().collect(Collectors.toSet())
                .forEach(s -> System.out.printf("%s = %.2f", s.getKey(), s.getValue()));
    }

    public static void task21() {
        List<Student> students = Util.getStudents();
        students.stream()
                .collect(Collectors.groupingBy(Student::getGroup, Collectors.counting()))
                .forEach((g, c) -> System.out.println(g + ": " + c + " students"));
    }

    public static Map<String, Integer> task22() {
        List<Student> students = Util.getStudents();
        return students.stream()
                .collect(Collectors.groupingBy(
                        Student::getFaculty, Collectors.reducing(Integer.MAX_VALUE, Student::getAge, Integer::min)));
    }
}
