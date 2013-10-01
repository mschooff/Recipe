package model.measurements;

import java.io.Serializable;
import java.util.Comparator;
import java.util.TreeSet;

public enum MeasurementType implements Serializable{
	VOLUME;
	
	private static Units[][] build()
	{
		return new Units[][] {VolumeUnits.values()};
	}
	
	public static MeasurementType getTypeForUnit (Units u)
	{
		Units[][] all = build();
		int type = -1;
		for (int i = 0; i < all.length; i++)
		{
			for(int j = 0; j < all[i].length; j++)
			{
				if (all[i][j] == u)
				{
					type = i;
					break;
				}
			}
		}
		switch(type)
		{
		case 0: return MeasurementType.VOLUME;
		default: return null;
		}
	}
	
	public static Units[] allUnits()
	{
		Units[][] all = build();
		TreeSet<Units> sorted = new TreeSet<Units>(new Comparator<Units>() {
			@Override
			public int compare(Units arg0, Units arg1) {
				return arg0.toString().compareTo(arg1.toString());
			}
		});
		for (int i = 0; i < all.length; i++)
		{
			for (int j = 0; j < all[i].length; j++)
			{
				sorted.add(all[i][j]);
			}
		}
		return sorted.toArray(new Units[sorted.size()]);
	}
	
}
