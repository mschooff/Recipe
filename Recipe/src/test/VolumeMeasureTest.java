package test;

import static org.junit.Assert.*;

import model.measurements.VolumeMeasurement;
import model.measurements.VolumeUnits;

import org.junit.Test;

public class VolumeMeasureTest {

	@Test
	public void test() {
		VolumeMeasurement gallon = new VolumeMeasurement(1, VolumeUnits.GALLON);
		assertTrue(gallon.getAmount(VolumeUnits.GALLON) == 1);
		assertTrue(gallon.getAmount(VolumeUnits.CUP) == 16);
		assertTrue(gallon.getAmount(VolumeUnits.PINCH) == 12288);
	}

}
