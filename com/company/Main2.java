/**
 * Copyright (c) 2017, 2017, ZJU Mathematical Modeling Team 57528. 
 * All rights reserved.
 */

package com.company;

import java.util.ArrayList;

/**
 * Implementation of the Main, which finishes task B's first model.
 *
 * The class includes methods for creating an instance, for 
 * adding a person to queues, for checking work.
 * In order to program more efficiently, member variables are default 
 * and could be changed by other class in this package.
 *
 * @author  Crcrcry
 * @since   JDK1.8
 * @version 1.0
 */

public class Main2 {
    /** These values are used for security lanes. */
    private static final int checkType = 3;
    private static final int everyCheckNum = 4;

    /** The value is used for recording the simulation time. */
    private static final int time = 300;

    /** The value is used for generating people per second per type. */
    private static final int person_PerSecondType = 1;

    /** The value is used for the shortest check time. */
    private static final int basicTime = 25;

    /** The value is used for recording the head of queues. */
    int[] head = new int[checkType];

    /** 
     * These values are used for mathematical models, 
     * security lanes and person queues. 
     */
    Calculate cal = new Calculate();
    ArrayList<Check2>[] check = new ArrayList[checkType];
    ArrayList<Person>[] queue = new ArrayList[checkType];

    /**
     * Initializes a newly created object so that it represents a system.
     */
    public Main2(){
        for(int i = 0; i < checkType; i++){
            check[i] = new ArrayList<Check2>();
            queue[i] = new ArrayList<Person>();
        }
    }

    /**
     * There are people arriving at the airport, 
     * adding them to corresponding queues.
     *
     * @param  id
     *         the id of queue people should be added to
     *
     * @param  second
     *         now time
     *
     * @param  num
     *         the number of people
     */
    private void addPersonToQueue(int id, int second, int num){
        int i;
        ArrayList<Person> q = queue[id];

        for (i = 0; i < num; i++){
            q.add(new Person(second, q.size()+1));
        }
    }

    /**
     * All security lanes work at the same time.
     *
     * @param  id
     *         the id of queue people should be added to
     *
     * @param  second
     *         now time
     *
     * @return the number of busy security lanes
     */
    private int check(int id, int second){
        int i;
        int deal = 0;
        ArrayList<Person> q = queue[id];
        ArrayList<Check2> c = check[id];

        for (i = 0; i < c.size(); i++){
            if(c.get(i).isEmpty()){
                if(head[id] >= q.size()){

                }else{
                    deal++;
                    Person firstP = q.get(head[id]);
                    c.get(i).dealNext(firstP, second);
                    head[id]++;
                }
            }else{
                deal++;
                c.get(i).dealNow();
            }
        }

        return deal;
    }

    /**
     * Data statistics and output.
     */
    private void getInfo(){
        int i, j;
        int[] wait_time = new int[checkType];
        double[] check_time = new double[checkType];
        double[] wait_avg = new double[checkType];
        double[] check_avg = new double[checkType];
        double[] wait_vari = new double[checkType];
        double[] check_vari = new double[checkType];
        for(i = 0; i < checkType; i++){
            wait_time[i] = 0;
            check_time[i] = 0;
            wait_vari[i] = 0;
            check_vari[i] = 0;
            //System.out.println("\n======== "+i+" ========\n");
            /** Getting everyone's data. */
            for(j = 0; j < queue[i].size(); j++){
                //queue[i].get(j).getInfo();
                wait_time[i] += queue[i].get(j).check1_time - 
                    queue[i].get(j).arrive_time;
                check_time[i] += queue[i].get(j).finish_time - 
                    queue[i].get(j).check1_time;
            }

            /** Getting average values. */
            wait_avg[i] = wait_time[i]/queue[i].size();
            check_avg[i] = check_time[i]/queue[i].size();

            /** Getting variances. */
            wait_vari[i] += Math.pow((queue[i].get(i).check1_time - 
                queue[i].get(i).arrive_time)-wait_avg[i],2);
            check_vari[i] += Math.pow((queue[i].get(i).finish_time - 
                queue[i].get(i).check1_time)-check_avg[i],2);

            wait_vari[i] /= queue[i].size();
            check_vari[i] /= queue[i].size();
        }
        for(i = 0; i < checkType; i++){
            System.out.println("\ncheck"+i+"：units(second)\nPeople number："
                +queue[i].size()+"\nWait time and Variance："+wait_avg[i]
                +"  "+wait_vari[i]+"\nCheck time and Variance："
                +check_avg[i]+"  "+check_vari[i]);
        }
    }

    /**
     * Program main function interface.
     */
    public static void main(String[] args){
        int i, j;
        boolean afterCheck = true;
        Main2 airport = new Main2();

        /** Initializing security lanes. */
        for(i = 0; i < checkType; i++){
            for(j = 0; j < everyCheckNum; j++){
                airport.check[i].add(new Check2(i*10+basicTime));
            }
        }

        /** 
         * Simulating the security check process. 
         * Variable i represent per second. 
         */
        for(i = 0; true; i++){
            /** Generating people preparing to enter the queue. */
            if(i < time){
                /** Adding people to corresponding queues. */
                airport.addPersonToQueue(0, i, 
                    airport.cal.possion(person_PerSecondType));
                airport.addPersonToQueue(1, i, 
                    airport.cal.possion(person_PerSecondType));
                airport.addPersonToQueue(2, i, 
                    airport.cal.possion(person_PerSecondType));
            }

            /** All security lanes work per second. */
            afterCheck = true;
            for(j = 0; j < checkType; j++){
                afterCheck = ((airport.check(j, i) == 0)&&afterCheck);
            }

            /** If all lanes are free, simulating will end. */
            if(afterCheck && i >= time){
                break;
            }
        }

        /** Data statistics and output. */
        System.out.println("\n========== Result ==========");
        airport.getInfo();
    }
}
