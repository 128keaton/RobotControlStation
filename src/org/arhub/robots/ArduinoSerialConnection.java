package org.arhub.robots;

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

	public List<String> GetComPorts() {
		
		setPorts((List<String>) Arrays.asList(SerialPortList.getPortNames()));

	
		
		
		return ports;
	}

	public void Connect(String comPort) throws SerialPortException {
		serialPort = new SerialPort(comPort);
	//	serialPort = new SerialPort("/dev/tty.HC-06-DevB");
		
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
	//	List<String> states = new ArrayList<String>();
		List<String> xAxis = new ArrayList<String>();
		List<String> yAxis = new ArrayList<String>();
		List<String> zAxis = new ArrayList<String>();
		for(ControlState controlState : controlStates){
		for(int i = 1; i <4; i++){
		
			switch(i){
			
			case 1: xAxis.add(Integer.toString(controlState.value));
				break;
			case 2: yAxis.add(Integer.toString(controlState.value));
				break;
			case 3: zAxis.add(Integer.toString(controlState.value));
				break;
			default: break;
			
			}
		}
		}
		List<String> bytesArray = new ArrayList<String>();
		
		
		if (serialPort != null) {
			
			int intBytesX = Integer.parseInt(StringUtils.join(xAxis, ",").replace(",", ""));
			int intBytesY = Integer.parseInt(StringUtils.join(yAxis, ",").replace(",", ""));
			int intBytesZ = Integer.parseInt(StringUtils.join(zAxis, ",").replace(",", ""));
			//int stopAxis = Integer.parseInt(StringUtils.join(intBytesX, intBytesY, intBytesZ, ",").replace(",", ","));
			
			/*
		
			System.out.println(intBytes);
			(betweenExclusive(intBytes, 8800, 9300) == true(betweenExclusive(intBytes, 8800, 9300) == true
				System.out.println("Zero");
				
				bytesArray.add(Integer.toString(0));
				bytesArray.add(Integer.toString(0));
				serialPort.writeBytes(((StringUtils.join(bytesArray, ",") + "\n").getBytes()));
				bytesArray.clear();
				
			}else if(betweenExclusive(intBytes, 900, 1600)){
				System.out.println("Left");
		
				
					bytesArray.add(Integer.toString(255));
					bytesArray.add(Integer.toString(0));
					serialPort.writeBytes(((StringUtils.join(bytesArray, ",") + "\n").getBytes()));
					bytesArray.clear();
					
			
			}else if(betweenExclusive(intBytes, 60, 800)){
				System.out.println("Backward");
				bytesArray.add(Integer.toString(500));
				bytesArray.add(Integer.toString(500));
				serialPort.writeBytes(((StringUtils.join(bytesArray, ",") + "\n").getBytes()));
				bytesArray.clear();
			}else if(betweenExclusive(intBytes, 16116, 18100)){
				System.out.println("Forward");
				bytesArray.add(Integer.toString(255));
				bytesArray.add(Integer.toString(255));
				serialPort.writeBytes(((StringUtils.join(bytesArray, ",") + "\n").getBytes()));
				bytesArray.clear();
			}else if(betweenExclusive(intBytes, 38000, 133100)){
				System.out.println("Right");
				bytesArray.add(Integer.toString(0));
				bytesArray.add(Integer.toString(255));
				serialPort.writeBytes(((StringUtils.join(bytesArray, ",") + "\n").getBytes()));
				bytesArray.clear();
			}*/
			
			if(betweenExclusive(intBytesY, 85, 89) == true){
				bytesArray.add(Integer.toString(0));
				bytesArray.add(Integer.toString(0));
				serialPort.writeBytes(((StringUtils.join(bytesArray, ",") + "\n").getBytes()));
				bytesArray.clear();
				
			}else if(betweenExclusive(intBytesY, 0, 85) == true){
				
				System.out.println("Forward");
				bytesArray.add(Integer.toString(180));
				bytesArray.add(Integer.toString(0));
				bytesArray.add(Integer.toString(0));
				bytesArray.add(Integer.toString(180));
				serialPort.writeBytes(((StringUtils.join(bytesArray, ",") + "\n").getBytes()));
				bytesArray.clear();
				
			}else if(betweenExclusive(intBytesY, 95, 180) == true){
				
				System.out.println("Backward");
				bytesArray.add(Integer.toString(0));
				bytesArray.add(Integer.toString(180));
				bytesArray.add(Integer.toString(180));
				bytesArray.add(Integer.toString(0));
				serialPort.writeBytes(((StringUtils.join(bytesArray, ",") + "\n").getBytes()));
				bytesArray.clear();
				
			}else if(betweenExclusive(intBytesX, 0, 85) == true){
				
				System.out.println("Left");

				bytesArray.add(Integer.toString(180));
				bytesArray.add(Integer.toString(180));
				bytesArray.add(Integer.toString(0));
				bytesArray.add(Integer.toString(0));
				serialPort.writeBytes(((StringUtils.join(bytesArray, ",") + "\n").getBytes()));
				bytesArray.clear();
				
			}
			else if(betweenExclusive(intBytesX, 95, 180) == true){
				
				System.out.println("Right");

				bytesArray.add(Integer.toString(180));
				bytesArray.add(Integer.toString(180));
				bytesArray.add(Integer.toString(0));
				bytesArray.add(Integer.toString(0));
				serialPort.writeBytes(((StringUtils.join(bytesArray, ",") + "\n").getBytes()));
				bytesArray.clear();
				
			}
		
			
			
		//	serialPort.writeBytes(((StringUtils.join(bytesArray, ",") + "\n").getBytes()));
			}
	
			//serialPort.writeBytes(((StringUtils.join(bytesArray, ",") + "\n").getBytes()));
		
//		System.out.println(StringUtils.join(states, ","));

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

