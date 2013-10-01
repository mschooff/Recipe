package view.dialogs;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import model.ingredients.IngredientInfo;
import model.measurements.Measurement;
import model.measurements.MeasurementType;
import model.measurements.Units;
import model.recipe.Ingredient;

public class AddIngredient extends JPanel{
	private JTextField amount;
	private JComboBox unit;
	private JComboBox ingredient;
	private JTextField directions;
	
	public AddIngredient()
	{
		super();
		common();
	}
		
	private void common()
	{
		this.setLayout(new BorderLayout());
		JPanel amountPanel = new JPanel();
		JLabel amountLabel = new JLabel("Amount");
		amountLabel.setHorizontalTextPosition(SwingConstants.LEFT);
		amountLabel.setHorizontalAlignment(SwingConstants.LEFT);
		JPanel left = new JPanel();
		left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
		left.add(new JLabel("Amount"));
		amount = new JTextField();
		amount.setColumns(5);
		unit = new JComboBox(MeasurementType.allUnits());
		JPanel amountSelection = new JPanel();
		amountSelection.setLayout(new BoxLayout(amountSelection, BoxLayout.Y_AXIS));
		left.add(amount);
		amountSelection.add(new JLabel("  "));
		amountSelection.add(unit);
		amountPanel.add(left);
		amountPanel.add(amountSelection);
		
		JPanel ingredientPanel = new JPanel();
		ingredientPanel.setLayout(new BoxLayout(ingredientPanel, BoxLayout.Y_AXIS));
		JLabel ingredientLabel = new JLabel("Ingredient");
		ingredientLabel.setHorizontalTextPosition(SwingConstants.LEFT);
		ingredientLabel.setHorizontalAlignment(SwingConstants.LEFT);
		ingredientPanel.add(ingredientLabel);
		ingredient = new JComboBox(IngredientInfo.getIngredients());
		ingredientPanel.add(ingredient);
		
		JPanel directionsPanel = new JPanel();
		directionsPanel.setLayout(new BoxLayout(directionsPanel, BoxLayout.Y_AXIS));
		directionsPanel.add(new JLabel("Directions"));
		directions = new JTextField();
		directions.setColumns(5);
		directionsPanel.add(directions);
		
		JPanel mainPanel = new JPanel();
		mainPanel.add(amountPanel);
		mainPanel.add(ingredientPanel);
		mainPanel.add(directionsPanel);
		this.add(mainPanel);
		
		JPanel buttonPanel = new JPanel();
		JButton newIngButton = new JButton("Add a new ingredient");
		newIngButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				NewIngredient ing = new NewIngredient();
				if (JOptionPane.showOptionDialog(AddIngredient.this, ing, "New Ingredient", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null) == JOptionPane.OK_OPTION)
				{
					IngredientInfo newIng = ing.getIngredient();
					IngredientInfo.addIngredient(newIng);
					ingredient.setSelectedItem(newIng);
					AddIngredient.this.repaint();
				}
			}
		});
		buttonPanel.add(newIngButton);
		this.add(buttonPanel, BorderLayout.SOUTH);
	}
	
	public Ingredient getIngredient()
	{
		IngredientInfo info = (IngredientInfo)ingredient.getSelectedItem();
		double quantity;
		try {
			quantity = Double.parseDouble(amount.getText());
		} catch (NumberFormatException e)
		{
			this.setVisible(true);
			JOptionPane.showMessageDialog(AddIngredient.this,
				    amount.getText() + " is not a number.",
				    "Error",
				    JOptionPane.ERROR_MESSAGE);
			return null;
		}
		Ingredient result =  new Ingredient(info, Measurement.create(quantity, (Units)unit.getSelectedItem()), directions.getText());
		System.out.println(result);
		return result;
	}
	
	public AddIngredient(Ingredient i)
	{
		super();
		common();
		amount.setText(Double.toString(i.getAmount().getPreferredAmount()));
		unit.setSelectedItem(i.getAmount().getPreferredUnit());
		ingredient.setSelectedItem(i.getInfo());
		directions.setText(i.getInstructions());
	}

}
