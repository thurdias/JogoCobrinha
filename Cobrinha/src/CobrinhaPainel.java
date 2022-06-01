import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.Random;


import javax.swing.JPanel;

public class CobrinhaPainel extends JPanel implements ActionListener{
	private static final long serialVersionUID = 1L;
	static final int SCREEN_WIDTH = 600;
	static final int SCREEN_HEIGHT = 600;
	static final int UNIT_SIZE = 25;
	static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
	static final int DELAY = 75;
	final int x[] = new int[GAME_UNITS];
	final int y[] = new int[GAME_UNITS];
	int bodyP = 6;
	int applesEaten;
	int appleX;
	int appleY;
	char direction = 'R';
	boolean running = false;
	Timer timer;
	Random random;
	
	public CobrinhaPainel() {
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(new Color(22, 22, 22));
		this.setFocusable(true);
		this.addKeyListener(new MinhaKeyAdapter());
		startG();
	}
	public void startG() {
		newApple();
		running = true;
		timer = new Timer(DELAY, this);
		timer.start();
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	public void draw(Graphics g) {
		if (running) {
				for (int i = 0; i < SCREEN_HEIGHT/UNIT_SIZE; i++) {
				g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
				g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);	
			}
			g.setColor(Color.orange);
			g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
		
			for (int i = 0; i < bodyP; i++){
			if(i == 0) {
				g.setColor(new Color(138, 43, 226));
				g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
			}
			else {
				g.setColor(new Color(153, 50, 204));
				g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
			}
		}
			g.setColor(Color.orange);
			g.setFont(new Font("Ink Free",Font.BOLD, 40));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten))/2, g.getFont().getSize());
	}
		else {
			gameOver(g);
		}
}
	public void newApple() {
		appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
		appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
	}
	public void movimento() {
		for (int i = bodyP; i > 0; i--) {
			x[i] = x[i-1];
			y[i] = y[i-1];
		}
		switch(direction) {
		case 'U':
			y[0] = y[0] - UNIT_SIZE;
			break;
		case 'D':
			y[0] = y[0] + UNIT_SIZE;
			break;
		case 'L':
			x[0] = x[0] - UNIT_SIZE;
			break;
		case 'R':
			x[0] = x[0] + UNIT_SIZE;
			break;
		}
	}
	public void checarMaça() {
		if ((x[0] == appleX) && (y[0] == appleY)) {
			bodyP++;
			applesEaten++;
			newApple();
		}
	}
	public void checarColisao() {
		// Checa se a cabeça colide com o corpo
		for (int i = bodyP; i > 0; i--) {
			if ((x[0]) == x[i] && (y[0] == y[i])) {
				running = false;
			}
		}
		// Checa se a cabeça toca a borda esquerda
		if (x[0] < 0) {
			running = false;
		}
		// Checa se a cabeça toca a borda direita
		if (x[0] > SCREEN_WIDTH) {
			running = false;
		}
		// Checa se a cabeça toca o topo
		if (y[0] < 0) {
			running = false;
		}
		// Checa se a cabeça toca a borda direita
		if (y[0] > SCREEN_HEIGHT) {
			running = false;
		}	
		if (!running) {
			timer.stop();
		}
	}
	public void gameOver(Graphics g) {
		// Pontuação
		g.setColor(Color.orange);
		g.setFont(new Font("Ink Free",Font.BOLD, 40));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: " + applesEaten))/2, g.getFont().getSize());
		// Texto de Game Over
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free",Font.BOLD, 75));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (running) {
			movimento();
			checarMaça();
			checarColisao();
		}
		repaint();
	}
	
	public class MinhaKeyAdapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(direction != 'R') {
					direction = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(direction != 'L') {
					direction = 'R';
				}
				break;
			case KeyEvent.VK_UP:
				if(direction != 'D') {
					direction = 'U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if(direction != 'U') {
					direction = 'D';
				}
				break;
			}
		}
	}

}
