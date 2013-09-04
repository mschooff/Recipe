package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import view.dialogs.RecipeAmountChange;

import model.measurements.Measurement;
import model.measurements.Units;
import model.recipe.Ingredient;

public class EditableIngredientPanel extends JPanel {
	
	private Ingredient ingredient;
	private JTextField amount;
	private JComboBox unit;
	private JLabel name;
	private RecipeAmountChange source;
	
	public EditableIngredientPanel(Ingredient ing, final RecipeAmountChange source)
	{
		super();
		ingredient = ing;
		this.source = source;
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		amount = new JTextField(String.valueOf(ing.getAmount().getPreferredAmount()));
		amount.setMaximumSize(amount.getPreferredSize());
		
		unit = new JComboBox(ing.getAmount().getUnits());
		unit.setSelectedItem(ing.getAmount().getPreferredUnit());
		unit.setMaximumSize(unit.getPreferredSize());
		unit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox unitBox = (JComboBox)e.getSource();
				amount.setText(Measurement.round(ingredient.getAmount().getAmount((Units)unitBox.getSelectedItem()), 5));
				amount.setColumns(amount.getText().length());
			}
		});
		name = new JLabel(ing.getNameAndInstructions());
		this.add(amount);
		this.add(unit);
		this.add(name);
		this.setAlignmentX(LEFT_ALIGNMENT);
		this.add(Box.createHorizontalGlue());
	}

	public JTextField getAmount() {
		return amount;
	}
	
	public Units getUnits()
	{
		return (Units) unit.getSelectedItem();
	}
	
	

}
