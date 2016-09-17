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
            System.out.println("等待连接...");
            while(true){                   
                s = ss.accept();  
                // 每收到一个socket连接 ，则启动一条SeverThread线程  
                System.out.println("socket"+s+"连接成功"); 
                socketList.add(s); 
                System.out.println("当前客户端数：" + socketList.size());
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
	            // 初始化该socket对应的输入流    得到客户端的信息  
	            br = new BufferedReader(new InputStreamReader(s.getInputStream()));  
	            // 获取该socket对应的输出流  
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
	                  System.out.println("客户端"+num+"号输入信息:"+totalString); 
	                  
	                  for(int i = totalString.length() - 1; i >= 0 ; i--){
	     	        	 finalString += totalString.charAt(i);
	     	          }
	                  
		              pw.println("服务端返回信息:"+finalString);  
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
