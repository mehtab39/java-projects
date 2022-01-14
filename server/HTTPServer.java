package server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class HTTPServer { 
	private final static int LISTENING_PORT = 50505;
	// listening port   
	public static void main(String[] args) throws IOException {  
		ServerSocket serverSocket;   
		try {          
			serverSocket = new ServerSocket(LISTENING_PORT);  
		} catch (Exception e) {      
			System.out.println("Failed to create listening socket.");     
			return;    
		}    
		System.out.println("Listening on port " + LISTENING_PORT);     
		try {       
			while (true) { 
				Socket connection = serverSocket.accept();    
				System.out.println("\nConnection from " + connection.getRemoteSocketAddress()); 
				ConnectionThread thread = new ConnectionThread(connection);       
				thread.start();        
			}   
		} catch (Exception e) {         
			System.out.println("Server socket shut down unexpectedly!");   
			System.out.println("Error: " + e);       
			System.out.println("Exiting.");    
		}       serverSocket.close(); 
	}  
	private static void handleConnection(Socket connection) {  
		try {     
			Scanner in = new Scanner(connection.getInputStream());  
			OutputStream outputStream = connection.getOutputStream();
			String rootDirectory = "./src/www";  
			int lineNum = 0;       
			String line = "";      
			while (true) {       
				if (!in.hasNextLine())     
					break;              
				line = in.nextLine();       
				lineNum++;               
				if (line.trim().length() == 0)   
					break;          
				if (lineNum == 1)      
					break;         
			}         
			String[] tokens = line.split(" ");      
			String httpRequestType = tokens[0];         
			String pathToResource = tokens[1];       
			String httpVersion = tokens[2];        
			System.out.println("Path:" + pathToResource);    
			File file = new File(rootDirectory + pathToResource);  
			if (!httpRequestType.equals("GET")) {              
				sendErrorResponse(501, outputStream);         
				in.close();              
				connection.close();             
				return;         
			} 
			else if (!httpVersion.equals("HTTP/1.1")) {      
				sendErrorResponse(400, outputStream);        
				in.close();           
				connection.close();       
				return;        
			} else if (file.exists())      
				if (!file.isDirectory() && file.length() != 0) {  
					if (file.canRead()) {         
						// System.out.println("Response can be sent.");   
						PrintWriter out = new PrintWriter(outputStream);   
						out.print(httpVersion + "200 OK " + "\r\n");     
						out.print("Connection:close" + "\r\n");       
						out.print("Content-Type:" + getMimeType(pathToResource) + "\r\n"); 
						out.print("Content-Length:" + file.length() + "\r\n");      
						out.print("\r\n");       
						out.flush();             
						sendFile(file, outputStream);    
						out.close();              
					} else {            
						sendErrorResponse(403, outputStream);  
						in.close();                     
						connection.close();                  
						return;                
					}
				}
				else {   
					sendErrorResponse(404, outputStream);   
					in.close();            
					connection.close();        
					return;           
				}         
			in.close();   
		} catch (Exception e) { 
			System.out.println("Error while communicating with client: " + e);  
		} finally {      
			try {      
				connection.close();  
			} catch (Exception e) {   

			} System.out.println("Connection closed.");  
		} 
	}    
	private static String getMimeType(String fileName) {    
		int pos = fileName.lastIndexOf('.');    
		if (pos < 0)    
			return "x-application/x-unknown";  
		String ext = fileName.substring(pos + 1).toLowerCase();     
		if (ext.equals("txt"))       
			return "text/plain";    
		else if (ext.equals("html"))       
			return "text/html";     
		else if (ext.equals("htm"))        
			return "text/html";     
		else if (ext.equals("css"))      
			return "text/css";    
		else if (ext.equals("js"))    
			return "text/javascript";   
		else if (ext.equals("java"))      
			return "text/x-java";       
		else if (ext.equals("jpeg"))      
			return "image/jpeg";      
		else if (ext.equals("jpg"))       
			return "image/jpeg";       
		else if (ext.equals("png"))       
			return "image/png";     
		else if (ext.equals("gif"))   
			return "image/gif";   
		else if (ext.equals("ico"))  
			return "image/x-icon";  
		else if (ext.equals("class"))  
			return "application/java-vm";    
		else if (ext.equals("jar"))          
			return "application/java-archive";   
		else if (ext.equals("zip"))        
			return "application/zip";
		else if (ext.equals("xml"))        
			return "application/xml";   
		else if (ext.equals("xhtml"))    
			return "application/xhtml+xml";    
		else       
			return "x-application/x-unknown"; 
	}   
	private static void sendFile(File file, OutputStream socketOut) throws IOException {   
		InputStream in = new BufferedInputStream(new FileInputStream(file));   
		OutputStream out = new BufferedOutputStream(socketOut);     
		while (true) {   
			int x = in.read(); 
			if (x < 0)            
				break;         
			out.write(x);     
		}    
		out.flush();   
		in.close();
	}static void sendErrorResponse(int errorCode, OutputStream socketOut) {     
		PrintWriter out = new PrintWriter(socketOut);      
		switch (errorCode) {    
		case 404:        
			out.print("HTTP/1.1" + errorCode + " Not Found " + "\r\n");      
			out.print("Connection:close" + "\r\n");        
			out.print("Content-Type:" + "text/html" + "\r\n");        
			out.print("<html><head><title>Error</title></head><body>" +"<h2>Error: 404 Not Found</h2>"           
					+ "<p>The resource that you requested does " + 
					"notexist on this server.</p></body></html>"); 
			out.print("\r\n");          
			out.flush();         
			break;     
		case 403:       
			out.print("HTTP/1.1" + errorCode + " Forbidden " + "\r\n");     
			out.print("Connection:close" + "\r\n");        
			out.print("Content-Type:" + "text/html" + "\r\n");   
			out.print("\r\n");    
			out.flush();        
			break;     
		case 400:    
			out.print("HTTP/1.1" + errorCode + " Bad Request " + "\r\n");     
			out.print("Connection:close" + "\r\n");         
			out.print("\r\n");       
			out.flush();      
			break;       
		case 501:
			out.print("HTTP/1.1" + errorCode + " Not Implemented " + "\r\n");       
			out.print("Connection:close" + "\r\n");        
			out.print("\r\n");         
			out.flush();     
			break; 
		case 500:         
			out.print("HTTP/1.1" + errorCode + " Internal Server Error " + "\r\n");    
			out.print("Connection:close" + "\r\n");        
			out.print("\r\n");         
			out.flush();        
			break;     
		}     
		out.close();  
	}
	private static class ConnectionThread extends Thread {
		Socket connection;    
		ConnectionThread(Socket connection) {    
			this.connection = connection;   
		}    
		public void run() {   
			handleConnection(connection);  
		}   
	}
}


