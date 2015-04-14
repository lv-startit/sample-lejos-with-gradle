package lv.startit.shumaher;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;


public class ShumaherRun {
	
	static RegulatedMotor motorLeft = Motor.A;
	static RegulatedMotor motorRight = Motor.B;
	
	private static EV3UltrasonicSensor us = new EV3UltrasonicSensor(SensorPort.S2);
	static SampleProvider distance = us.getDistanceMode();
	
	static boolean exit;
	
	public static void main(String[] args) {
		Thread t1 = new Thread(new ExitListener());
		t1.start();
		initialize();
		float[] usample = new float[distance.sampleSize()];
		 
			while(!exit){
				distance.fetchSample(usample, 0);
				 motorRight.forward();
				 motorLeft.forward();
				
				 if(usample[0] < 0.3){
					 distance.fetchSample(usample, 0);
					action();	
				 }
				
				 motorLeft.setSpeed(400);
			}
		
	}

	
	private static void initialize() {
		motorLeft.setSpeed(400);
		motorRight.setSpeed(400);
	}
	
	
	public static void action(){
		motorLeft.setSpeed(100);
		motorRight.setSpeed(400);
		motorLeft.forward();
		motorRight.forward();
	}
	
	
	private static class ExitListener implements Runnable{
		@Override
		public void run() {
			Button.waitForAnyPress();
			exit = true;
		}
	}
	
	

}
