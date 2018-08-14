package com.demo.other;



import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DymaticProxy {

    public static void main(String[] args)
    {
        //    我们要代理的真实对象
        Transport bicycle = new Bicycle();

        //    我们要代理哪个真实对象，就将该对象传进去，最后是通过该真实对象来调用其方法的
        InvocationHandler handler = new TransporyProxy(bicycle);

        /*
         * 通过Proxy的newProxyInstance方法来创建我们的代理对象，我们来看看其三个参数
         * 第一个参数 handler.getClass().getClassLoader() ，我们这里使用handler这个类的ClassLoader对象来加载我们的代理对象
         * 第二个参数realSubject.getClass().getInterfaces()，我们这里为代理对象提供的接口是真实对象所实行的接口，表示我要代理的是该真实对象，这样我就能调用这组接口中的方法了
         * 第三个参数handler， 我们这里将这个代理对象关联到了上方的 InvocationHandler 这个对象上
         */
        Transport subject = (Transport) Proxy.newProxyInstance(handler.getClass().getClassLoader(), bicycle
                .getClass().getInterfaces(), handler);

        subject.trans(new Passenger("liwen"));
        System.out.println(subject.getClass().getName());


        System.out.println("cglib代理");

        cglibProxy cglibProxy = new cglibProxy();
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Bicycle.class);

        enhancer.setCallback(cglibProxy);
        Bicycle o = (Bicycle)enhancer.create();

        o.trans(new Passenger("plw"));
    }
}


class cglibProxy implements MethodInterceptor {


    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("++++++before " + methodProxy.getSuperName() + "++++++");
        System.out.println(method.getName());
        Object o1 = methodProxy.invokeSuper(o, objects);
        System.out.println("++++++before " + methodProxy.getSuperName() + "++++++");
        return o1;
    }
}

class TransporyProxy implements InvocationHandler {

    private Transport transport;


    public TransporyProxy(Transport transport) {
        this.transport = transport;

    }

    @Override
    public Object invoke(Object object, Method method, Object[] args)
            throws Throwable
    {
        //　　在代理真实对象前我们可以添加一些自己的操作
        System.out.println("before rent house");

        System.out.println("Method:" + method);

        //    当代理对象调用真实对象的方法时，其会自动的跳转到代理对象关联的handler对象的invoke方法来进行调用
        method.invoke(transport, args);

        //　　在代理真实对象后我们也可以添加一些自己的操作
        System.out.println("after rent house");

        return null;
    }

}


class Passenger{

    private  String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Passenger(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Passenger{" +
                "name='" + name + '\'' +
                '}';
    }
}

interface Transport{
    void trans(Passenger passenger);
}

class Bicycle implements Transport{

    @Override
    public void trans(Passenger passenger) {

        System.out.println("bicycle is now taking the passenger"+passenger+"to his destination");
    }
}

class Car implements  Transport{
    @Override
    public void trans(Passenger passenger) {
        System.out.println("car is now taking the passenger"+passenger+"to his destination");

    }
}