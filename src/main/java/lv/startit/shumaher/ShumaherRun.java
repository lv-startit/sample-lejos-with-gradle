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
	
	static boolean check = false;
	static boolean exit;
	
	public static void main(String[] args) {
		Thread t1 = new Thread(new ExitListener());
		t1.start();
		initialize();
		float[] usample = new float[distance.sampleSize()];
		 while(!exit){
			 motorRight.forward();
			 motorLeft.forward();
			if(checkWall(usample)){
				action();	
			}
			motorLeft.setSpeed(750);
		}
	}

	
	private static void initialize() {
		motorLeft.setSpeed(750);
		motorRight.setSpeed(750);
	}
	
	
	private static boolean checkWall(float[] usample){
		if(usample[0] < 0.1)
		{
		 return true;
		}
		
		 return check;
	}
	
	public static void action(){
		motorLeft.setSpeed(300);
		motorRight.setSpeed(750);
	}
	private static class ExitListener implements Runnable{
		@Override
		public void run() {
			Button.waitForAnyPress();
			exit = true;
		}
	}

}
