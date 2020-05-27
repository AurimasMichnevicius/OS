package manooperacinesystema;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class Keyboard{

    public static int sector = 0;
    public static int page = 64 ;
    public static void readFromKeyboardToHDD(String sourceFile) {
        try{
            BufferedReader br = new BufferedReader(new FileReader(sourceFile));
            StringBuilder line = new StringBuilder();
            int c;
            String builder = "";
            
            int array[]= new int[256];
            int i = 0;
            while (((c = br.read()) != -1) && (i<256)) {
                
            
            if (Character.getNumericValue((char)c) == -1) {
               array[i] = Integer.parseInt(builder);
             i++;
             builder = "";
            }else
            {
                builder = builder+(char)c;
            }
            }
//                    for(int j = 0;  j< 256; j++)
//        {
//            System.out.print(array[j]+ " ");
//            
//        }
//                    
//        System.out.println();
            HDD.write(page, array);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Read from KEYOBNOARD to HDD.");
    }
}