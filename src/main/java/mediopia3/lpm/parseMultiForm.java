package mediopia3.lpm;
/**
 * <p>Title: 학습객체관리시스템</p>
 * <p>Description: SCORM기반의 학습객체관리시스템</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: (주)메디오피아</p>
 * @author 나재열
 * @version 1.0
 */
import java.io.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletInputStream;

public class parseMultiForm extends Object {
    private String fname = null;
    private String boundary = null;

    String param = null;
    DataInputStream in;

    public parseMultiForm(ServletInputStream instream) {
        this.in = new DataInputStream((ServletInputStream)instream);

        try{
            boundary=in.readLine();
        }catch(IOException e){
            System.out.println(e.getMessage());
            in=null;
        }
    }

    public parseMultiForm(InputStream instream) {
        this.in = new DataInputStream(instream);
        try{
            boundary=in.readLine();
        }catch(IOException e){
            System.out.println(e.getMessage());
            in=null;
        }
    }

    public String getParameter(String input_name) throws IOException {
        String str;
        while((str=in.readLine())!=null) {
            if(str.indexOf("name=") != -1) {
                int Start = str.indexOf("name=");
                int End = str.indexOf("\"", Start+6);
                param = str.substring(Start+6, End);
                if(param.equals(input_name)) {
                        str=in.readLine();
                        str=in.readLine();
                        return str;
                }
            }
        }
        return null;
    }

    public boolean getParameter(OutputStream out, String in_name) throws IOException{
        String str;
        while((str=in.readLine())!=null){
            if(str.indexOf("name=") != -1) {
                int Start=str.indexOf("name=");
                int End=str.indexOf("\"",Start+6);
                param=str.substring(Start+6,End);
                if(param.equals(in_name)) {
                        str=in.readLine();
                        if(readParameter(out))
                                return true;
                }
            }
        }
        return false;
    }

    public String getFileName() throws IOException {
        String str;
        int Start, End;

        while((str=in.readLine())!=null){
//System.out.println("STR===>" + str);
            if(str.indexOf("filename=\"\"") != -1) {
                str=in.readLine();
                return null;
            }
            if(str.indexOf("filename=") != -1) {
                Start=str.indexOf("filename=");
                End=str.indexOf("\"", Start+10);
                fname=str.substring(Start+10, End);
                if(fname.lastIndexOf("\\") != -1){
                   fname=fname.substring(fname.lastIndexOf("\\")+1);
                        return fname;
                }
            }
        }
        return null;
    }

    public boolean UpFile(OutputStream Out) throws IOException {
        String str;

        while((str=in.readLine())!=null) {
            if(str.indexOf("Content-Type") != -1) {
                str=in.readLine();
                if(readParameter(Out)) return true;
            }
        }
        return false;
    }

    public boolean readParameter(OutputStream Out)  {
        byte[] buffer=new byte[1024];
        byte CharIn;
        int x=0;
        try{
            for(;;) {
                buffer[x++]=CharIn=in.readByte();
                if(x==boundary.length()+1) {
                    int y=0;
                    String temp=new String(buffer,0,x);
                    if((y=temp.indexOf(boundary))!= -1) {
                        x=y;
                        if(x!=0)
                        Out.write(buffer,0,x-1);
                        return true;
                    }
                } else{
                    if((x==1023) || (CharIn =='\n')){
                        Out.write(buffer,0,x);
                        x=0;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error : "+e.toString());
        }
        return false;
    }
}