/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package org.cloudbus.cloudsim;

/**
 *
 * @author ADMIN
 */

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cloudbus.cloudsim;

/**
 *
 * @author INDHU
 */
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import cloudsimprojects1.org.cloudbus.cloudsim.examples.CloudEnv2;
        
public class MyTimerTask1 implements Runnable {
   public long sec;
   public int cloudletId;
   public long st;
   public MyTimerTask(long st1,long s,int c){
       sec=s;
       cloudletId=c;
       st=st1;
       //System.out.println(" HGV SEC "+sec);
       
   }
    @Override
    public void run() {
        startTask(st);
        System.out.println("cloudlet id "+ cloudletId + " started at:"+new Date());
        completeTask(sec);
        freeVm(cloudletId);
        //System.out.println("SEC "+sec);
        System.out.println("cloudlet id "+ cloudletId +" finished at:"+new Date());
        Log.printLine();
    }

    private void completeTask(long t) {
        try {
            //assuming it takes 20 secs to complete the task
            Thread.sleep(t*1000);
            
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
    }
    private void startTask(long st1){
        
        try {
            //assuming it takes 20 secs to complete the task
            Thread.sleep(st1*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void freeVm(int cid){
        
    }
    

}