package com.hero.rpc.producerStub;

import java.io.Serializable;

//封装类信息
public class ClassInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String className;  //类名
    private String methodName;//方法名
    private Class<?>[] types; //参数类型
    private Object[] objects;//参数列表

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getTypes() {
        return types;
    }

    public void setTypes(Class<?>[] types) {
        this.types = types;
    }

    public Object[] getObjects() {
        return objects;
    }

    public void setObjects(Object[] objects) {
        this.objects = objects;
    }
}

