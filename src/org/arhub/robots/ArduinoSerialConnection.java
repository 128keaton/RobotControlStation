package org.arhub.robots;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;

import org.apache.commons.lang3.StringUtils;


public class ArduinoSerialConnection {

	private SerialPort serialPort;
	private List<String> ports;
	private List<Integer> leftMotors;
	private List<Integer> rightMotors;
	public List<String> GetComPorts() {
		
		setPorts((List<String>) Arrays.asList(SerialPortList.getPortNames()));

	
		
		
		return ports;
	}

	public void Connect(String comPort) throws SerialPortException {
		serialPort = new SerialPort(comPort);
	//	serialPort = new SerialPort("/dev/cu.wchusbserial620");
		
		try{
			serialPort.openPort();
		}catch(SerialPortException ex){
			//if busy, try one more time. xcccccccccccccccccccccccccccccccccccccccccccccccc 58       88888888888888888                                                                                                                                                                                                                  ccccccccccmv,ggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggg.
 			
			if(ex.getExceptionType().equals("Port busy")){
				serialPort.openPort();
			}else{
				throw ex;
			}
		}
		serialPort.setParams(SerialPort.BAUDRATE_9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
				SerialPort.PARITY_NONE);
	}

	public void SendGamepadState(List<ControlState> controlStates) throws SerialPortException {
		List<String> states = new ArrayList<String>();
		for (ControlState controlState : controlStates) {
			states.add(Integer.toString(controlState.value));
		}
		List<String> bytesArray = new ArrayList<String>(5);
		
		int intBytesB = Integer.parseInt(states.get(0));
		int intBytesX = Integer.parseInt(states.get(1));
		int intBytesY = Integer.parseInt(states.get(2));
		int intBytesZ = Integer.parseInt(states.get(3));
		
		int backLeftMotor = 0;
		int backRightMotor = 0;
		int frontLeftMotor = 0;
		int frontRightMotor = 0;
		
		int outputButton = 0;
		System.out.println("Fire Button: " + intBytesB);
		System.out.println("X Axis: " + intBytesX);
		System.out.println("Y Axis: " + intBytesY);
		System.out.println("Z Axis: " + intBytesZ);
	
		if(betweenExclusive(intBytesX, 87, 92) && betweenExclusive(intBytesY, 87, 92)){
		
			//Zero'd Function;
			
			frontRightMotor = 90;
			frontLeftMotor = 90;
			backRightMotor = 90;
			backLeftMotor = 90;
			System.out.println("Zero");
			
		}
		else{
			double twoPI = 2 * Math.PI;
			intBytesX = intBytesX - 90;
			intBytesY = intBytesY - 90;
			double theta = Math.atan((double)intBytesY/intBytesX);
			double r = Math.sqrt((intBytesX * intBytesX) + (intBytesY * intBytesY));
			frontRightMotor = (int) (r * Math.cos(theta)) + 90 + intBytesZ;
			backRightMotor = (int) (r * Math.sin(twoPI - theta)) + 90 + intBytesZ;
			backLeftMotor = (int) (r * Math.cos(twoPI - theta)) + 90 + intBytesZ;
			frontLeftMotor = (int) (r * Math.sin(theta)) + 90 + intBytesZ;
		}
	
		
		if(intBytesB == 1){
			outputButton = 0;
			//Shoot catapult
		}else{
			outputButton = 90;
		}
		
		if (serialPort != null) {
			
			bytesArray.add(String.valueOf(backLeftMotor));
			bytesArray.add(String.valueOf(backRightMotor));
			bytesArray.add(String.valueOf(frontLeftMotor));
			bytesArray.add(String.valueOf(frontRightMotor));
			bytesArray.add(String.valueOf(outputButton));
			
		
			
	
			
			serialPort.writeBytes(((StringUtils.join(bytesArray, ",") + "\n").getBytes()));
			bytesArray.clear();
			
			
			}
		
	
}
		
	

	public void disconnect() throws SerialPortException {
		serialPort.closePort();
	}

	public List<String> getPorts() {
		return ports;
	}

	public void setPorts(List<String> ports) {
		this.ports = ports;
	}
	
	 public static boolean betweenExclusive(int x, int min, int max)
	   {
	       return x>min && x<max;    
	   }
	 

}
class MathUtil
{
   public static boolean betweenExclusive(int x, int min, int max)
   {
       return x>min && x<max;    
   }
}

