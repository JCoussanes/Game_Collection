import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;


public class TicTacToe{

	MyPanel container;
	Rectangle[][] button = new Rectangle[3][3];
	Rectangle rules,quit;
	
	boolean finish,click,run,win,lose,tie;
	int state,mistake;
	int[][] matrixContent= new int[3][3];
	int[][] matrixIA = new int[3][3];

	Random rand =new Random(); 

	TicTacToe(){
		
		container=new MyPanel();

		container.addMouseListener(new ClickListener());
		
		container.setVisible(true);
		
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				int x,y;
				button[i][j]=new Rectangle();
				button[i][j].setSize(130,130);
				x=273+j*130;
				y=55+i*130;
				button[i][j].setLocation(x, y);
			}
		}
	    rules=new Rectangle(15,10,120,60);
	    quit=new Rectangle(15,445,125,495);
		
	}

int kernel(){
	init();
	while (run){
		System.out.print("");
		while(true){
			System.out.print("");
			if(!run){
	    		break;
	    	}	    	
			if(finish){	
	    		try {
	    			mistake=rand.nextInt(5);
	    			checkWin();
	    			if(win || lose||tie){
	    				state=2;
	    				break;
	    			}
					Thread.sleep(1000);	
	    			iaPlay();
	    			paintMarks();
	    			checkWin();
	    			if(win || lose||tie){
	    				state=2;
	    				break;
	    			}	
	    		} catch (InterruptedException e) {}
	    	}
		}
	if(win){
		container.changeImgOver("win.png");
		container.repaint();
	}else if(lose){
		container.changeImgOver("lose.png");
		container.repaint();
		}
	else if(tie){
		container.changeImgOver("tie.png");
		container.repaint();
		}
	}
		

	return 0;
}

	
void init(){
	if(rand.nextInt(4)>=2){
		finish=true;
	}
	click=true;
	run=true;
	state=0;
	win=false;
	lose=false;
	tie=false;
	
	/* Initialization of the matrixes */
	for(int i=0;i<3;i++){
		matrixIA[1][1]=3;
		for(int j=0;j<3;j++){
			matrixContent[i][j]=0;
			if((i==0 || i==2) && (j==0 || j==2))
				matrixIA[i][j]=2;
			if( ((i==0 || i==2) && j==1) || (i==1 && (j==0 || j==2)))
				matrixIA[i][j]=1;
		}
	}
	container.init();
	container.repaint();
}
	
void paintMarks(){
	System.out.println("Dans paintMarks");
	for(int i=0;i<3;i++){
		for(int j=0;j<3;j++){
			if(matrixContent[i][j]==3){
				container.imgTableChange("cross.png", i, j);
				container.repaint();
			}
			if(matrixContent[i][j]==7){
				container.imgTableChange("circle.png", i, j);
				container.repaint();
			}
		}
	}
}

/* Method needed for the IA to play */	
void iaPlay(){
	System.out.println("in ia");
	boolean played=false;
	for(int i=0;i<3;i++){
		System.out.println("in for ia");

		if(checkLine(i) && mistake!=3){
			System.out.println("line");
			completeLine(i);
			played=true;
			break;
		}
		if(checkCol(i) && mistake != 3){
			System.out.println("col");
			completeCol(i);
			played=true;
			break;
		}
		if(i<2)
			if((checkDiag(i)==0 || checkDiag(i)==1) && mistake!=3){
				System.out.println("diag");
				completeDiag(checkDiag(i));
				played=true;
				break;
			}
	}
	
	if(!played){
		System.out.println("in non play");
		completeWthMatrix();
	}
	finish=false;
	click=true;
	
}
	
/* 				Checking methods
 * *****************************************************
 * Check if one line/column/diagonal has a total of 6 or 14
 * Theses conditions means : 2 aligned cases occupied by the same player and an empty one
 * Recall : Stones value: player 3, IA 7
 */

boolean checkLine(int i){
	boolean gpos=false;
	if((matrixContent[i][0] +matrixContent[i][1] +matrixContent[i][2])==6 ||(matrixContent[i][0] +matrixContent[i][1] +matrixContent[i][2])==14)
		gpos=true;
	return gpos;
}

boolean checkCol(int i){
	boolean gpos=false;
	if((matrixContent[0][i] +matrixContent[1][i] +matrixContent[2][i])==6 ||(matrixContent[0][i] +matrixContent[1][i] +matrixContent[2][i])==14)
		gpos=true;
	return gpos;
}

