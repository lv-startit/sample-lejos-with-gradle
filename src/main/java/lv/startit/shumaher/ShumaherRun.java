package lv.startit.shumaher;

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
	
	
	public static void main(String[] args) {
		initialize();
		float[] usample = new float[distance.sampleSize()];
		
		if(checkWall(usample)){
			action();
			
		}
	}

	
	private static void initialize() {
		LCD.clear();
		motorLeft.setSpeed(750);
		motorRight.setSpeed(750);
		motorLeft.forward();
		motorRight.forward();
	}
	
	
	private static boolean checkWall(float[] usample){
		if(usample[0] < 0.1)
		{
		 return true;
		}
		
		 return check;
	}
	
	public static void action(){
		 motorRight.stop();
		 motorRight.backward();
	}

}
