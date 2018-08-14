package com.demo.other;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.Joinpoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.assertj.core.condition.Join;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

@Aspect
@Component
@Slf4j
public class EnumAspect {


    @Pointcut("@annotation(com.demo.other.EnumAnnoation)")
    public void annotationPointCut() {
    }

    @Before("com.demo.other.EnumAspect.annotationPointCut()")
    public void beforeOutput() {

        // 包下面的类
        List<String> clazzs = getClassName("cn.package.test");

        List<Class> classes = new ArrayList<>();
        for(String path :clazzs){
            try {
                classes.add(Class.forName(path));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (classes != null) {
           log.info(clazzs.size()+"");
        }

    }


    public static void main(String[] args) throws IllegalAccessException {

        // 包下面的类
        List<String> clazzs = getClassName("com.demo.other");

        List<Class> classes = new ArrayList<>();
        for(String path :clazzs){
            try {
                classes.add(Class.forName(path));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (classes != null) {
            log.info(clazzs.size() + "");

            for (Class<?> cls : classes) {
                Field[] fields = cls.getDeclaredFields();
                for (Field field : fields) {
                    boolean fieldHasAnno = field.isAnnotationPresent(EnumAnnoation.class);
                    if (fieldHasAnno) {
                        EnumAnnoation fieldAnno = field.getAnnotation(EnumAnnoation.class);
                        //输出注解属性
                        String value = fieldAnno.value();
                        log.info(field + "value:" + value);

                        try {
                            field.set(cls, value);
                        } catch (Exception e) {
                        }
//                        System.out.println(field.getName() + " age = " + age + ", hobby = " + Arrays.asList(hobby).toString() + " type = " + type);
                    }
                }


            }
            TestAnnoation testAnnoation = new TestAnnoation();
            log.info(testAnnoation.value);
        }

    }

    public static List<String> getClassName(String packageName) {
        return getClassName(packageName, true);
    }

    public static List<String> getClassName(String packageName, boolean childPackage) {
        List<String> fileNames = null;
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        String packagePath = packageName.replace(".", "/");

        log.info(packagePath);
        URL url = loader.getResource(packagePath);
        if (url != null) {
            log.info(url.getPath());
            String type = url.getProtocol();
            if (type.equals("file")) {
                log.info(type);
                fileNames = getClassNameByFile(url.getPath(), null, childPackage);
            }
        }
        return fileNames;
    }

    /**
     * 从项目文件获取某包下所有类
     * @param filePath 文件路径
     * @param className 类名集合
     * @param childPackage 是否遍历子包
     * @return 类的完整名称
     */
    private static List<String> getClassNameByFile(String filePath, List<String> className, boolean childPackage) {
       final List<String> myClassName = new ArrayList<String>();
        if(Files.isWritable(Paths.get(filePath))){
                //目录下所有的文件和子目录
            log.info(filePath);
            try {
                Files.walkFileTree(Paths.get(filePath), new SimpleFileVisitor<Path>() {
                    // 访问目录时使用发该方法
                    @Override
                    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
                            throws IOException {
                        // 继续遍历 Continue继续
//                        if(dir.toString()!=filePath)
//                        myClassName.addAll(getClassNameByFile(dir.toString(),className,childPackage));
                        return FileVisitResult.CONTINUE;
                    }
                    // 访问文件使用该方法
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                            throws IOException {
                        if (file.toString().endsWith(".class")) {

                            String childFilePath = file.toString();


                            childFilePath = childFilePath.substring(childFilePath.indexOf("classes")+8, childFilePath.lastIndexOf("."));
                            childFilePath = childFilePath.replaceAll("/", ".");
                            log.info(childFilePath);
                            myClassName.add(childFilePath);
                            //如果找到一个就返回则 添加以下代码  TERMINATE 结束
                            //return FileVisitResult.TERMINATE;
                        }
                        return FileVisitResult.CONTINUE;
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        return myClassName;
    }

}