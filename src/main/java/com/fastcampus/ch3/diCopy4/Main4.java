package com.fastcampus.ch3.diCopy4;

import com.google.common.reflect.ClassPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

@Component
class Car {
    //    @Autowired Engine engine;
//    @Autowired Door door;
//
    @Resource
    Engine engine;
    @Resource
    Door door;

    @Override
    public String toString() {
        return "Car{" +
                "engine=" + engine +
                ", door=" + door +
                '}';
    }
}

@Component
class Door {
}

@Component
class SportCar extends Car {
}

@Component
class Truck extends Car {
}

@Component
class Engine {
}

@Component
class Chain {
}

class AppContext {
    Map map;

    AppContext() {
        map = new HashMap();
        doComponentSeen();
        doAutowired();
        doResource();
    }

    private void doResource() {
        // map에 저장된 객체의 iv 중에서 @Resource가 붙어있으면
        // map에서 iv의 타입에 맞는 객체를 찾아서 연결(객체의 주소룰 iv에 저장)
        try {
            for (Object bean : map.values()) {
                // bean이 가지고 있는 클래스의 정보를 가져온다.
                for (Field fid : bean.getClass().getDeclaredFields()) {
                    // 필드가 getAnnotation했을때 null이 아니면 필드값을 변경
                    if (fid.getAnnotation(Resource.class) != null) // byName
                        fid.set(bean, getBean(fid.getName())); // car.engine = obj;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doAutowired() {
        // map에 저장된 객체의 iv 중에서 @Autowired가 붙어있으면
        // map에서 iv의 타입에 맞는 객체를 찾아서 연결(객체의 주소룰 iv에 저장)
        try {
            for (Object bean : map.values()) {
                // bean이 가지고 있는 클래스의 정보를 가져온다.
                for (Field fid : bean.getClass().getDeclaredFields()) {
                    // 필드가 getAnnotation했을때 null이 아니면 필드값을 변경
                    if (fid.getAnnotation(Autowired.class) != null) // byType
                        fid.set(bean, getBean(fid.getType())); // car.engine = obj;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doComponentSeen() {
        // 1.패키지내의 클래스 목록을 가져온다
        // 2.반복문으로 클래스를 하나씩 읽어와서 @Component가 붙어 있는지 확인
        // 3. @Component가 붙어있으면 객체를 생성해서 Map에 저장
        try {
            ClassLoader classLoader = AppContext.class.getClassLoader();
            ClassPath classPath = ClassPath.from(classLoader);

            Set<ClassPath.ClassInfo> set = classPath.getTopLevelClasses("com.fastcampus.ch3.diCopy4");

            for (ClassPath.ClassInfo classInfo : set) {
                Class clazz = classInfo.load();
                Component component = (Component) clazz.getAnnotation(Component.class);
                if (component != null) {
                    String id = StringUtils.uncapitalize(classInfo.getSimpleName());
                    map.put(id, clazz.newInstance());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Object getBean(String key) {
        return map.get(key);
    }

    Object getBean(Class clazz) {
        for (Object obj : map.values()) {
            if (clazz.isInstance(obj)) {
                return obj;
            }
        }
        return null;
    }
}

public class Main4 {
    public static void main(String[] args) throws Exception {
        AppContext ac = new AppContext();

        Car car = (Car) ac.getBean("car"); // by Name으로 검색
        Car car2 = (Car) ac.getBean(Car.class); // by Type으로 검색
        Engine engine = (Engine) ac.getBean("engine");
        Chain chain = (Chain) ac.getBean("chain");
        Door door = (Door) ac.getBean(Door.class);

        // 수동으로 객체 연결 - @Autowired를 붙여 자동으로 연결해야함
//        car.engine = engine;
//        car.door = door;

        System.out.println("car = " + car);
        System.out.println("car = " + car2);
        System.out.println("engine = " + engine);
        System.out.println("chain = " + chain);
        System.out.println("door = " + door);
    }
}
