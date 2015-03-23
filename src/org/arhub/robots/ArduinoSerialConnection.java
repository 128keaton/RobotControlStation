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
		try{
			serialPort.openPort();
		}catch(SerialPortException ex){
			//if busy, try one more time.
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
		List<String> bytesArray = new ArrayList<String>();
		
		
		if (serialPort != null) {
			
			int intBytes = Integer.parseInt(StringUtils.join(states, ",").replace(",", ""));
			
		
			System.out.println(intBytes);
			if(betweenExclusive(intBytes, 8800, 9300) == true){
				System.out.println("Zero");
				
			}else if(betweenExclusive(intBytes, 900, 1600)){
				System.out.println("Forward");
		
				
					bytesArray.add(Integer.toString(255));
					bytesArray.add(Integer.toString(255));

			
			serialPort.writeBytes(new byte[255255]);
			}else if(betweenExclusive(intBytes, 60, 800)){
				System.out.println("Left");
				//serialPort.writeBytes(new byte[255-255]);
			}else if(betweenExclusive(intBytes, 16116, 18100)){
				System.out.println("Right");
				//serialPort.writeBytes(new byte[-255255]);
			}else if(betweenExclusive(intBytes, 38000, 133100)){
				System.out.println("Backwards");
				//serialPort.writeBytes(new byte[-255-255]);
			}
		//	serialPort.writeBytes(((StringUtils.join(bytesArray, ",") + "\n").getBytes()));
			}
	
			serialPort.writeBytes(((StringUtils.join(bytesArray, ",") + "\n").getBytes()));
		
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

