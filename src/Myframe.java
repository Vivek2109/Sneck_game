import javax.swing.*;
import javax.swing.event.MenuKeyListener;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Myframe extends JPanel implements ActionListener, KeyListener {
	private static final int Delay = 100;
	int x_pos[] = new int[850];
	int y_pos[] = new int[850];
	private ImageIcon heading = new ImageIcon(getClass().getResource("snaketitle.jpg"));
	private ImageIcon leftmouth = new ImageIcon(getClass().getResource("leftmouth.png"));
	private ImageIcon rightmouth = new ImageIcon(getClass().getResource("rightmouth.png"));
	private ImageIcon upmouth = new ImageIcon(getClass().getResource("upmouth.png"));
	private ImageIcon snakeimage = new ImageIcon(getClass().getResource("snakeimage.png"));
	private ImageIcon enemy = new ImageIcon(getClass().getResource("enemy.png"));
	private ImageIcon downmouth = new ImageIcon(getClass().getResource("downmouth.png"));
	private int en_xpos[] = { 25, 50, 75, 100, 125, 150, 175, 200, 225, 250, 275, 300, 325, 350, 375, 400, 425, 450,
			475, 500, 525, 550, 575, 600, 625, 650, 675, 700, 725, 750, 775, 800, 825, 850 };
	private int en_ypos[] = { 75, 100, 125, 150, 175, 200, 225, 250, 275, 300, 325, 350, 375, 400, 425, 450, 475, 500,
			525, 550, 575, 600, 625, 650 };

	private Random random = new Random();
	private int xpos, ypos;
	private boolean left = false;
	private boolean right = true;
	private boolean up = false;
	private boolean down = false;
	private boolean Game_over = false;
	private int score = 0;
	private int max_score = 0;
	int move = 0;
	int sneck_size = 3;
	int b_xpos;
	int b_ypos;

	private Timer timer;

	Myframe() {
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(true);
		timer = new Timer(Delay, this);
		timer.start();
		NewEnemy();
	}

	public void paint(Graphics g) {
		super.paint(g);
		g.drawRect(24, 10, 851, 576);
		g.setColor(Color.BLACK);
		heading.paintIcon(this, g, 25, 11);
		g.fillRect(24, 74, 851, 576);
		if (move == 0) {
			x_pos[0] = 75;
			x_pos[1] = 50;
			x_pos[2] = 25;
			y_pos[0] = 100;
			y_pos[1] = 100;
			y_pos[2] = 100;

		}

		if (left) {
			leftmouth.paintIcon(this, g, x_pos[0], y_pos[0]);
		}
		if (right) {
			rightmouth.paintIcon(this, g, x_pos[0], y_pos[0]);
		}
		if (up) {
			upmouth.paintIcon(this, g, x_pos[0], y_pos[0]);
		}
		if (down) {
			downmouth.paintIcon(this, g, x_pos[0], y_pos[0]);
		}
		for (int i = 1; i < sneck_size; i++) {
			snakeimage.paintIcon(this, g, x_pos[i], y_pos[i]);
		}

		enemy.paintIcon(this, g, xpos, ypos);

		if (Game_over) {
			g.setColor(Color.white);
			g.setFont(new Font("Areal", Font.BOLD, 50));
			g.drawString("Game Over", 300, 300);
			g.setFont(new Font("Areal", Font.PLAIN, 30));
			g.drawString("Press SPACE to Restart", 280, 350);
			g.dispose();
		}
		g.setColor(Color.white);
		g.setFont(new Font("Areal", Font.PLAIN, 14));
		g.drawString("Score :" + score, 750, 30);
		g.drawString("Length :" + sneck_size, 750, 50);
		g.drawString("Max Score :" + max_score, 30, 30);
		// g.drawString("By:-Vivek Gayakwad",30,50);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		for (int i = sneck_size; i > 0; i--) {
			x_pos[i] = x_pos[i - 1];
			y_pos[i] = y_pos[i - 1];
		}
		if (left) {
			x_pos[0] = x_pos[0] - 25;
		}
		if (up) {
			y_pos[0] = y_pos[0] - 25;
		}
		if (right) {
			x_pos[0] = x_pos[0] + 25;
		}
		if (down) {
			y_pos[0] = y_pos[0] + 25;
		}
		if (x_pos[0] > 850) {
			x_pos[0] = 25;
		}
		if (x_pos[0] < 25) {
			x_pos[0] = 850;
		}
		if (y_pos[0] > 625) {
			y_pos[0] = 75;
		}
		if (y_pos[0] < 75) {
			y_pos[0] = 625;
		}
		colide_enemy();// 142
		colide_body();

		repaint();
	}

	private void colide_body() {
		for (int i = sneck_size - 1; i > 0; i--) {
			if (x_pos[i] == x_pos[0] && y_pos[i] == y_pos[0]) {
				Game_over = true;
				max_score = Math.max(max_score, score);
				timer.stop();
			}
		}

	}

	private void colide_enemy() {
		if (x_pos[0] == xpos && y_pos[0] == ypos) {
			NewEnemy();
			sneck_size++;
			score++;
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT && !right) {
			left = true;
			right = false;
			up = false;
			down = false;
			move++;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT && !left) {
			left = false;
			right = true;
			up = false;
			down = false;
			move++;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP && !down) {
			left = false;
			right = false;
			up = true;
			down = false;
			move++;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN && !up) {
			left = false;
			right = false;
			up = false;
			down = true;
			move++;
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			restart();
		}
	}

	private void restart() {
		Game_over = false;
		move = 0;
		score = 0;
		sneck_size = 3;
		left = false;
		right = true;
		up = false;
		down = false;
		timer.start();

		repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	private void NewEnemy() {
		xpos = en_xpos[random.nextInt(34)];
		ypos = en_ypos[random.nextInt(23)];
		for (int i = sneck_size - 1; i >= 0; i--) {
			if (x_pos[i] == xpos && y_pos[i] == ypos) {
				NewEnemy();
			}
		}
		if (b_xpos == xpos && b_ypos == ypos)
			NewEnemy();
		if (b_xpos == xpos && b_ypos == ypos + 25)
			NewEnemy();
		if (b_xpos - 25 == xpos && b_ypos == ypos)
			NewEnemy();
		if (b_xpos - 25 == xpos && b_ypos + 25 == ypos)
			NewEnemy();
	}

}
