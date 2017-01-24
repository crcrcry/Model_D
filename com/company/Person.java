/**
 * Copyright (c) 2017, 2017, ZJU Mathematical Modeling Team 57528. 
 * All rights reserved.
 */

package com.company;

/**
 * Implementation of the people waiting for aircraft security check.
 *
 * The class includes methods for creating an instance, for getting 
 * information. In order to program more efficiently, member variables 
 * are default and could be changed by other class in this package.
 *
 * @author  Crcrcry
 * @since   JDK1.8
 * @version 1.0
 */

public class Person {
    /** These values are used for recording pivotal time points. */
    int arrive_time, check1_time = -1, 
        check2_time = -1, finish_time = -1;

    /** The value is used to identify people. */
    int id; //person id

    /**
     * Initializes a newly created object so that it represents 
     * a person who arrives at the airport and prepare 
     * for security check.
     *
     * @param  arrive
     *         the time of arrival of the people
     *
     * @param  pid
     *         the identification of the people
     */
    public Person(int arrive, int pid){
        id = pid;
        arrive_time = arrive;
    }

    /**
     * Get time information of the object which represents a person.
     */
    public void getInfo(){
        System.out.println("People "+id+": "+
            arrive_time+" "+check1_time+" "+check2_time+" "+finish_time);
    }
}
