package view.dialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import model.measurements.Measurement;
import model.measurements.MeasurementType;
import model.measurements.Units;
import model.recipe.Ingredient;
import model.recipe.Recipe;

import view.IngredientPanel;

public class NewRecipeDialog extends JDialog{
	
	private JTextField title;
	private JTextField yieldAmount;
	private JComboBox yieldUnit;
	private JTextField servings;
	private JTextArea directions;
	private ArrayList<IngredientPanel> ingredients;
	private JPanel ingredientPanel;
	
	public NewRecipeDialog()
	{
		super();
		this.setModal(true);
		this.setTitle("New Recipe");
		
		JPanel topPanel = new JPanel();
		JPanel titlePanel = new JPanel();
		titlePanel.add(new JLabel("Title: "), BorderLayout.WEST);
		title = new JTextField();
		title.setColumns(5);
		titlePanel.add(title, BorderLayout.EAST);
		topPanel.add(titlePanel, BorderLayout.NORTH);
		JPanel yieldPanel = new JPanel();
		yieldPanel.setLayout(new FlowLayout());
		yieldPanel.add(new JLabel("Yields: "));
		yieldAmount = new JTextField();
		yieldAmount.setColumns(5);
		yieldPanel.add(yieldAmount);
		yieldUnit = new JComboBox(MeasurementType.allUnits());
		yieldPanel.add(yieldUnit);
		yieldPanel.add(new JLabel("Number of Servings: "));
		servings = new JTextField();
		servings.setColumns(5);
		yieldPanel.add(servings);
		topPanel.add(yieldPanel, BorderLayout.SOUTH);
		
		
		ingredientPanel = new JPanel();
		ingredientPanel.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		ingredientPanel.setSize(400,200);
		directions = new JTextArea();
		directions.setEditable(true);
		directions.setSize(200,200);
		JPanel left = new JPanel();
		left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
		left.add(new JLabel("Ingredients:"));
		left.add(new JScrollPane(ingredientPanel));
		JPanel right = new JPanel();
		right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
		right.add(new JLabel("Directions:"));
		right.add(new JScrollPane(directions));
		JSplitPane bottomPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, left, right);
		
		JPanel buttonPanel = new JPanel();
		JButton confirmButton = new JButton("Confirm");
		confirmButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ArrayList<Ingredient> ings = new ArrayList<Ingredient>();
				for(IngredientPanel panel: ingredients)
				{
					ings.add(panel.getIngredient());
				}
				try {
					double yield = Double.parseDouble(yieldAmount.getText());
					int serves = Integer.parseInt(servings.getText());
					Recipe.addRecipe(new Recipe(title.getText(), ings, Measurement.create(yield, (Units)yieldUnit.getSelectedItem()), serves, directions.getText()));
					NewRecipeDialog.this.setVisible(false);
				} catch(NumberFormatException e)
				{
					JOptionPane.showMessageDialog(NewRecipeDialog.this,
						    e.getMessage(),
						    "Error",
						    JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				NewRecipeDialog.this.setVisible(false);
			}
		});
		JButton addIngButton = new JButton("Add Ingredient");
		addIngButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				AddIngredient info = new AddIngredient();
				if (JOptionPane.showOptionDialog(NewRecipeDialog.this, info, "Add Ingredient", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null) == JOptionPane.OK_OPTION)
				{
					Ingredient i = info.getIngredient();
					if (i != null)
					{
						IngredientPanel newIngPanel = new IngredientPanel(i);
						ingredients.add(newIngPanel);
						JPanel ingPlus = new JPanel();
						ingPlus.add(newIngPanel);
						JButton editButton = new JButton("Edit");
						editButton.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent arg0) {
								JButton button = (JButton)(arg0.getSource());
								IngredientPanel panel = (IngredientPanel)(button.getParent().getComponent(0));
								int index = -1;
								for (int i = 0; i < ingredients.size(); i++)
								{
									if (ingredients.get(i) == panel)
									{
										index = i;
										break;
									}
								}
								AddIngredient changedInfo = new AddIngredient(panel.getIngredient());
								if (JOptionPane.showOptionDialog(NewRecipeDialog.this, changedInfo, "Edit " + panel.getIngredient().getInfo().getName(), JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null) == JOptionPane.OK_OPTION)
								{
									//TODO Change Ingredient
								}
							}
						});
						
						JButton removeButton = new JButton("Remove");
						removeButton.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent arg0) {
								JButton button = (JButton)(arg0.getSource());
								IngredientPanel panel = (IngredientPanel)(button.getParent().getComponent(0));
								int index = -1;
								for (int i = 0; i < ingredients.size(); i++)
								{
									if (ingredients.get(i) == panel)
									{
										index = i;
										break;
									}
								}
								ingredients.remove(panel);
								ingredientPanel.remove(index);
							}
						});
						ingPlus.add(editButton);
						ingPlus.add(removeButton);
						ingredientPanel.add(ingPlus);
						
					}
				}
			}
		});
		buttonPanel.add(addIngButton);
		buttonPanel.add(cancelButton);
		buttonPanel.add(confirmButton);
		
		this.add(topPanel, BorderLayout.NORTH);
		this.add(bottomPane);
		this.add(buttonPanel, BorderLayout.SOUTH);
		this.pack();
		this.setMinimumSize(new Dimension(350,200));
		
	}

	
	
	
	private static final long serialVersionUID = 9098590890710873230L;

	public static void main(String[] args) {
		new NewRecipeDialog().setVisible(true);

	}

}
