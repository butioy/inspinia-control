package org.butioy;

/**
 * Created by butioy on 2016/4/11.
 */
public class DemoImpl implements DemoInterface {
    @Override
    public void demo() {
        DemoInterface.super.demo();
    }

    @Override
    public void printf() {
        System.out.println("interface printf!");
    }

    public static void main(String[] args) {
        DemoInterface impl = new DemoImpl();
        impl.demo();
    }
}