int checkDiag(int i){
	int j=-1;
	if(i==0)
		if((matrixContent[0][0] +matrixContent[1][1] +matrixContent[2][2])==6 ||(matrixContent[0][0] +matrixContent[1][1] +matrixContent[2][2])==14)
			j=0;
	else
		if((matrixContent[0][2] +matrixContent[1][1] +matrixContent[2][0])==6 || (matrixContent[0][2] +matrixContent[1][1] +matrixContent[2][0])==14)
			j=1;
	return j;
}


/* 
 * 				Completion methods
 * *****************************************************
 * These methods complete a line/column/diagonal in order to win or block the player
 * 
 */

void completeLine(int i){
	for(int j=0;j<3;j++){
		if(matrixContent[i][j]==0){
			matrixContent[i][j]=7;
			matrixIA[i][j]=0;
		}	
	}
}

void completeCol(int i){
	for(int j=0;j<3;j++){
		if(matrixContent[j][i]==0){
			matrixContent[j][i]=7;
			matrixIA[j][i]=0;
		}
	}
}

void completeDiag(int i){
	System.out.println("in complete diag with i "+i);
	if(i==0){
		for(int j=0;j<3;j++)
			if(matrixContent[j][j]==0){
				matrixContent[j][j]=7;
				matrixIA[j][j]=0;
			}
	}
	else
	{	
		for(int j=0,k=2;j<3&&k>-1;j++,k--){
			if(matrixContent[j][k]==0){
				matrixContent[j][k]=7;
				matrixIA[j][k]=0;
			}
		}
	}
}

void completeWthMatrix (){
	int k=-1,l=-1,t=-1;
	int[][] lowcase ={{-1,-1},{-1,-1},{-1,-1},{-1,-1}};
	int[][] medcase = {{-1,-1},{-1,-1},{-1,-1},{-1,-1}};
	if(matrixIA[1][1]==3 && matrixContent[1][1]==0){
		k=l=1;
		t=k;
	}
	else{
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				if(matrixContent[i][j]==0 && matrixIA[i][j]==2){
					medcase[k+1][0]=i;
					medcase[k+1][1]=j;
					k++;
				}
				if(matrixContent[i][j]==0 && matrixIA[i][j]==1){
					lowcase[l+1][0]=i;
					lowcase[l+1][1]=j;
					l++;
				}	
			}
		}
		if(k!=-1){
			t=k+1;
			System.out.println(k);
			l=rand.nextInt(k+1);
			k=medcase[l][0];
			l=medcase[l][1];
		}
		else if(l!=-1){
			t=l;
			System.out.println(l);
			k=rand.nextInt(l+1);
			l=lowcase[k][1];
			k=lowcase[k][0];
			System.out.println(k + " " + l);
		}
	}
	if(t!=-1){
		matrixContent[k][l]=7;	
		matrixIA[k][l]=0;
	}
	if(t==-1 || t==0){
		tie=true;
	}
}
	
/* Method to figure out if there is a win or a lose */
void checkWin (){
			int valueCol,valueRow,valueDiagL=0,valueDiagR=0;
			for(int i=0;i<3;i++){
				valueCol=0;
				valueRow=0;
				valueDiagL+=matrixContent[i][i];
				valueDiagR+=matrixContent[i][2-i];
				for(int j=0;j<3;j++){
					valueRow+= matrixContent[i][j];
					valueCol+= matrixContent[j][i];
				}
				if(valueCol==9 || valueRow==9){
					win=true;
					break;
				}
				if(valueCol==21 || valueRow==21){
					lose=true;
					break;
				}
			}
			if(valueDiagL==9 ||valueDiagR==9){
				win=true;
			}
			if(valueDiagL==21 ||valueDiagR==21){
				lose=true;
			}
				
}

