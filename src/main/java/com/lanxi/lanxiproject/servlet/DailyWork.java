package com.lanxi.lanxiproject.servlet;

import java.util.Date;

/**
 * Created by summer on 16/3/29.
 */
public class DailyWork {
    int i = 0;

    public void work(){
        System.out.println(new Date() + " Quartz working... "+ ++i );
    }
}
