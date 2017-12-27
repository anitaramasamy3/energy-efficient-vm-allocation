
package examples.org.cloudbus.cloudsim.examples;

import static examples.org.cloudbus.cloudsim.examples.CloudEnvv.hostList;
import static examples.org.cloudbus.cloudsim.examples.CloudEnvv.timeLists;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.Time;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;  
import java.util.concurrent.Executors;  
//import java.util.

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.CloudletSchedulerSpaceShared;
import org.cloudbus.cloudsim.CloudletSchedulerTimeShared;
import org.cloudbus.cloudsim.Datacenter;
import org.cloudbus.cloudsim.DatacenterBroker;
import org.cloudbus.cloudsim.DatacenterCharacteristics;
import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.MyTimerTaskFirst;
import org.cloudbus.cloudsim.MyTimerTask;
import org.cloudbus.cloudsim.Pe;
import org.cloudbus.cloudsim.ResourceAllocationPolicy;
import org.cloudbus.cloudsim.ResourceAllocationTable;
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
import org.cloudbus.cloudsim.power.models.PowerModel;
import org.cloudbus.cloudsim.power.models.PowerModelSpecPowerHpProLiantBl460cG6Xeon5630;
import org.cloudbus.cloudsim.power.models.PowerModelSpecPowerHpProLiantSl390sG7Xeon5649;
import org.cloudbus.cloudsim.power.models.PowerModelSpecPowerHpProLiantMl110G4Xeon3040;
import org.cloudbus.cloudsim.power.models.PowerModelSpecPowerHpProLiantMl110G5Xeon3075;
import org.cloudbus.cloudsim.provisioners.BwProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.RamProvisionerSimple;



public class CloudEnv2 {
        static Vm vm[]=new Vm[42];
        static Cloudlet cloudlet[]= new Cloudlet[4000];
        static PowerHost ph[]=new PowerHost[8];
         static List<PowerHost> p;//= null;
	/** The cloudlet list. */
	private static List<Cloudlet> cloudletList;
        private static List<Cloudlet> oldclist;
        private static List<Cloudlet> migOld;
        private static List<Cloudlet> migNew;
        private static List<Cloudlet> currNew,sortedcurr;
        static List<PowerHost> hostList;
        private static List<PowerHost> hostList2;
	/** The vmlist. */
	private static List<Vm> vmlist;
        private static List<Vm> vl;
      
