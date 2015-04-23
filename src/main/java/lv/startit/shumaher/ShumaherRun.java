package lv.startit.shumaher;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;


public class ShumaherRun {
	
	static RegulatedMotor motorLeft = Motor.A;
	static RegulatedMotor motorRight = Motor.B;
	
	private static EV3UltrasonicSensor ultraRight = new EV3UltrasonicSensor(SensorPort.S2);
	static SampleProvider distanceRight = ultraRight.getDistanceMode();
	
//	private static EV3ColorSensor colorRight = new EV3ColorSensor(SensorPort.S1);
//	static SampleProvider colorRed = colorRight.getColorIDMode();
	
//	private static EV3UltrasonicSensor ultraLeft = new EV3UltrasonicSensor(SensorPort.S1);
//	static SampleProvider distanceLeft = ultraLeft.getDistanceMode();
	
	public static EV3TouchSensor touchRight = new EV3TouchSensor(SensorPort.S3);
	public static EV3TouchSensor touchLeft = new EV3TouchSensor(SensorPort.S4);
	

	static boolean exit;
	
	public static void main(String[] args) {
		Thread t1 = new Thread(new ExitListener());
		t1.start();
		initialize();
		float[] usampleRight = new float[distanceRight.sampleSize()];
//		float[] usampleLeft = new float[distanceLeft.sampleSize()];

			while(!exit){
				distanceRight.fetchSample(usampleRight, 0);
//				distanceLeft.fetchSample(usampleLeft, 0);
				 motorRight.forward();
				 motorLeft.forward();
				 int sampleSizeRight = touchRight.sampleSize();
				 float[] sampleRight = new float[sampleSizeRight];
				 touchRight.fetchSample(sampleRight, 0);
				 int sampleSizeLeft = touchLeft.sampleSize();
				 float[] sampleLeft = new float[sampleSizeLeft];
				 touchLeft.fetchSample(sampleLeft, 0);
				 //LCD.drawString("1 Button pressed " + sampleRight[0], 0, 1);
				 //LCD.drawString("2 Button pressed " + sampleLeft[0], 0, 3);
				 if(sampleLeft[0] == 1)
					 touchLeftAction();
				 else if(sampleRight[0] == 1)
					 touchRightAction();
				 
				
				 if(usampleRight[0] < 0.25){
					 distanceRight.fetchSample(usampleRight, 0);
					 LCD.drawString("Right ultra" + usampleRight[0], 0, 1);
					actionRight();
					motorLeft.setSpeed(600);
				 }
				 /*if(usampleLeft[0] < 0.15){
					 distanceLeft.fetchSample(usampleLeft, 0);
					 LCD.drawString("Left ultra" + usampleLeft[0], 0, 4);
					 actionLeft();
					 motorRight.setSpeed(550);
				 }*/
				
				 
				
			}
		
	}

	
	private static void initialize() {
		motorLeft.setSpeed(600);
		motorRight.setSpeed(600);
	}
	
	
	public static void actionRight(){
		motorLeft.setSpeed(200);
		motorRight.setSpeed(600);
		Delay.msDelay(250);
		motorLeft.forward();
		motorRight.forward();
	}
	public static void actionLeft(){
		motorLeft.setSpeed(550);
		motorRight.setSpeed(200);
		motorLeft.forward();
		motorRight.forward();
	}
	public static void touchLeftAction(){
		motorLeft.stop();
		motorRight.backward();
		Delay.msDelay(250);
		motorRight.forward();
		motorLeft.forward();
	}
	public static void touchRightAction(){
		motorRight.stop();
		motorLeft.backward();
		Delay.msDelay(250);
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
