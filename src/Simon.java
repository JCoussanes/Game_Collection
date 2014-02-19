import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class Simon {
	MyPanel container;
	
	Rectangle[] colorSelect=new Rectangle[4];
	Rectangle rules,quit;
	
	String[] color={"green_light.png","yellow_light.png","blue_light.png","red_light.png"};
	LinkedList<String> L=new LinkedList<String>();
	boolean click,finish=true,lose=false,run=true;
	int checkPos,state=0;
	
	Simon(){
		//Initialization
		container=new MyPanel();   
	   
	    rules=new Rectangle();
	    quit=new Rectangle();
		for(int i=0;i<4;i++){
			colorSelect[i]=new Rectangle();
		}
		rules.setSize(200,105);
		rules.setLocation(20, 25);
		quit.setSize(200,105);
		quit.setLocation(20,420);
		colorSelect[0].setSize(85, 95);
		colorSelect[0].setLocation(245, 145);
		colorSelect[1].setSize(85, 95);
		colorSelect[1].setLocation(410, 145);
		colorSelect[2].setSize(85, 95);
		colorSelect[2].setLocation(245, 315);
		colorSelect[3].setSize(85, 95);
		colorSelect[3].setLocation(410, 315);
		container.addMouseListener(new ClickListener());
		
	    
	}
	
	int kernel(){
		init();
		while(run){
			System.out.print("");
			while(true){
				System.out.print("");
				if(!run){
					break;
				}
	    		if(finish){
	    			try {
						Thread.sleep(1000);
						
						L.add(randColor());
						play();
						click=true;
						finish=false;
	    			} catch (InterruptedException e) {}
	    		
	    		}
	    		if(lose){
	    			state=2;
	     			break;	
	    		}
	    	}
			if(!run){
				break;
			}
			container.changeImgOver("lose.png");	
			container.repaint();
		}
		return 0;
	}
	
	void init(){
		L.clear();
		click=false;
		finish=true;
		lose=false;
		run=true;
		checkPos=0;
		state=0;
		container.changeImgOver(null);
		
	}
	
	String randColor(){
		Random random=new Random();
		int i=random.nextInt(4);
		
		return color[i];
	}
	
	void play(){
		String c;
		for(int i=0;i<L.size();i++){
			try {
				c=L.get(i);
				container.changeImgOver(c);	
				container.repaint();
				Thread.sleep(1000);
				container.changeImgOver(null);	
				container.repaint();
				Thread.sleep(1000);
				
			} catch (InterruptedException e) {}
			
			
		}
	}
	
	class ClickListener implements MouseListener{
		public void mouseReleased(MouseEvent e){
			if(state==0){
				inGameMouseReleased();
			}					
		}
		public void mouseEntered(MouseEvent e){}
		public void mouseExited(MouseEvent e){}
		public void mouseClicked(MouseEvent e){}
		
		public void mousePressed(MouseEvent e){
			int x=e.getX(),y=e.getY();

			if(state==0){
				inGameMousePressed(x,y);
			}
			else if(state==1){
				inRulesMousePressed();
			}
			else if(state==2){
				inEndGameMousePressed();
			}
		}
		
		void inGameMouseReleased(){
			try{
				Thread.sleep(200);
				container.changeImgOver(null);	
				container.repaint();
			}catch(InterruptedException ex){}		
		}
		
		void inGameMousePressed(int x, int y){
			String c;
			if(x>=colorSelect[0].x && y>=colorSelect[0].y && x<(colorSelect[0].x+colorSelect[0].width) && y<(colorSelect[0].y+colorSelect[0].height) && click){
				container.changeImgOver(color[0]);
				container.repaint();
				
				c=L.get(checkPos);
				if(c!=color[0])
					lose=true;
				else if(checkPos+1==L.size()){
					finish=true;
					click=false;
					checkPos=0;
				}
				else
					checkPos++;
				
			}
			else if(x>=colorSelect[1].x && y>=colorSelect[1].y && x<(colorSelect[1].x+colorSelect[1].width) && y<(colorSelect[1].y+colorSelect[1].height) && click){
				container.changeImgOver(color[1]);
				container.repaint();
				
				c=L.get(checkPos);
				if(c!=color[1])
					lose=true;
				else if(checkPos+1==L.size()){
					finish=true;
					click=false;
					checkPos=0;
				}
				else
					checkPos++;
			}
			else if(x>=colorSelect[2].x && y>=colorSelect[2].y && x<(colorSelect[2].x+colorSelect[2].width) && y<(colorSelect[2].y+colorSelect[2].height) && click){
				container.changeImgOver(color[2]);
				container.repaint();
				
				c=L.get(checkPos);
				if(c!=color[2])
					lose=true;
				else if(checkPos+1==L.size()){
					finish=true;
					click=false;
					checkPos=0;
				}
				else
					checkPos++;
			}
			else if(x>=colorSelect[3].x && y>=colorSelect[3].y && x<(colorSelect[3].x+colorSelect[3].width) && y<(colorSelect[3].y+colorSelect[3].height) && click){
				container.changeImgOver(color[3]);
				container.repaint();
				
				c=L.get(checkPos);
				if(c!=color[3])
					lose=true;
				else if(checkPos+1==L.size()){
					finish=true;
					click=false;
					checkPos=0;
				}
				else
					checkPos++;
			}	
			else if(x>quit.x && x<quit.x+quit.width && y>quit.y && y<quit.y+quit.height){
				//click on "Quit"
				run=false;
			}
			else if(x>rules.x && x<rules.x+rules.width && y>rules.y && y<rules.y+rules.height){
				//click on "Rules"
				container.changeImgOver("rules.png");
				container.repaint();
				state=1;
			}
		}
		
		void inRulesMousePressed(){
			container.changeImgOver(null);
			container.repaint();
			state=0;
		}
		
		void inEndGameMousePressed(){
			run=false;
		}
	}
	
	class MyPanel extends JPanel {
		Image bg,over;
		String path="img/simon/";
		
		MyPanel(){		
			try {
				bg= ImageIO.read(new File(path+"Interface.png"));
				
			} catch (IOException e) {
		    	e.printStackTrace();
		    		
		    }
			over=null;
		}
		
		public void changeImgOver(String fileName){
			if(fileName!=null){
				try {
					over= ImageIO.read(new File(path+fileName));	
				} catch (IOException e) {
					e.printStackTrace();		
				}
			}else{over=null;}
		}
		
		public void paintComponent(Graphics g){	
			g.drawImage(bg, 0, 0, this.getWidth(), this.getHeight(), this); 
			g.drawImage(over, 0, 0, this.getWidth(), this.getHeight(), this); 
		}              
	}
	
}


