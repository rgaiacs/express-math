/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.usp.ime.faguilar.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author frank
 */
public class RWFiles {
     public static void write(String fileName,String str){

        FileWriter fichero = null;
        PrintWriter pw = null;
        try
        {
            fichero = new FileWriter(fileName);
            pw = new PrintWriter(fichero);

            pw.print(str);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
           
           if (null != fichero)
              fichero.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
    }


    public static BufferedReader getBufferedReader(String fileName){

      File archivo = null;
      FileReader fr = null;
      BufferedReader br = null;

      try {

         archivo = new File (fileName);
         fr = new FileReader (archivo);
         br = new BufferedReader(fr);
      }
      catch(Exception e){
         e.printStackTrace();
      }
      return br;
    }


    public static String getContentAsString(String fileName){
        BufferedReader br = getBufferedReader(fileName);
        String content = "";
        try {
            String line = null;
            line = br.readLine();
            while (line != null) {
                content += (line+"\n");
                line = br.readLine();
            }
        } catch (IOException ex) {
            Logger.getLogger(RWFiles.class.getName()).log(Level.SEVERE, null, ex);
        }
        return content;
    }

    public void closeBReader(){


    }

}
