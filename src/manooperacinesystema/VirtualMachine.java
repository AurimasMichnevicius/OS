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
public class VirtualMachine {

    private static final int PAGE_SIZE = 256;
    private static final int PAGE_COUNT = 256;
    private static final int MEMORY_SIZE = PAGE_SIZE * PAGE_COUNT;
    private static final int DATA_SEGMENT_START = 0;
    private static final int CODE_SEGMENT_START = PAGE_SIZE * 4;
    public static final int SHARED_MEMORY_SEGMENT = 0x0D;
    public static int printerPage;
    private VirtualMachine shrVm;
    private RealMachine rm;
       private static final int Data_segment_start = 0;
        private static final int Code_segment_start = PAGE_SIZE * 16;

    private int[] MEMORY = new int[MEMORY_SIZE]; /// vidine atmintis

    public VirtualMachine(RealMachine rm) {
        this.rm = rm;
    }

    public void loadProgram(String programName) {
        try (BufferedReader br = new BufferedReader(new FileReader(programName))) {
            String state = "START";
            String currentLine= "";
            int offset = 0;
            while((currentLine = br.readLine() )!= null)
            {
                String[] split = currentLine.split(" ");
               
                currentLine = split[0];
                 System.out.println(currentLine);
                if(state.equals("START"))
                {
                    if(currentLine.equals("DATA"))
                    {
                      
                        state = "DATA";
                        offset = Data_segment_start;
                        continue;
                        
                    }
                
                else
                {
                    System.err.println("Progam did not find Data segment");
                    return;
                }
                }
                else if(state.equals("DATA"))
                {
                    if(currentLine.equals("CODE"))
                    {
                    
                        state= "CODE";
                        offset = Code_segment_start;
                    }
                    else if(currentLine.equals("DW"))
                    {
                          System.out.println(currentLine);
                        for (int i = 0; i < 1; i++){ // how many times i need to read before i am done.
                        MEMORY[offset] = Integer.parseInt(split[i+1]);
                        offset++;
                        }
                    }
                
                }
                else if(state.equals("CODE"))
                {
                    Instruction instr = 
                    // so when i get operation i make it into code. so i can know what to execute.
                    MEMORY[offset] = Integer.parseInt(split[1]);
                }
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void printRegisters() {

    }

    public void ADD() {
        // imame realmachine adressa kuriso yra masina, tada paimama stecko virsune ir sudedame virsunes adresus.
      //  rm.pop()
    }

}
