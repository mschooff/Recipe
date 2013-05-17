package model.measurements;

public class Measurement {
	
	private double amount;
	private MeasurementType type;
	private Units preferredUnit;
	
	public Measurement(double amount, MeasurementType type, Units preferred)
	{
		this.amount = amount;
		this.type = type;
		preferredUnit = preferred;
	}
	
	public Measurement(Measurement m)
	{
		amount = m.getAmount();
		type = m.getType();
		preferredUnit = m.getPreferredUnit();
	}
	
	public double getAmount(Units unit)
	{
		return amount / unit.conversion();
	}
	
	public double getPreferredAmount()
	{
		return getAmount(preferredUnit);
	}
	
	public Units getPreferredUnit()
	{
		return preferredUnit;
	}
	
	public void divide(double by)
	{
		amount = amount / by;
	}
	
	public void setAmount(double amount)
	{
		this.amount = amount;
	}
	
	public double getAmount()
	{
		return amount;
	}

	public MeasurementType getType() {
		return type;
	}

	public void setType(MeasurementType type) {
		this.type = type;
	}


}
