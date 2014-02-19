import java.text.AttributedCharacterIterator;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

public class MasterMind {
	MyPanel container;
	Rectangle[] playerSelect=new Rectangle[4];
	Rectangle[] shapeChange=new Rectangle[4];
	Rectangle[] colorChange=new Rectangle[4];
	Rectangle rules,quit,ok;
	String[] shapeName={"triangle.png","square.png","diamond.png","circle.png"};
	String[] colorName={"img/master_mind/blue/","img/master_mind/green/","img/master_mind/yellow/","img/master_mind/red/"};
	int[] colorPlayer=new int[4],colorCode=new int[4];
	int[] shapePlayer=new int[4],shapeCode=new int[4];
	int[] colorAns=new int[4];
	int[] shapeAns=new int[4];
	int[] colorShapeAns=new int[4];
	int state, turn, place;
	boolean finish,win,click,select,run;
	
	
	MasterMind(){
		container=new MyPanel();
		

	    container.addMouseListener(new ClickListener());
	    
	    container.setVisible(true);
	   
	    
	    
	    for(int i=0;i<4;i++){
	    	playerSelect[i]=new Rectangle(520+(i*50),448,50,50);
	    	colorChange[i]=new Rectangle(520,52+(i*(50-1)*2),200,50);
	    	shapeChange[i]=new Rectangle(520,(i*(50-1)*2),200,50);
	    	
	    	Random r=new Random();
	     	colorCode[i]=r.nextInt(4);
	    	shapeCode[i]=r.nextInt(4);
	    }
	    rules=new Rectangle(520,396,90,52);
	    quit=new Rectangle(610,396,55,52);
	    ok=new Rectangle(665,396,55,52);         	    
	}
	
	int kernel(){
		init();
		while(run){
			System.out.print("");
			while(turn<10){
				System.out.print("");
				if(!run){
					break;
				}
				if(finish){
				
					for(int i=0;i<4;i++){
						colorAns[i]=0;
						shapeAns[i]=0;
						colorShapeAns[i]=0;
					}
					click=false;
					check();
					finish=false;
					click=true;
					if(win){	
						break;
					}
					turn++;
				}
			}
			state=2;
			if(win && run){
				container.changeImgOver("img/master_mind/win.png");
				container.repaint();
			}else if(run){
				container.changeImgOver("img/master_mind/lose.png");
				container.repaint();
			}
		}
		return 0;
	}
	
	void init(){
		finish=false;
		win=false;
		click=true;
		select=false;
		run=true;
		state=0;
		turn=0;
		place=0;
		container.init();
		container.repaint();
		for(int i=0;i<4;i++){
			Random r=new Random();
	     	colorCode[i]=r.nextInt(4);
	    	shapeCode[i]=r.nextInt(4);
	    	colorPlayer[i]=0;
	    	shapePlayer[i]=0;
	    	colorAns[i]=0;
	    	shapeAns[i]=0;
	    	colorShapeAns[i]=0;
	    	
		}
	}
	
