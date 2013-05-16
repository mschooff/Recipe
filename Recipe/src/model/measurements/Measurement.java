package model.measurements;

public class Measurement {
	
	private double amount;
	private MeasurementType type;
	
	public Measurement(double amount, MeasurementType type)
	{
		this.amount = amount;
		this.type = type;
	}
	
	public Measurement(Measurement m)
	{
		amount = m.getAmount();
		type = m.getType();
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
