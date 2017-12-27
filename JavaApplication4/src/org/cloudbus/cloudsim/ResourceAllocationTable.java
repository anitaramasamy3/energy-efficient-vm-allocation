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
public class ResourceAllocationTable {
    public int ar;
    public int rtr;
    public List<Integer> rs;
    public Double rst;
    public Double ot;

    public ResourceAllocationTable(int ar, int rtr, List<Integer> rs, Double rst, Double ot) {
        this.ar = ar;
        this.rtr = rtr;
        this.rs = rs;
        this.rst = rst;
        this.ot = ot;
    }

    @Override
    public String toString() {
        return ("" +  System.getProperty("line.separator") + ar + " " + rtr + " " + rs + " " + rst + " " + ot + " ");
    }
    
    
}
