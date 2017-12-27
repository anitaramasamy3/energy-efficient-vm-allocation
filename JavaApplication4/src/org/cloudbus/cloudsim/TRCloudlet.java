
package org.cloudbus.cloudsim;

/**
 *
 * @author INDHU
 */
public class TRCloudlet extends Cloudlet {
    private double startTime;
    private int wid;
    private int tid;
    private double durTime;
    private int numVM;
    private int status;
    
    public static final int CREATED = 0;

	/** The Cloudlet has been assigned to a CloudResource object as planned. */
	public static final int READY = 1;

	/** The Cloudlet has moved to a Cloud node. */
	public static final int QUEUED = 2;

	/** The Cloudlet is in execution in a Cloud node. */
	public static final int INEXEC = 3;

	/** The Cloudlet has been executed successfully. */
	public static final int SUCCESS = 4;

	/** The Cloudlet is failed. */
	public static final int FAILED = 5;

	/** The Cloudlet has been canceled. */
	public static final int CANCELED = 6;

	/**
	 * The Cloudlet has been paused. It can be resumed by changing the status into <tt>RESUMED</tt>.
	 */
	public static final int PAUSED = 7;

	/** The Cloudlet has been resumed from <tt>PAUSED</tt> state. */
	public static final int RESUMED = 8;
    
    public TRCloudlet(int cloudletId, long cloudletLength, int pesNumber, long cloudletFileSize, long cloudletOutputSize, UtilizationModel utilizationModelCpu, UtilizationModel utilizationModelRam, UtilizationModel utilizationModelBw) {
        super(cloudletId, cloudletLength, pesNumber, cloudletFileSize, cloudletOutputSize, utilizationModelCpu, utilizationModelRam, utilizationModelBw);
        //status=4;
    }

     public int getNumVM() {
        return numVM;
    }

    public void setStartTime(double st) {
        this.startTime = st;
    }

    public double getStartTime() {
        return startTime;
    }

    public void setWid(int wid1) {
        this.wid = wid1;
    }
    
    public int getCloudletStatus() {
		return status;
	}


    public int getWid() {
        return wid;
    }
    
    public void setTid(int tid1) {
        this.tid = tid1;
    }

    
    public void setDurTime(double dt) {
        this.durTime = dt;
    }

    public double getDurTime() {
        return durTime;
    }
    
    public void setNumVM(int num) {
        this.numVM = num;
    }

   
}
