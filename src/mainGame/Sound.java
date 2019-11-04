/*
 * By: Rob Laudadio
 * 11/9/17
 * This class takes advantage of multi-threading
 * in Java by having each sound file play on a 
 * different thread game lag is reduced
 */

package mainGame;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound implements Runnable {
	private Clip clip;
	private File file;
	private AudioInputStream audioIn;

	@Override
	public void run() {

		// If loops determine which noise to play
		if (Thread.currentThread().getName() == "GameMusic") {
			System.out.println("Background Music Triggered");
			file = new File("Sound/GameMusic.wav");
		} else if (Thread.currentThread().getName() == "IncreaseHealthPickupNoise") {
			System.out.println("Increase Health Pickup Noise Triggered");
			file = new File("Sound/IncreaseHealthPickupNoise.wav");
		} else if (Thread.currentThread().getName() == "DecreaseHealthPickupNoise") {
			System.out.println("Decrease Health Pickup Noise Triggered");
			file = new File("Sound/DecreaseHealthPickupNoise.wav");
		} else if (Thread.currentThread().getName() == "IncreaseSpeedPickupNoise") {
			System.out.println("Increase Speed Pickup Noise Triggered");
			file = new File("Sound/IncreaseSpeedPickupNoise.wav");
		} else if (Thread.currentThread().getName() == "MenuSelectNoise") {
			System.out.println("Menu Select Noise Triggered");
			file = new File("Sound/MenuSelectNoise.wav");
		} else if (Thread.currentThread().getName() == "NFLSound") {
			System.out.println("NFL Noise Triggered");
			file = new File("Sound/NFLTheme.wav");
		} else if (Thread.currentThread().getName() == "ShootingNoise") {
			System.out.println("Shooting Noise Triggered");
			file = new File("Sound/PewPewNoise.wav");
		} else if (Thread.currentThread().getName() == "ReloadNoise") {
			System.out.println("Reload Noise Triggered");
			file = new File("Sound/ReloadNoise.wav");
		} else if (Thread.currentThread().getName() == "DeathNoise") {
			System.out.println("Death Noise Triggered");
			file = new File("Sound/StarWarsScream.wav");
		} else if (Thread.currentThread().getName() == "EnemyDamageNoise") {
			System.out.println("Enemy shot with player bullet Noise Triggered ");
			file = new File("Sound/EnemyDamageNoise.wav");
		} else if (Thread.currentThread().getName() == "PlayerDamage") {
			System.out.println("player hit by enemy sound");
			file = new File("Sound/PlayerDamaged.wav");
		}

		// This is where file is read in
		audioIn = null;
		try {
			audioIn = AudioSystem.getAudioInputStream(file);
		} catch (UnsupportedAudioFileException | IOException e) {
			//e.printStackTrace();
		}
		clip = null;
		try {
			clip = AudioSystem.getClip();
		} catch (LineUnavailableException e) {
			//e.printStackTrace();
		}
		try {
			clip.open(audioIn);
		} catch (LineUnavailableException | IOException e) {
			//e.printStackTrace();
		}

		// Increases volume of all sound files except background music
		// More parameters can be added to this
		if (Thread.currentThread().getName() != "GameMusic") {
			FloatControl gainControl = (FloatControl) clip
					.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(6.0f);
			
			
		}

		clip.start();

		// Changes loop amount to infinite if it is background music
		if (Thread.currentThread().getName() == "GameMusic") {
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		}
		try {
			System.out.println("File: " + Thread.currentThread().getName());
			Thread.sleep(clip.getMicrosecondLength());
			System.out.println(Thread.currentThread().isAlive());

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		clip.stop();
		try {
			audioIn.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
