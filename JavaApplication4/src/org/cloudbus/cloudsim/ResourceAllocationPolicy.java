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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author INDHU
 */
public class ResourceAllocationPolicy {
    public int hid;
    public String st;
    public String ft;

    public ResourceAllocationPolicy(int id, String s, String e) {
        this.hid = id;
        this.st = s;
        this.ft = e;
        
    }

    @Override
    public String toString() {
        return ("" +  System.getProperty("line.separator") + hid + " " + st + " " + ft + " "); 
    }
    
    
}
