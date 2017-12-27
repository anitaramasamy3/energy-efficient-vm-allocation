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
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
//import 
        
public class MyTimerTask implements Runnable {
   public long sec;
   public int cloudletId;
   public long st;
      public long dt;
   public List<Vm> free;
   public MyTimerTask(long st1,long s,int c,List<Vm> f){
       sec=s;
       cloudletId=c;
       st=st1;
      
       free=f;
       //System.out.println(" HGV SEC "+sec);
       
   }
    @Override
    public void run() {
        startTask(st);
        System.out.println("cloudlet id "+ cloudletId + " started at:"+new Date());
        Log.printLine();
        completeTask(sec);
        System.out.println("cloudlet id "+ cloudletId +" finished at:"+new Date());
        freeVm(free);
        System.out.println("cloudlet id " + cloudletId + " has freed its resources");
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
            Thread.sleep(st1*10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void freeVm(List<Vm> f){
       // cloudletList.
       int s = f.size();
       for(int i=0;i<s;i++){
           f.get(i).setCloudlet(null);
       }
       
        
    }
    

}