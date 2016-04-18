package org.butioy;

/**
 * Created by butioy on 2016/4/11.
 */
public interface DemoInterface {

    default void demo() {
        System.out.println("interface demo!");
        printf();
    }

    void printf();
}
