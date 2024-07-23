package com.dreamwork.game;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

public class Sound {
	
	Clip clip;
	AudioInputStream inputStream; 
	
	public static final Sound MusicBackground = new Sound("/music.wav");
	public static final Sound Hurt = new Sound("/hurt.wav");
	
	private Sound(String name)
	{
		try {
			clip = AudioSystem.getClip();
			inputStream = AudioSystem.getAudioInputStream(Sound.class.getResource(name));
			clip.open(inputStream);
			
			
		} catch (Throwable e) {}
		
	}
	
	public void play()
	{
		try {
			new Thread()
			{
				public void run()
				{
					try {
						clip.start();
					} catch (Throwable e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}.start();
		} catch (Throwable e) {
			// TODO: handle exception
		}
	}
	
	public void loop()
	{
		try {
			new Thread()
			{
				public void run()
				{
					clip.loop(MAX_PRIORITY);;
				}
			}.start();
		} catch (Throwable e) {
			// TODO: handle exception
		}
	}
}
