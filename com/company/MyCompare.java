/**
 * Copyright (c) 2017, 2017, ZJU Mathematical Modeling Team 57528. 
 * All rights reserved.
 */

package com.company;

import java.util.Comparator;

/**
 * Implementation of the comparator, which is used for sorting.
 *
 * The class includes a method for comparing rule.
 *
 * @author  Crcrcry
 * @since   JDK1.8
 * @version 1.0
 */

public class MyCompare implements Comparator<Object>{

    /**
     * Compare the size of two objects.
     *
     * @param  o0
     *         the first object to compare
     *
     * @param  o1
     *         the second object to compare
     *
     * @return {@code, 1} if o0 is larger than o1
     *         {@code, -1} if o0 is smaller than o1
     *         {@code, 0} if o0 is equal to o1
     */
    public int compare(Object o0, Object o1) {
        Person3 user0 = (Person3) o0;
        Person3 user1 = (Person3) o1;
        if (user0.getPreTime() > user1.getPreTime()){
            return 1;
        }else if(user0.getPreTime() < user1.getPreTime()){
            return -1;
        }else{
            return 0;
        }
    }
}

