package mainGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import mainGame.Game.STATE;

/**
 * The main Heads Up Display of the game
 *
 * @author Brandon Loehle 5/30/16
 *
 */

public class HUD {

	public double health = 100;
	private double healthMax = 100;
	private double greenValue = 255;

	private int score = 00000000000;

	private int level = 0;
	private String boss = "";
	private boolean isBoss = false;
	private boolean regen = false;
	private boolean isWave = false;
	private int timer = 60;
	private int healthBarWidth = 300;
	private int healthBarModifier = 2;
	private int voteCount = 0;
	private boolean doubleHealth = false;
	private String ability = "";
	private int abilityUses;
	private Color scoreColor = Color.black;
	private int extraLives = 0;
	private STATE state = null;
	private int hillarySpeedX = 20;
	private int hillarySpeedY = 20;
	
	public void tick() {
		health = Game.clamp(health, 0, health);
		greenValue = Game.clamp(greenValue, 0, 255);
		greenValue = health * healthBarModifier;

		score++;

		if (regen) {// regenerates health if that ability has been unlocked
			timer--;
			if (timer == 0 && health < 100) {
				health += 1;
				timer = 60;
			}
		}
	}

	public void render(Graphics g) {
		Font font = new Font("Roboto", 1, 20);
		Font font2 = new Font("Roboto", 1, 40);
		Color color1 = new Color(0, 133, 180); // Blue
		Color color2 = new Color(255, 0, 255); // Pink

		g.setColor(color1);
		g.fillRect(15, 15, healthBarWidth, 30);
		g.setColor(new Color(75, (int) greenValue, 0));
		g.fillRect((int) 15, (int) 15, (int) health * 3, 30);
		g.setColor(color1);
		
		g.drawRect(15, 15, healthBarWidth, 30);
		g.setFont(font);
		g.setColor(Color.black);
		
		g.setFont(font2);
		g.setColor(Color.black);
		g.drawString("Score: " + score, 50, 100);
		
		if (isBoss == false) {
		g.drawString("Level: " + level, 475, 45);
		} else {
			g.drawString("   " + boss, 15, 150);
		}
	}

	public void setAbility(String ability) {
		this.ability = ability;
	}

	public int getAbilityUses() {
		return this.abilityUses;
	}

	public void setAbilityUses(int abilityUses) {
		this.abilityUses = abilityUses;
	}

	public void updateScoreColor(Color color) {
		this.scoreColor = color;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getScore() {
		return score;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getBossLevel(){
		return boss;
	}

	public void setBossLevel(String boss){
		this.boss = boss;
	}

	public boolean getBoss(){
		return isBoss;
	}

	public void setBoss(boolean isBoss){
		this.isBoss = isBoss;
	}

	public void setHealth(double d) {
		this.health = d;
	}

	public void setRegen() {
		regen = true;
	}

	public void resetRegen() {
		regen = false;
	}

	public void setExtraLives(int lives) {
		this.extraLives = lives;
	}

	public int getExtraLives() {
		return this.extraLives;
	}


	public void healthIncrease() {
		doubleHealth = true;
		healthMax = 200;
		this.health = healthMax;
		healthBarModifier = 1;
		healthBarWidth = 800;
	}

	public void resetHealth() {
		doubleHealth = false;
		healthMax = 100;
		this.health = healthMax;
		healthBarModifier = 2;
		healthBarWidth = 400;
	}

	public void restoreHealth() {
		this.health = healthMax;
	}
	
	//used to set the variable gamestate to whatever state the game is in
	//used mainly for coop implementation
	public void setState(STATE n) {
		state = n;
	}
	
	//used to update vote count to track in coop
	public void updateVote() {
		voteCount++;
	}
	
	public int getVote() {
		return voteCount;
	}
	
	public void resetVote() {
		voteCount = 0;
	}
	
	public STATE getState() {
		return state;
	}
	
	public void setWave(boolean wave){
		this.isWave = wave;
	}
	
	public boolean getWave(){
		return this.isWave;
	}
	
	public void setHillaryX(int hillaryX){
		this.hillarySpeedX = hillaryX;
	}
	
	public int getHillaryX(){
		return hillarySpeedX;
	}
	
	public void setHillaryY(int hillaryY){
		this.hillarySpeedY = hillaryY;
	}
	
	public int getHillaryY(){
		return hillarySpeedY;
	}
}
