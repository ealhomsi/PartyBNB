package com.partybnb.eventregistration.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
public class PythonToJava{
    private static String parameter;
    private static String script;

    public static void main(String[] argv){
        PythonToJava converter=new PythonToJava("countFaces.py","sample2.jpg");
        System.out.println(converter.exec());
    }

    public PythonToJava(String script,String parameter){
        this.script="countFaces.py";
        this.parameter=parameter;
    }
    
    public static int exec(){
    	Process p;
		try {
			File f = new File(script);
			File f2 = new File(parameter);
				
			p = Runtime.getRuntime().exec("./" + script+" "+parameter);	
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return -2;
		}
        
        try{
            BufferedReader stdOutput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            return Integer.parseInt(stdOutput.readLine());
        }catch(IOException e){
            e.printStackTrace();
            System.out.println(e);
            System.out.print("Execution failed\n");
            return -1;
        }        
    }
};