import java.io.*;
class PythonToJava{

    public static void main(String[] argv){
        if(argv.length<1){
            System.out.println("Please specify a file to analyis");
            return;
        }
        try{
            Process p = Runtime.getRuntime().exec("./countFaces.py "+argv[0]);
            BufferedReader stdOutput = new BufferedReader(new 
                    InputStreamReader(p.getInputStream()));

            System.out.print(Integer.parseInt(stdOutput.readLine()));
        }catch(Exception e){
            e.printStackTrace();
            System.out.println(e);
            System.out.print("Execution failed\n");
        }
    }  
};