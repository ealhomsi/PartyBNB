import java.io.*;
public class PythonToJava{
    private static String parameter;
    private static String script;

    public static void main(String[] argv){
        PythonToJava converter=new PythonToJava("countFaces.py","sample2.jpg");
        System.out.println(converter.exec());
    }

    public PythonToJava(String script,String parameter){
        this.script=script;
        this.parameter=parameter;
    }
    
    public static int exec(){
        try{
            Process p = Runtime.getRuntime().exec(script+" "+parameter);
            BufferedReader stdOutput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            return Integer.parseInt(stdOutput.readLine());
        }catch(Exception e){
            e.printStackTrace();
            System.out.println(e);
            System.out.print("Execution failed\n");
            return -1;
        }
        
    }
};