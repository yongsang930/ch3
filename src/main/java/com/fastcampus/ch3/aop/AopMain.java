package com.fastcampus.ch3.aop;

import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AopMain {
    public static void main(String[] args) throws Exception {
        MyAdivice myAdivice = new MyAdivice();

        Class myClass = Class.forName("com.fastcampus.ch3.aop.MyClass"); // Class 정보를 읽어옮
        Object obj = myClass.newInstance(); // myClass 객체 생성

        for (Method m : myClass.getDeclaredMethods()) { // myClass의 Method 정보를 읽어옮
            myAdivice.invoke(m, obj, null);
        }
    }
}

class MyAdivice {

    Pattern p = Pattern.compile("a.*"); // 패턴생성

    boolean matches(Method m) { // 패턴과 일치하면 true를 return
        Matcher matcher = p.matcher(m.getName());
        return matcher.matches();
    }

    void invoke(Method m, Object obj, Object... args) throws Exception {
//        if (matches(m)) 패턴과 일치하는 메서드만 출력
        if (m.getAnnotation(Transactional.class) != null) // @Transactional이 붙은 메서드만 출력
            System.out.println("[before]{");

        m.invoke(obj, args); // aaa(), aaa2(), bbb() 호출 가능

//        if (matches(m)) 패턴과 일치하는 메서드만 출력
        if (m.getAnnotation(Transactional.class) != null) // @Transactional이 붙은 메서드만 출력
            System.out.println("}[after]");
    }
}

class MyClass {
    @Transactional
    void aaa() {
        System.out.println("aaa call");
    }

    void aaa2() {
        System.out.println("aaa2 call");
    }

    void bbb() {
        System.out.println("bbb call");
    }
}