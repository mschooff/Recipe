package model.measurements;

public class VolumeMeasurement extends Measurement {

	public VolumeMeasurement(double amount) {
		super(amount);
	}
	
	public VolumeMeasurement(double amount, VolumeUnits unit)
	{
		super(amount * unit.toTeaspoons());
	}
	
	public double getAmount(VolumeUnits unit)
	{
		return getAmount() / unit.toTeaspoons();
	}
}
