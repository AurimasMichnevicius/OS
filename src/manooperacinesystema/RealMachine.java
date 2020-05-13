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
    private byte Mode; // supervizorine arba vartotojo
    private int PTR; // puslapio letnetels registras 
    private int IC;// virtualios masines atmintes lasteliu skaitiklis
    private byte PI;// atminties pazeidimui nustatyti
    private byte SI;// uzrakinimo atminties lasteles X
    private int H; // baitu naudojimo registras 
    private int IOL;//israsymo irasimo pertraukimas
    private int TI;// laikmatis
    private int SW; // swapinogo triggeris 
    private int SP; // steko virsunes rodiklis
    private static byte CH1; // klaiviatura 
    private static byte CH2;// hdd
    private static byte CH3;// ekranas
    private byte C; // loginis triggeris
    public static final int PAGE_SIZE = 256; // puslapio dydis  
    public static final int PAGE_COUNT = 256; // puslapyje yra zodziu   
    public static final int MAX_VM_COUNT = 4; // maksimalus vm skaiciuos
    public static final int WORD_SIZE = 4; // zodzio dydis  
 
    public static final int ENTRIES_PER_PAGE_TABLE = PAGE_COUNT; 
    private static final int MEMORY_SIZE = PAGE_COUNT * PAGE_SIZE * MAX_VM_COUNT;
    private int[] MEMORY = new int[MEMORY_SIZE]; /// vidine atmintis
    // daryti is char arba stringo po 4
    // 
    
    private HashMap<Integer, Integer> allocatedMemory = new HashMap<>();
    // Physiscal įrenginiai
    public static HDD hdd;
    public static Keyboard keyboard;
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
    public void Push(int word) {
    
}
    public int Pop(int word)
    {
     return MEMORY[1];
    }
    public void test()
    {
        if (this.SI !=0) // checking SI
        {
            // things to test SI PI IOL TI SW SP
            // to test all itnerupts after every commit
            switch (this.SI) {
      
                case 1:
                          // uzrakiname bendros atmitiens lastele X kurio nuoroda yra SI
                    break;
           
                case 2:
                     // atrakiname lastele X
                    break;
         
                case 3:
                       //baigiame darba
                    break;
                default:
                    break;
            }
        }
        
        
       if (this.TI ==1)
       {
           // tada daromas laiko interuptas ir perduodama kitam vykdymas 
       }
       if (this.PI != 0 )
       {
            switch (this.PI) {
         
                case 1:
                       // pažeista atminties apsauga.
                    break;
            
                case 2:
                    // kai sutinkama neleistina operacija
                    break;
            
                case 3:
                    // neteisingas priskirimas
                    break;
                default:
                    break;
            }
       }
       if (this.IOL == 0)
       {
            switch (this.IOL) {
         
                case 1:
                       // READ
                    break;
           
                case 2:
                     // printinmame X zodi
                    break;
            
                case 3:
                    // steko virsuneje esancius skaiciuos spausdinam kaip numerius
                    break;
           
                case 4:
                     // steko virsuneje esancius skaiciuos spausdinam kaip simbolius
                    break;
                default:
                    break;
            }
          
       }
       if(this.SW != 0)
       {
           if(this.SW == 1)
       {
            // tada mes darome swappinga is MEMORY i HDD ir atgal is HDD I memory    
       }
       }
    }
   private void Swapping()
   {
       //TODO: realizuoti swpapinga 
   }
   public int getPageTableAddress()
    {
        int page   = PTR;  
        return PAGE_SIZE * page;
    }
    public int virtualToRealAddress(int page, int offset)
    {
        int tableAdress = getPageTableAddress();
        return MEMORY[tableAdress * page] * PAGE_SIZE + offset;
    }
    public void setWord(int page, int offset, int value)
    {
        MEMORY[virtualToRealAddress(page,offset)] = value;
    }
    public int getWord(int page,int offset)
    {
       return MEMORY[virtualToRealAddress(page,offset)];
    }
    
        public void printPageTable()
    {
        int tableAddress = getPageTableAddress();
        System.out.println( "---------Page table -----------------");
        System.out.println( "PTR: " + getPTR());
        System.out.print( (tableAddress) + ": ");
        for (int i = 0; i < ENTRIES_PER_PAGE_TABLE; i++) {
            System.out.print(" " + MEMORY[tableAddress+i]);
        }
        System.out.println( "\n-------------------------------------");
    }

public void setPTR(int ptr)
{
    this.PTR = ptr;
}
public void setMode(byte mode)
{
    this.Mode = mode;
}
public void setH(int h)
{
    this.H = h;
}
public int getH()
{
    return this.H;
}
public int getPTR()
{
    return this.PTR;
}
public byte getMode()
{
    return this.Mode;
}
    public void setIC(int IC) {
        this.IC = IC;
    }

    public void setPI(byte PI) {
        this.PI = PI;
    }

    public void setSI(byte SI) {
        this.SI = SI;
    }

    public void setC(byte C) {
        this.C = C;
    }

    public void setIOL(int IOL) {
        this.IOL = IOL;
    }

    public void setTI(int TI) {
        this.TI = TI;
    }

    public void setSW(int SW) {
        this.SW = SW;
    }

    public void setSP(int SP) {
        this.SP = SP;
    }

    public static void setCH1(byte CH1) {
        RealMachine.CH1 = CH1;
    }

    public static void setCH2(byte CH2) {
        RealMachine.CH2 = CH2;
    }

    public static void setCH3(byte CH3) {
        RealMachine.CH3 = CH3;
    }


    public int getIC() {
        return IC;
    }

    public byte getPI() {
        return PI;
    }

    public byte getSI() {
        return SI;
    }

    public byte getC() {
        return C;
    }

    public int getIOL() {
        return IOL;
    }

    public int getTI() {
        return TI;
    }

    public int getSW() {
        return SW;
    }

    public int getSP() {
        return SP;
    }

    public static byte getCH1() {
        return CH1;
    }

    public static byte getCH2() {
        return CH2;
    }

    public static byte getCH3() {
        return CH3;
    }

}
