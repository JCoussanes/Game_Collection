import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;






public class HangMan {
	MyPanel container;
	Rectangle rules,quit;
	Rectangle[][] alpha=new Rectangle[7][4];
	char playerAns;
	int state,errors;
	boolean run,finish,toHang,win;
	boolean[][] clickable=new boolean[7][4];
	String word;
	char[] toDisplay;
	
	HangMan(){
		container=new MyPanel();	   
		for(int i=0;i<7;i++){
	    	for(int j=0;j<4;j++){
	    		alpha[i][j]=new Rectangle(386+(79*j),118+(i*51),75,45);
	    		clickable[i][j]=true;
	    	}
	    }	    
	    rules=new Rectangle(20,10,180,50);
	    quit=new Rectangle(20,440,150,50);
	    
	    container.addMouseListener(new ClickListener());

	         
	}
	
	int kernel(){
	
		init();
		while(run){
			System.out.print("");
			while(errors<11){
				System.out.print("");
				if(!run){
					break;
				}
				
				if(finish){
					check();
					finish=false;
				}
				if(win){
					break;
				}
			}
			state=2;
			if(win){
				container.changeImgOver("win.png");
			}else{
				container.changeImgOver("lose.png");
			}
			container.changeString(word);
			container.repaint();

		}

		return 0;
	}
	
	void init(){
		container.init();
		chooseWord();
		toDisplay=word.toCharArray();
		for(int i=0;i<toDisplay.length;i++){
			toDisplay[i]='-';
		}
		for(int i=0;i<7;i++){
	    	for(int j=0;j<4;j++){
	    		clickable[i][j]=true;
	    	}
	    }
		container.changeString(String.copyValueOf(toDisplay));
		container.repaint();
		errors=0;
		state=0;
		run=true;
		win=false;
	}
	
	void check(){
		toHang=true;
		for(int i=0;i<word.length();i++){
			if(playerAns==word.charAt(i)){
				toDisplay[i]=playerAns;
				toHang=false;
			}
		}
		
		if(toHang){
			errors++;
		}
		
		container.changeImgUsed(clickable);
		container.changeString(String.copyValueOf(toDisplay));
		container.changeImgHang(errors);
		container.repaint();
		
		if(container.screen.compareToIgnoreCase(word)==0){
			win=true;
		}
			
	}
	
