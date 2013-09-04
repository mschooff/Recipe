package model.measurements;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;

public class Measurement implements Serializable {
	
	private static final long serialVersionUID = -4082111688120076980L;
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
	
	public Units[] getUnits()
	{
		switch(type) {
		case VOLUME: return VolumeUnits.values();
		default: return null;
		}
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

	public static String round(double value, int decimals)
	{
		int temp = (int)((value * Math.pow(10, decimals)));
		double result = temp / Math.pow(10, decimals);
		BigDecimal rounded = new BigDecimal(result).round(new MathContext(decimals));
		return String.valueOf(rounded.doubleValue());
	}

}