        static DatacenterBroker broker;
        static  List<Integer> up;// = null;
	/**
	 * Creates main() to run this example
     * @param args
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
                  
			//Third step: Create Broker
			broker = createBroker();
			int brokerId = broker.getId();

			//Fourth step: Create one virtual machine
			vmlist = new ArrayList<Vm>();
                        
			//VM description
			int pesNumber = 1;
			hostList2=new ArrayList<PowerHost>();
                        createVmAndHost(brokerId);
			//submit vm list to the broker
			broker.submitVmList(vmlist);
                         List<Vm> vmlist=broker.getVmList();
                        System.out.println("Vm list is being printed");
                        Log.printLine();
                        Log.printLine();
			//printHostList(hostList);
                        printVmList(vmlist);
                        Log.printLine();
                        Log.printLine();
                        System.out.println("before sorting");
                        Log.printLine("host list is being printed");
                  //     sortpm(hostList);
                        //System.out.println("#1");
			printHostList(hostList);
                       
			//Fifth step: Create two Cloudlets
			cloudletList = new ArrayList<Cloudlet>();

			//Cloudlet properties
			int id = 0;
			long length = 40000;
			long fileSize = 300;
			long outputSize = 300;
			UtilizationModel utilizationModel = new UtilizationModelFull();
                        File file = new File("E:\\yoi.txt");
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
                        int wid,tid,nm;
                        double st,dt;
                        int k=0, lenFile=0,tnr=0;
			String line;
			while ((line = bufferedReader.readLine()) != null) {
			    lenFile++;	
                            String fields[];
                                if(line.equals(" "))
                                    continue;
                                else if(line.equals("["))
                                    continue;
                                else if(line.equals("]"))
                                    break;
                                fields =line.split(" ");  
                                wid= Integer.parseInt(fields[0]);
                                tid= Integer.parseInt(fields[1]);
                                st= Double.parseDouble(fields[2]);
                                dt= Double.parseDouble(fields[3]);
                                nm= Integer.parseInt(fields[4]);  
                                  
                                cloudlet[k] = new Cloudlet(id, length, pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
                           
                                cloudlet[k].setUserId(brokerId);
                                cloudlet[k].setWid(wid);
                                cloudlet[k].setTid(tid);
                                cloudlet[k].setStartTime(st);
                                cloudlet[k].setDurTime(dt);
                                cloudlet[k].setNumVM(nm);
                         
                                tnr+=cloudlet[k].getNumVM();
                              
                                cloudletList.add(cloudlet[k]);
                                 
                                id++;
                                k++;
                                
                              
                        }
                        oldclist=new ArrayList<Cloudlet>();

                        for(int i=0;i<cloudletList.size();i++){
                        oldclist.add(i,cloudletList.get(i));
                        }
                        int s0=cloudletList.size();
                        double nst=1.1;
                        System.out.println("size of cloudletlist "+ s0);
                        broker.submitCloudletList(cloudletList);
                       // broker.
                        Log.printLine();
                        Log.printLine("Vms allocation to cloudlets");
                        algo4first(cloudletList,tnr);
                        timeListsFirst(cloudletList);
                    migOld = new ArrayList<Cloudlet>();
                        migOld=createMig(cloudletList);
                        printMig();
                       //migNew=updateOT(migOld,nst);
                        
                      //  vmlist.clear();
                        freeVmAndHost(hostList,vmlist);
                        desort(hostList);
                        broker = createBroker();
			brokerId = broker.getId();
                        createVmAndHost(brokerId);
                        desort(hostList);
			//submit vm list to the broker
                       // broker.
			//broker.submitVmList(vmlist);
                        // vmlist=broker.getVmList();
                        System.out.println("Vm list is being printed");
                        Log.printLine();
//                     System.out.println("bomb Vm " + vmlist.get(5) + " host " + vmlist.get(5).getHost().getId());
                          
                            Log.printLine();
                            printHostList(hostList);
                            
                         // printVmList(vmlist);
                               Log.printLine();
                        file = new File("E:\\yoi1.txt");
			 fileReader = new FileReader(file);
                         bufferedReader = new BufferedReader(fileReader);
			
                  //      int wid,tid,nm;
                    //    double st,dt;
                        k=0; lenFile=0;tnr=0;id=20;
		//	String line;
			cloudletList.clear();
                        while ((line = bufferedReader.readLine()) != null) {
			    lenFile++;	
                            String fields[];
                                if(line.equals(" "))
                                    continue;
                                else if(line.equals("["))
                                    continue;
                                else if(line.equals("]"))
                                    break;
                                fields =line.split(" ");  
                                wid= Integer.parseInt(fields[0]);
                                tid= Integer.parseInt(fields[1]);
                                st= Double.parseDouble(fields[2]);
                                dt= Double.parseDouble(fields[3]);
                                nm= Integer.parseInt(fields[4]);  
                                  
                                cloudlet[k] = new Cloudlet(id, length, pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
                           
                                cloudlet[k].setUserId(brokerId);
                                cloudlet[k].setWid(wid);
                                cloudlet[k].setTid(tid);
                                cloudlet[k].setStartTime(st);
                                cloudlet[k].setDurTime(dt);
                                cloudlet[k].setNumVM(nm);
                         
                                tnr+=cloudlet[k].getNumVM();
                                
                                cloudletList.add(cloudlet[k]);
                                 
                                id++;
                                k++;
                                
                              
                        }
                         s0=cloudletList.size();
                        System.out.println("size of cloudletlist "+ s0);
                        broker.submitCloudletList(cloudletList);
                        Log.printLine();
                        Log.printLine("Vms allocation to cloudlets");
                        migNew=algo4(cloudletList,tnr);
                       // effVmsAssign(migNew);
                      timeLists(migNew);
                       /* for(int i=0;i<hostList.size();i++){
                            PowerHost h=hostList.get(i);
                                pmModeTable(h);
                                }*/
                     //  desort(hostList);
                       //  printPm();
                      /*   allocVmForCloud(cloudletList); 
                         Log.printLine();
                         Log.printLine();
                            System.out.println("RAT is being printed");
                            printRATTable();
                            Log.printLine();
                            Log.printLine();
                            System.out.println("alloc policy for each task request is being printed");
                           /* for(int p=0;p<cloudletList.size();p++){
                            allocPolicy(cloudletList.get(p));
                            }*/
                        /*    allocPolicy();
                            Log.printLine();
                            Log.printLine();
                            
                          /* System.out.println("Idle vm identification");
                           Log.printLine();
                           
                            for(int i=0;i<hostList.size();i++){
                            PowerHost h=hostList.get(i);
                         
                            int na;
                            na = countIdleVms(h,1.0);
                            Log.printLine(" Number of Idle Vms in Host " + h.getId() + " is " +na);
                            }*/
                          /*  Log.printLine();
                            Log.printLine();
                           
			System.out.println("# of vm required  for total task requests :" + tnr);
                        Log.printLine();
                        Log.printLine();
                        Log.printLine("pms are being sorted based on their power and vm quantity ");
                        sortpm(hostList);
                        Log.printLine();
                        Log.printLine();
                        Log.printLine("after sorting");
                        Log.printLine("hostlist is being printed");
                        printHostList(hostList);
                        Log.printLine();
                        Log.printLine("number of employable pms are being calculated");
                       int reqpms1 =findRequiredPm(tnr);
                       Log.printLine();
                       Log.printLine();
                              System.out.println("required pm :" + reqpms1);
                              Log.printLine();
                              Log.printLine();
                              Log.printLine("now the current and new tasks are being combined to better allocate the vm resource");
                              currNew=new ArrayList<Cloudlet>();
                              currNew=combineTask();
                              Log.printLine();
                              Log.printLine();
                              sortedcurr=sortdecVM(currNew);
                              Log.printLine();
                              System.out.println("Idle vm identification");
                           Log.printLine();
                           int sz=sortedcurr.size();
                           int s=1;
                           while(s<=sz){
                            for(int i=0;i<hostList.size();i++){
                            PowerHost h=hostList.get(i);
                         
                            int na;
                            
                            na = countIdleVms(h,1.0);
                            Log.printLine(" Number of Idle Vms in Host " + h.getId() + " is " +na);
                            }
                            s++;
                             } 
                             // printCloudletList(currNew);
                             // Log.printLine();
                              Log.printLine();
                              Log.printLine("tasks are set in motion");
                             // Log.printLine("to"+currNew.size());
                              timeLists(cloudletList);
                              Log.printLine();
                              Log.printLine();
           */
			// Sixth step: Starts the simulation
			CloudSim.startSimulation();
                        


			// Final step: Print results when simulation is over
			List<Cloudlet> newList = broker.getCloudletReceivedList();  
           
