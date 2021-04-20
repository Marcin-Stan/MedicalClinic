package org.table;

import org.database.employee.EmployeeEntity;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class TableTest {

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        List<Field> allFields = Arrays.asList(EmployeeEntity.class.getDeclaredFields());
        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setId(999);
        for(Field field:allFields){
            System.out.println(field.getName());
        }

        List<Method> methods = Arrays.asList(EmployeeEntity.class.getMethods());
        for(Method methode : methods){
            if(isGetter(methode)){
                System.out.println(methode.invoke(employeeEntity));
            }

        }


    }

    public static boolean isGetter(Method method){
        if(!method.getName().startsWith("get"))      return false;
        if(method.getParameterTypes().length != 0)   return false;
        if(void.class.equals(method.getReturnType())) return false;
        return true;
    }

    }

