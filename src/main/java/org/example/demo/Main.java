package org.example.demo;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
//        robotExample();
//        polymorphismExample1();
        polymorphismExample2(new Cat("cat"));
        polymorphismExample2(new Dog("dog"));
    }

    private static void polymorphismExample1() {
        List<Animal> animals = new ArrayList<>();
        animals.add(new Cat("cat"));
        animals.add(new Dog("dog"));

        for (Animal animal : animals) {
            System.out.println(animal);
            animal.voice();
        }
    }

    private static void polymorphismExample2(Animal animal) {
        System.out.println(animal);
        animal.voice();
    }

    private static void robotExample() {
        SimpleRobot robot = new SimpleRobot();
        System.out.println(robot);
        robot.move(new Point(3, -5));
        System.out.println(robot);
    }

}