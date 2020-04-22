/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manooperacinesystema;

import java.util.HashMap;

/**
 *
 * @author User
 */
public class RealMachine {
    private byte Mode;
    private int PTR;
    private int IC;
    private byte PI;
    private byte SI;
    private int H;
    private byte C;
    private int IOL;
    private int TI;
    private int SW;
    private int SP;
    private static byte CH1;
    private static byte CH2;
    private static byte CH3;
    
    public static final int PAGE_SIZE = 256;
    public static final int PAGE_COUNT = 256;
    public static final int MAX_VM_COUNT = 4;
    public static final int WORD_SIZE = 4;
 
    public static final int ENTRIES_PER_PAGE_TABLE = PAGE_COUNT;
    private static final int MEMORY_SIZE = PAGE_COUNT * PAGE_SIZE * MAX_VM_COUNT;
    private int[] MEMORY = new int[MEMORY_SIZE];
    
    private HashMap<Integer, Integer> allocatedMemory = new HashMap<>();
    // Physiscal įrenginiai
    public static HDD hdd;
    public static FlashMemory flashMemory;
    public static Printer printer;
    /*
        public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("--------RM--------\n");
        builder.append("MODE: " + getMODE() + "\t\tPTR: " + getPTR() + "\tIC: " + getIC() + "\n");
        builder.append("PI: " + getPI() + "\t\tSI: " + getSI() + "\t\tR1: " + getR1() + "\n");
        builder.append("R2: " + getR2() + "\t\tR3: " + getR3() + "\t\tCMP: " + getCMP() + "\n");
        builder.append("IO: " + getIO() + "\t\tTI: " + getTI() + "\t\tCH1: " + getCH1() + "\n");
        builder.append("CH2: " + getCH3() + "\t\tCH3: " + getCH3() + "\t\tSHR: " + getSHR() + "\n");
        builder.append("LCK: " + getLCK() + "\n");
        builder.append("------------------\n");
        return builder.toString();
    }
*/
    public void ADD()
    {
        MEMORY[SI-1] = MEMORY[SI-1]+MEMORY[SI];
    }
    public void SUB()
    {
        MEMORY[SI-1] = MEMORY[SI]-MEMORY[SI-1];
    }
    public void MUL()
    {
        MEMORY[SI-1] = MEMORY[SI]*MEMORY[SI-1];
    }
    public void DIV()
    {
        MEMORY[SI-1] = MEMORY[SI]/MEMORY[SI-1];
    }
    public void CMP()
    {
        
    }
    public void WB()
    {
        
    }
    public void RB()
    {
        
    }
    public void LC()
    {
    }
    public void UC()
    {
        
    }
    public void JM()
    {
        
    }
    public void JE()
    {
        
    } 
    public void JL()
    {
        
    }
     public void JG()
    {
        
    }
     public void HW(int x)
     {
         
     }
     public void PUSH(int x)
     {
         
     }
     public int POP()
     {
         return 1;
     }
     public void PRTS()
     {
         
     }
     public void PRTN()
     {
         
     }
     public void PRT()
     {
     
     }
     public void SWP()
     {
     
     }
     public void RD()
     {
         
     }
     public void HALT()
     {
         
     }
     
}
