package com.springboot.telegym.common;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.Date;

public class MyListComparator implements Comparator<Object> {

    final Pageable pageable;

    public MyListComparator(Pageable pageable) {
        this.pageable = pageable;
    }

    @Override
    public int compare(Object o1, Object o2) {

        Sort a = pageable.getSort();
        String sortBy = "";
        String sortOrder = "";
        for (Sort.Order order : a) {
            sortBy = order.getProperty();
            sortOrder = order.getDirection().toString();
        }

        try {
            Field field1;
            Field field2;
            Class<?> typeField;
            Field[] arrayField = o1.getClass().getDeclaredFields();
            boolean checkField =false;
            for ( Field field : arrayField) {
                if (field.getName().equals(sortBy)) {
                    checkField = true;
                    break;
                }
            }
            if (checkField) {
                field1 = o1.getClass().getDeclaredField(sortBy);
                field2 = o2.getClass().getDeclaredField(sortBy);
            }
            else {
                field1 = o1.getClass().getSuperclass().getDeclaredField(sortBy);
                field2 = o1.getClass().getSuperclass().getDeclaredField(sortBy);
            }
            typeField = field1.getType();

            field1.setAccessible(true); // because the fields in entity has "private"
            field2.setAccessible(true);

            if(typeField == long.class || typeField == Long.class){
                Long d1 = (Long) field1.get(o1);
                Long d2 = (Long) field2.get(o2);
                return (sortOrder.equalsIgnoreCase("asc"))? d1.compareTo(d2) : d2.compareTo(d1);

            }else if(typeField == Date.class){
                Date d1 = (Date) field1.get(o1);
                Date d2 = (Date) field2.get(o2);
                return (sortOrder.equalsIgnoreCase("asc"))? d1.compareTo(d2) : d2.compareTo(d1);

            }else if(typeField == Integer.class || typeField == int.class){
                Integer d1 = (Integer) field1.get(o1);
                Integer d2 = (Integer) field2.get(o2);
                return (sortOrder.equalsIgnoreCase("asc"))? d1.compareTo(d2) : d2.compareTo(d1);

            }
            else{
                String d1 = (String) field1.get(o1);
                String d2 = (String) field2.get(o2);
                return (sortOrder.equalsIgnoreCase("asc"))? d1.compareTo(d2) : d2.compareTo(d1);

            }
        } catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Missing variable sortBy");
        }catch (ClassCastException e) {
            throw new RuntimeException("sortBy is not found in class list");
        } //shoud not happen

    }
}
