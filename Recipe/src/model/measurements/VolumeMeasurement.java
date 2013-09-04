package model.measurements;

public class VolumeMeasurement extends Measurement {

	private static final long serialVersionUID = -3238874991039752117L;


	public VolumeMeasurement(double amount, VolumeUnits unit)
	{
		super(amount * unit.conversion(), MeasurementType.VOLUME, unit);
	}

	
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(this.getPreferredAmount());
		sb.append(" ");
		sb.append(this.getPreferredUnit());
		return sb.toString();
	}
	
}
