package com.nxl.utils;

/**
 * @author : nixl
 * @date : 2020/11/5
 */
public class QueryUtil {

    public static final String GREATER = ">";

    public boolean equal(String parameter, String target) {
        return parameter.equals(target);
    }

    public boolean greater(String parameter, String target) {

        return new Integer(parameter) > new Integer(target);

    }

    public boolean less(String parameter, String target){
        return new Integer(parameter) < new Integer(target);
    }

    public void synthesize(String... strings) {

    }


    public static void main(String[] args) {
        Object equal = ReflexUtil.invokeInstanceMethod(new QueryUtil(), "equal", new Class[]{String.class, String.class, String.class}, new String[]{"10", "1", ">"});
        if ((boolean) equal) {
            System.out.println(true);
        } else {
            System.out.println(false);
        }
    }

}
