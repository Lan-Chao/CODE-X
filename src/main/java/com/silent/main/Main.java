package com.silent.main;

import com.silent.reference.PhantomReferenceObject;

import java.util.concurrent.TimeUnit;

/**
 * @author zhao
 * @date 2021/11/12 14:02
 */
public class Main {


    public static void main(String[] args) throws InterruptedException {
        PhantomReferenceObject object = new PhantomReferenceObject();
        object = null;

        System.gc();

        TimeUnit.SECONDS.sleep(5);

    }



}
