import java.util.*;
import javax.swing.*;
import java.awt.*;
import javax.imageio.*;
import java.awt.image.*;
import java.io.*;
import java.awt.event.*;

public class Dutch extends JPanel implements MouseMotionListener, MouseListener{
	static final long serialVersionUID = 0L;
	static String title = "Dutch Solitaire";
	static String path = "res/";
	static JFrame frame;
	static int h = 100;
	static int w = h*2/3; 
	static final int WIDTH = w*14;
	static final int HEIGHT = h*4;
	static char[] suits = {'D','C','H','S'};
	static char[] values = {'A','2','3','4','5','6','7','8','9','T','J','Q','K'};
	static Card[][] board = new Card[4][14];
	static Color back = new Color(0, 153, 0);
	static Card dragged;
	public Dutch(){
		try{
			for(int i=0;i<suits.length;i++){
				for(int j = 0;j<values.length;j++){
					String key = String.valueOf(values[j]=='T'?10+"":values[j])+suits[i];
					String p = path+key+".png";
					board[i][j] = new Card(suits[i],values[j],i,j,ImageIO.read(new File(p)));
					board[i][j].x = j*w;
					board[i][j].y = i*h;
				}
				swap(board, i, 0, i, board[i].length-1);
			}
			swap(board, 0, 4, 0, 13, 50);
		}catch(IOException e){
			e.printStackTrace();
			System.exit(0);
		}
		frame = new JFrame(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.getContentPane().addMouseMotionListener(this);
		frame.getContentPane().addMouseListener(this);
		setPreferredSize(new Dimension(WIDTH,HEIGHT));
		frame.add(this);
		frame.getContentPane().setPreferredSize(new Dimension(WIDTH,HEIGHT));
		frame.pack();
		frame.setVisible(true);
	}
	public void debug(Object... a){
		System.out.println(Arrays.toString(a));
	}
	public void paintComponent(Graphics g){
		g.setColor(back);
		g.fillRect(0,0,WIDTH,HEIGHT);
		int x = 0;
		int y = 0;
		for(Card[] row:board){
			for(Card card:row){
				if(card!=null&&card==dragged)
					g.drawImage(card.img, card.x, card.y, w, h, null);
				else if(card!=null)
					g.drawImage(card.img, x, y, w, h, null);
				x+=w;
				if(x>WIDTH){
					x=0;
					y+=h;
				}
			}
		}
	}
	public static void main(String[] args) {
		new Dutch();
	}
	public void mouseReleased(MouseEvent e){
		int x = e.getX();
		int y = e.getY();
		int r = x/w;
		int c = y/h;
		debug(4);
		if(dragged!=null){
			swap(board, r, c, dragged.r, dragged.c);
			debug(3);
		}
	}
	public void mouseExited(MouseEvent e){
		
	}
	public void mouseEntered(MouseEvent e){
	
	}
	public void mousePressed(MouseEvent e){
	
	}
	public void mouseClicked(MouseEvent e){
	
	}
	public void mouseMoved(MouseEvent e){
	
	}
	public void mouseDragged(MouseEvent e){
		int x = e.getX();
		int y = e.getY();
		if(dragged!=null){
			dragged.x = x;
			dragged.y = y;
		}else{
			int r = x/w;
			int c = y/h;
			if(r<board.length&&c<board[0].length-2&&c>=0&&r>=0){
				debug(1);
				dragged = board[r][c];
			}
		}
	}
	public void swap(Card[][] arr, int ri, int re, int ci, int ce, int times){
		for(int i=0;i<times;i++){
			int r = ri+(int)(Math.random()*(re-ri));
			int c = ci+(int)(Math.random()*(ce-ci));
			int r2 = ri+(int)(Math.random()*(re-ri));
			int c2 = ci+(int)(Math.random()*(ce-ci));
			// debug(r,c,r2,c2);
			Card temp = arr[r][c];
			arr[r][c] = arr[r2][c2];
			arr[r2][c2] = temp;
		}
	}
	public void swap(Card[][] arr, int ri, int ci, int re, int ce){
		Card temp = arr[ri][ci];
		if(temp!=null){
			temp.r = re;
			temp.c = ce;
		}
		arr[ri][ci] = arr[re][ce];
		if(arr[ri][ci]!=null){
			arr[ri][ci].r = ri;
			arr[ri][ci].c = ci;
		}
		arr[re][ce] = temp;
	}
}
class Card{
	BufferedImage img;
	int r;
	int c;
	int x;
	int y;
	char suit;
	char val;
	public Card(char s, char v, int x1, int y1, BufferedImage i){
		suit = s;
		val = v;
		img = i;
		r = x1;
		c = y1;
	}
}