	void check(){
		for(int i=0;i<4;i++){
			String fileName=colorName[colorPlayer[i]]+shapeName[shapePlayer[i]];
			container.changeImgComb(fileName, i, turn);
			container.repaint();
			if(colorCode[i]==colorPlayer[i]){
				colorAns[i]=2;
			}
			if(shapeCode[i]==shapePlayer[i]){
				shapeAns[i]=2;
			}
			if(colorCode[i]==colorPlayer[i] && shapeCode[i]==shapePlayer[i]){
				colorShapeAns[i]=2;
			}
		}
		
		for(int i=0;i<4;i++){
			if(colorAns[i]!=2){
				for(int j=0;j<4;j++){
					if(colorAns[j]==0 && colorCode[i]==colorPlayer[j]){
						colorAns[j]=1;
						break;
					}
				}
			}
			if(shapeAns[i]!=2){
				for(int j=0;j<4;j++){
					if(shapeAns[j]==0 && shapeCode[i]==shapePlayer[j]){
						shapeAns[j]=1;
						break;
					}
				}
			}
			if(colorShapeAns[i]!=2){
				for(int j=0;j<4;j++){
					if(colorShapeAns[j]==0 && colorCode[i]==colorPlayer[j] && shapeCode[i]==shapePlayer[j]){
						colorShapeAns[j]=1;
						break;
					}
				}
			}
		}
		int c=0,s=0,cs=0;
		for(int i=0;i<4;i++){
			if(colorAns[i]==2){
				container.changeImgCAns("img/master_mind/red_light.png", c, turn);
				container.repaint();
				c++;
			}
			if(shapeAns[i]==2){
				container.changeImgSAns("img/master_mind/red_light.png", s, turn);
				container.repaint();
				s++;
			}
			
			if(colorShapeAns[i]==2){
				container.changeImgCSAns("img/master_mind/red_light.png", cs, turn);
				container.repaint();
				cs++;
			}
		}
		for(int i=0;i<4;i++){
			if(colorAns[i]==1){
				container.changeImgCAns("img/master_mind/white_light.png", c, turn);
				container.repaint();
				c++;
			}
			
			if(shapeAns[i]==1){
				container.changeImgSAns("img/master_mind/white_light.png", s, turn);
				container.repaint();
				s++;
			}
			
			if(colorShapeAns[i]==1){
				container.changeImgCSAns("img/master_mind/white_light.png", cs, turn);
				container.repaint();
				cs++;
			}
		}
		
		int ref=0;
		for(ref=0;ref<4;ref++){
			if(colorShapeAns[ref]!=2){
				break;
			}
		}
		if(ref==4){
			win=true;
		}
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
			if(x>playerSelect[0].x && x<playerSelect[0].x+playerSelect[0].width && y>playerSelect[0].y && y<playerSelect[0].y+playerSelect[0].height){
				//click on 1st 	
				
				place=0;
				select=true;
			}
			if(x>playerSelect[1].x && x<playerSelect[1].x+playerSelect[1].width && y>playerSelect[1].y && y<playerSelect[1].y+playerSelect[1].height){
				//click on 2nd 
				
				place=1;
				select=true;
			}
			if(x>playerSelect[2].x && x<playerSelect[2].x+playerSelect[3].width && y>playerSelect[2].y && y<playerSelect[2].y+playerSelect[2].height){
				//click on 3rd	
				place=2;
				select=true;
			}
			if(x>playerSelect[3].x && x<playerSelect[3].x+playerSelect[3].width && y>playerSelect[3].y && y<(playerSelect[3].y+playerSelect[3].height)){
				//click on 4th
				place=3;
				select=true;
			}
			if(x>shapeChange[0].x && x<shapeChange[0].x+shapeChange[0].width && y>shapeChange[0].y && y<shapeChange[0].y+shapeChange[0].height && select){
				//click on triangle 
				shapePlayer[place]=0;
				String fileName=colorName[colorPlayer[place]]+shapeName[shapePlayer[place]];
				container.changeImgPlayer(fileName, place);
				container.repaint();
			}
			if(x>shapeChange[1].x && x<shapeChange[1].x+shapeChange[1].width && y>shapeChange[1].y && y<shapeChange[1].y+shapeChange[1].height && select){
				//click on square
				shapePlayer[place]=1;
				String fileName=colorName[colorPlayer[place]]+shapeName[shapePlayer[place]];
				container.changeImgPlayer(fileName, place);
				container.repaint();
			}
			if(x>shapeChange[2].x && x<shapeChange[2].x+shapeChange[2].width && y>shapeChange[2].y && y<shapeChange[2].y+shapeChange[2].height && select){
				//click on diamond 
				shapePlayer[place]=2;
				String fileName=colorName[colorPlayer[place]]+shapeName[shapePlayer[place]];
				container.changeImgPlayer(fileName, place);
				container.repaint();
			}
			if(x>shapeChange[3].x && x<shapeChange[3].x+shapeChange[3].width && y>shapeChange[3].y && y<shapeChange[3].y+shapeChange[3].height && select){
				//click on circle
				shapePlayer[place]=3;
				String fileName=colorName[colorPlayer[place]]+shapeName[shapePlayer[place]];
				container.changeImgPlayer(fileName, place);
				container.repaint();
			}
			if(x>colorChange[0].x && x<colorChange[0].x+colorChange[0].width && y>colorChange[0].y && y<colorChange[0].y+colorChange[0].height && select){
				//click on blue
				colorPlayer[place]=0;
				String fileName=colorName[colorPlayer[place]]+shapeName[shapePlayer[place]];
				container.changeImgPlayer(fileName, place);
				container.repaint();
			}
			if(x>colorChange[1].x && x<colorChange[1].x+colorChange[1].width && y>colorChange[1].y && y<colorChange[1].y+colorChange[1].height && select){
				//click on green
				colorPlayer[place]=1;
				String fileName=colorName[colorPlayer[place]]+shapeName[shapePlayer[place]];
				container.changeImgPlayer(fileName, place);
				container.repaint();
			}
			if(x>colorChange[2].x && x<colorChange[2].x+colorChange[2].width && y>colorChange[2].y && y<colorChange[2].y+colorChange[2].height && select){
				//click on yellow
				colorPlayer[place]=2;
				String fileName=colorName[colorPlayer[place]]+shapeName[shapePlayer[place]];
				container.changeImgPlayer(fileName, place);
				container.repaint();
			}
			if(x>colorChange[3].x && x<colorChange[3].x+colorChange[3].width && y>colorChange[3].y && y<colorChange[3].y+colorChange[3].height && select){
				//click on red
				colorPlayer[place]=3;
				String fileName=colorName[colorPlayer[place]]+shapeName[shapePlayer[place]];
				container.changeImgPlayer(fileName, place);
				container.repaint();
			}
			if(x>quit.x && x<quit.x+quit.width && y>quit.y && y<quit.y+quit.height){
				//click on "Quit"
				run=false;
			}
			if(x>rules.x && x<rules.x+rules.width && y>rules.y && y<rules.y+rules.height){
				//click on "Rules"
				container.changeImgOver("img/master_mind/rules.png");
				container.repaint();
				state=1;
			}
			if(x>ok.x && x<ok.x+ok.width && y>ok.y && y<ok.y+ok.height && select){
				//click on "OK"
				finish=true;
				place=0;
				select=false;
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
		Image bg;
		Image[][] imgComb=new Image[10][4],imgCAns=new Image[10][4],imgSAns=new Image[10][4],imgCSAns=new Image[10][4];
		Image[] player=new Image[4];
		Image over;
		int[] xComb=new int[4],xCAns=new int[4],xSAns=new int[4],xCSAns=new int[4];
		int[] yComb=new int[10],yAns=new int[10];
		int[] xPlayer=new int[4];
		int yPlayer=448;
		MyPanel(){	
			try {
				bg=ImageIO.read(new File("img/master_mind/Interface.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			for(int i=0;i<4;i++){
				try {
					player[i]=ImageIO.read(new File("img/master_mind/blue/triangle.png"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			over=null;
			
			for(int i=0;i<10;i++){
				this.yComb[i]=i*50-1;
				this.yAns[i]=12+((i*50)-1);
				for(int j=0;j<4;j++){
					
					imgComb[i][j]=null;
					imgCAns[i][j]=null;
					imgSAns[i][j]=null;
					imgCSAns[i][j]=null;		
				}
			}
			for(int i=0;i<4;i++){
				this.xPlayer[i]=520+(i*50);
				this.xComb[i]=i*50;
				this.xCAns[i]=205+(i*25);
				this.xSAns[i]=310+(i*25);
				this.xCSAns[i]=415+(i*25);
			}
		}
		
		void init(){
			for(int i=0;i<4;i++){
				try {
					player[i]=ImageIO.read(new File("img/master_mind/blue/triangle.png"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			over=null;
			for(int i=0;i<10;i++){
				for(int j=0;j<4;j++){
					imgComb[i][j]=null;
					imgCAns[i][j]=null;
					imgSAns[i][j]=null;
					imgCSAns[i][j]=null;		
				}
			}
		}
		
		public void changeImgComb(String fileName,int xIndex,int yIndex){
			try {
				imgComb[yIndex][xIndex]= ImageIO.read(new File(fileName));
			} catch (IOException e) {
	    		e.printStackTrace();
		    		
	    	}
		}
		
		public void changeImgCAns(String fileName,int xIndex,int yIndex){
			try {
				imgCAns[yIndex][xIndex]= ImageIO.read(new File(fileName));
			} catch (IOException e) {
	    		e.printStackTrace();
		    		
	    	}
		}
		
		public void changeImgSAns(String fileName,int xIndex,int yIndex){
			try {
				imgSAns[yIndex][xIndex]= ImageIO.read(new File(fileName));
			} catch (IOException e) {
	    		e.printStackTrace();
		    		
	    	}
		}
		
		public void changeImgCSAns(String fileName,int xIndex,int yIndex){
			try {
				imgCSAns[yIndex][xIndex]= ImageIO.read(new File(fileName));
			} catch (IOException e) {
	    		e.printStackTrace();
		    		
	    	}
		}
		
		public void changeImgPlayer(String fileName,int xIndex){
			try {
				player[xIndex]= ImageIO.read(new File(fileName));
			} catch (IOException e) {
		    	e.printStackTrace();
		    		
		    }
		}
		
		public void changeImgOver(String fileName){
			if(fileName!=null){
				try {
			
					over= ImageIO.read(new File(fileName));
				} catch (IOException e) {
					e.printStackTrace();	
				}
			}else{over=null;}
		}
		
		public void paintComponent(Graphics g){	
			g.drawImage(bg, 0, 0, this.getWidth(),this.getHeight(), this);
			for(int i=0;i<4;i++){
				g.drawImage(player[i], xPlayer[i], yPlayer, 50,50, this); 
			}
			for(int i=0;i<10;i++){
				for(int j=0;j<4;j++){
					if(imgComb[i][j]!=null){
						g.drawImage(imgComb[i][j], xComb[j], yComb[i], 50,50, this);
						g.drawImage(imgCAns[i][j], xCAns[j], yAns[i], 25,25, this); 
						g.drawImage(imgSAns[i][j], xSAns[j], yAns[i], 25,25, this); 
						g.drawImage(imgCSAns[i][j], xCSAns[j], yAns[i], 25,25, this); 
					}
				}
			}
			if(over!=null){
				g.drawImage(over, 0, 0, this.getWidth(),this.getHeight(), this);
			}
			
		}            
	}
}
	