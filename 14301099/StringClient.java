import java.io.BufferedReader;  
import java.io.BufferedWriter;  
import java.io.IOException;  
import java.io.InputStreamReader;  
import java.io.OutputStreamWriter;  
import java.io.PrintWriter;  
import java.net.Socket;  
    
public class StringClient {  
   
    public static void main(String[] args) {  
        Socket s = null;  
        BufferedReader br = null;  
        PrintWriter pw = null;
        String str = null;
        String totalString = "";
        String partString = "";
        try {  
            //�ͻ���socketָ���������ĵ�ַ�Ͷ˿ں�  
            s = new Socket("127.0.0.1", 3333);  
            System.out.println(s + "���ڳ�������");  
            //ͬ������ԭ��һ��  
            br = new BufferedReader(new InputStreamReader(  
                    s.getInputStream()));  
            pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(  
                    s.getOutputStream())));  
            //һ��һ�е����� �ÿͻ���Ҫ�������Ϣ
            for (int i = 0; i < 10; i++) {
            	//System.out.println(i);
                pw.println(i);   
                pw.flush();
            } 
            pw.println("END");  
            pw.flush();
            
            while((partString = br.readLine()) != null){
            	totalString += partString;
            }  
            System.out.println(totalString); 
              
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                System.out.println("�ͻ��˹ر�");  
                br.close();  
                pw.close();  
                s.close();  
            } catch (IOException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }  
        }  
    }  
  
}  