package model.measurements;

public class VolumeMeasurement extends Measurement {

	public VolumeMeasurement(double amount) {
		super(amount, MeasurementType.VOLUME);
	}
	
	public VolumeMeasurement(double amount, VolumeUnits unit)
	{
		super(amount * unit.toTeaspoons(), MeasurementType.VOLUME);
	}
	
	public double getAmount(VolumeUnits unit)
	{
		return getAmount() / unit.toTeaspoons();
	}
}
