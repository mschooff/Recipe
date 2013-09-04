package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.measurements.Measurement;
import model.measurements.Units;
import model.recipe.Ingredient;

public class IngredientPanel extends JPanel {
	
	private Ingredient ingredient;
	private JLabel amount;
	private JComboBox unit;
	private JLabel name;
	
	public IngredientPanel(Ingredient ing)
	{
		super();
		ingredient = ing;
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		amount = new JLabel(String.valueOf(ing.getAmount().getPreferredAmount()));
		amount.setMaximumSize(amount.getPreferredSize());
		unit = new JComboBox(ing.getAmount().getUnits());
		unit.setSelectedItem(ing.getAmount().getPreferredUnit());
		unit.setMaximumSize(unit.getPreferredSize());
		unit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox unitBox = (JComboBox)e.getSource();
				amount.setText(Measurement.round(ingredient.getAmount().getAmount((Units)unitBox.getSelectedItem()), 5));
				//amount.setColumns(amount.getText().length());
			}
		});
		name = new JLabel(ing.getNameAndInstructions());
		this.add(amount);
		this.add(unit);
		this.add(name);
		this.setAlignmentX(LEFT_ALIGNMENT);
		this.add(Box.createHorizontalGlue());
	}

	public JLabel getAmount() {
		return amount;
	}
	
	public Units getUnits()
	{
		return (Units) unit.getSelectedItem();
	}
	
	

}
