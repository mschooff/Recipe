package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import view.dialogs.RecipeAmountChange;

import model.ingredients.IngredientInfo;
import model.measurements.Measurement;
import model.measurements.MeasurementType;
import model.measurements.Units;
import model.measurements.VolumeMeasurement;
import model.measurements.VolumeUnits;
import model.recipe.Ingredient;
import model.recipe.Recipe;

public class RecipeFrame extends JInternalFrame {
	
	private Recipe recipe;
	private double multiplyer;
	private JLabel title;
	private JLabel numRecipes;
	private JLabel yieldAmount;
	private JComboBox yieldUnit;
	private JPanel ingredients;
	
	public RecipeFrame(Recipe r)
	{
		super(r.getName(), true, true, true, true);
		recipe = r;
		multiplyer = 1;
		
		JPanel buttonPanel = new JPanel();
		JButton changeAmountButton = new JButton("Change Amount");
		changeAmountButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new RecipeAmountChange(RecipeFrame.this).setVisible(true);
				
			}
		});
		buttonPanel.add(changeAmountButton);
		
		title = new JLabel(r.getName());
		title.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel numRecipesPanel = new JPanel();
		numRecipes = new JLabel("1");
		//numRecipes.setColumns(5);
		//numRecipes.addActionListener(multiplyerListener);
		numRecipesPanel.add(numRecipes);
		numRecipesPanel.add(new JLabel("Recipes"));
		
		JPanel yieldPanel = new JPanel();
		yieldPanel.add(new JLabel("Yields"));
		yieldAmount = new JLabel(String.valueOf(r.getYield().getPreferredAmount()));
		//yieldAmount.setColumns(5);
		//yieldAmount.addActionListener(multiplyerListener);
		yieldPanel.add(yieldAmount);
		yieldUnit = new JComboBox(r.getYield().getUnits());
		yieldUnit.setSelectedItem(r.getYield().getPreferredUnit());
		yieldUnit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox unitBox = (JComboBox)e.getSource();
				yieldAmount.setText(Measurement.round(RecipeFrame.this.recipe.getYield().getAmount((Units)unitBox.getSelectedItem()), 5));
				//yieldAmount.setColumns(yieldAmount.getText().length());
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
			IngredientPanel ingPanel = new IngredientPanel(ing);
			//ingPanel.getAmount().addActionListener(multiplyerListener);
			ingredients.add(ingPanel);
		}
		ingredients.add(Box.createGlue());
		JScrollPane ingredientPane = new JScrollPane();
		ingredientPane.setViewportView(ingredients);
		
		JTextArea directions = new JTextArea(recipe.getDirections());
		directions.setEditable(false);
		JScrollPane directionsPane = new JScrollPane();
		directionsPane.setViewportView(directions);
		directionsPane.setAlignmentY(JScrollPane.TOP_ALIGNMENT);
		
		JSplitPane horizontalSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, ingredientPane, directionsPane);
		
		JScrollPane titleAndYield = new JScrollPane(topPanel);
		titleAndYield.setMinimumSize(new Dimension(54, 54));
		JSplitPane verticalSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, titleAndYield, horizontalSplit);
		verticalSplit.setContinuousLayout(true);
		
		this.add(verticalSplit, BorderLayout.CENTER);
		this.add(buttonPanel, BorderLayout.NORTH);
		
		this.pack();
		this.setSize(this.getWidth() + 20, this.getHeight() + 30);
		
	}
	
	private ActionListener multiplyerListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				int ingredientCount = 0;
				for (int i = 0; i < ingredients.getComponentCount(); i++)
				{
					if (ingredients.getComponent(i) instanceof IngredientPanel)
					{
						if (e.getSource() == ((IngredientPanel)ingredients.getComponent(i)).getAmount())
						{
							IngredientPanel ingP = (IngredientPanel)ingredients.getComponent(i);
							double text = Double.parseDouble(ingP.getAmount().getText());
							double original = recipe.getIngredient(ingredientCount).getAmount().getAmount(ingP.getUnits());
							multiplyer = text / original;
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
				
				// other sources of updatable amounts
			} catch (NumberFormatException ex)
			{
				JOptionPane.showMessageDialog(RecipeFrame.this, ex.getMessage(), "Value is not a Number", JOptionPane.ERROR_MESSAGE);
			}
			updateMeasurements();
		}
	};
	
	public void updateMeasurements()
	{
		numRecipes.setText(Measurement.round(multiplyer, 5));
		yieldAmount.setText(Measurement.round(multiplyer * recipe.getYield().getAmount((Units)yieldUnit.getSelectedItem()), 5));
		int ingredientCount = 0;
		for (int i = 0; i < ingredients.getComponentCount(); i++)
		{
			if (ingredients.getComponent(i) instanceof IngredientPanel)
			{
				IngredientPanel ingP = (IngredientPanel)ingredients.getComponent(i);
				double amount = multiplyer * recipe.getIngredient(ingredientCount).getAmount().getAmount(ingP.getUnits());
				ingP.getAmount().setText(Measurement.round(amount, 5));
				ingredientCount++;
			}
		}
	}
	
	public Recipe getRecipe()
	{
		return recipe;
	}
	
	public double getMultiplyer()
	{
		return multiplyer;
	}
	
	public void setMultiplyer(double newMultiplyer)
	{
		multiplyer = newMultiplyer;
	}
	
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable() {		
			public void run() {
				
		Recipe r = new Recipe("Test", new VolumeMeasurement(5, VolumeUnits.TEASPOON), 10, "Directions\n 1. Step One\n  2. Step Two\n  3. Step Three");
		r.addIngredient(new Ingredient(new IngredientInfo("first", null, MeasurementType.VOLUME), new VolumeMeasurement(2, VolumeUnits.CUP), "test"));
		r.addIngredient(new Ingredient(new IngredientInfo("second", null, MeasurementType.VOLUME), new VolumeMeasurement(12, VolumeUnits.DROP)));
		r.addIngredient(new Ingredient(new IngredientInfo("third", null, MeasurementType.VOLUME), new VolumeMeasurement(3, VolumeUnits.TABLESPOON), "cut in thirds"));
		r.addIngredient(new Ingredient(new IngredientInfo("fourth", null, MeasurementType.VOLUME), new VolumeMeasurement(983, VolumeUnits.GALLON), "that's a lot"));
		RecipeFrame frame = new RecipeFrame(r);
		frame.setVisible(true);
		
		JFrame main = new JFrame();
		JDesktopPane desktop = new JDesktopPane();
		main.setContentPane(desktop);
		desktop.add(frame);
		main.setSize(1000,800);
		main.setVisible(true);
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		
		}
	});
	}
}
