package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import model.measurements.Measurement;
import model.measurements.VolumeMeasurement;
import model.measurements.VolumeUnits;
import model.recipe.Recipe;

public class RecipeFrame extends JFrame {
	
	private Recipe recipe;
	private JLabel title;
	private JTextField numRecipes;
	private JTextField yield;
	
	public RecipeFrame(Recipe r)
	{
		recipe = r;
		
		title = new JLabel(r.getName());
		title.setHorizontalAlignment(SwingConstants.CENTER);
		JPanel numRecipesPanel = new JPanel();
		numRecipes = new JTextField("1");
		numRecipesPanel.add(numRecipes);
		numRecipesPanel.add(new JLabel("Recipes"));
		JPanel yieldPanel = new JPanel();
		yieldPanel.add(new JLabel("Yields"));
		yield = new JTextField(r.getYield().toString());
		yieldPanel.add(yield);
		JPanel lowerTopPanel = new JPanel();
		lowerTopPanel.setLayout(new BorderLayout());
		lowerTopPanel.add(numRecipesPanel, BorderLayout.WEST);
		lowerTopPanel.add(yieldPanel, BorderLayout.EAST);
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout());
		topPanel.add(title, BorderLayout.NORTH);
		topPanel.add(lowerTopPanel, BorderLayout.SOUTH);
		this.add(topPanel, BorderLayout.NORTH);
		
		
		this.pack();
	}
	
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable() {		
			public void run() {
				
		RecipeFrame frame = new RecipeFrame(new Recipe("Test", new VolumeMeasurement(5, VolumeUnits.CUP), 10, "Directions"));
		frame.setVisible(true);
		
		}
	});
	}

}
