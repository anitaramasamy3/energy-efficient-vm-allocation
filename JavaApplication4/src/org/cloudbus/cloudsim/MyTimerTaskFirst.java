/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cloudbus.cloudsim;

import java.util.Date;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class MyTimerTaskFirst implements Runnable {
    public long sec;
   public int cloudletId;
   public long st;
      public double nst;
      public double ot;
   public List<Vm> free;
   Boolean cm=false;
   public MyTimerTaskFirst(long st1,long s,int c,List<Vm> f,double nst1,double ot1){
       sec=s;
       cloudletId=c;
       st=st1;
      nst=nst1;
      ot=ot1;
       free=f;
       //System.out.println(" HGV SEC "+sec);
       
   }
    @Override
    public void run() {
        startTask(st);
        System.out.println("cloudlet id "+ cloudletId + " started at:"+new Date());
        Log.printLine();
        System.out.println("ot " + ot);
        System.out.println("nst "+ nst);
        System.out.println();
        if(ot>=nst){
        cm=checkMigration(ot,nst);
        }
        Log.printLine();
        if(cm==false){
        completeTask(sec);
        System.out.println("cloudlet id "+ cloudletId +" finished at:"+new Date());
        freeVm(free);
        System.out.println("cloudlet id " + cloudletId + " has freed its resources");
        }
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
    private void freeVm(List<Vm> f){
       // cloudletList.
       int s = f.size();
       for(int i=0;i<s;i++){
           f.get(i).setCloudlet(null);
       }
       
        
    }
    private Boolean checkMigration(double ot,double nst){
        
        Log.printLine("cloudlet  " + cloudletId + " is queued to migrate along with the next time partition");
        
        double rem=ot-nst;
        Log.printLine(" occupied time updation of cloudlet "+ cloudletId + "updated remaining time for migration " + rem);
        Log.printLine();
        return true;
        
    }
    

}
