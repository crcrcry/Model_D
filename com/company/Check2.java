/**
 * Copyright (c) 2017, 2017, ZJU Mathematical Modeling Team 57528. 
 * All rights reserved.
 */

package com.company;

/**
 * Implementation of the aircraft security lane, which classifies 
 * passengers in different speed.
 *
 * The class includes methods for creating an instance, for taking 
 * a security check for people, for getting hte status of security lane.
 * In order to program more efficiently, member variables are default 
 * and could be changed by other class in this package.
 *
 * @author  Crcrcry
 * @since   JDK1.8
 * @version 1.0
 */

public class Check2 {
    /** The value is used for getting the status of the check. */
    boolean empty;

    /** The value is used for recording this lane's average check time. */
    int checkTime, sum;

    /** The value is used for recording this lane's average check time. */
    int thisTime;

    /** The values is used for recording the people in ID-check. */
    Person now_person;

    /** The values is used for mathematical models. */
    Calculate cal = new Calculate();

    /**
     * Initializes a newly created object 
     * so that it represents a free security lane.
     *
     * @param  avg
     *         security lane's average check time
     */
    public Check2(int avg){
        empty = true;
        checkTime = avg;
    }

    /**
     * Security lanes take a security check for next people in the queue.
     * Check time follow the Negative exponential distribution model.
     *
     * @param  passenger
     *         the next people taking checks
     *
     * @param  time
     *         now time
     */
    public void dealNext(Person passenger, int time){
        empty = false;
        thisTime = cal.exp(1.0/checkTime);
        sum = thisTime;

        now_person = passenger;
        now_person.check1_time = time;
        now_person.finish_time = now_person.check1_time + thisTime;
    }

    /**
     * Security lanes continue to check people in the ID-check.
     */
    public void dealNow(){
        if((--sum) <= thisTime*0.6){
            empty = true;
        }
    }

    /**
     * Returns true if the security lane is empty.
     * 
     * @return {@code true} if the security lane is empty, 
     *         otherwise {@code false}
     */
    public boolean isEmpty(){
        return empty;
    }
}
