import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.*;
import java.util.ArrayList;

public class StringSever {  
    
    public static ArrayList<Socket> socketList = new ArrayList<Socket>();  
    public static void main(String[] args){ 
    	ServerSocket ss = null;  
        Socket s  = null;
        try {  
            ss = new ServerSocket(3333);  
            System.out.println("�ȴ�����...");
            while(true){                   
                s = ss.accept();  
                // ÿ�յ�һ��socket���� ��������һ��SeverThread�߳�  
                System.out.println("socket"+s+"���ӳɹ�"); 
                socketList.add(s); 
                System.out.println("��ǰ�ͻ�������" + socketList.size());
                new Thread(new MyThread(s,socketList.size())).start();  
            }  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }finally{  
            try {  
                s.close();  
            } catch (IOException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }  
        } 
    }  
}  
class MyThread implements Runnable{
	
	    Socket s = null;  
	    BufferedReader br = null;  
	    PrintWriter pw = null; 
	    int num;
	      
	    public MyThread(Socket s, int num) {  
	        this.s = s;  
	        this.num = num;
	        try {  
	            // ��ʼ����socket��Ӧ��������    �õ��ͻ��˵���Ϣ  
	            br = new BufferedReader(new InputStreamReader(s.getInputStream()));  
	            // ��ȡ��socket��Ӧ�������  
	            pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(  
	                    s.getOutputStream())));     
	        } catch (IOException e) {  
	            // TODO Auto-generated catch block  
	            e.printStackTrace();  
	        }  
	    }  
	  
	    public void run() {  
	        // TODO Auto-generated method stub  
	    	String totalString = "";
	    	String str = "";
	    	String finalString = "";
	        try {  
	            while((str = br.readLine()) != null){   
	               if(str.equals("END")){ 
	                  System.out.println("�ͻ���"+num+"��������Ϣ:"+totalString); 
	                  
	                  for(int i = totalString.length() - 1; i >= 0 ; i--){
	     	        	 finalString += totalString.charAt(i);
	     	          }
	                  
		              pw.println("����˷�����Ϣ:"+finalString);  
		              pw.flush(); 
	                  break;  
	                }else{
	                  totalString += str; 
	                }
	            }
	         } catch (Exception e) {          
	             e.printStackTrace();      
	         }finally{  
	             try {  
	               br.close();  
	               pw.close();  
	               s.close(); 
	               StringSever.socketList.remove(s);
	             } catch (IOException e) {  
	                    // TODO Auto-generated catch block  
	               e.printStackTrace();  
	             }  
	         }   
	        
	    }   
	         
	     
	      
}
