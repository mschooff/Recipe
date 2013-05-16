package model.measurements;

public enum VolumeUnits {
	CUP, DASH, DROP, GALLON, LITER, MILLILITER, OUNCE, 
	PINCH, PINT, QUART, TABLESPOON, TEASPOON;

public double toTeaspoons()
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

};