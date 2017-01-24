/**
 * Copyright (c) 2017, 2017, ZJU Mathematical Modeling Team 57528. 
 * All rights reserved.
 */

package com.company;

/**
 * Implementation of one type of aircraft security lane, 
 * which includes regular check and pre-check.
 *
 * The class includes methods for creating an instance, for taking 
 * a security check for people, for generating a check time 
 * according to mathematical models and so on.
 * In order to program more efficiently, member variables are default 
 * and could be changed by other class in this package.
 *
 * @author  Crcrcry
 * @since   JDK1.8
 * @version 1.0
 */

public class Check {
    /** 
     * These values are used for generating check times 
     * according to mathematical models. 
     */
    int time1, time2, preTime;

    /** 
     * These values are used for getting the status
     * and type of the check. 
     */
    boolean empty, isPreCheck;

    /** The values is used for recording the people in ID-check. */
    Person now_person;

    /** The values is used for mathematical models. */
    Calculate cal = new Calculate();

    /**
     * Initializes a newly created object 
     * so that it represents a free security lane.
     *
     * @param  is
     *         the type of security lane
     */
    public Check(boolean is){
        empty = true;
        isPreCheck = is;
        if(isPreCheck)  preTime = 9;
        else preTime = 20;
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
        generateTime1();

        now_person = passenger;
        now_person.check1_time = time;
        now_person.check2_time = now_person.check1_time + time1;
    }

    /**
     * Security lanes continue to check people in the ID-check.
     */
    public void dealNow(){
        if((--time1) <= 1){
            generateTime2();
            now_person.finish_time = now_person.check2_time + time2;
            empty = true;
        }
    }

    /**
     * Get people's check time in ID-check
     * according to the Negative exponential distribution model.
     */
    private void generateTime1(){
        time1 = cal.exp(1.0/11.2) + preTime;
    }

    /**
     * Get people's check time in body scanning and baggage screening
     * according to the Negative exponential distribution model.
     */
    private void generateTime2(){
        time2 = Math.max(cal.exp(1.0/11.6),cal.exp(1.0/7.0));
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
