/**
 * Copyright (c) 2017, 2017, ZJU Mathematical Modeling Team 57528. 
 * All rights reserved.
 */

package com.company;

import java.util.ArrayList;

/**
 * Implementation of the Main, which finishes task A.
 *
 * The class includes methods for creating an instance, for adding a 
 * person to queues, for checking work.
 * In order to program more efficiently, member variables are default 
 * and could be changed by other class in this package.
 *
 * @author  Crcrcry
 * @since   JDK1.8
 * @version 1.0
 */

public class Main {
    /** 
     * These values are used for recording 
     * the number of security lanes. 
     */
    private static final int r_checkNum = 8;
    private static final int p_checkNum = 4;

    /** The value is used for getting the ratio of regular-check. */
    private static final double beta = 0.55;

    /** The value is used for recording the simulation time. */
    private static final int time = 300;

    /** The value is used for generating people per second. */
    private static final int person_PerSecond = 3;

    /** These values are used for recording the head of queues. */
    int r_head, p_head;

    /** 
     * These values are used for mathematical models, 
     * security lanes and person queues. 
     */
    Calculate cal = new Calculate();
    ArrayList<Person> r_queue = new ArrayList<Person>(), p_queue = 
        new ArrayList<Person>();
    ArrayList<Check> r_check = new ArrayList<Check>(), p_check = 
        new ArrayList<Check>();

    /**
     * There are people arriving at the airport, adding them to 
     * corresponding queues.
     *
     * @param  type
     *         the type of check, pre-check: 1 regular check: 2
     *
     * @param  second
     *         now time
     *
     * @param  num
     *         the number of people
     */
    private void addPersonToQueue(int type, int second, int num){
        int i;
        ArrayList<Person> q = (type == 1)?p_queue:r_queue;

        for (i = 0; i < num; i++){
            q.add(new Person(second, q.size()+1));
        }
    }

    /**
     * All security lanes work at the same time.
     *
     * @param  type
     *         the type of check, pre-check: 1 regular check: 2
     *
     * @param  second
     *         now time
     *
     * @return the number of busy security lanes
     */
    private int check(int type, int second){
        int i;
        int deal = 0;
        int head = (type == 1)?p_head:r_head;
        ArrayList<Person> q = (type == 1)?p_queue:r_queue;
        ArrayList<Check> c = (type == 1)?p_check:r_check;

        for (i = 0; i < c.size(); i++){
            if(c.get(i).isEmpty()){
                if(head >= q.size()){

                }else{
                    deal++;
                    Person firstP = q.get(head);
                    c.get(i).dealNext(firstP, second);
                    head++;
                }
            }else{
                deal++;
                c.get(i).dealNow();
            }
        }

        if(type == 1) p_head = head;
        else  r_head = head;

        return deal;
    }

    /**
     * Data statistics and output.
     */
    private void getInfo(){
        int i;
        double p_wait_avg, r_wait_avg, p_check_avg, r_check_avg, 
            p_all_avg, r_all_avg;
        double p_wait_vari, r_wait_vari, p_check_vari, r_check_vari, 
            p_all_vari, r_all_vari;
        int p_wait_time = 0, p_check_time = 0;
        int r_wait_time = 0, r_check_time = 0;

        /** Getting everyone's data. */
        //System.out.println("\n======== pre-check ========\n");
        for (i = 0; i < p_queue.size(); i++) {
            //p_queue.get(i).getInfo();
            p_wait_time += p_queue.get(i).check1_time - 
                p_queue.get(i).arrive_time;
            p_check_time += p_queue.get(i).finish_time - 
                p_queue.get(i).check1_time;
        }
        //System.out.println("\n======== reg-check ========\n");
        for (i = 0; i < r_queue.size(); i++){
            //r_queue.get(i).getInfo();
            r_wait_time += r_queue.get(i).check1_time - 
                r_queue.get(i).arrive_time;
            r_check_time += r_queue.get(i).finish_time - 
                r_queue.get(i).check1_time;
        }

        /** Getting average values. */
        p_wait_avg = p_wait_time/p_queue.size();
        p_check_avg = p_check_time/p_queue.size();
        p_all_avg = p_check_avg + p_wait_avg;

        r_wait_avg = r_wait_time/r_queue.size();
        r_check_avg = r_check_time/r_queue.size();
        r_all_avg = r_check_avg + r_wait_avg;

        /** Getting variances. */
        p_wait_vari=r_wait_vari=p_check_vari=r_check_vari=0;
        p_all_vari=r_all_vari = 0;
        for(i = 0; i < p_queue.size(); i++){
            p_wait_vari += Math.pow((p_queue.get(i).check1_time - 
                p_queue.get(i).arrive_time)-p_wait_avg,2);
            p_check_vari += Math.pow((p_queue.get(i).finish_time - 
                p_queue.get(i).check1_time)-p_check_avg,2);
            p_all_vari += Math.pow((p_queue.get(i).finish_time - 
                p_queue.get(i).arrive_time)-p_all_avg,2);
        }
        p_wait_vari /= p_queue.size();
        p_check_vari /= p_queue.size();
        p_all_vari /= p_queue.size();

        for(i = 0; i < r_queue.size(); i++){
            r_wait_vari += Math.pow((r_queue.get(i).check1_time - 
                r_queue.get(i).arrive_time)-r_wait_avg,2);
            r_check_vari += Math.pow((r_queue.get(i).finish_time - 
                r_queue.get(i).check1_time)-r_check_avg,2);
            r_all_vari += Math.pow((r_queue.get(i).finish_time - 
                r_queue.get(i).arrive_time)-r_all_avg,2);
        }
        r_wait_vari /= r_queue.size();
        r_check_vari /= r_queue.size();
        r_all_vari /= r_queue.size();

        System.out.println("\npre-check：units(second)\nPeople number："+
            p_queue.size()+"\nWait time and Variance："+p_wait_avg+"  "+
            p_wait_vari+"\nCheck time and Variance："+p_check_avg+"  "+
            p_check_vari+"\nAll time and Variance："+p_all_avg+"  "+
            p_all_vari);
        System.out.println("\nreg-check：units(second)\nPeople number："+
            r_queue.size()+"\nWait time and Variance："+r_wait_avg+"  "+
            r_wait_vari+"\nCheck time and Variance："+r_check_avg+"  "+
            r_check_vari+"\nAll time and Variance："+r_all_avg+"  "+
            r_all_vari);
    }

    /**
     * Program main function interface.
     */
    public static void main(String[] args){
        int i;
        Main airport = new Main();

        /** Initializing security lanes. */
        for(i = 0; i < r_checkNum; i++){
            airport.r_check.add(new Check(false));
        }
        for(i = 0; i < p_checkNum; i++){
            airport.p_check.add(new Check(true));
        }

        /** 
         * Simulating the security check process.
         * Variable i represent per second. 
         */
        for(i = 0; true; i++){
            if(i < time){
                /** Generating people preparing to enter the queue. */
                int all_num = airport.cal.possion(person_PerSecond);
                int p_num = (int)(all_num * beta);
                int r_num = all_num - p_num;

                /** Adding people to corresponding queues. */
                airport.addPersonToQueue(1, i, p_num);
                airport.addPersonToQueue(2, i, r_num);
            }

            /** All security lanes work per second. */
            boolean check1 = (airport.check(1, i) == 0);
            boolean check2 = (airport.check(2, i) == 0);

            /** If all lanes are free, simulating will end. */
            if(check1 && check2 && i >= time){
                break;
            }
        }

        /** Data statistics and output. */
        System.out.println("\n========== Result ==========");
        airport.getInfo();
    }
}
