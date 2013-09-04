package view.dialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


import model.measurements.Measurement;
import model.measurements.Units;
import model.recipe.Ingredient;
import model.recipe.Recipe;

import view.EditableIngredientPanel;
import view.IngredientPanel;
import view.RecipeFrame;

public class RecipeAmountChange extends JDialog {
	private RecipeFrame frame;
	private JLabel title;
	private JTextField numRecipes;
	private JTextField yieldAmount;
	private JComboBox yieldUnit;
	private JPanel ingredients;
	protected double multiplyer;
	private JTextField lastUsed;
	
	public RecipeAmountChange(final RecipeFrame frame) {
		super();
		lastUsed = null;
		this.frame = frame;
		this.setModal(true);
		this.setName("Change Recipe Amount");
		final Recipe recipe = frame.getRecipe();
		title = new JLabel(recipe.getName());
		title.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel numRecipesPanel = new JPanel();
		numRecipes = new JTextField("1");
		numRecipes.setColumns(5);
		numRecipes.addActionListener(multiplyerListener);
		numRecipes.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent arg0) {
				lastUsed = numRecipes;
			}
			@Override
			public void focusLost(FocusEvent arg0) {}
		});
		numRecipesPanel.add(numRecipes);
		numRecipesPanel.add(new JLabel("Recipes"));
		
		JPanel yieldPanel = new JPanel();
		yieldPanel.add(new JLabel("Yields"));
		yieldAmount = new JTextField(String.valueOf(recipe.getYield().getPreferredAmount()));
		yieldAmount.setColumns(5);
		yieldAmount.addActionListener(multiplyerListener);
		yieldAmount.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent arg0) {
				lastUsed = yieldAmount;
			}
			@Override
			public void focusLost(FocusEvent arg0) {}
		});
		
		yieldPanel.add(yieldAmount);
		yieldUnit = new JComboBox(recipe.getYield().getUnits());
		yieldUnit.setSelectedItem(recipe.getYield().getPreferredUnit());
		yieldUnit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//simulate pressing enter in numRecipes field to register value
				JComboBox unitBox = (JComboBox)e.getSource();
				yieldAmount.setText(Measurement.round(recipe.getYield().getAmount((Units)unitBox.getSelectedItem()), 5));
				yieldAmount.setColumns(yieldAmount.getText().length());
			}
		});
		yieldPanel.add(yieldUnit);
		
		JPanel lowerTopPanel = new JPanel();
		lowerTopPanel.setLayout(new BorderLayout());
		lowerTopPanel.add(numRecipesPanel, BorderLayout.WEST);
		lowerTopPanel.add(yieldPanel, BorderLayout.EAST);
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout());
		topPanel.add(title, BorderLayout.NORTH);
		topPanel.add(lowerTopPanel, BorderLayout.SOUTH);
		
		ingredients = new JPanel();
		ingredients.setLayout(new BoxLayout(ingredients, BoxLayout.Y_AXIS));
		for (Ingredient ing: recipe.getIngredients())
		{
			ingredients.add(Box.createGlue());
			EditableIngredientPanel ingPanel = new EditableIngredientPanel(ing, this);
			ingPanel.getAmount().addActionListener(multiplyerListener);
			ingPanel.getAmount().addFocusListener(new FocusListener() {
				@Override
				public void focusGained(FocusEvent arg0) {
					lastUsed = (JTextField) arg0.getSource();
				}
				@Override
				public void focusLost(FocusEvent arg0) {}
			});
			ingredients.add(ingPanel);
		}
		ingredients.add(Box.createGlue());
		JScrollPane ingredientPane = new JScrollPane();
		ingredientPane.setViewportView(ingredients);
		JScrollPane titleAndYield = new JScrollPane(topPanel);
		titleAndYield.setMinimumSize(new Dimension(54, 54));
		JSplitPane verticalSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, titleAndYield, ingredientPane);
		verticalSplit.setContinuousLayout(true);
		
		this.add(verticalSplit, BorderLayout.CENTER);
		
		JPanel buttonsPanel = new JPanel();
		JButton confirmButton = new JButton("Confirm");
		confirmButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//TODO check for updated value not confirmed
				if (lastUsed == yieldAmount)
				{
					double value = Double.parseDouble(yieldAmount.getText()) / recipe.getYield().getAmount((Units)yieldUnit.getSelectedItem());
					if(multiplyer != value)
					{
						multiplyer = value;
					}
				}
				else if (lastUsed == numRecipes)
				{
					double value =  Double.parseDouble(numRecipes.getText());
					if (multiplyer != value)
					{
						multiplyer = value;
					}
				}
				else
				{
					Recipe recipe = frame.getRecipe();
					int ingredientCount = 0;
					for (int i = 0; i < ingredients.getComponentCount(); i++)
					{
						if (ingredients.getComponent(i) instanceof EditableIngredientPanel)
						{
							if (lastUsed == ((EditableIngredientPanel)ingredients.getComponent(i)).getAmount())
							{
								EditableIngredientPanel ingP = (EditableIngredientPanel)ingredients.getComponent(i);
								double text = Double.parseDouble(ingP.getAmount().getText());
								double original = recipe.getIngredient(ingredientCount).getAmount().getAmount(ingP.getUnits());
								multiplyer = text / original;
								//frame.setMultiplyer(multiplyer);
								break;
							}
							ingredientCount++;
						}
					}
				}
				frame.setMultiplyer(multiplyer);
				frame.updateMeasurements();
				RecipeAmountChange.this.setVisible(false);
			}
		});
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				RecipeAmountChange.this.setVisible(false);
			}
		});
		buttonsPanel.add(confirmButton);
		buttonsPanel.add(cancelButton);
		this.add(buttonsPanel, BorderLayout.SOUTH);
		
		this.pack();
	}
	
	private ActionListener multiplyerListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				Recipe recipe = frame.getRecipe();
				int ingredientCount = 0;
				for (int i = 0; i < ingredients.getComponentCount(); i++)
				{
					if (ingredients.getComponent(i) instanceof EditableIngredientPanel)
					{
						if (e.getSource() == ((EditableIngredientPanel)ingredients.getComponent(i)).getAmount())
						{
							EditableIngredientPanel ingP = (EditableIngredientPanel)ingredients.getComponent(i);
							double text = Double.parseDouble(ingP.getAmount().getText());
							double original = recipe.getIngredient(ingredientCount).getAmount().getAmount(ingP.getUnits());
							multiplyer = text / original;
							//frame.setMultiplyer(multiplyer);
							break;
						}
						ingredientCount++;
					}
				}
				if (e.getSource() == yieldAmount)
				{
					multiplyer = Double.parseDouble(yieldAmount.getText()) / recipe.getYield().getAmount((Units)yieldUnit.getSelectedItem());
				}
				else if (e.getSource() == numRecipes)
				{
					multiplyer = Double.parseDouble(numRecipes.getText());
				}
				//frame.setMultiplyer(multiplyer);
				
				// other sources of updatable amounts
			} catch (NumberFormatException ex)
			{
				JOptionPane.showMessageDialog(frame, ex.getMessage(), "Value is not a Number", JOptionPane.ERROR_MESSAGE);
			}
			updateMeasurements();
		}
	};
	
	private void updateMeasurements()
	{
		Recipe recipe = frame.getRecipe();
		numRecipes.setText(Measurement.round(multiplyer, 5));
		numRecipes.setColumns(numRecipes.getText().length());
		yieldAmount.setText(Measurement.round(multiplyer * recipe.getYield().getAmount((Units)yieldUnit.getSelectedItem()), 5));
		yieldAmount.setColumns(yieldAmount.getText().length());
		int ingredientCount = 0;
		for (int i = 0; i < ingredients.getComponentCount(); i++)
		{
			if (ingredients.getComponent(i) instanceof EditableIngredientPanel)
			{
				EditableIngredientPanel ingP = (EditableIngredientPanel)ingredients.getComponent(i);
				double amount = multiplyer * recipe.getIngredient(ingredientCount).getAmount().getAmount(ingP.getUnits());
				ingP.getAmount().setText(Measurement.round(amount, 5));
				ingP.getAmount().setColumns(ingP.getAmount().getText().length());
				ingredientCount++;
				ingP.getAmount().revalidate();
				ingP.getAmount().repaint();
				ingP.revalidate();
				ingP.repaint();
			}
		}
		//frame.updateMeasurements();
	}

}
