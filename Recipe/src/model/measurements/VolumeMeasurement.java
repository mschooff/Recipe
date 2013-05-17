package model.measurements;

public class VolumeMeasurement extends Measurement {

	
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
		if (this.getPreferredAmount() > 1)
		{
			sb.append("s");
		}
		return sb.toString();
	}
	
}
