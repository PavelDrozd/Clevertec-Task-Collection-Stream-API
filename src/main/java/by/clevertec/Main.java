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
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
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
        System.out.println(task22());
    }

    public static void task1() {
        List<Animal> animals = Util.getAnimals();
        List<Animal> sorted = animals.stream()
                .filter(a -> a.getAge() >= 10 && a.getAge() <= 20)
                .sorted(Comparator.comparingInt(Animal::getAge))
                .toList();
        IntStream.range(0, (int) Math.ceil((double) sorted.size() / 7))
                .mapToObj(i -> sorted.subList(i * 7, Math.min((i + 1) * 7, sorted.size())))
                .toList().forEach(System.out::println);
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
        animals.stream()
                .filter(a -> a.getAge() >= 20 && a.getAge() <= 30)
                .filter(a -> a.getOrigin().equals("Hungarian"))
                .forEach(System.out::println);
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
                .orElseThrow(ArithmeticException::new);
        System.out.println("Older animal: " + older);
    }

    public static void task9() {
        List<Animal> animals = Util.getAnimals();
        int minLength = animals.stream()
                .map(Animal::getBread)
                .map(String::toCharArray)
                .mapToInt(arr -> arr.length)
                .min()
                .orElseThrow(ArithmeticException::new);
        System.out.println("Min array length: " + minLength);
    }

    public static void task10() {
        List<Animal> animals = Util.getAnimals();
        int sum = animals.stream()
                .mapToInt(Animal::getAge)
                .sum();
        System.out.println("Sum: " + sum);
    }

    public static void task11() {
        List<Animal> animals = Util.getAnimals();
        double average = animals.stream()
                .filter(a -> a.getOrigin().equals("Indonesian"))
                .mapToInt(Animal::getAge)
                .average()
                .orElseThrow(ArithmeticException::new);
        System.out.println("Average: " + average);
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
        final double[] generalProfit = {0.0};
        cars.stream()
                .collect(Collectors.groupingBy(c -> {
                    if (c.getCarMake().equals("Jaguar") || c.getColor().equals("White")) {
                        return "Туркменистан";
                    } else if (c.getMass() < 1500 && (c.getCarMake().equals("BMW")
                                                      || c.getCarMake().equals("Lexus")
                                                      || c.getCarMake().equals("Chrysler")
                                                      || c.getCarMake().equals("Toyota"))) {
                        return "Узбекистан";
                    } else if ((c.getColor().equals("Black") && c.getMass() > 4000)
                               || c.getCarMake().equals("GMC") || c.getCarMake().equals("Dodge")) {
                        return "Казахстан";
                    } else if (c.getReleaseYear() < 1982
                               || c.getCarModel().equals("Civic")
                               || c.getCarModel().equals("Cherokee")) {
                        return "Кыргызстан";
                    } else if (!(c.getColor().equals("Yellow")
                                 || c.getColor().equals("Red")
                                 || c.getColor().equals("Green")
                                 || c.getColor().equals("Blue"))
                               || c.getPrice() > 40000) {
                        return "Россия";
                    } else if (c.getVin().contains("59")) {
                        return "Монголия";
                    } else {
                        return "None";
                    }
                }))
                .forEach((esh, carz) -> {
                    if (esh.equals("None")) {
                        System.out.printf("General profit: %.2f", generalProfit[0]);
                        return;
                    }
                    double sum = carz.stream().mapToDouble(c -> c.getMass() * 7.14).sum();
                    generalProfit[0] += sum;
                    System.out.printf("%s: %.2f$\n", esh, sum);
                });
    }

    public static void task15() {
        List<Flower> flowers = Util.getFlowers();
        double sum = flowers.stream()
                .sorted(Comparator.comparing(
                                Flower::getOrigin).reversed().
                        thenComparing(Flower::getPrice).reversed().
                        thenComparing(Flower::getWaterConsumptionPerDay).reversed())
                .filter(f -> f.getCommonName().charAt(0) <= 'S' && f.getCommonName().charAt(0) >= 'C')
                .filter(Flower::isShadePreferred)
                .mapToDouble(f -> f.getPrice() + (f.getWaterConsumptionPerDay() * 5 * 365.2425 * 1.39))
                .sum();
        System.out.println("Sum: " + sum + "$");
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
                .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));
    }

    public static void task19() {
        List<Examination> examinations = Util.getExaminations();
        List<Student> students = Util.getStudents();
        students.stream()
                .filter(s -> examinations.stream()
                        .filter(e -> e.getStudentId() == s.getId())
                        .allMatch(e -> e.getExam3() > 4))
                .forEach(System.out::println);
    }

    public static void task20() {
        List<Student> students = Util.getStudents();
        List<Examination> examinations = Util.getExaminations();
        String facultyWithMaxAvg = students.stream()
                .flatMap(s -> examinations.stream()
                        .filter(e -> e.getStudentId() == s.getId())
                        .map(e -> new Object[]{s.getFaculty(), e.getExam1()}))
                .collect(Collectors.groupingBy(obj -> (String) obj[0],
                        Collectors.averagingInt(obj -> (int) obj[1])))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElseThrow(RuntimeException::new);
        System.out.println("Faculty with max average score: " + facultyWithMaxAvg);
    }

    public static void task21() {
        List<Student> students = Util.getStudents();
        Map<String, Long> group = students.stream()
                .collect(Collectors.groupingBy(Student::getGroup, Collectors.counting()));
        System.out.println(group);
    }

    public static Map<String, Integer> task22() {
        List<Student> students = Util.getStudents();
        return students.stream()
                .collect(Collectors.groupingBy(
                        Student::getFaculty, Collectors.reducing(Integer.MAX_VALUE, Student::getAge, Integer::min)));
    }
}
