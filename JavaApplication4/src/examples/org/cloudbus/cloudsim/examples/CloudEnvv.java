/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package examples.org.cloudbus.cloudsim.examples;

/**
 *
 * @author ADMIN
 */


package examples.org.cloudbus.cloudsim.examples;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;  
import java.util.concurrent.Executors;  

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.CloudletSchedulerSpaceShared;
import org.cloudbus.cloudsim.CloudletSchedulerTimeShared;
import org.cloudbus.cloudsim.Datacenter;
import org.cloudbus.cloudsim.DatacenterBroker;
import org.cloudbus.cloudsim.DatacenterCharacteristics;
import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.MyTimerTask;
import org.cloudbus.cloudsim.Pe;
import org.cloudbus.cloudsim.ResourceAllocationTable;
import org.cloudbus.cloudsim.ResourceAllocationPolicy;
import org.cloudbus.cloudsim.Storage;
import org.cloudbus.cloudsim.UtilizationModel;
import org.cloudbus.cloudsim.UtilizationModelFull;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.VmAllocationPolicySimple;
import org.cloudbus.cloudsim.VmSchedulerTimeShared;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.lists.VmList;
import org.cloudbus.cloudsim.lists.HostList;
//import static org.cloudbus.cloudsim.examples.CloudEnvV1.hostList;
//import static org.cloudbus.cloudsim.examples.CloudEnvV1.vm;
import org.cloudbus.cloudsim.power.PowerHost;
import org.cloudbus.cloudsim.power.models.PowerModelSpecPowerHpProLiantMl110G3PentiumD930;
import org.cloudbus.cloudsim.power.models.PowerModelSpecPowerHpProLiantMl110G4Xeon3040;
import org.cloudbus.cloudsim.power.models.PowerModelSpecPowerHpProLiantMl110G5Xeon3075;
import org.cloudbus.cloudsim.power.models.PowerModelSpecPowerHpProLiantBl460cG6Xeon5630;
import org.cloudbus.cloudsim.power.models.PowerModelSpecPowerHpProLiantSl390sG7Xeon5649;

import org.cloudbus.cloudsim.provisioners.BwProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.RamProvisionerSimple;



public class CloudEnvv {
        static Vm vm[]=new Vm[42];
        static Cloudlet cloudlet[]= new Cloudlet[4000];
        static PowerHost ph[]=new PowerHost[8];
	/** The cloudlet list. */
	private static List<Cloudlet> cloudletList;
        static List<PowerHost> hostList;
	/** The vmlist. */
	private static List<Vm> vmlist;
        private static Map<Integer,List<ResourceAllocationTable>> map;
        private static Map<Integer,List<ResourceAllocationPolicy>> map1;
        static DatacenterBroker broker;
        static ResourceAllocationPolicy rap[];
        static double sTime=0;

