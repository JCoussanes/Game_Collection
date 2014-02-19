import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.*;



import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class Menu extends JFrame{
	JPanel container;
	CardLayout cl=new CardLayout();
	String[] names={"menu","master","simon","hang","tic"};
	MasterMind master;
	Simon simon;
	Choice menu;
	HangMan hang;
	TicTacToe tic;
	int state=0;
	
	
	Menu(){
		master=new MasterMind();
		menu=new Choice();
		simon=new Simon();
		hang=new HangMan();
		tic=new TicTacToe();
		
		container=new JPanel();
		this.setTitle("Games Collection");
	    this.setSize(720, 520);
	    this.setLocationRelativeTo(null);
	    this.setResizable(false);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setContentPane(container);
	    container.setLayout(cl);
	    container.add(menu.container,names[0]);
	    container.add(master.container,names[1]);
	    container.add(simon.container,names[2]);
	    container.add(hang.container,names[3]);
	    container.add(tic.container,names[4]);

	    container.setVisible(true);
	    this.setVisible(true);
	    
	    while(true){

	    	switch(state){
	    	case 0:
	    		cl.show(container, names[0]);
	    		state=menu.kernel();
	    		break;
	    	case 1:
	    		cl.show(container, names[1]);
	    		state=master.kernel();
	    		break;
	    	case 2:
	    		cl.show(container, names[2]);
	    		state=simon.kernel();
	    		break;
	    	case 3:
	    		cl.show(container, names[3]);
	    		state=hang.kernel();
	    		break;
	    	case 4:
	    		cl.show(container, names[4]);
	    		state=tic.kernel();
	    		break;
	    		
	    	}
	    	
	    }
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Menu m= new Menu();	
	}
	
		
}

