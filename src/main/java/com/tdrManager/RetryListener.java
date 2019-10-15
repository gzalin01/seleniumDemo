package com.tdrManager;

import org.testng.IAnnotationTransformer;
import org.testng.IRetryAnalyzer;
import org.testng.annotations.ITestAnnotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class RetryListener  implements IAnnotationTransformer {
    public void transform(ITestAnnotation annotation, Class testClass,
                          Constructor testConstructor, Method testMethod) {
        //获取到retryAnalyzer的注解
        IRetryAnalyzer retryAnalyzer = annotation.getRetryAnalyzer();
        if (retryAnalyzer == null) {
            ////如果注解为空，则动态设置注解，以确保用例失败后重跑。
            annotation.setRetryAnalyzer( Retry.class );
        }
    }
}
