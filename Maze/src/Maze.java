import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;


public class Maze {

	Character [] [] m;
	Point p;
	
	public Maze(File f) throws IOException {
	    BufferedReader br = new BufferedReader(new FileReader(f));
	    try {
	        String line = br.readLine();
	        parseSize(line);
	        line = br.readLine();
	        for (int i = 0; i < m.length; i++) {
				for (int j = 0; j < m[i].length; j++) {
					if(line.charAt(j)!=' '){
						m[i][j] = 'X';
					} else {
						m[i][j] = ' ';
					}
				}
	            line = br.readLine();
	        }
            initPlayer();
	        
	    } catch (IOException e) {
			e.printStackTrace();
		} finally {
	        br.close();
	    }
	}

	private void initPlayer() {
		for (int i = 0; i < m.length; i++) {
			int width = m[i].length;
			if(m[i][width-1] == ' '){
				m[i][width-1] = 'P';
				p = new Point(width-1,i);
			}
		}
	}

	private void parseSize(String line) {
		String [] s = line.split(" ");
		Integer x = Integer.valueOf(s[0]);
		Integer y = Integer.valueOf(s[1]);
		m = new Character[y][x];
	}

	@Override
	public String toString() {
		String s = "";
		for (int i = 0; i < m.length; i++) {
			for (int j = 0; j < m[0].length; j++) {
				s+=m[i][j];
			}
			s+='\n';
		}
		return s;
	}
	
	public static void main(String[] args) {
		File f = new File("res/maze.txt");
		try {
			Maze m = new Maze(f);
			Scanner in = new Scanner(System.in);
			System.out.println("Welcome to the maze!");
			while(!m.gameOver()){
				System.out.println(m);
				String possibleMoves = m.getPossibleMoves();
				System.out.println("choose your move: "+possibleMoves);
				Character c = in.next().charAt(0);
				System.out.println("------------------------------------------------");
				m.move(c);
			}
			System.out.println("Congratulation you have escaped the maze!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String getPossibleMoves() {
		String possibleMoves = "";
		if(p.x-1 >= 0 && m[p.y][p.x-1]==' '){
			possibleMoves += "left(a) ";
		} 
		if(p.x+1 < m[p.y].length && m[p.y][p.x+1]==' '){
			possibleMoves += "right(d) ";
		}
		if(p.y-1 >=0 && m[p.y-1][p.x]==' '){
			possibleMoves += "up(w) ";
		}
		if(p.y+1 < m.length && m[p.y+1][p.x]==' '){
			possibleMoves += "down(s) ";
		}
		return possibleMoves;
	}

	private void move(Character c) {
		Point target = null;
		if(c == 'a'){
			target = new Point(p.x-1,p.y);
		} else if(c =='d'){
			target = new Point(p.x+1,p.y);			
		} else if(c =='w'){
			target = new Point(p.x,p.y-1);			
		} else if(c =='s'){
			target = new Point(p.x,p.y+1);			
		}
		if(target == null){
			System.out.println("No such action");
		} else if(m[target.y][target.x] != ' '){
			System.out.println("Can't walk into walls");
		} else {
			m[p.y][p.x] = ' ';
			p = target;
			m[p.y][p.x] = 'P';			
		}
		
	}

	private boolean gameOver() {
		return (p.x == 0);
	}
}
