package com.kib.weblab4.permissions;

import com.kib.weblab4.entities.ApplicationUser;
import com.kib.weblab4.entities.Permission;
import com.kib.weblab4.exceptions.PermissionIsMissingException;
import com.kib.weblab4.repo.PermissionRepo;
import com.kib.weblab4.repo.UserRepo;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.lang.model.util.Types;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

@Component
public class HasPermissionsBeanPostProcessor implements BeanPostProcessor {

    private final Map<Class<?>, Map<Method, String[]>> classesAnnotatedWithHasPermissions;
    private final PermissionRepo permissionRepo;
    private final UserRepo userRepo;

    @Autowired
    public HasPermissionsBeanPostProcessor(PermissionRepo permissionRepo, UserRepo userRepo) {
        this.userRepo = userRepo;
        this.classesAnnotatedWithHasPermissions = new HashMap<>();
        this.permissionRepo = permissionRepo;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Method[] methods = bean.getClass().getDeclaredMethods();

        for (Method method : methods) {
            if (method.isAnnotationPresent(HasPermissions.class)) {
                classesAnnotatedWithHasPermissions.putIfAbsent(bean.getClass(), new HashMap<>());
                classesAnnotatedWithHasPermissions.get(bean.getClass()).put(method, method.getAnnotation(HasPermissions.class).value());
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (classesAnnotatedWithHasPermissions.containsKey(bean.getClass())) {
            Map<Method, String[]> annotatedMethods = classesAnnotatedWithHasPermissions.get(bean.getClass());
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(bean.getClass());
            enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> {
                if (annotatedMethods.containsKey(method)) {
                    Authentication userAuth = null;
                    for (Object elem : args) {
                        if (Authentication.class.isAssignableFrom(elem.getClass())) {
                            userAuth = (Authentication) elem;
                        }
                    }
                    if (userAuth != null) {
                        ApplicationUser applicationUser = userRepo.findByUsername(userAuth.getName());
                        List<String> permissionsNames = applicationUser.getPermissions().stream().map(Permission::getName).toList();
                        for (String permissionName : annotatedMethods.get(method)) {
                            if (permissionsNames.contains(permissionName)) {
                                return proxy.invokeSuper(obj, args);
                            }
                        }
                        throw new PermissionIsMissingException();
                    }
                }
                return proxy.invokeSuper(obj, args);
            });

            Field[] fields = bean.getClass().getDeclaredFields();

            Class<?>[] argumentClasses = new Class[fields.length];
            Object[] arguments = new Object[fields.length];

            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                field.setAccessible(true);

                try {
                    Object argument = field.get(bean);

                    arguments[i] = argument;
                    argumentClasses[i] = field.getType();
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }

            }

            bean = enhancer.create(argumentClasses, arguments);
        }
        return bean;
    }
}