	void chooseWord(){
		LinkedList<String> l=new LinkedList<String>();
		Scanner sc=null;
		Random random=new Random();
		int i;
		try {
			sc=new Scanner(new File("img/hang_man/word.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while(sc.hasNext()){
			l.add(sc.next());
		}
		i=random.nextInt(l.size());
		
		word=l.get(i);		
	}	

	class ClickListener implements MouseListener{
		public void mouseReleased(MouseEvent e){}
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
		
		void inGameMousePressed(int x, int y){
						
			if(x>alpha[0][0].x && x<alpha[0][0].x+alpha[0][0].width && y>alpha[0][0].y && y<alpha[0][0].y+alpha[0][0].height && clickable[0][0]){
				//click on A

				playerAns='a';
				clickable[0][0]=false;	
				finish=true;
			}
			if(x>alpha[0][1].x && x<alpha[0][1].x+alpha[0][1].width && y>alpha[0][1].y && y<alpha[0][1].y+alpha[0][1].height && clickable[0][1]){
				//click on B

				playerAns='b';
				clickable[0][1]=false;	
				finish=true;
			}
			if(x>alpha[0][2].x && x<alpha[0][2].x+alpha[0][2].width && y>alpha[0][2].y && y<alpha[0][2].y+alpha[0][2].height && clickable[0][2]){
				//click on C

				playerAns='c';
				clickable[0][2]=false;	
				finish=true;
			}
			if(x>alpha[0][3].x && x<alpha[0][3].x+alpha[0][3].width && y>alpha[0][3].y && y<alpha[0][3].y+alpha[0][3].height && clickable[0][3]){
				//click on D

				playerAns='d';
				clickable[0][3]=false;	
				finish=true;
			}
			if(x>alpha[1][0].x && x<alpha[1][0].x+alpha[1][0].width && y>alpha[1][0].y && y<alpha[1][0].y+alpha[1][0].height && clickable[1][0]){
				//click on E

				playerAns='e';
				clickable[1][0]=false;	
				finish=true;
			}
			if(x>alpha[1][1].x && x<alpha[1][1].x+alpha[1][1].width && y>alpha[1][1].y && y<alpha[1][1].y+alpha[1][1].height && clickable[1][1]){
				//click on F

				playerAns='f';
				clickable[1][1]=false;	
				finish=true;
			}
			if(x>alpha[1][2].x && x<alpha[1][2].x+alpha[1][2].width && y>alpha[1][2].y && y<alpha[1][2].y+alpha[1][2].height && clickable[1][2]){
				//click on G

				playerAns='g';
				clickable[1][2]=false;	
				finish=true;
			}
			if(x>alpha[1][3].x && x<alpha[1][3].x+alpha[1][3].width && y>alpha[1][3].y && y<alpha[1][3].y+alpha[1][3].height && clickable[1][3]){
				//click on H

				playerAns='h';
				clickable[1][3]=false;	
				finish=true;
			}
			if(x>alpha[2][0].x && x<alpha[2][0].x+alpha[2][0].width && y>alpha[2][0].y && y<alpha[2][0].y+alpha[2][0].height && clickable[2][0]){
				//click on I

				playerAns='i';
				clickable[2][0]=false;	
				finish=true;
			}
			if(x>alpha[2][1].x && x<alpha[2][1].x+alpha[2][1].width && y>alpha[2][1].y && y<alpha[2][1].y+alpha[2][1].height && clickable[2][1]){
				//click on J

				playerAns='j';
				clickable[2][1]=false;	
				finish=true;
			}
			if(x>alpha[2][2].x && x<alpha[2][2].x+alpha[2][2].width && y>alpha[2][2].y && y<alpha[2][2].y+alpha[2][2].height && clickable[2][2]){
				//click on K

				playerAns='k';
				clickable[2][2]=false;	
				finish=true;
			}
			if(x>alpha[2][3].x && x<alpha[2][3].x+alpha[2][3].width && y>alpha[2][3].y && y<alpha[2][3].y+alpha[2][3].height && clickable[2][3]){
				//click on L

				playerAns='l';
				clickable[2][3]=false;	
				finish=true;
			}
			if(x>alpha[3][0].x && x<alpha[3][0].x+alpha[3][0].width && y>alpha[3][0].y && y<alpha[3][0].y+alpha[3][0].height && clickable[3][0]){
				//click on M

				playerAns='m';
				clickable[3][0]=false;	
				finish=true;
			}
			if(x>alpha[3][1].x && x<alpha[3][1].x+alpha[3][1].width && y>alpha[3][1].y && y<alpha[3][1].y+alpha[3][1].height && clickable[3][1]){
				//click on N

				playerAns='n';
				clickable[3][1]=false;	
				finish=true;
			}
			if(x>alpha[3][2].x && x<alpha[3][2].x+alpha[3][2].width && y>alpha[3][2].y && y<alpha[3][2].y+alpha[3][2].height && clickable[3][2]){
				//click on O

				playerAns='o';
				clickable[3][2]=false;	
				finish=true;
			}
			if(x>alpha[3][3].x && x<alpha[3][3].x+alpha[3][3].width && y>alpha[3][3].y && y<alpha[3][3].y+alpha[3][3].height && clickable[3][3]){
				//click on P

				playerAns='p';
				clickable[3][3]=false;
				finish=true;
			}
			if(x>alpha[4][0].x && x<alpha[4][0].x+alpha[4][0].width && y>alpha[4][0].y && y<alpha[4][0].y+alpha[4][0].height && clickable[4][0]){
				//click on Q
				playerAns='q';
				clickable[4][0]=false;	
				finish=true;
			}
			if(x>alpha[4][1].x && x<alpha[4][1].x+alpha[4][1].width && y>alpha[4][1].y && y<alpha[4][1].y+alpha[4][1].height && clickable[4][1]){
				//click on R

				playerAns='r';
				clickable[4][1]=false;	
				finish=true;
			}
			if(x>alpha[4][2].x && x<alpha[4][2].x+alpha[4][2].width && y>alpha[4][2].y && y<alpha[4][2].y+alpha[4][2].height && clickable[4][2]){
				//click on S

				playerAns='s';
				clickable[4][2]=false;	
				finish=true;
			}
			if(x>alpha[4][3].x && x<alpha[4][3].x+alpha[4][3].width && y>alpha[4][3].y && y<alpha[4][3].y+alpha[4][3].height && clickable[4][3]){
				//click on T

				playerAns='t';
				clickable[4][3]=false;
				finish=true;
			}
			if(x>alpha[5][0].x && x<alpha[5][0].x+alpha[5][0].width && y>alpha[5][0].y && y<alpha[5][0].y+alpha[5][0].height && clickable[5][0]){
				//click on U

				playerAns='u';
				clickable[5][0]=false;
				finish=true;
			}
			if(x>alpha[5][1].x && x<alpha[5][1].x+alpha[5][1].width && y>alpha[5][1].y && y<alpha[5][1].y+alpha[5][1].height && clickable[5][1]){
				//click on V

				playerAns='v';
				clickable[5][1]=false;	
				finish=true;
			}
			if(x>alpha[5][2].x && x<alpha[5][2].x+alpha[5][2].width && y>alpha[5][2].y && y<alpha[5][2].y+alpha[5][2].height && clickable[5][2]){
				//click on W

				playerAns='w';
				clickable[5][2]=false;	
				finish=true;
			}
			if(x>alpha[5][3].x && x<alpha[5][3].x+alpha[5][3].width && y>alpha[5][3].y && y<alpha[5][3].y+alpha[5][3].height && clickable[5][3]){
				//click on X
	
				playerAns='x';
				clickable[5][3]=false;	
				finish=true;
			}
			if(x>alpha[6][1].x && x<alpha[6][1].x+alpha[6][1].width && y>alpha[6][1].y && y<alpha[6][1].y+alpha[6][1].height && clickable[6][1]){
				//click on Y

				playerAns='y';
				clickable[6][1]=false;	
				finish=true;
			}
			if(x>alpha[6][2].x && x<alpha[6][2].x+alpha[6][2].width && y>alpha[6][2].y && y<alpha[6][2].y+alpha[6][2].height && clickable[6][2]){
				//click on Z

				playerAns='z';
				clickable[6][2]=false;	
				finish=true;
			}
			if(x>quit.x && x<quit.x+quit.width && y>quit.y && y<quit.y+quit.height){
				//click on "Quit"
				run=false;
			}
			if(x>rules.x && x<rules.x+rules.width && y>rules.y && y<rules.y+rules.height){
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
	
	class MyPanel extends JPanel{
          Image bg,over,hang;
          Image[][] used=new Image[7][4];
          String screen=null;   
          String path="img/hang_man/";
          
          int[] xUsed=new int[4],yUsed=new int[7];
          int xString,yString;
          
          MyPanel(){
        	  try {
  				bg= ImageIO.read(new File(path+"Interface.png"));
  				
  				} catch (IOException e){
  					e.printStackTrace();	
  				}	
        	  for(int i=0;i<7;i++){
        		  yUsed[i]=118+(i*51);
        	  }
        	  for(int i=0;i<4;i++){
        		  xUsed[i]=386+(79*i);
        	  }
        	  yString=90;
        	  xString=380;
        		
        	  init();
  			
          }
         
          void init(){

			hang=null;
			over=null;
        	  
        	  for(int i=0;i<7;i++){
        		  for(int j=0;j<4;j++){
        		      used[i][j]=null;
        		  }
       	  	  }
        	  used[6][0]=null;
        	  used[6][3]=null;
         }
          
         public void changeString(String str){
        	 screen=str;
         }
          
         public void changeImgHang(int nb){
   			if(nb!=0){
   				try {
   					hang= ImageIO.read(new File(path+nb+".png"));	
   				} catch (IOException e) {
    				e.printStackTrace();		
    			}
    		}else{hang=null;}
          }
         
          public void changeImgUsed(boolean[][] table){
        	  for(int i=0;i<7;i++){
        		  for(int j=0;j<4;j++){
        			  if(table[i][j]==false){
        				  try {
        					  used[i][j]= ImageIO.read(new File(path+"used.png"));	
        				  } catch (IOException e) {
        					  e.printStackTrace();		
        				  }
        			  }else{used[i][j]=null;}
        		  }
        	  }
        	  used[6][0]=null;
        	  used[6][3]=null;
        	  
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
        	 g.setFont(new Font("Courier", Font.PLAIN, 20));
  			g.drawImage(bg, 0, 0, this.getWidth(), this.getHeight(), this); 
  			g.drawImage(hang, 0, 0, this.getWidth(), this.getHeight(), this); 
  			for(int i=0;i<7;i++){
  				for(int j=0;j<4;j++){
  		  			g.drawImage(used[i][j], xUsed[j],yUsed[i], 75,45, this); 

  				}
  			}
  			if(screen!=null){
  				g.drawString(screen, xString, yString);
  			}
  			g.drawImage(over, 0, 0, this.getWidth(), this.getHeight(), this); 
  			
  		}   
	}
}
