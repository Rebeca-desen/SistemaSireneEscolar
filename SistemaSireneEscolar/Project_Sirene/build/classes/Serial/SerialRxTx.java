/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Serial;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
//import Tela_Principal.java;


/**
 *
 * @author felip
 */
public class SerialRxTx implements SerialPortEventListener {
    SerialPort serialPort = null;
    private String Horarios;

    private OutputStream output;
    private static final int TIME_OUT =1000;
    private static final int DATA_RATE = 9600;
    private String serialPortName = "COM3";
    
    public boolean iniciaSerial (){
         boolean status = false;
        try {
            CommPortIdentifier portId = null;
            Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();
            //Enquanto não for setado ele:
            while( portId == null && portEnum.hasMoreElements()){
                                 //porta corrente
                CommPortIdentifier currPortId = ( CommPortIdentifier) portEnum.nextElement();
                //Se o nome da porta corrente for o mesmo da porta serial
                if(currPortId.getName().equals(serialPortName) || currPortId.getName().startsWith(serialPortName)){
                    serialPort = (SerialPort) currPortId.open(Horarios, TIME_OUT);
                    portId = currPortId;
                    System.out.println("Conectado em: " + currPortId.getName());
                    break;
                }
                
            }
            
            if(portId == null || serialPort == null){
                return false;
            }
            
            serialPort.setSerialPortParams(DATA_RATE,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);
               
                    //notificar quando estiver chegando dados
                     serialPort.addEventListener(this);
                     serialPort.notifyOnDataAvailable(true);
                     status = true;
            
            try {
                Thread.sleep(1000  );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }            
        } catch (Exception e) {
                e.printStackTrace();
                status = false;
        }
        
        return status;

    }
    public void sendData(String data){
     
        try {
            output = serialPort.getOutputStream();
            output.write(data.getBytes());
        } catch (IOException ex) {
            Logger.getLogger(SerialRxTx.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    public void serialEvent(SerialPortEvent spe) {
    }

    
}