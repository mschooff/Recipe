package view.dialogs;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.ingredients.IngredientInfo;
import model.ingredients.NutritionInfo;
import model.measurements.Measurement;
import model.measurements.MeasurementType;
import model.measurements.Units;

public class NewIngredient extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6502858023213080432L;
	private JTextField name;
	private JTextField servingSize;
	private JComboBox servingUnit;
	private JTextField calories;
	private JTextField totalFat;
	private JTextField totalCarbs;
	private JTextField fiber;
	private JTextField sugar;
	private JTextField protein;

	public NewIngredient() {
		super();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JPanel namePanel = new JPanel();
		namePanel.add(new JLabel("Ingredient Name: "));
		name = new JTextField();
		name.setColumns(5);
		namePanel.add(name);
		this.add(namePanel);
		
		JPanel servingPanel = new JPanel();
		servingPanel.add(new JLabel("Serving Size: "));
		servingSize = new JTextField();
		servingSize.setColumns(5);
		servingPanel.add(servingSize);
		servingUnit = new JComboBox(MeasurementType.allUnits());
		servingPanel.add(servingUnit);
		this.add(servingPanel);
		
		JPanel cal = new JPanel();
		cal.add(new JLabel("Calories: "));
		calories = new JTextField();
		calories.setColumns(5);
		cal.add(calories);
		this.add(cal);
		
		JPanel fat = new JPanel();
		fat.add(new JLabel("Total Fat: "));
		totalFat = new JTextField();
		totalFat.setColumns(5);
		fat.add(totalFat);
		fat.add(new JLabel("grams"));
		this.add(fat);
		
		JPanel carb = new JPanel();
		carb.add(new JLabel("Total Carbohydrates: "));
		totalCarbs = new JTextField();
		totalCarbs.setColumns(5);
		carb.add(totalCarbs);
		carb.add(new JLabel("grams"));
		this.add(carb);
		
		JPanel fib = new JPanel();
		fib.add(new JLabel("Fiber: "));
		fiber = new JTextField();
		fiber.setColumns(5);
		fib.add(fiber);
		fib.add(new JLabel("grams"));
		this.add(fib);
		
		JPanel sug = new JPanel();
		sug.add(new JLabel("Sugar: "));
		sugar = new JTextField();
		sugar.setColumns(5);
		sug.add(sugar);
		sug.add(new JLabel("grams"));
		this.add(sug);
		
		JPanel pro = new JPanel();
		pro.add(new JLabel("Protein: "));
		protein = new JTextField();
		protein.setColumns(5);
		pro.add(protein);
		pro.add(new JLabel("grams"));
		this.add(pro);
	}

	public IngredientInfo getIngredient() {
		try {
			NutritionInfo nutrition = new NutritionInfo(Measurement.create(Double.parseDouble(servingSize.getText()), (Units) servingUnit.getSelectedItem()), 
					Double.parseDouble(calories.getText()), Double.parseDouble(totalFat.getText()), Double.parseDouble(totalCarbs.getText()),
					Double.parseDouble(fiber.getText()), Double.parseDouble(sugar.getText()), Double.parseDouble(protein.getText()));
			return new IngredientInfo(name.getText(), nutrition, MeasurementType.getTypeForUnit((Units) servingUnit.getSelectedItem()));
	} catch (NumberFormatException e)
	{
		JOptionPane.showMessageDialog(this,
			    e.getMessage(),
			    "Error",
			    JOptionPane.ERROR_MESSAGE);
		this.setVisible(true);
		return null;
	}
	}

}
