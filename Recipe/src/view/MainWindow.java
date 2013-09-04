package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyVetoException;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.border.EtchedBorder;
import javax.swing.tree.TreeSelectionModel;

import view.dialogs.NewRecipeDialog;
import view.dialogs.RecipeAmountChange;

import model.category.Categories;
import model.recipe.Recipe;

public class MainWindow extends JFrame {
	
	private JTree tree;
	JDesktopPane desktopPane;

	public MainWindow()
	{
		super();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Recipe Book");
		this.setLocation(100, 100);
		this.setLayout(new BorderLayout());
		this.setSize(1000, 600);
		
		
		tree = new JTree(Categories.getSingleton());
		tree.getSelectionModel().setSelectionMode
		        (TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.setShowsRootHandles(true);
		tree.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				for (JInternalFrame frame : desktopPane.getAllFrames())
				{
					if (((RecipeFrame)frame).getRecipe() == tree.getLastSelectedPathComponent())
					{
						if(frame.isIcon())
						{
							try {
								frame.setIcon(false);
							} catch (PropertyVetoException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
						try {
							frame.setSelected(true);
						} catch (PropertyVetoException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						frame.moveToFront();
						return;
					}
				}
				if (e.getClickCount() == 2)
				{
					if (Categories.getSingleton().isLeaf(tree.getLastSelectedPathComponent()))
					{
						RecipeFrame recipe = new RecipeFrame((Recipe)tree.getLastSelectedPathComponent());
						if (desktopPane.getSelectedFrame() != null)
						{
							recipe.setLocation(desktopPane.getSelectedFrame().getX() + 80, desktopPane.getSelectedFrame().getY() + 40);
						}
						else
						{
							recipe.setLocation(20, 20);
						}
						recipe.setVisible(true);
						desktopPane.add(recipe);
						recipe.moveToFront();
						try {
							recipe.setSelected(true);
						} catch (PropertyVetoException e1) {
							e1.printStackTrace();
						}
						
					}
				}
				
			}
		});
		JScrollPane treePane = new JScrollPane(tree);
		
		desktopPane = new JDesktopPane();
		desktopPane.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
	
		JSplitPane treeAndDesktop = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treePane, desktopPane);
		treeAndDesktop.setOneTouchExpandable(true);
		this.add(treeAndDesktop);
		
		JPanel buttonPanel = new JPanel();
		JButton addRecipeButton = new JButton("Add New Recipe");
		addRecipeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new NewRecipeDialog().setVisible(true);
				
			}
		});
		buttonPanel.add(addRecipeButton);
		this.add(buttonPanel, BorderLayout.NORTH);
		
		
	}
	
}
