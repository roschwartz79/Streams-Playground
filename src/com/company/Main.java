package com.company;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {

        Employee[] empArray = setDefaults();

        // You can create a stream pretty easily, passing in a variety of args
        Stream<Employee> empStream = Arrays.stream(empArray);

        // forEach is a terminal op!
        empStream.forEach(e -> e.addToSalary(100));

        System.out.println("Salary for Rob after forEach: " + empArray[0].getSalary());

        empArray = setDefaults();

        // Map produces a new stream after applying a function to each element in the original stream
        // Here we take the Employee instance and use the getAge function... the new stream is of Integers!
        List<Integer> ages = Stream.of(empArray)
                .map(Employee::getAge)
                .collect(Collectors.toList());

        System.out.println("After mapping employees to their Ages.. " + ages.get(0) + " " + ages.get(1) + " " + ages.get(2));

        // We can also filter stuff out! A filter can return none, 1 or many "objects" as part of the new stream! Here we just get one
        Employee practiceEmp = Stream.of(empArray)
                .filter(e -> e.getSalary() > 500)
                .findFirst()
                .orElse(null);

        // Peek can be used to perform multiple ops on something, without being a terminal op
        Arrays.stream(empArray)
                .peek(e -> e.addToSalary(100))
                .peek(e -> System.out.println(e.getFirstName()))
                .collect(Collectors.toList());


        // This is an infinite stream!
        Stream<Integer> infiniteStream = Stream.iterate(2, i -> i * 2);

        // WE short circuit it to limit it and skip the first 4 elements!
        List<Integer> collect = infiniteStream
                .skip(4)
                .limit(5)
                .collect(Collectors.toList());

        System.out.println(collect.toString());


        // We can use a comparator to sort, get min and max
        List<Employee> sortedList = Arrays.stream(empArray)
                .sorted((e1, e2) -> e1.compareTo(e2))
                .collect(Collectors.toList());

        System.out.println(sortedList.get(0).getFirstName());

        // Stream will get unique elements for us! How nice of Stream to do this for us
        List<Employee> distinctList = Arrays.stream(empArray)
                .distinct()
                .collect(Collectors.toList());

        List<Integer> intList = Arrays.asList(2, 4, 5, 6, 8);

        boolean allEven = intList.stream().allMatch(i -> i % 2 == 0);
        boolean oneEven = intList.stream().anyMatch(i -> i % 2 == 0);
        boolean noneMultipleOfThree = intList.stream().noneMatch(i -> i % 3 == 0);
        System.out.println(allEven + " " + oneEven + " " + noneMultipleOfThree);

        empArray = setDefaults();

        Integer maxSalary = Arrays.stream(empArray)
                .mapToInt(Employee::getSalary)
                .max()
                .orElseThrow(NoSuchElementException::new);

        System.out.println("Max salary is " + maxSalary);

        // Reduction operations can be used to take sequences of input and combine them into 1 result
        Integer salarySum = Arrays.stream(empArray)
                .map(Employee::getSalary)
                .reduce(0, Integer::sum);

        System.out.println("Summed salaries: " + salarySum);

        String names = Arrays.stream(empArray)
                .map(Employee::getFirstName)
                .collect(Collectors.joining(", "))
                .toString();

        System.out.println("Names is " + names);

        // here is something special and magical...
        DoubleSummaryStatistics stats = Arrays.stream(empArray)
                .collect(Collectors.summarizingDouble(Employee::getSalary));

        System.out.println("The max is: " + stats.getMax() + " sum is: " + stats.getSum() + " so much more info in here!!");

        List<Employee> empList = Arrays.asList(setDefaults());

        Map<Boolean, List<Employee>> makesAlot = empList.stream().collect(
                Collectors.partitioningBy(e -> e.getSalary() > 600));

        System.out.println(makesAlot.toString());

        // If we want more than 2 groups we can group by whatever we want!
        Map<String, List<Employee>> groupByName = empList.stream().collect(Collectors.groupingBy(Employee::getFirstName));
        System.out.println(groupByName.toString());

        Map<Character, List<Employee>> groupByFirstLetter = empList.stream().collect(Collectors.groupingBy(employee -> employee.getFirstName().charAt(0)));
        System.out.println(groupByFirstLetter.toString());

        // The above examples grouped items into their own element type, type employee. Here we adapt the collector to a different type
        // I used the value as a string, just to play around but it could also be a list if there was a duplicate first name
        Map<String, String> groupByNameIntoName = empList.stream().collect(
                Collectors.groupingBy(e -> e.getFirstName(), Collectors.mapping(e -> e.getLastName(), Collectors.joining())));
        System.out.println(groupByNameIntoName.toString());

        // Here's how we can generate valeus on the fly!
        Stream.generate(Math::random)
                .limit(3)
                .forEach(System.out::println);

    }

    public static Employee[] setDefaults(){
        Employee[] empArray = {
                new Employee("Rob", "Schwartz", 23, 100),
                new Employee("Bill", "Gates", 40, 1000),
                new Employee("Brian", "Cornell", 55, 10000)
        };
        return empArray;
    }
}

class Employee implements Comparable<Employee>{

    private String firstName, lastName;
    private int age, salary;

    public Employee(String firstName, String lastName, int age, int salary){
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.salary = salary;
    }

    public String getFirstName() { return firstName;}

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public int getAge() { return age; }

    public void setAge(int age) { this.age = age; }

    public int getSalary() { return salary; }

    public void setSalary(int salary) { this.salary = salary; }

    public void addToSalary(int increment) {this.salary += increment; }

    @Override
    public int compareTo(Employee o) {
        if (this.getAge() > o.getAge()){
            return 1;
        }
        return -1;
    }
}
