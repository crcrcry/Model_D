/**
 * Copyright (c) 2017, 2017, ZJU Mathematical Modeling Team 57528. 
 * All rights reserved.
 */

package com.company;

import java.util.Random;

/**
 * Implementation of the Mathematical Model, which includes Poisson 
 * distribution model and Negative exponential distribution model.
 *
 * The class includes methods for creating an instance, for producing 
 * a random number of Poisson distribution, for producing a random number 
 * of Negative exponential distribution.
 * In order to program more efficiently, member variables are default 
 * and could be changed by other class in this package.
 *
 * @author  Crcrcry
 * @since   JDK1.8
 * @version 1.0
 */

public class Calculate {

    /**
     * Producing a random number of Poisson distribution.
     *
     * @param  lamda
     *         mathematical expectation of Poisson distribution
     *
     * @return  the random number of Poisson distribution produced
     */
    public int possion(double lamda){
        int k = 0;
        double Lambda = lamda;
        double p = 1.0;
        double l= Math.exp(-Lambda);
        Random r = new Random();

        while (p>=l){
            double u = r.nextDouble();
            p *= u;
            k++;
        }
        return k-1;
    }

    /**
     * Producing a random number of Negative exponential distribution.
     *
     * @param  lamda
     *         mathematical expectation's reciprocal of Negative exponential 
     *         distribution
     *
     * @return  the random number of Negative exponential distribution produced
     */
    public int exp(double lamda){
        double x, z = Math.random();
        double Lamda = lamda;

        x = -(1 / lamda) * Math.log(z);
        return (int)x;
    }
}
