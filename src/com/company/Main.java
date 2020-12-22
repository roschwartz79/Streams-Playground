package com.company;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This is a simple java command line app to experiment and learn about Java Streams!
 * Java 11 is used here even though Streams were introduced in Java 8
 *
 * Key concepts:
 *  - Streams are really good for plumbing and dealing with lots of data! Super convenient to have in the toolbox
 *  - Pipelines consist of a stream souce, then zero or more intermediate ops, then a terminal op
 *  - Short Circuit ops let us do infinite streams in finite time! (It sounds worse than it is)
 */

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

class Employee {

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
}
