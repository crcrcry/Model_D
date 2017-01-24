/**
 * Copyright (c) 2017, 2017, ZJU Mathematical Modeling Team 57528. 
 * All rights reserved.
 */

package com.company;

import java.util.ArrayList;

/**
 * Implementation of the Main, which finishes task B's second model.
 *
 * The class includes methods for creating an instance, for getting 
 * preparation time, for calculating wait time.
 * In order to program more efficiently, member variables are default 
 * and could be changed by other class in this package.
 *
 * @author  Crcrcry
 * @since   JDK1.8
 * @version 1.0
 */

public class Person3 {
    /** These values are used for recording time. */
    int preTime, waitTime;

    /** The value is used for sorting. */
    private static final boolean isSort = true;

    /** The value is used for maximum size of queue. */
    private static final int max = 6;

    /** The value is used for recording the simulation time. */
    private static final int pNum = 10000;

    /**
     * Initializes a newly created object so that it represents 
     * a person who prepares for body and baggage scanning.
     *
     * @param  time
     *         people's preparation time
     */
    public Person3(int time) {
        preTime = time;
        waitTime = 0;
    }

    /**
     * Calculating one's preparation time and waiting time each round
     *
     * @param  time
     *         the first people's preparation time
     */
    public void sub(int time){
        waitTime += time;
        preTime -= time;
        if(preTime <= 0)    preTime = 0;
    }

    /**
     * Getting one's preparation time.
     *
     * @return the value of one's preparation time
     */
    public int getPreTime(){
        return preTime;
    }

    /**
     * Program main function interface.
     */
    public static void main(String[] args){
        /** Initializing queues, comparator and models. */
        int i, j;
        MyCompare comp = new MyCompare();

        Calculate cal = new Calculate();
        ArrayList<Person3> q = new ArrayList<Person3>();
        ArrayList<Person3> list = new ArrayList<Person3>();

        /** Simulating the waiting process. 
         * Variable i represent per person. 
         */
        for(i = 0; i < max; i++){
            q.add(new Person3(cal.exp(1.0/30.0)));
        }
        for(i = 0; i < pNum; i++){
            /** Sorting the queue. */
            if(isSort)    q.sort(comp);
            int thisTime = q.get(0).getPreTime();

            for(j = 0; j < q.size(); j++){
                q.get(j).sub(thisTime);
            }
            list.add(q.remove(0));
            q.add(new Person3(cal.exp(1.0/30.0)));
        }

        /** Data statistics and output. */
        int sum = 0;
        double avg, vari = 0;
        for(i = 0; i < list.size(); i++){
            sum += list.get(i).waitTime;
        }
        avg = sum/list.size();
        for(i = 0; i < list.size(); i++){
            vari += Math.pow(list.get(i).waitTime-avg,2);
        }
        vari /= list.size();
        System.out.println("\n========== Result ==========");
        System.out.println("\nAverage wait time: " + avg);
        System.out.println("\nVariance value: " + vari);
    }
}
