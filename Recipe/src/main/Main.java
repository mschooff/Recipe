package main;

import java.awt.EventQueue;
import view.MainWindow;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		initIngredients();
		
			EventQueue.invokeLater(new Runnable() {		
				public void run() {
					
			MainWindow window = new MainWindow();
			window.setVisible(true);
			
			}
		});
	}

	
	private static void initIngredients()
	{
		//read Ingredients file and call Ingredient.addIngredientInfo(i);
	}

}