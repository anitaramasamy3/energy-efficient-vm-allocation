/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cloudbus.cloudsim.power.models;

/**
 *
 * @author ADMIN
 */
public class PowerModelSpecPowerHpProLiantBl460cG6Xeon5630 extends PowerModelSpecPower{
    /** The power. */
	private final double[] power = { 192 };

	/*
	 * (non-Javadoc)
	 * @see org.cloudbus.cloudsim.power.models.PowerModelSpecPower#getPowerData(int)
	 */
	@Override
	protected double getPowerData(int index) {
		return power[index];
	}

    
}
