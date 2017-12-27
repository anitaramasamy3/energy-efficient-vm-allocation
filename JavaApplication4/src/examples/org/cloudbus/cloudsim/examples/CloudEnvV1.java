
package examples.org.cloudbus.cloudsim.examples;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.CloudletSchedulerSpaceShared;
import org.cloudbus.cloudsim.CloudletSchedulerTimeShared;
import org.cloudbus.cloudsim.Datacenter;
import org.cloudbus.cloudsim.DatacenterBroker;
import org.cloudbus.cloudsim.DatacenterCharacteristics;
import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Pe;
import org.cloudbus.cloudsim.Storage;
import org.cloudbus.cloudsim.UtilizationModel;
import org.cloudbus.cloudsim.UtilizationModelFull;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.VmAllocationPolicySimple;
import org.cloudbus.cloudsim.VmSchedulerTimeShared;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.power.models.PowerModelSpecPowerHpProLiantMl110G4Xeon3040;
import org.cloudbus.cloudsim.provisioners.BwProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.RamProvisionerSimple;
import org.cloudbus.cloudsim.power.PowerHost;

/**
 * A simple example showing how to create
 * a datacenter with two hosts and run two
 * cloudlets on it. The cloudlets run in
 * VMs with different MIPS requirements.
 * The cloudlets will take different time
 * to complete the execution depending on
 * the requested VM performance.
 */
public class CloudEnvV1 {
        static Vm vm[]=new Vm[30];
        static Cloudlet cloudlet[]= new Cloudlet[4000];
	/** The cloudlet list. */
	private static List<Cloudlet> cloudletList;
        static List<PowerHost> hostList;
	/** The vmlist. */
	private static List<Vm> vmlist;

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

			//Third step: Create Broker
			DatacenterBroker broker = createBroker();
			int brokerId = broker.getId();

			//Fourth step: Create one virtual machine
			vmlist = new ArrayList<Vm>();

			//VM description
			int vmid = 0;
			int mips = 0;
			long size = 100; //image size (MB)
			int ram = 128; //vm memory (MB)
			long bw = 100;
			int pesNumber = 1; //number of cpus
			String vmm = "Xen"; //VMM name
                        
                        
                        for(int i=0;i<30;i++){
                            vm[i]=new Vm(vmid, brokerId, mips, pesNumber, ram, bw, size, vmm, new CloudletSchedulerSpaceShared());
                            vmid++;
                            vmlist.add(vm[i]);
                            System.out.println("Vm Id:" + vmid);
                         //    Log.printLine("Vm Id: "+ vmid);
                            
                        }
                       // int t=vmlist.size();
                        //System.out.println("int value yo="+t);
                        
			//submit vm list to the broker
			broker.submitVmList(vmlist);
                       
			//Fifth step: Create two Cloudlets
			cloudletList = new ArrayList<Cloudlet>();

			//Cloudlet properties
			int id = 0;
			long length = 4000;
			long fileSize = 300;
			long outputSize = 300;
			UtilizationModel utilizationModel = new UtilizationModelFull();
                        