	/**
	 * Creates main() to run this example
	 */
	public static void main(String[] args) {

		Log.printLine("Starting CloudSimExample3...");

		try {
			// First step: Initialize the CloudSim package. It should be called
			// before creating any entities.
			int num_user = 1;   // number of cloud users
			Calendar calendar = Calendar.getInstance();
			boolean trace_flag = false;  // mean trace events

			// Initialize the CloudSim library
			CloudSim.init(num_user, calendar, trace_flag);

			// Second step: Create Datacenters
			//Datacenters are the resource providers in CloudSim. We need at list one of them to run a CloudSim simulation
			Datacenter datacenter0 = createDatacenter("Datacenter_0");
                        
                         /*  for(int i=0;i<4;i++){
                     Log.printLine("HEYYYAAAAAAAAAAAAAAAAAAAA");
                     Log.printLine(hostList.get(i).getId());
                 }*/
                     

			//Third step: Create Broker
			broker = createBroker();
			int brokerId = broker.getId();

			//Fourth step: Create one virtual machine
			vmlist = new ArrayList<Vm>();

			//VM description
			int vmid = 0;
			int mips = 5;
			long size = 10000; //image size (MB)
			int ram = 8; //vm memory (MB)
			long bw = 100;
			int pesNumber = 1; //number of cpus
			String vmm = "Xen"; //VMM name
                        String uid;
                        for(int i=0;i<42;i++){
                            vm[i]=new Vm(vmid, brokerId, mips, pesNumber, ram, bw, size, vmm, new CloudletSchedulerSpaceShared());
                            vmid++;
                            vmlist.add(vm[i]);
                           // System.out.println("Vm Id:" + vmid);
                            uid=vm[i].getUid();
                           Log.printLine("Vm Uid: "+ uid);
                           if(vm[i].getId()<4){
                               vm[i].setHost(ph[0]);
                               Log.printLine("vm  ##"+vm[i].getId()+"is set to the host ##"+vm[i].getHost().getId());
                           }
                           //System.out.println("host:"+vm[i].getHost().getId());
                            if(vm[i].getId()>=4&&vm[i].getId()<=9){
                               vm[i].setHost(ph[1]);
                                Log.printLine("vm  ##"+vm[i].getId()+"is set to the host ##"+vm[i].getHost().getId());
                           }
                            if(vm[i].getId()>=10&&vm[i].getId()<=16){
                               vm[i].setHost(ph[2]);
                                Log.printLine("vm  ##"+vm[i].getId()+"is set to the host ##"+vm[i].getHost().getId());
                           }
                            if(vm[i].getId()>=17&&vm[i].getId()<=24){
                               vm[i].setHost(ph[3]);
                                Log.printLine("vm  ##"+vm[i].getId()+"is set to the host ##"+vm[i].getHost().getId());
                           }
                            if(vm[i].getId()>=25&&vm[i].getId()<=26){
                               vm[i].setHost(ph[4]);
                                Log.printLine("vm  ##"+vm[i].getId()+"is set to the host ##"+vm[i].getHost().getId());
                           }
                            if(vm[i].getId()>=27&&vm[i].getId()<=29){
                               vm[i].setHost(ph[5]);
                                Log.printLine("vm  ##"+vm[i].getId()+"is set to the host ##"+vm[i].getHost().getId());
                           }
                            if(vm[i].getId()>=30&&vm[i].getId()<=34){
                               vm[i].setHost(ph[6]);
                                Log.printLine("vm  ##"+vm[i].getId()+"is set to the host ##"+vm[i].getHost().getId());
                           }
                            if(vm[i].getId()>=35&&vm[i].getId()<=41){
                               vm[i].setHost(ph[7]);
                                Log.printLine("vm  ##"+vm[i].getId()+"is set to the host ##"+vm[i].getHost().getId());
                           }
                        }
                 
                
                        

			/*for(int i=0;i<42;i++){
                            vm[i]=new Vm(vmid, brokerId, mips, pesNumber, ram, bw, size, vmm, new CloudletSchedulerSpaceShared());
                            vmid++;
                            vmlist.add(vm[i]);
                            System.out.println("Vm Id:" + vmid);
                            //vm[i].setHost(host[0]);
                         //    Log.printLine("Vm Id: "+ vmid);
                            
                        }*/
                      /*  int u=0;
                        PowerModelSpecPowerHpProLiantMl110G4Xeon3040 p1= new PowerModelSpecPowerHpProLiantMl110G4Xeon3040();
                        PowerModelSpecPowerHpProLiantMl110G5Xeon3075 p2 = new PowerModelSpecPowerHpProLiantMl110G5Xeon3075();
                        for(int g=0;g<4;g++){
                            if(ph[g].getPowerModel().getClass().equals(p1.getClass()))
                            {
                                for(int f=0;f<6;f++){
                                    //Log.printLine("HOLA");
                                     vm[u]=new Vm(vmid, brokerId, mips, pesNumber, ram, bw, size, vmm, new CloudletSchedulerSpaceShared());
                                     vmid++;
                                    ph[g].vmCreate(vm[u]);
                                    vmlist.add(vm[u]);
                                    u++;
                                }
                            }
                            if(ph[g].getPowerModel().getClass().equals(p2.getClass()))
                                
                            {
                                for(int f=0;f<15;f++){
                                     vm[u]=new Vm(vmid, brokerId, mips, pesNumber, ram, bw, size, vmm, new CloudletSchedulerSpaceShared());
                                     vmid++;
                                    ph[g].vmCreate(vm[u]);
                                    vmlist.add(vm[u]);
                                    u++;  
                                }
                            }
                        }*/
                             
                        
			//submit vm list to the broker
			broker.submitVmList(vmlist);
                        //Log.printLine("VMLISTTTTTTTTTTTTTTTTTT   "+vmlist.size());
                       
                  

			//Fifth step: Create two Cloudlets
			cloudletList = new ArrayList<Cloudlet>();

			//Cloudlet properties
			int id = 0;
			long length = 40000;
			long fileSize = 300;
			long outputSize = 300;
			UtilizationModel utilizationModel = new UtilizationModelFull();
                        File file = new File("E:\\output1.txt");
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			//StringBuffer stringBuffer = new StringBuffer();
                        int wid,tid,nm;
                        double st,dt;
                        int k=0, lenFile=0;
			String line;
			while ((line = bufferedReader.readLine()) != null) {
			    lenFile++;	
                            String fields[];
                                if(line.equals(" "))
                                    continue;
                                else if(line.equals("["))
                                    continue;
                                fields =line.split(" ");  
                                wid= Integer.parseInt(fields[0]);
                                tid= Integer.parseInt(fields[1]);
                                st= Double.parseDouble(fields[2]);
                                dt= Double.parseDouble(fields[3]);
                                nm= Integer.parseInt(fields[4]);  
                                //System.out.println("wid:" + wid+"tid:" + tid+"st:" + st+"dt:" + dt+"nm:" + nm);
                          //for(int k= 0;k<3982;k++){      
                                cloudlet[k] = new Cloudlet(id, length, pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
                            //    int stt=cloudlet[k].getStatus();
                               // Log.printLine("status yo ="+stt);
                                cloudlet[k].setUserId(brokerId);
                                cloudlet[k].setWid(wid);
                                cloudlet[k].setTid(tid);
                                cloudlet[k].setStartTime(st);
                                cloudlet[k].setDurTime(dt);
                                cloudlet[k].setNumVM(nm);
                           /*    wid= cloudlet[k].getWid();
                                tid=cloudlet[k].getTid();
                                st=cloudlet[k].getStartTime();
                                dt=cloudlet[k].getDurTime();
                                nm=cloudlet[k].getNumVM();
                              
                                System.out.println("wid:" + wid+"tid:" + tid+"st:" + st+"dt:" + dt+"nm:" + nm);*/
                                //Log.printLine("HI");
                                cloudletList.add(cloudlet[k]);
                                  //System.out.println("CLOUDLET Id:" + id);
                                id++;
                                k++;
                               // break;
                        }
                        
//			Random rand = new Random();
//                          for(int i=0;i<lenFile;i++){
//                            int min=0,max=42;
//                        int randomNum = rand.nextInt((max - min) + 1) + min;
//                        broker.bindCloudletToVm(cloudlet[i].getCloudletId(),vm[randomNum].getId());
//                        }

			//submit cloudlet list to the broker
                       //broker.
			broker.submitCloudletList(cloudletList);
                        
                  
                        /*  for(int i=0;i<42;i++){
                            Log.printLine("yoyoyooyoyoyooyoyoyooyooyoyoyoyoyooyoyoyoyoyooyoyo vm  ##"+vm[i].getId()+"is set to the host ##"+vm[i].getHost().getId());
                        }*/
  
			 
           
                /*for(int i=0;i<hostList.size();i++){
                                 
                                 PowerHost h= hostList.get(i);
                                 Log.printLine(h.getId());
                                 
                             }*/
                //System.out.println(Arrays.asList(rar));
             
       
		// Sixth step: Starts the simulation
			CloudSim.startSimulation();
                        
                         for(int i=0;i<42;i++){
//                            vm[i]=new Vm(vmid, brokerId, mips, pesNumber, ram, bw, size, vmm, new CloudletSchedulerSpaceShared());
//                            vmid++;
//                            vmlist.add(vm[i]);
                           // System.out.println("Vm Id:" + vmid);
//                           
                             
                           if(vm[i].getId()<4){
                               vm[i].setHost(hostList.get(0));
                               Log.printLine("vm  ##"+vm[i].getId()+"is set to the host ##"+vm[i].getHost().getId());
                               hostList.get(0).vmCreate(vm[i]);
                           }
                           //System.out.println("host:"+vm[i].getHost().getId());
                            if(vm[i].getId()>=4&&vm[i].getId()<=9){
                               vm[i].setHost(hostList.get(1));
                                Log.printLine("vm  ##"+vm[i].getId()+"is set to the host ##"+vm[i].getHost().getId());
                                hostList.get(1).vmCreate(vm[i]);
                           }
                            if(vm[i].getId()>=10&&vm[i].getId()<=16){
                               vm[i].setHost(hostList.get(2));
                                Log.printLine("vm  ##"+vm[i].getId()+"is set to the host ##"+vm[i].getHost().getId());
                                hostList.get(2).vmCreate(vm[i]);
                           }
                            if(vm[i].getId()>=17&&vm[i].getId()<=24){
                               vm[i].setHost(hostList.get(3));
                                Log.printLine("vm  ##"+vm[i].getId()+"is set to the host ##"+vm[i].getHost().getId());
                                hostList.get(3).vmCreate(vm[i]);
                           }
                            if(vm[i].getId()>=25&&vm[i].getId()<=26){
                               vm[i].setHost(hostList.get(4));
                                Log.printLine("vm  ##"+vm[i].getId()+"is set to the host ##"+vm[i].getHost().getId());
                                hostList.get(4).vmCreate(vm[i]);
                           }
                            if(vm[i].getId()>=27&&vm[i].getId()<=29){
                               vm[i].setHost(hostList.get(5));
                                Log.printLine("vm  ##"+vm[i].getId()+"is set to the host ##"+vm[i].getHost().getId());
                                hostList.get(5).vmCreate(vm[i]);
                           }
                            if(vm[i].getId()>=30&&vm[i].getId()<=34){
                               vm[i].setHost(hostList.get(6));
                                Log.printLine("vm  ##"+vm[i].getId()+"is set to the host ##"+vm[i].getHost().getId());
                                hostList.get(6).vmCreate(vm[i]);
                           }
                            if(vm[i].getId()>=35&&vm[i].getId()<=41){
                               vm[i].setHost(hostList.get(7));
                                Log.printLine("vm  ##"+vm[i].getId()+"is set to the host ##"+vm[i].getHost().getId());
                                hostList.get(7).vmCreate(vm[i]);
                           }
                        }
                 
                
                        
                                            
            
                
//         for(int i=0;i<42;i++){
//                               Cloudlet c = cloudletList.get(i);
//                              Log.printLine("Vm of Cloudlet "+c.getVmId());
//                }

			// Final step: Print results when simulation is over
			List<Cloudlet> newList = broker.getCloudletReceivedList(); 
                           

                       
                	CloudSim.stopSimulation();
                timeLists(newList);
        	printCloudletList(newList);
                printRAT(newList);
                printAllocPolicy(newList);
                Map<Integer, List<ResourceAllocationTable>> treeMap = new TreeMap<Integer, List<ResourceAllocationTable>>(map);
                int a= treeMap.keySet().size();
                for(int i=0;i<a;i++){
                    Host h=hostList.get(i);
                    int hid=h.getId();
                    int na;
                    na = countIdleVms(hid,1.0);
                    Log.printLine(" Number of Idle Vms in Host " + hid + " is " +na);
                }
             

			//Print the debt of each user to each datacenter
			datacenter0.printDebts();

			Log.printLine("CloudSimExample3 finished!");
		}
		catch (Exception e) {
			e.printStackTrace();
			Log.printLine("The simulation has been terminated due to an unexpected error");
		}
	}

	private static Datacenter createDatacenter(String name){

		// Here are the steps needed to create a PowerDatacenter:
		// 1. We need to create a list to store
		//    our machine
		hostList = new ArrayList<PowerHost>();

		// 2. A Machine contains one or more PEs or CPUs/Cores.
		// In this example, it will have only one core.
		List<Pe> peList = new ArrayList<Pe>();
              //  vmlist = new ArrayList<Vm>()
		int mips = 1000;
                //System.out.println("afgkfsdhfdfadsfgujhdfjhgjfh");
                //PowerModelSpecPowerHpProLiantMl110G3PentiumD930 pm1 = new PowerModelSpecPowerHpProLiantMl110G3PentiumD930();
                 PowerModelSpecPowerHpProLiantMl110G4Xeon3040 pm1 = new PowerModelSpecPowerHpProLiantMl110G4Xeon3040();
                PowerModelSpecPowerHpProLiantMl110G5Xeon3075 pm2 = new PowerModelSpecPowerHpProLiantMl110G5Xeon3075();
		PowerModelSpecPowerHpProLiantBl460cG6Xeon5630 pm3 = new PowerModelSpecPowerHpProLiantBl460cG6Xeon5630();
                PowerModelSpecPowerHpProLiantSl390sG7Xeon5649 pm4 = new PowerModelSpecPowerHpProLiantSl390sG7Xeon5649();
		// 3. Create PEs and add these into a list.
		peList.add(new Pe(0, new PeProvisionerSimple(mips))); // need to store Pe id and MIPS Rating

		//4. Create Hosts with its id and list of PEs and add them to the list of machines
		int hostId=0;
		int ram = 2048; //host memory (MB)
		long storage = 1000000; //host storage
		int bw = 10000;
               int em=0;
                for(int d=0;d<2;d++){
                    
                ph[d+em] = new PowerHost(
    				hostId,
    				new RamProvisionerSimple(ram),
    				new BwProvisionerSimple(bw),
    				storage,
    				peList,
    				new VmSchedulerTimeShared(peList),
                                pm1
                                
    			); 
                    hostList.add(ph[d+em]); 		
                 hostId++;
                em++;
                
                // for(int d=2;d<4 ;d++){
                ph[d+em] = new PowerHost(
    				hostId,
    				new RamProvisionerSimple(ram),
    				new BwProvisionerSimple(bw),
    				storage,
    				peList,
    				new VmSchedulerTimeShared(peList),
                                pm2
                                
    			); 
                    hostList.add(ph[d+em]); 		
              
                 hostId++;
                 em++;
                  ph[d+em] = new PowerHost(
    				hostId,
    				new RamProvisionerSimple(ram),
    				new BwProvisionerSimple(bw),
    				storage,
    				peList,
    				new VmSchedulerTimeShared(peList),
                                pm3
                                
    			); 
                    hostList.add(ph[d+em]); 		
              
                 hostId++;
                 em++;
                 ph[d+em] = new PowerHost(
    				hostId,
    				new RamProvisionerSimple(ram),
    				new BwProvisionerSimple(bw),
    				storage,
    				peList,
    				new VmSchedulerTimeShared(peList),
                                pm4
                                
    			); 
                    hostList.add(ph[d+em]); 		
                    //e++;
                 hostId++;
                }             
                 for(int i=0;i<4;i++){
                     Log.printLine("HOLA");
                     Log.printLine(HostList.getById(hostList, i).getId());
                 }
                // broker.submitHostList(hostList);
          
               /* for(int i=0;i<4;i++){
                   Host h= hostList.get(i);
                   Log.printLine(h.getId());
                }*/
                
          
		//List<Pe> peList2 = new ArrayList<Pe>();

		//peList2.add(new Pe(0, new PeProvisionerSimple(mips)));

//		hostId++;
//
//		hostList.add(
//    			new Host(
//    				hostId,
//    				new RamProvisionerSimple(ram),
//    				new BwProvisionerSimple(bw),
//    				storage,
//    				peList2,
//    				new VmSchedulerTimeShared(peList2)
//    			)
//    		); // This is our second machine
//


		// 5. Create a DatacenterCharacteristics object that stores the
		//    properties of a data center: architecture, OS, list of
		//    Machines, allocation policy: time- or space-shared, time zone
		//    and its price (G$/Pe time unit).
		String arch = "x86";      // system architecture
		String os = "Linux";          // operating system
		String vmm = "Xen";
		double time_zone = 10.0;         // time zone this resource located
		double cost = 3.0;              // the cost of using processing in this resource
		double costPerMem = 0.05;		// the cost of using memory in this resource
		double costPerStorage = 0.001;	// the cost of using storage in this resource
		double costPerBw = 0.0;			// the cost of using bw in this resource
		LinkedList<Storage> storageList = new LinkedList<Storage>();	//we are not adding SAN devices by now

        DatacenterCharacteristics characteristics = new DatacenterCharacteristics(
                arch, os, vmm, hostList, time_zone, cost, costPerMem, costPerStorage, costPerBw);

		// 6. Finally, we need to create a PowerDatacenter object.
		Datacenter datacenter = null;
		try {
			datacenter = new Datacenter(name, characteristics, new VmAllocationPolicySimple(hostList), storageList, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
                //System.out.println("afgkfsdhfdfadshghdglhfgadsjafjdhfjadshfjdfhkjdhfjsdhfjsdhffjhfjahdsfjhfjhjahsjfhdhfjsdh;fjadsh;jh;lafhkldhf;ihdsjfghfjgkfjghfgujhdfjhgjfh");
		
	
                        return datacenter;
        }
        
	//We strongly encourage users to develop their own broker policies, to submit vms and cloudlets according
	//to the specific rules of the simulated scenario
	//We strongly encourage users to develop their own broker policies, to submit vms and cloudlets according
	//to the specific rules of the simulated scenario
	private static DatacenterBroker createBroker(){

		DatacenterBroker broker = null;
		try {
			broker = new DatacenterBroker("Broker");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return broker;
	}
	/**
	 * Prints the Cloudlet objects
	 * @param list  list of Cloudlets
	 */
	private static void printCloudletList(List<Cloudlet> list) {
		int size = list.size();
		Cloudlet cloudlet;

		String indent = "    ";
		Log.printLine();
		Log.printLine("========== OUTPUT ==========");
		Log.printLine("Cloudlet ID" + indent + "STATUS" + indent +
				"Data center ID" + indent + "VM ID" + indent + "Time" + indent + "Start Time" + indent + "Finish Time");

		DecimalFormat dft = new DecimalFormat("###.##");
		for (int i = 0; i < size; i++) {
			cloudlet = list.get(i);
			Log.print(indent + cloudlet.getCloudletId() + indent + indent);

			if (cloudlet.getCloudletStatus() == Cloudlet.SUCCESS){
				Log.print("SUCCESS");

				Log.printLine( indent + indent + cloudlet.getResourceId() + indent + indent + indent + cloudlet.getVmId() +
						indent + indent + dft.format(cloudlet.getActualCPUTime()) + indent + indent + dft.format(cloudlet.getStartTime())+
						indent + indent + dft.format(cloudlet.getStartTime()+cloudlet.getDurTime()));
			}
		}

	}
        public static void printRAT(List<Cloudlet> clist){
            //int size=clist.size();
            Cloudlet cloudlet;
            
             map = new HashMap<Integer,List<ResourceAllocationTable>>();
             int len = clist.size();
             Log.printLine("Size of newlist "+len);
             ResourceAllocationTable rar[] = new ResourceAllocationTable[len];
             DecimalFormat dft = new DecimalFormat("###.##");
             Cloudlet cloudlet1;
             //VmList Vmlist;
      
                
                for(int i=0;i<len;i++){
                    cloudlet1 = clist.get(i);
                    rar[i] = new ResourceAllocationTable(i,cloudlet1.getCloudletId(),cloudlet1.getVmId(),dft.format(cloudlet1.getStartTime()),dft.format(cloudlet1.getDurTime()));
                }
                
                for(int i=0;i<len;i++){
                    Log.printLine(rar[i].ot);
                }
                //Log.printLine("SUP");
			   
                 for(int i=0;i<len;i++){
//                  Log.printLine("SUP");
                     
                     cloudlet1=clist.get(i);
                     int vmId=rar[i].rs;
                     Vm vm;
                     vm=vmlist.get(0);
                     //Log.printLine(vm.getId());
                     for(int x=0;x<vmlist.size();x++){
                         vm = vmlist.get(x);
                         Host h=vm.getHost();

                         int hid;
                         //hid=h.getId();
//                         hid = VmList.getById(broker.getVmsCreatedList(), vmId).getHost().getId();
                         //Log.printLine(hid);
                         if(vm.getId()==vmId)
                             break;
                     }
                     
                     Log.printLine(vm.getId());
                     Host h1=vm.getHost();
                     int hid=h1.getId();
                    // int hid= VmList.getById(vmlist, vmId).getHost().getId();
                     Log.printLine(hid);
                   //  int hid=h.getId();
                    // Log.printLine(hid);
            if(map.containsKey(hid)){
                List <ResourceAllocationTable> list1 = map.get(hid);
                list1.add(rar[i]);
                map.put(hid,list1);
               // Log.printLine("SUP");
               // System.out.println(Arrays.asList(list1));  
            }
            else{
                List<ResourceAllocationTable> list2 = new ArrayList<ResourceAllocationTable>();
                list2.add(rar[i]);
                map.put(hid,list2);
                //System.out.println(Arrays.asList(list2));  
            }
        }
                    
         Map<Integer, List<ResourceAllocationTable>> treeMap = new TreeMap<Integer, List<ResourceAllocationTable>>(map);
         printMap(treeMap);
                String indent = "    ";
		Log.printLine();
		Log.printLine("========== OUTPUT ==========");
		Log.printLine(indent + "ar" + indent + indent + "rtr" + indent + indent+
				"rs" + indent + indent +"rst" + indent + indent + "ot");

		//DecimalFormat dft = new DecimalFormat("###.##");
                for (int i = 0; i < len; i++) {
			cloudlet = clist.get(i);
                        //ResourceAllocationTable rar[] = new ResourceAllocationTable[50]; 
			Log.print(indent + i + indent + indent);

			if (cloudlet.getCloudletStatus() == Cloudlet.SUCCESS){
				Log.print(cloudlet.getCloudletId());

				Log.printLine( indent + indent + indent + cloudlet.getVmId() +
						indent + indent + dft.format(cloudlet.getStartTime())+
						indent + indent + dft.format(cloudlet.getDurTime()));
			}
		}
             
                       
        }
 
    public static int countIdleVms(int hid, double t){
        List <ResourceAllocationTable> list = new ArrayList<ResourceAllocationTable>();
        ResourceAllocationTable record;
        list=map.get(hid);
        Host h= HostList.getById(hostList,hid);
       // Log.printLine("Host Id " + h.getId());
        List<Vm> vmslist = h.getVmList();
        Log.printLine(" Size "+list.size());
        for(int i=0;i<vmslist.size();i++){
        //Log.printLine("hjbjhkhvk " + i);
    }
        double ftmax=0;
    for(int i=0;i<vmslist.size();i++){
        for(int j=0;j<list.size();j++){
            record=list.get(j);
            double fti=Double.parseDouble(record.rst)+Double.parseDouble(record.ot);
            if(fti>ftmax){
                ftmax=fti;
            }
        }
    }
        int na=0;
        //Log.printLine(ftmax);
      //  double t=1.0;
        for(int i=0;i<list.size();i++){
            record=list.get(i);
            double ft=Double.parseDouble(record.rst)+Double.parseDouble(record.ot);
            if(ft<t)
                na=na+1;
        }
        return na;
    }
    public static void printAllocPolicy(List <Cloudlet> list){
                map1 = new HashMap<Integer,List<ResourceAllocationPolicy>>();
                
                int len = list.size();
             Log.printLine("Size of newlist "+len);
            rap = new ResourceAllocationPolicy[len];
             DecimalFormat dft = new DecimalFormat("###.##");
             Cloudlet cloudlet1;
             //VmList Vmlist;
      
                
                for(int i=0;i<len;i++){
                    cloudlet1 = list.get(i);
                    int vmId=cloudlet1.getVmId();
                    if(vmId==-1)
                         rap[i] = new ResourceAllocationPolicy(-1,dft.format(cloudlet1.getStartTime()),dft.format(cloudlet1.getDurTime()));
                    else{
                        Vm vm = vmlist.get(vmId);
                        Host h=vm.getHost();
                        rap[i] = new ResourceAllocationPolicy(h.getId(),dft.format(cloudlet1.getStartTime()),dft.format(cloudlet1.getDurTime()));
                    }
                }
                
                for(int i=0;i<len;i++){
                    Log.printLine(rap[i].hid);
                }
                //Log.printLine("SUP");
			   
                 for(int i=0;i<len;i++){
//                  Log.printLine("SUP");
                     
                     cloudlet1=list.get(i);
//                     int vmId=rar[i].rs;
//                     Vm vm;
//                     vm=vmlist.get(0);
//                     //Log.printLine(vm.getId());
//                     for(int x=0;x<vmlist.size();x++){
//                         vm = vmlist.get(x);
//                         Host h=vm.getHost();
//
//                         int hid;
//                         //hid=h.getId();
////                         hid = VmList.getById(broker.getVmsCreatedList(), vmId).getHost().getId();
//                         //Log.printLine(hid);
//                         if(vm.getId()==vmId)
//                             break;
//                     }
                     
//                     Log.printLine(vm.getId());
//                     Host h1=vm.getHost();
//                     int hid=h1.getId();
//                    // int hid= VmList.getById(vmlist, vmId).getHost().getId();
//                     Log.printLine(hid);
//                   //  int hid=h.getId();
//                    // Log.printLine(hid);
            if(map1.containsKey(cloudlet1.getCloudletId())){
                List <ResourceAllocationPolicy> list1 = map1.get(cloudlet1.getCloudletId());
                list1.add(rap[i]);
                map1.put(cloudlet1.getCloudletId(),list1);
               // Log.printLine("SUP");
               // System.out.println(Arrays.asList(list1));  
            }
            else{
                List<ResourceAllocationPolicy> list2 = new ArrayList<ResourceAllocationPolicy>();
                list2.add(rap[i]);
                map1.put(cloudlet1.getCloudletId(),list2);
                //System.out.println(Arrays.asList(list2));  
            }
        }

                 Map<Integer, List<ResourceAllocationPolicy>> treeMap1 = new TreeMap<Integer, List<ResourceAllocationPolicy>>(map1);
         printMap1(treeMap1);
                String indent = "    ";
		Log.printLine();
		Log.printLine("========== OUTPUT ==========");
		Log.printLine(indent + "Host ID" + indent + indent + "Start" + indent + indent+
				"Finish");

		//DecimalFormat dft = new DecimalFormat("###.##");
                for (int i = 0; i < len; i++) {
			cloudlet1 = list.get(i);
                        //ResourceAllocationTable rar[] = new ResourceAllocationTable[50]; 
			//Log.print(indent + i + indent + indent);

			if (cloudlet1.getCloudletStatus() == Cloudlet.SUCCESS){
				//Log.print(cloudlet1.getCloudletId());

				Log.printLine( indent + indent + indent + rap[i].hid +
						indent + indent + rap[i].st+
						indent + indent + rap[i].ft);
			}
		}
                
    }
    
       public static void timeLists(List <Cloudlet> list){
               
            
            DecimalFormat dft = new DecimalFormat("###.##");
            //running timer task as daemon thread
            Timer timer = new Timer(true);
            ExecutorService executor = Executors.newFixedThreadPool(list.size());
            
            //Log.printLine("LIST SIZE " + list.size());
           // MyTimerTask timerTask[] = new MyTimerTask[list.size()];
            for(int i=0;i<list.size();i++){
                Cloudlet cloudlet1;
                double s;
                cloudlet1=list.get(i);
                s=cloudlet1.getStartTime();
                long period;
                double t= 100*(cloudlet1.getStartTime()+cloudlet1.getDurTime());
                period = (long)t;
                Log.printLine(cloudlet1.getStartTime());
                Log.printLine(cloudlet1.getDurTime());
                Runnable timerTask = new MyTimerTask(period,cloudlet1.getCloudletId());
                
                timer = new Timer(true);
                //Thread.run();
                Log.printLine(" PERIOD "+period);
                Log.printLine("Cloudlet "+i);
              //  cloudlet1.setVmId(-1);
                //timerTask.sec=period;
           // timer.scheduleAtFixedRate(timerTask, 0, 100*period);
               executor.execute(timerTask);
            }
           //executor.execute(timerTask[]);
            executor.shutdown();  
            System.out.println("TimerTask started");
            //cancel after sometime
            try {
                Thread.sleep(120000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            timer.cancel();
            System.out.println("TimerTask cancelled");
            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
             while (!executor.isTerminated()) {   }  
  
        System.out.println("Finished all threads"); 
    

     }
       public static <K, V> void printMap(Map<K, V> map) {
      
 
	//BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
              new FileOutputStream("C:\\Users\\INDHU\\Documents\\RAT.txt"), "utf-8"))) {
            
        for (Map.Entry<K, V> entry : map.entrySet()) {
//            System.out.println("\n\nKey : " + entry.getKey()
//				+ " Value : \n" + entry.getValue());
            //writer.newLine();
           // writer.write("Key : " + entry.getKey()
			//	+ " Value : " + entry.getValue());
             writer.write(" " + System.getProperty("line.separator") + entry.getValue());
             
        }
             
        }
        catch(IOException e){
            
        }
 

       
    }
             public static <K, V> void printMap1(Map<K, V> map) {
      
 
	//BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
              new FileOutputStream("C:\\Users\\INDHU\\Documents\\RAP.txt"), "utf-8"))) {
            
        for (Map.Entry<K, V> entry : map.entrySet()) {
//            System.out.println("\n\nKey : " + entry.getKey()
//				+ " Value : \n" + entry.getValue());
            //writer.newLine();
           // writer.write("Key : " + entry.getKey()
			//	+ " Value : " + entry.getValue());
             writer.write(" " + System.getProperty("line.separator") + entry.getValue());
             
        }
             
        }
        catch(IOException e){
            
        }
 

       
    }
     public static void execCloudlets(){
         
     }
}
