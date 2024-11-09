package PLC.Sandbox.example_lambda;

import java.util.Arrays;
import java.util.List;

public class LambdaExpressionsExample {
    public static void main(String[] args) {
        // Example list with mixed types
        List<Person> people = Arrays.asList(
            new Person(20), // Age: 20
            new Student(28), // Age: 28
            new Worker(35, 2500), // Age: 35, Salary: 2500
            new Worker(40, 1800) // Age: 40, Salary: 1800
        );

        // 1. Output all persons older than 25
        System.out.println("Persons older than 25:");
        people.stream().filter(p->p.getAge() > 25)
                       //.forEach(p->System.out.println(p));
                       .forEach(System.out::println);
        // 2. Output all students younger than 30
        System.out.println("\nStudents younger than 30:");
        /*
        people.stream().filter(p->p.getAge() < 30 && p instanceof Student)
                       .forEach(p->System.out.println(p));
        */
        //a.x().y();
        //b = a.x();
        //b.y();
        /*people.stream().filter ((p)->{return p.getAge() < 30 && p instanceof Student;})
                       .forEach((p)->{System.out.println(p);});
        */
        //(arguments)->{body}
        /*
          people.stream().filter((p)->{return p instanceof Student;})
                         .filter((p)->{return p.getAge() < 30;})
                         .forEach((p)->{System.out.println(p);});
        */
        people.stream().filter(p-> p.getAge() < 30)
                       .filter(p-> p instanceof Student)
                       .forEach(p->System.out.println(p));                 
        // 3. Output all workers with salary greater than 2000
        System.out.println("\nWorkers with salary > 2000:");
        people.stream().filter(p-> p instanceof Worker)
                       .map(p->(Worker)p)
                       .filter(p-> p.getSalary() > 2000)
                       .forEach(p->System.out.println(p));
    }
}


class Person {
    private int age;

    public Person(int age) {this.age = age;}

    public int getAge() {return age;}

    @Override
    public String toString() {return "Person{age=" + age + "}";}
}

class Student extends Person {
    public Student(int age) {super(age);}

    @Override
    public String toString() {return "Student{age=" + getAge() + "}";}
}

class Worker extends Person {
    private int salary;

    public Worker(int age, int salary) {
        super(age);
        this.salary = salary;
    }

    public int getSalary() {return salary;}

    @Override
    public String toString() {return "Worker{age=" + getAge() + ", salary=" + salary + "}";}
}
