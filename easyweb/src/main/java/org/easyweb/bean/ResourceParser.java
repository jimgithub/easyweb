package org.easyweb.bean;

import groovy.lang.GroovyObject;
import org.apache.commons.lang.StringUtils;
import org.easyweb.app.App;
import org.easyweb.groovy.annotation.AnnotationParser;

import javax.annotation.Resource;
import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * User: jimmey/shantong
 * DateTime: 13-3-27 下午5:52
 */
public class ResourceParser extends AnnotationParser {

    public ResourceParser() {
        super(new ParsePhase[]{ParsePhase.Ioc});
    }

    @Override
    public boolean isParse(Annotation annotation) {
        return annotation instanceof Resource;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void parse(App app, Annotation annotation, File file, Object target, GroovyObject groovyObject) {
        if (!(target instanceof Field)) {
            return;
        }

        Field field = (Field) target;
        Resource resource = (Resource) annotation;
        String name = field.getName();
        if (StringUtils.isNotBlank(resource.name())) {
            name = resource.name();
        }
        Object obj = BeanFactory.getBeans(app).get(name);
//        if (obj == null && !field.getType().isAnnotationPresent(NoGroovy.class)) {
//            try {
//                obj = BeanFactory.getSpringBean(name, field.getType());
//            } catch (Exception e) {
//            }
//        }
        if (obj != null) {
            try {
                field.setAccessible(true);
                field.set(groovyObject, obj);
            } catch (Exception e) {
            }
            groovyObject.setProperty(field.getName(), obj);
        }
    }
}
