package com.fastcampus.ch3.diCopy1;

import java.io.FileReader;
import java.util.Properties;

class Car {
}

class SportCar extends Car {
}

class Truck extends Car {
}
class Engine {}
class Chain {}

public class Main1 {
    public static void main(String[] args) throws Exception {
        Car car = (Car)getObject("car");
        Engine engine = (Engine)getObject("engine");
        Chain chain = (Chain) getObject("chain");

        System.out.println("car = " + car);
        System.out.println("engine = " + engine);
        System.out.println("chain = " + chain);
    }

    static Object getObject(String key) throws Exception {
        Properties p = new Properties();

        p.load(new FileReader("config.txt"));

        Class clazz = Class.forName(p.getProperty(key));

        return clazz.newInstance();
    }

    static Car getCar() throws Exception {
        Properties p = new Properties();

        p.load(new FileReader("config.txt"));

        Class clazz = Class.forName(p.getProperty("car"));

        return (Car) clazz.newInstance();
    }

}
