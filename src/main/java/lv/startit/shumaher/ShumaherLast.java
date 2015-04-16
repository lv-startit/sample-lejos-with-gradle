package lv.startit.shumaher;

import java.util.Date;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.Motor;
import lejos.hardware.motor.NXTRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class ShumaherLast {
	
	/* 
	 * S1 = left light;
	 * S2 = left touch;
	 * S3 = right touch;
	 * S4 = right light;
	 * 
	 * A = left Motor;
	 * D = right Motor;
	 */
	
	public static boolean exit = false;
	
	static EV3LargeRegulatedMotor motorLeft = new EV3LargeRegulatedMotor(MotorPort.A);
	static EV3LargeRegulatedMotor motorRight = new EV3LargeRegulatedMotor(MotorPort.D);
	
	static int defaultSpeed = 800;
	static int backpedalDistance = 500;
	
	static LightSensorL leftLight;
	static LightSensorR rightLight;
	static TouchSensorL leftTouch;
	static TouchSensorR rightTouch;
	private static float leftDist;
	private static float rightDist;
	private static boolean trigerredLeftBumper, trigerredRightBumper;
	
	public static void main(String[] args) {
		//---
		initialize();
		LCD.drawString("Wait for button pressed", 1, 1);
		Button.ENTER.waitForPress();
		Delay.msDelay(5000);
		motorRight.forward();
		motorLeft.forward();
		//---

		while(!exit){
			
			if(Button.ESCAPE.isDown()){
				exit = true;
			}
				defaultSpeed();
				forward();	
				
				if(leftTouch.getButton() != 1 && rightTouch.getButton() != 1){
					leftDist = leftLight.getLight();
					rightDist = rightLight.getLight();
					if(leftDist <= 0){
						if(rightDist <= 0){
							while((leftTouch.getButton() != 1 && rightTouch.getButton() != 1) && (leftDist <= 0 && rightDist <= 0)){
								defaultSpeed();
								forward();
								leftDist = leftLight.getLight();
								rightDist = rightLight.getLight();
							}
						} else {
							while((leftTouch.getButton() != 1 && rightTouch.getButton() != 1) && (leftDist <= 0 && rightDist > 0)){
								turnLeft();
								forward();
								leftDist = leftLight.getLight();
								rightDist = rightLight.getLight();
							}
							defaultSpeed();
							
						}
					} else {
						while((leftTouch.getButton() != 1 && rightTouch.getButton() != 1) && (leftDist > 0 && rightDist <= 0)){
							turnRight();
							forward();
							leftDist = leftLight.getLight();
							rightDist = rightLight.getLight();
						}
						defaultSpeed();
					}
				}else{
					if(rightTouch.getButton() == 1.0){
						trigerredRightBumper = true;
					}else if(leftTouch.getButton() == 1.0){
						trigerredLeftBumper = true;
					}
					backward();
					Delay.msDelay(backpedalDistance);
					if(trigerredLeftBumper){
						turnRIGHT();
					}else if(trigerredRightBumper){
						turnLEFT();
					}
					trigerredRightBumper = false;
					trigerredLeftBumper = false;
					forward();
					Delay.msDelay(backpedalDistance);
				}
			
		}
		

	}
	
	public static void initialize(){
		leftLight = new LightSensorL();
		rightLight = new LightSensorR();
		leftTouch = new TouchSensorL();
		rightTouch = new TouchSensorR();
		motorLeft.setSpeed(defaultSpeed);
		motorRight.setSpeed(defaultSpeed);
	}
	public static void turnLeft(){
		motorLeft.setSpeed(Math.round(defaultSpeed/1.5));
		motorRight.setSpeed(defaultSpeed);
	}
	public static void turnRight(){
		motorLeft.setSpeed(defaultSpeed);
		motorRight.setSpeed(Math.round(defaultSpeed/1.5));
	}
	public static void defaultSpeed(){
		motorLeft.setSpeed(defaultSpeed);
		motorRight.setSpeed(defaultSpeed);
	}
	public static void forward(){
		motorLeft.forward();
		motorRight.forward();
	}	
	public static void backward(){
		motorLeft.backward();
		motorRight.backward();
	}
	private static void turnLEFT(){
		motorLeft.setSpeed(defaultSpeed/6);
		motorRight.setSpeed(defaultSpeed*2);
	}
	private static void turnRIGHT(){
		motorLeft.setSpeed(defaultSpeed*2);
		motorRight.setSpeed(defaultSpeed/6);
	}

 private static class LightSensorL{

		EV3ColorSensor sensor;
		SampleProvider color;
		float[] sample;
		int ColorId;
		SensorMode red;
		
		LightSensorL(){
			sensor = new EV3ColorSensor(SensorPort.S1); 
			red = sensor.getRedMode();
			sample = new float[red.sampleSize()]; 
		}
		
		public float getLight(){
			red.fetchSample(sample, 0);
			return sample[0];
		}
		
 }
 private static class LightSensorR{

		EV3ColorSensor sensor;
		SampleProvider color;
		float[] sample;
		int ColorId;
		SensorMode red;
		
		LightSensorR(){
			sensor = new EV3ColorSensor(SensorPort.S4); 
			red = sensor.getRedMode();
			sample = new float[red.sampleSize()]; 
		}
		
		public float getLight(){
			red.fetchSample(sample, 0);
			return sample[0];
		}
			
}
 private static class TouchSensorL{

		EV3TouchSensor sensor;
		SampleProvider touch;
		float[] sample;	
		
		TouchSensorL(){
			sensor = new EV3TouchSensor(SensorPort.S2); 
			sample = new float[sensor.sampleSize()]; 
			touch = sensor.getTouchMode();
		}
		
		public float getButton(){
			touch.fetchSample(sample, 0);
			return sample[0];
		}

		
 }
 private static class TouchSensorR{

		EV3TouchSensor sensor;
		SampleProvider touch;
		float[] sample;	
		
		TouchSensorR(){
			sensor = new EV3TouchSensor(SensorPort.S3); 
			sample = new float[sensor.sampleSize()]; 
			touch = sensor.getTouchMode();
		}
		
		public float getButton(){
			touch.fetchSample(sample, 0);
			return sample[0];
		}
		
}
 

}