class ClickListener implements MouseListener {
		 
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {		
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
	public void mouseReleased(MouseEvent e) {}
	
	void inGameMousePressed(int x, int y){
		if(x>button[0][0].x && y>button[0][0].y && x<(button[0][0].x+button[0][0].width) && y<(button[0][0].y +button[0][0].height) && click && matrixContent[0][0]==0){
			container.imgTableChange("cross.png",0,0);
			container.repaint();
			matrixContent[0][0]=3;
			matrixIA[0][0]=0;
			finish=true;
			click=false;
		}
		if(x>button[0][1].x && y>button[0][1].y && x<(button[0][1].x+button[0][1].width) && y<(button[0][1].y +button[0][1].height) && click && matrixContent[0][1]==0){
			container.imgTableChange("cross.png",0,1);
			container.repaint();
			matrixContent[0][1]=3;
			matrixIA[0][1]=0;
			finish=true;
			click=false;
		}
		if(x>button[0][2].x && y>button[0][2].y && x<(button[0][2].x+button[0][2].width) && y<(button[0][2].y +button[0][2].height) && click && matrixContent[0][2]==0){
			container.imgTableChange("cross.png",0,2);
			container.repaint();
			matrixContent[0][2]=3;
			matrixIA[0][2]=0;
			finish=true;
			click=false;
		}
		if(x>button[1][0].x && y>button[1][0].y && x<(button[1][0].x+button[1][0].width) && y<(button[1][0].y +button[1][0].height) && click && matrixContent[1][0]==0){
			container.imgTableChange("cross.png",1,0);
			container.repaint();
			matrixContent[1][0]=3;
			matrixIA[1][0]=0;
			finish=true;
			click=false;
		}
		if(x>button[1][1].x && y>button[1][1].y && x<(button[1][1].x+button[1][1].width) && y<(button[1][1].y +button[1][1].height) && click && matrixContent[1][1]==0){
			container.imgTableChange("cross.png",1,1);
			container.repaint();
			matrixContent[1][1]=3;
			matrixIA[1][1]=0;
			finish=true;
			click=false;
		}
		if(x>button[1][2].x && y>button[1][2].y && x<(button[1][2].x+button[1][2].width) && y<(button[1][2].y +button[1][2].height) && click && matrixContent[1][2]==0){
			container.imgTableChange("cross.png",1,2);
			container.repaint();
			matrixContent[1][2]=3;
			matrixIA[1][2]=0;
			finish=true;
			click=false;
		}
		if(x>button[2][0].x && y>button[2][0].y && x<(button[2][0].x+button[2][0].width) && y<(button[2][0].y +button[2][0].height) && click && matrixContent[2][0]==0){
			container.imgTableChange("cross.png",2,0);
			container.repaint();
			matrixContent[2][0]=3;
			matrixIA[2][0]=0;
			finish=true;
			click=false;
		}
		if(x>button[2][1].x && y>button[2][1].y && x<(button[2][1].x+button[2][1].width) && y<(button[2][1].y +button[2][1].height) && click && matrixContent[2][1]==0){
			container.imgTableChange("cross.png",2,1);
			container.repaint();
			matrixContent[2][1]=3;
			matrixIA[2][1]=0;
			finish=true;
			click=false;
		}
		if(x>button[2][2].x && y>button[2][2].y && x<(button[2][2].x+button[2][2].width) && y<(button[2][2].y +button[2][2].height) && click && matrixContent[2][2]==0){
			container.imgTableChange("cross.png",2,2);
			container.repaint();
			matrixContent[2][2]=3;
			matrixIA[2][2]=0;
			finish=true;
			click=false;	
		}
		
	
		if(x>quit.x && x<quit.x+quit.width && y>quit.y && y<quit.y+quit.height){
			//click on "Quit"
			System.out.println("Click on quit");
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
	Image bg;
	Image[][] imgTable =new Image[3][3];
	Image over;
	int[] xTable=new int[3],yTable=new int[3];
	String path="img/tic_tac_toe/";

	
	
	MyPanel(){	
		try {
			bg=ImageIO.read(new File(path+"Interface.png"));
		} catch (IOException e) {

			e.printStackTrace();
		}
		for(int i=0;i<3;i++){
			this.yTable[i]= 50+ 130*i;
			this.xTable[i]=265 + 130*i;
		}
		init();

	}
	
	void init(){
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
					imgTable[i][j]=null;
			}
		}
		over=null;
	}
	
	public void imgTableChange(String fileName,int x,int y){
		try {
			imgTable[y][x]= ImageIO.read(new File(path+fileName));
		} catch (IOException e) {
	    	e.printStackTrace();
	    		
	    }
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
		g.drawImage(bg, 0, 0, this.getWidth(),this.getHeight(), this);
		
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				if(imgTable[i][j]!=null){
					g.drawImage(imgTable[i][j], xTable[i], yTable[j], 130,130, this);
				}
			}
		}
		if(over!=null){
			g.drawImage(over, 0, 0, this.getWidth(),this.getHeight(), this);
		}
		
	}            

}
}	


