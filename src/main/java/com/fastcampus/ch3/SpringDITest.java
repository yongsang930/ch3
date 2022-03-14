//package com.fastcampus.ch3;
//
//
//import org.checkerframework.checker.units.qual.C;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.GenericXmlApplicationContext;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Controller;
//
//import java.util.Arrays;
//
//@Component("engine")
//class Engine {
//}
//
//@Component
//class SuperEngine extends Engine {
//}
//
//@Component
//class TurboEngine extends Engine {
//}
//
//@Component
//class Door {
//}
//
//@Component
//class Car {
//    @Value("red")
//    String color;
//    @Value("100")
//    int oil;
//    @Autowired  // byType으로 찾고 여러개라면 byName으로 찾음
//    Engine engine;
//    @Autowired
//    Door[] doors;
//
//    public void setColor(String color) {
//        this.color = color;
//    }
//
//    public void setOil(int oil) {
//        this.oil = oil;
//    }
//
//    public void setEngine(Engine engine) {
//        this.engine = engine;
//    }
//
//    public void setDoors(Door[] doors) {
//        this.doors = doors;
//    }
//
//    @Override
//    public String toString() {
//        return "Car{" +
//                "color='" + color + '\'' +
//                ", oil=" + oil +
//                ", engine=" + engine +
//                ", doors=" + Arrays.toString(doors) +
//                '}';
//    }
//}
//
//public class SpringDITest {
//    public static void main(String[] args) {
//        ApplicationContext ac = new GenericXmlApplicationContext("config.xml");
////        Car car = (Car) ac.getBean("car");
//        Car car = ac.getBean("car", Car.class); // 형변환 생략
//        Car car2 = (Car) ac.getBean(Car.class);
//
//        Engine engine = (Engine) ac.getBean("superEngine");
//        Door door = (Door) ac.getBean("door");
//
////        car.setColor("red");
////        car.setOil(100);
////        car.setEngine(engine);
////        car.setDoors(new Door[]{ac.getBean("door", Door.class), (Door) ac.getBean("door")});
//
//        System.out.println("car = " + car);
////        System.out.println("car2 = " + car2);
//        System.out.println("engine = " + engine);
////        System.out.println("door = " + door);
//
//    }
//}
//
