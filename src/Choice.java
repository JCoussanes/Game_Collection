import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;




public class Choice {
	JPanel container;
	JButton mM,s,hM,t;
	boolean choose;
	int ans;
	
	Choice(){
		container=new JPanel();
		mM=new JButton("Master Mind");
		s=new JButton("Simon");
		hM=new JButton("Hang Man");
		t=new JButton("Tic Tac Toe");
		container.setLayout(new GridLayout(4,1));
	    container.add(mM);
	    container.add(s);
	    container.add(hM);
	    container.add(t);
	    mM.addActionListener(new MasterMindListener());
	    s.addActionListener(new SimonListener());
	    hM.addActionListener(new HangManListener());
	    t.addActionListener(new TicTacToeListener());
	    container.setVisible(true);	         
	}
	
	int kernel(){	
		choose=false;
		ans=0;
		while(true){
			System.out.print("");
			if(choose){
				
				break;
			}
		}

		return ans;
	}
	
	class SimonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			ans=2;
			choose=true;
		}
	}
	
	class TicTacToeListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			ans=4;
			choose=true;
		}
	}
	
	class HangManListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			ans=3;
			choose=true;
		}
	}
	
	class MasterMindListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			ans=1;
			choose=true;
		}
		
	}
}
