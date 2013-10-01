package model.measurements;

import java.io.Serializable;

public enum VolumeUnits implements Units, Serializable{
	CUP, DASH, DROP, GALLON, LITER, MILLILITER, OUNCE, 
	PINCH, PINT, QUART, TABLESPOON, TEASPOON;

public double conversion()
{
	switch(this) {
	case CUP: return 48;
	case DASH: return 0.125;
	case DROP: return 0.010416666667;
	case GALLON: return 768;
	case LITER: return 202.88413621;
	case MILLILITER: return 0.20288413621;
	case OUNCE: return 6;
	case PINCH: return 0.0625;
	case PINT: return 96;
	case QUART: return 192;
	case TABLESPOON: return 3;
	case TEASPOON: return 1;
	default: return -1;
	}
}

public String toString()
{
	switch(this) {
	case CUP: return "Cups";
	case DASH: return "Dashes";
	case DROP: return "Drops";
	case GALLON: return "Gallons";
	case LITER: return "Liters";
	case MILLILITER: return "milliliters";
	case OUNCE: return "Ounces";
	case PINCH: return "Pinches";
	case PINT: return "Pints";
	case QUART: return "Quarts";
	case TABLESPOON: return "Tablespoons";
	case TEASPOON: return "teaspoons";
	}
	return null;
}

@Override
public String abbreviation() {
	switch(this) {
	case CUP: return "c";
	case DASH: return "dash";
	case DROP: return "drop";
	case GALLON: return "Gal";
	case LITER: return "L";
	case MILLILITER: return "ml";
	case OUNCE: return "Oz";
	case PINCH: return "pinch";
	case PINT: return "Pt";
	case QUART: return "Qt";
	case TABLESPOON: return "Tbsp";
	case TEASPOON: return "tsp";
	default: return "";
	}
}
	
}


