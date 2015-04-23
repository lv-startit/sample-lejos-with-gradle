package lv.startit.shumaher;

import lejos.hardware.Button;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class testSensor {
	
	private static EV3ColorSensor cRed = new EV3ColorSensor(SensorPort.S1);
	private static EV3ColorSensor cBlue = new EV3ColorSensor(SensorPort.S4);
	static EV3LargeRegulatedMotor motorLeft = new EV3LargeRegulatedMotor(MotorPort.A);
	static EV3LargeRegulatedMotor motorRight = new EV3LargeRegulatedMotor(MotorPort.D);
	public static boolean exit = false;
	static TouchSensorL leftTouch = new TouchSensorL();
	static TouchSensorR rightTouch = new TouchSensorR();
	
	
	public static void main(String[] args){
		while(!exit){
	/*		
			SensorMode red = cRed.getRedMode();
			SensorMode blue = cBlue.getAmbientMode();
			
			float[] cr = new float[red.sampleSize()]; 
			red.fetchSample(cr, 0);
			Delay.msDelay(500);

			float[] cb = new float[blue.sampleSize()];
			blue.fetchSample(cb, 0);
			Delay.msDelay(500);
			
			System.out.println("Red mode: " + cr[0]);
			System.out.println("Blue mode: " + cb[0]);*/
			if(Button.ESCAPE.isDown()){
				exit = true;
			}else{
				motorLeft.setSpeed(500);
				motorRight.setSpeed(500);
				motorRight.forward();
				motorLeft.forward();
				
				if(leftTouch.getButton() == 1){
					motorLeft.rotate(200);
					motorRight.rotate(-200);
				}
				
				if(rightTouch.getButton() == 1){
					motorLeft.rotate(-200);
					motorRight.rotate(200);
				}
			}
			
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
