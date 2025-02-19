package com.threadConst;

public class TC {
    private static ThreadLocal<ThreadConstants> setup = new ThreadLocal<>();

    public static void set(ThreadConstants tc){
        setup.set(tc);
    }
    public static ThreadConstants get(){
        return setup.get();
    }

}