			CloudSim.stopSimulation();
                       Log.printLine("cloudletList status check");
                        printCloudletList(newList);
                        Log.printLine();
                        Log.printLine();
			datacenter0.printDebts();
                        Log.printLine();
                        Log.printLine();
			Log.printLine("Project Implementation Successful !");
                        Log.printLine();
		}
		catch (Exception e) {
			e.printStackTrace();
                        Log.printLine();
			Log.printLine("The project implementation has been terminated due to an unexpected error");
                        Log.printLine();
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
                    
                ph[0] = new PowerHost(
    				hostId,
    				new RamProvisionerSimple(ram),
    				new BwProvisionerSimple(bw),
    				storage,
    				peList,
                        
    				new VmSchedulerTimeShared(peList),
                                pm2
                                
    			); 
                    hostList.add(ph[0]); 		
                 hostId++;
                em++;
               
                ph[1] = new PowerHost(
    				hostId,
    				new RamProvisionerSimple(ram),
    				new BwProvisionerSimple(bw),
    				storage,
    				peList,
    				new VmSchedulerTimeShared(peList),
                                pm4
                                
    			); 
                    hostList.add(ph[1]); 		
              
                 hostId++;
                em++;
                  ph[2] = new PowerHost(
    				hostId,
    				new RamProvisionerSimple(ram),
    				new BwProvisionerSimple(bw),
    				storage,
    				peList,
    				new VmSchedulerTimeShared(peList),
                                pm1
                                
    			); 
                    hostList.add(ph[2]); 		
              
                 hostId++;
                em++;
                 ph[3] = new PowerHost(
    				hostId,
    				new RamProvisionerSimple(ram),
    				new BwProvisionerSimple(bw),
    				storage,
    				peList,
    				new VmSchedulerTimeShared(peList),
                                pm3
                                
    			); 
                    hostList.add(ph[3]); 		
                  
                 hostId++;
                }          
                for(int i=0;i<hostList.size();i++){
                  
                   System.out.println(" ## powerhost: "+hostList.get(i).getId()+" PowerModel: "+hostList.get(i).getPowerModel().getClass()+" power :"+hostList.get(i).getPower());
                }
                Log.printLine();
                  Log.printLine();
                System.out.println("Powerhosts are successfully created !! ");
                  Log.printLine();
                    Log.printLine();

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
        
        private static void printRATTable() {
                int size=hostList.size();
                PowerHost h;
          
		Log.printLine("====================== RESOURCE ALLOCATION TABLE ==========================");
               Log.printLine();
               Log.printLine();
               Log.printLine();
               String indent = "    ";
               int vmsize;
               
            
                DecimalFormat dft = new DecimalFormat("###.##");
		for (int i = 0; i < size; i++) {
                    int id;
                    List<Integer> vmarr;
                    vmarr=new ArrayList<Integer>();
                    Double st,dt;
                 
                   h=hostList.get(i);
                   vmsize=h.getVmList().size();
                    Log.printLine("RAT FOR HOST : " + hostList.get(i).getId());
                    Log.printLine();
                    Log.printLine();
                    Log.printLine();
                    Log.printLine(" ## " + indent + "CLOUDLET ID " + indent + "RESOURCE LIST" + indent + indent + indent + indent + indent + "START TIME" + indent + indent + indent + indent + indent + "DURATION TIME" + indent);
			

                                for(int j=0;j<vmsize;j++){
                                    if(h.getVmList().get(j).getCloudlet()== null){
                                        return;
                                    }
                            
                                 Log.printLine(indent + (j+1) + indent + indent + h.getVmList().get(j).getCloudlet().getCloudletId() + indent + indent + indent + h.getVmList().get(j).getCloudlet().geReqdVmId() + indent + indent + indent + indent + indent + indent + indent + indent + dft.format(h.getVmList().get(j).getCloudlet().getStartTime()) + indent + indent + indent + indent + indent + indent + indent + dft.format(h.getVmList().get(j).getCloudlet().getDurTime()) );
                         
                                }
                                Log.printLine();
                                Log.printLine();
                                Log.printLine();
                               
                }
		}
         
           private static void printVmList(List<Vm> list) {
		int size = list.size();
		Vm vm;

		String indent = "    ";
		Log.printLine();
		Log.printLine("========== VM LIST BOUND TO HOST ==========");
		Log.printLine("Vm ID" + indent + "Host ID" + indent);

		for (int i = 0; i < size; i++) {
			vm = list.get(i);
			Log.print(indent + vm.getId() );

			
				Log.printLine(  indent + indent + indent + vm.getHost().getId());
			}
		}
          private static void printHostList(List<PowerHost> list) {
		int size = list.size();
                int vsize;
		PowerHost h;
           
		String indent = "    ";
		Log.printLine();
		Log.printLine("========== HOST LIST AND THEIR RESPECTIVE VM LIST  ==========");
		Log.printLine("Host ID" + indent + "Vm ID list" + indent );

		for (int i = 0; i < size; i++) {
			h=list.get(i);
			
                        vsize=h.getVmList().size();

                                for(int j=0;j<vsize;j++){
                              
                                Log.print(indent + h.getId());
				Log.printLine(  indent + indent + indent + h.getVmList().get(j).getId());
                                }
                }
		}
          private static void printHostLists(List<PowerHost> list) {
		int size = list.size();
                int vsize;
		PowerHost h;
           
		String indent = "    ";
		Log.printLine();
		Log.printLine("========== HOST LIST AND THEIR RESPECTIVE VM LIST  ==========");
		Log.printLine("Host ID" + indent + "Vm ID list" + indent );

		for (int i = 0; i < size; i++) {
			h=list.get(i);
			
                        vsize=h.getVmList().size();

                                for(int j=0;j<vsize;j++){
                              
                                Log.print(indent + h.getId());
				Log.printLine(  indent + indent + indent + h.getVmList().get(j).getId());
                                }
                                
                }
		}
                private static int findRequiredPm(int tnr){
                   List<Vm> v;
                    int u=0,i=0;
                    
                    do{
                       
                    v=hostList.get(i).getVmList();
                
                    u+=v.size();
                    Log.printLine();
                    System.out.println("size of vm requirement"+u);
                    Log.printLine();
                      if(u>=tnr){
                     
                        break;
                      }
                        i++;
                        v.clear();
                    }while(i<hostList.size());
                    if(u<tnr){
                        Log.printLine();
                        System.out.println("pm not available");
                        Log.printLine();}
                    return (i+1);
                }
                private static void allocVmForCloud(List<Cloudlet> list){
                    int size = list.size();
                    int vm1=0;
                    int lim=0;
               
                    
                 //   System.out.println("straw");
                 Log.printLine();
                     System.out.println("vm size:"+vmlist.size());
                     Log.printLine();
		Cloudlet cloudlet;
                
		for (int i = 0; i < size; i++) {
			cloudlet = list.get(i);
                
                     int vmsize=cloudlet.getNumVM();
                     lim+=vmsize;
                     if(lim>vmlist.size()){
                         Log.printLine();
                         System.out.println("vm is full");
                         Log.printLine();
                         Log.printLine("upcoming tasks halted");
                         Log.printLine();
                         Log.printLine();
                         break;
                     }  
                            broker.bindCloudletToVm(cloudlet.getCloudletId(),vmlist.get(vm1).getId());
                            	cloudlet.setVmId(vmlist.get(vm1).getId());
                  
                           vl = new ArrayList<Vm>();
                             
                                for(int j=0;j<vmsize;j++){
                                   
                                    Log.printLine("Sending cloudlet "+ cloudlet.getCloudletId() + " to VM #" + vmlist.get(vm1+j).getId());
                                   vl.add(j,vmlist.get(vm1+j));
                                   vmlist.get(vm1+j).setCloudlet(cloudlet);
                                   Log.printLine("vm "+vmlist.get(vm1+j).getId()+" has now taken cloudlet "+vmlist.get(vm1+j).getCloudlet().getCloudletId());
                                }
                               cloudlet.setReqdVm(vl);
                               Log.printLine();
                                Log.printLine("Cloudlet  "+cloudlet.getCloudletId()+"  bound to vm set  " + cloudlet.geReqdVmId());
                                Log.printLine();
                        vm1+=vmsize;
	                
                }
              
               
                }
           private static void sortpm(List<PowerHost> list) {
		int size = list.size();
                System.out.println("size of host :"+size);
                Log.printLine();
		PowerHost t1,t2;
             
                for (int i = 0; i < size; i++) {
                    for(int j=i+1;j<size;j++){
			
                        if(list.get(i).getPower()>list.get(j).getPower()){
                          //  System.out.println("diff ");
                            t1=list.get(i);
                            t2=list.get(j);
                            list.set(i,t2);
                            list.set(j, t1);     
                          
                        }
                        if(list.get(i).getPower()==list.get(j).getPower()){
                            if(list.get(i).getVmList().size()<list.get(j).getVmList().size()){
                                t1=list.get(i);
                                t2=list.get(j);
                                list.set(i,t2);
                                list.set(j, t1);      
                            }
                                
                        }
			
                    }
                }
                Log.printLine();
                Log.printLine("sorted in ascending order of power and number of vms ");
                Log.printLine();
                for(int i=0;i<size;i++){
                    Log.printLine(" Host id : "+list.get(i).getId()+" host power "+list.get(i).getPower() + "host's number of vms" + list.get(i).getVmList().size());
                }
                Log.printLine();
		}
            public static void allocPolicy(){
                int i=0;
                while(i<cloudletList.size()){
                Cloudlet c=cloudletList.get(i);
                String indent = "    ";
		Log.printLine();
		Log.printLine("========== ALLOCATION POLICY FOR TASK REQUEST  "+c.getCloudletId()+"  ==========");
		Log.printLine("HOST ID" + indent + "START TIME" + indent + "DURATION TIME");
                List<Vm> vmq=c.getReqdVm();
                if(vmq==null)
                    break;
                
                Vm v=vmq.get(0);
                int hid=v.getHost().getId();
                Double st=c.getStartTime();
                Double dt=c.getDurTime();
                Log.printLine(indent + hid + indent + indent + indent + st + indent + indent + indent + dt);
                i++;
                }
            }
            public static int countIdleVms(PowerHost h,double t){
              
               
                int na=0;
                int hid=h.getId();
                Log.printLine();
                Log.printLine("host id "+ hid);
                int vmsize=h.getVmList().size();
                Log.printLine();
                 Log.printLine("number of vms in this host :"+ vmsize);
                 Log.printLine();
            List<Vm> vmslist = h.getVmList();
  
            double ftmax=0;
            for(int i=0;i<vmsize;i++){
                if(vmslist.get(i).getCloudlet()==null)
                    return -1;
         
                Cloudlet c=vmslist.get(i).getCloudlet();
             
              double fti=c.getStartTime()+c.getDurTime();
                if(fti>ftmax){
                    ftmax=fti;
                }
               
            }
           //  Log.printLine("ftmax = " + ftmax);
        
            for(int i=0;i<vmsize;i++){
                Cloudlet c=vmslist.get(i).getCloudlet();
                double ft=c.getStartTime()+c.getDurTime();
                if(ft<t)
                na=na+1;
            }
            return na;
            }
            
             public static void timeLists(List <Cloudlet> list) throws ParseException{
               
            
            DecimalFormat dft = new DecimalFormat("###.##");
            
            Timer timer = new Timer(true);
            ExecutorService executor = Executors.newFixedThreadPool(list.size());
            Log.printLine();
             System.out.println("TimerTask started");
             Log.printLine();
            for(int i=0;i<list.size();i++){
                Cloudlet cloudlet1,cloudlet2;
                double s;
                cloudlet1=list.get(i);
                Time ti;
                long st;
                long period;
                List<Vm> res;
                res=cloudlet1.getReqdVm();
                s=100*(cloudlet1.getStartTime());
                double t= 100*(cloudlet1.getDurTime());
                period = (long)t;
                st=(long)s;
                Runnable timerTask = new MyTimerTask(st,period,cloudlet1.getCloudletId(),res);
                
            timer = new Timer(true);
            
              executor.execute(timerTask);
             /* List<Vm> freedvm = cloudlet1.getReqdVm();
              cloudlet1.setReqdVm(null);
              Log.printLine("cloudlet " + cloudlet1.getCloudletId() + "has freed its resources " + freedvm );
             */
            }
           
            executor.shutdown();  
           
            try {
                Thread.sleep(120000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            timer.cancel();
            
            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
             while (!executor.isTerminated()) {   }  
             Log.printLine();
             System.out.println("TimerTask executed");
             Log.printLine();
        System.out.println("Finished all threads"); 
        Log.printLine();
    

     }
             public static void timeListsFirst(List <Cloudlet> list) throws ParseException{
               
            
            DecimalFormat dft = new DecimalFormat("###.##");
            
            Timer timer = new Timer(true);
            ExecutorService executor = Executors.newFixedThreadPool(list.size());
            Log.printLine();
             System.out.println("TimerTask started");
             Log.printLine();
            for(int i=0;i<list.size();i++){
                Cloudlet cloudlet1,cloudlet2;
                double s;
               // double nextst;
                //nextst = 1.1;
                cloudlet1=list.get(i);
                Time ti;
                long st;
                long dt;
                double nextst= 1.1;
                double ot=cloudlet1.getDurTime();
                long period;
                List<Vm> res;
                res=cloudlet1.getReqdVm();
                s=100*(cloudlet1.getStartTime());
              //  d=100*(cloudlet1.getDurTime());
                double t= 100*(cloudlet1.getStartTime()+cloudlet1.getDurTime());
                period = (long)t;
                st=(long)s;
                Runnable timerTask = new MyTimerTaskFirst(st,period,cloudlet1.getCloudletId(),res,nextst,ot);
                
            timer = new Timer(true);
            
              executor.execute(timerTask);
             /* List<Vm> freedvm = cloudlet1.getReqdVm();
              cloudlet1.setReqdVm(null);
              Log.printLine("cloudlet " + cloudlet1.getCloudletId() + "has freed its resources " + freedvm );
             */
            }
           
            executor.shutdown();  
           
            try {
                Thread.sleep(120000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            timer.cancel();
            
            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
             while (!executor.isTerminated()) {   }  
             Log.printLine();
             System.out.println("TimerTask executed");
             Log.printLine();
        System.out.println("Finished all threads"); 
        Log.printLine();
    

     }
             public static List<Cloudlet> combineTask(List<Cloudlet> old){
                 List<Cloudlet> com;
                 
                 double st;
                 int y=0;
                 Cloudlet c1,c2;
                 st=cloudletList.get(0).getStartTime();
                 Log.printLine("st "+st);
                   com=new ArrayList<Cloudlet>();
                 //  migOld=new ArrayList<Cloudlet>();
                   int c =cloudletList.size();
                   for(int i=0;i<c;i++){
                       com.add(i,cloudletList.get(i));
                   }
                   int o=migOld.size();
                   Log.printLine("old size = "+ o);
                   
                   for(int i=0;i<o;i++){
                       c1=migOld.get(i);
                      // double dt=c1.getDurTime();
                       //Log.printLine("dt "+ dt);
                       //if(dt>=st){
                         // migNew.add(y,c1);
                           com.add(c, c1);
                           c++;
                          // y++;
                          
                            
                       //}
                     
                      // printMig();
                   }
                    Log.printLine();
               Log.printLine("printing the combined cloudlets now");
                Log.printLine();
                int cs=com.size();
                Log.printLine("com size = "+ o);
               for(int j=0;j<cs;j++){
                   c2=com.get(j);
                   Log.printLine("Cloudlet ID :" + c2.getCloudletId() + " Cloudlet start time :" + c2.getStartTime() + " Cloudlet Duration time : " + c2.getDurTime());
               }
                Log.printLine();
                   return com;
             }    		
             public static List<Cloudlet> sortdecVM(List<Cloudlet> cur){
                // List<Cloudlet> sorted=new ArrayList<Cloudlet>();
                 int size=cur.size();
                 Cloudlet c1,c2;
                   for (int i = 0; i < size; i++) {
                       if(cur.get(i).getReqdVm()==null)
                            break;
                    for(int j=i+1;j<size;j++){
			if(cur.get(j).getReqdVm()==null)
                            break;
                        if(cur.get(i).getReqdVm().size()<cur.get(j).getReqdVm().size()){
                          //  System.out.println("diff ");
                            c1=cur.get(i);
                            c2=cur.get(j);
                            cur.set(i,c2);
                            cur.set(j,c1);     
                          
                        }
                        
                    }
                }
                   
                   Log.printLine();
                Log.printLine("sorted in descending order of number of vms ");
                Log.printLine();
                for(int i=0;i<size;i++){
                    if(cur.get(i).getReqdVm()==null)
                        break;
                   // sorted.add(i, c2);
                    Log.printLine(" Cloudlet id : "+cur.get(i).getCloudletId()+" required vms " + cur.get(i).geReqdVmId() + "cloudlet's number of vms" + cur.get(i).getReqdVm().size());
                }
                
                Log.printLine();
                return cur;
             }
             public static List<Cloudlet> algo4(List<Cloudlet> cur,int tnr) throws ParseException{
                  
                            allocVmForCloud(cloudletList); 
                            Log.printLine();
                            Log.printLine();
                            System.out.println("RAT is being printed");
                            printRATTable();
                            Log.printLine();
                            Log.printLine();
                            System.out.println("alloc policy for each task request is being printed");
                            allocPolicy();
                            Log.printLine();
                            Log.printLine();
                            
                           System.out.println("Idle vm identification");
                           Log.printLine();
                           
                            for(int i=0;i<hostList.size();i++){
                            PowerHost h=hostList.get(i);
                         
                            int na;
                            na = countIdleVms(h,3.0);
                            Log.printLine(" Number of Idle Vms in Host " + h.getId() + " is " +na);
                            }
                            Log.printLine();
                            Log.printLine();
                           
			System.out.println("# of vm required  for total task requests :" + tnr);
                        Log.printLine();
                        Log.printLine("ruler");
                         printPm();
                        Log.printLine();
                        Log.printLine("pms are being sorted based on their power and vm quantity ");
                        
                        sortpm(hostList);
                        for(int i=0;i<hostList.size();i++){
                            hostList2.add(i,hostList.get(i));
                        }
                        Log.printLine();
                        Log.printLine();
                        Log.printLine("after sorting");
                        Log.printLine("hostlist is being printed");
                        printHostList(hostList);
                                Log.printLine("2is");
                        printHostList(hostList2);
                        Log.printLine();
                        Log.printLine("number of employable pms are being calculated");
                  //     int reqpms1 =findRequiredPm(tnr);
                       Log.printLine();
                       printHostList(hostList);
                       Log.printLine();
                     //  reqpms1--;
                         //     System.out.println("required pm :" + reqpms1);
                              Log.printLine();
                              Log.printLine();
                              Log.printLine("now the current and new tasks are being combined to better allocate the vm resource");
                              currNew=new ArrayList<Cloudlet>();
                              currNew=combineTask(oldclist);
                              Log.printLine();
                              printHostList(hostList);
                              Log.printLine();
                              sortedcurr=sortdecVM(currNew);
                              Log.printLine();
                              printHostList(hostList);
                             // System.out.println("Idle vm identification");
                           Log.printLine();
                           int sz=sortedcurr.size();
                           Log.printLine("migNew size "+sz);
                           int s=1;
                         /*  while(s<=sz){
                            for(int i=0;i<hostList.size();i++){
                            PowerHost h=hostList.get(i);
                         
                            int na;
                            
                            na = countIdleVms(h,1.0);
                            Log.printLine(" Number of Idle Vms in Host " + h.getId() + " is " +na);
                            }
                            s++;
                             } */
                             // printCloudletList(currNew);
                             // Log.printLine();
                              Log.printLine();
                              Log.printLine("tasks are set in motion");
                             // Log.printLine("to"+currNew.size());
                          //    timeLists(cloudletList);
                              Log.printLine();
                              Log.printLine("rulers");
                               
                              Log.printLine();
                              return sortedcurr;
             }
             public static void algo4first(List<Cloudlet> cur,int tnr) throws ParseException{
                          
                            allocVmForCloud(cloudletList); 
                            Log.printLine();
                            Log.printLine();
                            System.out.println("RAT is being printed");
                            printRATTable();
                            Log.printLine();
                            Log.printLine();
                            System.out.println("alloc policy for each task request is being printed");
                            allocPolicy();
                            Log.printLine();
                            Log.printLine();
                            
                          System.out.println("Idle vm identification");
                           Log.printLine();
                           
                            for(int i=0;i<hostList.size();i++){
                            PowerHost h=hostList.get(i);
                         
                            int na;
                            na = countIdleVms(h,1.0);
                            Log.printLine(" Number of Idle Vms in Host " + h.getId() + " is " +na);
                            }
                            Log.printLine();
                            Log.printLine();
                           
			System.out.println("# of vm required  for total task requests :" + tnr);
                        Log.printLine();
                        printPm();
                        Log.printLine();
                        Log.printLine("pms are being sorted based on their power and vm quantity ");
                        sortpm(hostList);
                        Log.printLine();
                        Log.printLine();
                        Log.printLine("after sorting");
                        Log.printLine("hostlist is being printed");
                        printHostList(hostList);
                        Log.printLine();
                        Log.printLine("number of employable pms are being calculated");
                       int reqpms1 =findRequiredPm(tnr);
                       Log.printLine();
                       Log.printLine();
                     //  reqpms1--;
                              System.out.println("required pm :" + reqpms1);
                              Log.printLine();
                              Log.printLine();
                             // Log.printLine("now the current and new tasks are being combined to better allocate the vm resource");
                            //  currNew=new ArrayList<Cloudlet>();
                              //currNew=combineTask();
                              Log.printLine();
                              Log.printLine();
                              //sortedcurr=sortdecVM(currNew);
                            /*  Log.printLine();
                              System.out.println("Idle vm identification");
                           Log.printLine();
                           int sz=sortedcurr.size();
                           int s=1;
                           while(s<=sz){
                            for(int i=0;i<hostList.size();i++){
                            PowerHost h=hostList.get(i);
                         
                            int na;
                            
                            na = countIdleVms(h,1.0);
                            Log.printLine(" Number of Idle Vms in Host " + h.getId() + " is " +na);
                            }
                            s++;
                             }*/ 
                             // printCloudletList(currNew);
                             // Log.printLine();
                              Log.printLine();
                              Log.printLine("tasks are set in motion");
                             // Log.printLine("to"+currNew.size());
                              //timeLists(cloudletList);
                              Log.printLine();
                              Log.printLine();
           
             }
            public static void createVmAndHost(int brokerId){
                vmlist.clear();
                 int vmid = 0;
			int mips = 5;
			long size = 10000; //image size (MB)
			int ram = 8; //vm memory (MB)create
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
                          for(int p=0;p<hostList.size();p++){
                           System.out.println("host ID "+hostList.get(p).getId()+" powermodel :"+hostList.get(p).getPowerModel().toString());
                           System.out.println("host has "+hostList.get(p).getVmList().size()+" number of vms");
                        }
                          printVmList(vmlist);
                        
             }
           public static void freeVmAndHost(List<PowerHost> hostList,List<Vm> vmlist){
                int h=hostList.size();
                PowerHost ho;
                Log.printLine("freeing host size : "+ h);
                for(int i=0;i<h;i++){
                    ho=hostList.get(i);
                    ho.vmDestroyAll();
                }
                int v=vmlist.size();
                Vm vo;
                 Log.printLine("freeing vm size : "+ v);
                 for(int i=0;i<v;i++){
                    vo=vmlist.get(i);
                    vo.setHost(null);
                }
            }
           public static void desort(List<PowerHost> list){
               Log.printLine("desort");
               int size=list.size();
               PowerHost t1,t2;
             
                for (int i = 0; i < size; i++) {
                    for(int j=i+1;j<size;j++){
			
                        if(list.get(i).getId()>list.get(j).getId()){
                          //  System.out.println("diff ");
                            t1=list.get(i);
                            t2=list.get(j);
                            list.set(i,t2);
                            list.set(j, t1);     
                          
                        }
                      
			
                    }
                }
              
                for(int i=0;i<size;i++){
                    Log.printLine(" Host id : "+list.get(i).getId()+" host id "+list.get(i).getId() + "host's number of vms" + list.get(i).getVmList().size());
                }
                Log.printLine();

           }
          public static List<Cloudlet> updateOT(List<Cloudlet> m,double nst){
              List<Cloudlet> nc;
              Cloudlet n;
              Double ot;
              nc=new ArrayList<Cloudlet>();
              int msize=m.size();
              for(int i=0;i<msize;i++){
               // n=m.get(i);
                ot=m.get(i).getStartTime()+m.get(i).getDurTime();
                m.get(i).setDurTime(ot-nst);
             //   nc.add(i, n);
                Log.printLine("Cloudlet " + m.get(i).getCloudletId() + " has its occupied time updated to " + m.get(i).getDurTime());
              }
              
              return m;
          }
          public static void printMig(){
              int s=migOld.size();
              Log.printLine("printing migrating cloudlets ...... ");
              for(int i=0;i<s;i++){
                  Log.printLine("Cloudlet" + migOld.get(i).getCloudletId() + " had ot " + migOld.get(i).getDurTime());
              }
          } 
          public static List<Cloudlet> createMig(List<Cloudlet> clist){
              Log.printLine("Creating migrating list ....");
              int s=clist.size();
              double dt,nst=1.1;
              Cloudlet c;
              int j=0;
              for(int i=0;i<s;i++){
                  c=clist.get(i);
                  dt=c.getDurTime();
                  if(dt>=nst){
                      c.setDurTime(dt-nst);
                      migOld.add(j, c);
                      j++;
                  }
                      
              }
              for(int y=0;y<migOld.size();y++){
                  Log.printLine("cloudlet " + migOld.get(y).getCloudletId() + "is ready to migrate and has ot " + migOld.get(y).getDurTime());
              }
              return migOld;
          }
          public static void pmModeTable(PowerHost host){
              Log.printLine("PM MODE FOR HOST " + host.getId());
                      
        //      Boolean idle=true,active=false;
              int vs=host.getVmList().size();
              Vm v;
              List<Double> atlist;
              List<Double> stlist;
              atlist=new ArrayList<Double>();
              stlist=new ArrayList<Double>();
              double atime;
              for(int i=0;i<vs;i++){
                  v=host.getVmList().get(i);
                  atime=v.getCloudlet().getDurTime();//- v.getCloudlet().getStartTime();
                  Log.printLine(" active time of vm " + v.getId()+" is " + atime );
                  atlist.add(i, atime);
                  stlist.add(i,v.getCloudlet().getStartTime());
              }
              Log.printLine("atlist size " + atlist.size());
              host.setActiveMode(stlist,atlist);
              printPm();
          }
          public static void printPm(){
              int size=hostList.size();
                PowerHost h;
        //  Log.printLine("PM MODE FOR HOST " + h.getId());
                      
        //      Boolean idle=true,active=false;
            
		Log.printLine("====================== PM MODE  TABLE ==========================");
               Log.printLine();
               Log.printLine();
               Log.printLine();
               String indent = "    ";
               int vmsize;
               
            
                DecimalFormat dft = new DecimalFormat("###.##");
		for (int i = 0; i < size; i++) {
                    h=hostList.get(i);
                  //  Log.printLine("PM MODE FOR HOST " + h.getId());
                    int vs=h.getVmList().size();
              Vm v;
              List<Double> atlist;
              List<Double> stlist;
              atlist=new ArrayList<Double>();
              stlist=new ArrayList<Double>();
              double atime;
              for(int i1=0;i1<vs;i1++){
                  v=h.getVmList().get(i1);
                  if(v.getCloudlet()!=null){
                  atime=v.getCloudlet().getDurTime();//- v.getCloudlet().getStartTime();
                  Log.printLine(" active time of vm " + v.getId()+" is " + atime );
                  atlist.add(i1, atime);
                  stlist.add(i1,v.getCloudlet().getStartTime());
              }}
             // Log.printLine("atlist size " + atlist.size());
              h.setActiveMode(stlist,atlist);
                    int id;
                    List<Integer> vmarr;
                    vmarr=new ArrayList<Integer>();
                    Double st,dt;
                 
                   h=hostList.get(i);
                   vmsize=h.getVmList().size();
                    Log.printLine("PM MODE FOR HOST : " + hostList.get(i).getId());
                    Log.printLine();
                    Log.printLine();
                    Log.printLine();
                    Log.printLine(" ## " + indent + indent + indent + indent + "IDLE MODE " + indent + "ACTIVE MODE ON" + indent + indent + indent + indent + indent + "ACTIVE MODE OFF" + indent + indent + indent + indent + indent + "IDLE MODE" + indent);
			

                                for(int j=0;j<vmsize;j++){
                                    if(h.getVmList().get(j).getCloudlet()== null){
                                        return;
                                    }
                            
                                 Log.printLine(indent +(j+1) + indent + indent +indent + indent +indent + " ON " + indent + indent + indent + indent +  dft.format(h.getVmList().get(j).getCloudlet().getStartTime()) + indent + indent + indent + indent + indent +indent + indent + indent + dft.format(h.getVmList().get(j).getCloudlet().getStartTime()+h.getVmList().get(j).getCloudlet().getDurTime()) + indent + indent +indent + indent +indent + indent + indent + " ON " );
                         
                                }
                                Log.printLine();
                                Log.printLine();
                                Log.printLine();
                               
                }
          }
          public static void  effVmAssign(List<Cloudlet> m){
              System.out.println("efficient vm allocation");
              
            //  allocVmForCloud(cloudletList); 
              //desort(hostList);
              //sortpm(hostList);
                      Log.printLine(hostList2.size());
                      for(int i=0;i<hostList2.size();i++){
                         Log.printLine("no of vms : " + hostList2.get(i).getVmList().size()); 
                      }
                      Log.printLine();
              printHostList(hostList2);
           //   printCloudletList(m);
           int ms=m.size();
           int mvm=0,mv=0,vs=0;
           int k=0;
           for(int i=0;i<ms;i++){
               Cloudlet c=m.get(i);
               mv+=c.getNumVM();
           }
           vs=vmlist.size();
           if(mv>vs){
               deployMoreVm(2);
           }
           PowerHost h;
          //Cloudlet c;
           for(int i=0;i<ms;i++){
               Cloudlet c=m.get(i);
               mvm=c.getNumVM();
               for(int j=0;j<hostList.size();j++){
                   h=hostList.get(j);
                   int hs=h.getVmList().size();
                   if(hs==mvm){
                      
                            broker.bindCloudletToVm(c.getCloudletId(),h.getVmList().get(k).getId());
                            	c.setVmId(h.getVmList().get(k).getId());
                                k=k+mvm;
                   }
                   else if(hs!=mvm){
                   hs++;
                   }
                   else if(hs==mvm){
                       broker.bindCloudletToVm(c.getCloudletId(),h.getVmList().get(k).getId());
                            	c.setVmId(h.getVmList().get(k).getId());
                                k=k+mvm;
                   }
                   
               }
           }
           
          }
          public static void deployMoreVm(int fac){
               //int vmid = 0;
               broker = createBroker();
			int brokerId = broker.getId();
                 int hs=hostList.size();
              int vs=vmlist.size();
              int vmid=vs;
			int mips = 5;
			long size = 10000; //image size (MB)
			int ram = 8; //vm memory (MB)create
			long bw = 100;
			int pesNumber = 1; //number of cpus
			String vmm = "Xen"; //VMM name
                        String uid;
            
              int vmcount,newvc;
              PowerHost h;
              
              for(int i=0;i<hs;i++){
                  
                  h=hostList.get(i);
                  vmcount=h.getVmList().size();
                  newvc=fac+vmcount;
                  while(vs<newvc){
                  vm[vs]=new Vm(vmid, brokerId, mips, pesNumber, ram, bw, size, vmm, new CloudletSchedulerSpaceShared());
                  vmid++;
                  vmlist.add(vm[vs]);
                   uid=vm[vs].getUid();
                   Log.printLine("Vm Uid: "+ uid);
                    vm[vs].setHost(hostList.get(i));
                               Log.printLine("vm  ##"+vm[i].getId()+"is set to the host ##"+vm[i].getHost().getId());
                               hostList.get(i).vmCreate(vm[vs]);
                               vs++;
              }
              }
          }
         /* public static void effVmsAssign(List<Cloudlet> m){
              int hsize=hostList.size();
              int msize=m.size();
              Cloudlet c;
             
                  c=m.get(0);
                  Log.printLine("cloudlet "+ c.getCloudletId()+" has vm requirement : " + c.getNumVM()+"is assigned to "+ hostList.get(0)+" with vm capacity " + hostList.get(0).getVmList().size());
                  c=m.get(1);
                  Log.printLine("cloudlet "+ c.getCloudletId()+" has vm requirement : " + c.getNumVM()+"is assigned to "+ hostList.get(2)+" with vm capacity " + hostList.get(2).getVmList().size());
                   c=m.get(2);
                  Log.printLine("cloudlet "+ c.getCloudletId()+" has vm requirement : " + c.getNumVM()+"is assigned to "+ hostList.get(4)+" with vm capacity " + hostList.get(4).getVmList().size());
                   c=m.get(3);
                  Log.printLine("cloudlet "+ c.getCloudletId()+" has vm requirement : " + c.getNumVM()+"is assigned to "+ hostList.get(5)+" with vm capacity " + hostList.get(5).getVmList().size());
                  c=m.get(4);
                  Log.printLine("cloudlet "+ c.getCloudletId()+" has vm requirement : " + c.getNumVM()+"is assigned to "+ hostList.get(2)+" with vm capacity " + hostList.get(2).getVmList().size());
                  c=m.get(5);
                  Log.printLine("cloudlet "+ c.getCloudletId()+" has vm requirement : " + c.getNumVM()+"is assigned to "+ hostList.get(6)+" with vm capacity " + hostList.get(6).getVmList().size());
                   c=m.get(6);
                  Log.printLine("cloudlet "+ c.getCloudletId()+" has vm requirement : " + c.getNumVM()+"is assigned to "+ hostList.get(6)+" with vm capacity " + hostList.get(6).getVmList().size());
                   c=m.get(7);
                  Log.printLine("cloudlet "+ c.getCloudletId()+" has vm requirement : " + c.getNumVM()+"is assigned to "+ hostList.get(3)+" with vm capacity " + hostList.get(3).getVmList().size());
                   c=m.get(8);
                  Log.printLine("cloudlet "+ c.getCloudletId()+" has vm requirement : " + c.getNumVM()+"is assigned to "+ hostList.get(5)+" with vm capacity " + hostList.get(5).getVmList().size());
         
             
          }*/
          
}
