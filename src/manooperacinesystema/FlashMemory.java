/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manooperacinesystema;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author User
 */
public class FlashMemory{

    public static int sector = 0;

    public static void readFromFlashToHDD(String sourceFile) {
        try{
            BufferedReader br = new BufferedReader(new FileReader(sourceFile));
            StringBuilder line = new StringBuilder();
            int c;

            while ((c = br.read()) != -1) {
                if (line.length() == 256) {
                    HDD.write(line.toString().toCharArray(), sector);
                    sector++;
                    line = new StringBuilder();
                }
                // 
                if (c != 10 && c != 13) {
                    line.append((char) c);
                }
            }
            if (!line.toString().equals("")) {
                HDD.write(line.toString().toCharArray(), sector);
                sector++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Read from FLASH to HDD.");
    }
}