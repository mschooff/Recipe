package view;

import javax.swing.JFrame;

import model.recipe.Recipe;

public class RecipeFrame extends JFrame {
	
	private Recipe recipe;
	
	public RecipeFrame()
	{
		
	}
	
	public RecipeFrame(Recipe r)
	{
		recipe = r;
	}

}