                       File file = new File("C:\\Users\\INDHU\\Documents\\Output1.txt");
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			//StringBuffer stringBuffer = new StringBuffer();
                        int wid,tid,nm;
                        double st,dt;
                        int k=0;
			String line;
			while ((line = bufferedReader.readLine()) != null) {
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
                          //for(int k= 0;k<3982;k++){      
                                cloudlet[k] = new Cloudlet(id, length, pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
                            //    int stt=cloudlet[k].getStatus();
                               // Log.printLine("status yo ="+stt);
                               cloudlet[k].setUserId(brokerId);
                             /*   cloudlet[k].setWid(wid);
                                cloudlet[k].setTid(tid);
                                cloudlet[k].setStartTime(st);
                                cloudlet[k].setDurTime(dt);
                                cloudlet[k].setNumVM(nm);*/
                           /*    wid= cloudlet[k].getWid();
                                tid=cloudlet[k].getTid();
                                st=cloudlet[k].getStartTime();
                                dt=cloudlet[k].getDurTime();
                                nm=cloudlet[k].getNumVM();
                              
                                System.out.println("wid:" + wid+"tid:" + tid+"st:" + st+"dt:" + dt+"nm:" + nm);*/
                                //Log.printLine("HI");
                                cloudletList.add(cloudlet[k]);
                                  System.out.println("CLOUDLET Id:" + id);
                                id++;
                                k++;
                               // break;
                        }
			/*Boolean c=cloudletList.isEmpty();
                        System.out.println("bool for c yo :"+c);
                        int ct=cloudletList.size();
                        System.out.println("int for cloudlet yo :"+ct);*/
			int numCloudlets=k;
			

			//submit cloudlet list to the broker
                        broker.submitCloudletList(cloudletList);
			
                      /*   List<Cloudlet> newList2= broker.getCloudletSubmittedList();
                         int size2 = newList2.size();
                         Log.printLine("boooooooooooooo cloudsub size="+size2);*/
                   //     broker.

                      Random rand = new Random();
			//bind the cloudlets to the vms. This way, the broker
			// will submit the bound cloudlets only to the specific VM
                        for( int i=0;i<numCloudlets;i++){
                            int min=0,max=29;
                            int randomNum = rand.nextInt((max - min) + 1) + min;
                            broker.bindCloudletToVm(cloudlet[i].getCloudletId(),vm[randomNum].getId());
                           
			    //broker.bindCloudletToVm(cloudlet2.getCloudletId(),vm[i].getId());
                        }
			

			// Sixth step: Starts the simulation
			CloudSim.startSimulation();

                        Boolean c=cloudletList.isEmpty();
                        System.out.println("bool for c yo :"+c);
                        int ct=cloudletList.size();
                        System.out.println("int for cloudlet yo :"+ct);
			// Final step: Print results when simulation is over
			List<Cloudlet> newList = broker.getCloudletReceivedList();
                       // List<Cloudlet> newList = broker.
                        int size1 = newList.size();
                           Log.printLine("boooooooooooooo size="+size1);
		
                           List<Vm> newList1 = broker.getVmsCreatedList();
                        size1 = newList1.size();
                           Log.printLine("boooooooooooooo VM size="+size1);
                         

                   
                       
                       /*  for(Vm vmVar1:vmlist){
                               int idVm = vmVar1.getId();
                               Log.printLine("Vm Id: " + idVm);
                        }*/
			
			   int j;
                        VmAllocationPolicySimple vmPolicy = new VmAllocationPolicySimple(hostList);
                        for(j=0;j<30;j++){
                            vmPolicy.allocateHostForVm(vm[j]);
                             //Log.printLine("Allocated");
                            //List<Vm> vmFromHost = new ArrayList<Vm>();
                        }
                        //vmPolicy.allocateHostForVm(vm1);
                        List<Vm> vmFromHost = new ArrayList<Vm>();
                        //Host h=vmPolicy.getHost(vm1);
                        for(PowerHost host: hostList){
                            vmFromHost = host.getVmList();
                            int y=vmFromHost.size();
                            System.out.println("size of vm list="+y);
                         y=y/2;
                          vmFromHost=vmFromHost.subList(0,y);
                            Log.printLine("Host id: "+host.getId());
                 //           List<Vm> listDistinct = vmFromHost.stream().distinct().collect(Collectors.toList());
                            for(Vm vmVar:vmFromHost){
                               int idVm1 = vmVar.getId();
                               Log.printLine("Vm Id: "+ idVm1);
                            
                            }
                        }
                           CloudSim.stopSimulation();

        	printCloudletList(newList);

			//Print the debt of each user to each datacenter
			datacenter0.printDebts();
                        
                        // Final step: Print results when simulation is over
                   //     printCloudletList(newList);
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

		int mips = 1000;

		// 3. Create PEs and add these into a list.
		peList.add(new Pe(0, new PeProvisionerSimple(mips))); // need to store Pe id and MIPS Rating
               int pp= peList.size();
               for(int p=0;p<pp;p++){
                   Pe pop=peList.get(p);
                   System.out.println("anita"+pop);
                   
               }
		//4. Create Hosts with its id and list of PEs and add them to the list of machines
		int hostId=0;
		int ram = 2048; //host memory (MB)
		long storage = 1000000; //host storage
		int bw = 100000;
                PowerModelSpecPowerHpProLiantMl110G4Xeon3040 pm = new PowerModelSpecPowerHpProLiantMl110G4Xeon3040();
		for(int d=0;d<5;d++){
                    
                
                 hostList.add(
    			new PowerHost(
    				hostId,
    				new RamProvisionerSimple(ram),
    				new BwProvisionerSimple(bw),
    				storage,
    				peList,
    				new VmSchedulerTimeShared(peList),
                                pm
    			)
    		 ); 
                 hostId++;
                }
		//create another machine in the Data center
		/*List<Pe> peList2 = new ArrayList<Pe>();

		peList2.add(new Pe(0, new PeProvisionerSimple(mips)));

		hostId++;

		hostList.add(
    			new PowerHost(
    				hostId,
    				new RamProvisionerSimple(ram),
    				new BwProvisionerSimple(bw),
    				storage,
    				peList2,
    				new VmSchedulerTimeShared(peList2),
                                pm
    			)
    		); // This is our second machine
                */


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
	public static void printCloudletList( List <Cloudlet> list) {
		int size = list.size();
                Log.printLine("size="+size);
                    
		Cloudlet cloudlet1;

		String indent = "    ";
		Log.printLine();
		Log.printLine("========== OUTPUT ==========");
		Log.printLine("Cloudlet ID" + indent + "STATUS" + indent +
				"Data center ID" + indent + "VM ID" + indent + "Time" + indent + "Start Time" + indent + "Finish Time");

		DecimalFormat dft = new DecimalFormat("###.##");
		for (int i = 0; i < size; i++) {
			cloudlet1 = list.get(i);
			Log.printLine(indent + cloudlet1.getCloudletId() + indent + indent);
                        Log.print("HI");
                        Log.printLine(cloudlet1.getCloudletStatus());
			if (cloudlet1.getCloudletStatus() == Cloudlet.CREATED){
				Log.print("SUCCESS");

				Log.printLine( indent + indent + cloudlet1.getResourceId() + indent + indent + indent + cloudlet1.getVmId() +
						indent + indent + dft.format(cloudlet1.getActualCPUTime()) + indent + indent + dft.format(cloudlet1.getExecStartTime())+
						indent + indent + dft.format(cloudlet1.getFinishTime()));
			}
		}

	}
}
