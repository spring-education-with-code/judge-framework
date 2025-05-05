package org.example.judgeframework.setting;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;
import org.example.annotation.OneProcess;
import org.example.judgeframework.process.OneJudgeProcess;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ApplicationContextSetting {

    public Map<String, Object> componentScanAndMakeBean(String rootPkg){

        Map<String, Object> beanFactory = new ConcurrentHashMap<>();

        //ClassGraph에 루트 패키지만 지정—하위 패키지까지 전부 스캔된다
        try (ScanResult scan = new ClassGraph()
                .enableAnnotationInfo()
                .acceptPackages(rootPkg)
                .scan()) {

            //@Process 어노테이션이 붙은 클래스 찾기
            for (ClassInfo ci : scan.getClassesWithAnnotation("org.example.annotation.Process")) {

                Class<?> configClass = ci.loadClass();

                try {
                    //reflection API 사용
                    Object configInstance = configClass.getDeclaredConstructor().newInstance();

                    //reflection API를 사용해서 메서드 생성해서 map 에 넣음
                    for (Method method : configClass.getDeclaredMethods()) {
                        if (method.isAnnotationPresent(OneProcess.class)) {
                            method.setAccessible(true);
                            Object result = method.invoke(configInstance);
                            beanFactory.put(method.getName(), (OneJudgeProcess) result);
                        }
                    }
                }catch(Exception e){
                    throw new RuntimeException("Failed to load and run processes", e);
                }
            }
        }

        return beanFactory;
    }


}
