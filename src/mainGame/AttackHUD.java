package mainGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import mainGame.Game.STATE;

/**
 * 
 * @author Kyle Horton
 * 
 * The heads up display for the Attack mode.
 *
 */
public class AttackHUD {
	
	// instance variables
	public double health;
	private double greenValue = 255;
	private int score = 0;
	private int level = 0;
	private String boss = "";
	private boolean isBoss = false;
	private boolean isAttack = false;
	private int healthBarWidth = 300;
	private int healthBarModifier = 2;
	private int abilityUses;
	private Color scoreColor = Color.white;
	private int ammo = 300;
	private int mag = 300;
	
	public void tick() {
		health = Game.clamp(health, 0, health);
		greenValue = Game.clamp(greenValue, 0, 255);
		greenValue = health * healthBarModifier;
	}

	public void render(Graphics g) {
		Font font = new Font("Roboto", 1, 20);
		Font font2 = new Font("Roboto", 1, 40);
		Color color1 = new Color(0, 255, 255); // Blue
		Color color2 = new Color(255, 0, 255); // Pink

		g.setColor(Color.GRAY);
		g.fillRect(15, 15, healthBarWidth, 30);
		g.setColor(new Color(75, (int) greenValue, 0));
		g.fillRect((int) 15, (int) 15, (int) health *3, 30);
		g.setColor(Color.black);
		
		g.drawRect(15, 15, healthBarWidth, 30);
		g.setFont(font);
		
		g.drawString("Score: " + score, 15, 80);
		g.setColor(Color.black);
		
		// switches display based on if player has ammo or needs to reload
		if (ammo > 0){
		g.drawString("Ammo: " + ammo + "/" + mag , 15, 110);
		} if (ammo == 0 && mag > 0) {
			g.setFont(font2);
			g.drawString("PRESS 'ENTER' TO RELOAD!" , 325, 375);
		} if (ammo == 0 && mag == 0) {
			g.drawString("OUT OF AMMO!!!" , 600, 60);
		}
		
		// switches display based on if player is on boss or not
		g.setFont(font2);
		g.setColor(color2);
		if (isBoss == false) {
		g.drawString("WAVE " + level, 475, 45);
		} else {
			g.drawString("" + boss, 475, 45);
		}
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
	
	public boolean getAttack(){
		return this.isAttack;
	}
	
	public void setAttack(boolean isAttack){
		this.isAttack = isAttack;
	}

	public void setHealth(double d) {
		this.health = d;
	}
	
	public void setAmmo(int ammo){
		this.ammo = ammo;
	}
	
	public int getAmmo(){
		return ammo;
	}
	
	public void setMag(int mag){
		this.mag = mag;
	}
	
	public int getMag(){
		return mag;
	}

}
