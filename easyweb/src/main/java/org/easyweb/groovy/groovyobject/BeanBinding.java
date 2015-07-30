package org.easyweb.groovy.groovyobject;

import groovy.lang.Binding;
import org.easyweb.bean.BeanFactory;

import java.util.HashMap;
import java.util.Map;

public class BeanBinding extends Binding {

    public static Map<String, Object> beans = new HashMap<String, Object>();

    @Override
    public Object getVariable(String name) {
        Object obj = getBean(name);
        if (obj != null) {
            return obj;
        }
        return super.getVariable(name);
    }

    @Override
    public Object getProperty(String property) {
        Object obj = getBean(property);
        if (obj != null) {
            return obj;
        }
        return super.getProperty(property);
    }

    private Object getBean(String name) {
        Object obj = BeanFactory.getBean(name);
        if (obj == null) {
            obj = beans.get(name);
        }
        return obj;
    }

    public static void putOuterBindings(Map<String, Object> map) {
        beans.putAll(map);
    }

}
