package application;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ClientController {

    @FXML
    private Button mEnterButton;
    
    @FXML
    private TextField mInputField;
    
    @FXML
    private TextArea mLog;
    
    private DataOutputStream mToServer = null;
    private DataInputStream mFromServer = null;
    private Socket mSocket = null;
    
    public ClientController()
    {
    	try
    	{
    		mSocket = new Socket("localhost", 3000);
    		mFromServer = new DataInputStream(mSocket.getInputStream());
    		mToServer = new DataOutputStream(mSocket.getOutputStream());
    		
    		
    	}
    	catch(Exception e)
    	{
    		System.out.println(e.getMessage());
    	}
    	
    	new Thread( () -> {
    		while(true)
    		{
    		   boolean mAnswer = false;
			try {
				mAnswer = mFromServer.readBoolean();
				if (mAnswer == true)
				{
					mLog.appendText("The number is prime");
					mLog.appendText(System.lineSeparator());
				}
				else
				{
					mLog.appendText("The number is not prime");
					mLog.appendText(System.lineSeparator());
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
    		}
    	}).start();
    	
    }
    
    
    
    @FXML
    void enterPress(ActionEvent event) {
    	try {
			int mNumber = Integer.parseInt(mInputField.getText());
			mToServer.writeInt(mNumber);
			mToServer.flush();
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
