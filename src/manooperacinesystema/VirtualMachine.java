/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manooperacinesystema;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import sun.applet.Main;

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
    public static final int SHARED_MEMORY_SEGMENT = 0xFE;
    public static final int stack_start = 0x90;
    public static int printerPage;
    private int SP = 0;
    private VirtualMachine shrVm;
    private RealMachine rm;
    private static final int Data_segment_start = 0;
    private static final int Code_segment_start = PAGE_SIZE * 0x40;

    private int[] MEMORY = new int[MEMORY_SIZE]; /// vidine atmintis

    public VirtualMachine(RealMachine rm) {
        this.rm = rm;
    }

    public void loadProgram(String programName) {
      //  Scanner scanner = new Scanner(System.in);
          try(BufferedReader br = new BufferedReader(new FileReader(programName)))
        {
        String state = "START";
        String currentLine = "";
        int offset = 0;
        while ((currentLine = currentLine = br.readLine()) != null) {
            String[] split = currentLine.split(" ");

            currentLine = split[0];
            //System.out.println(currentLine);
            if (state.equals("START")) {
                if (currentLine.equals("DATA")) {

                    state = "DATA";
                    offset = Data_segment_start;
                    continue;

                } else {
                    System.err.println("Progam did not find Data segment");
                    return;
                }
            } else if (state.equals("DATA")) {
                if (currentLine.equals("CODE")) {

                    state = "CODE";
                    offset = Code_segment_start;
                } else if (currentLine.equals("DW")) {
                    // System.out.println(currentLine);
                    rm.writeWord(offset++, Integer.parseInt(split[1]));
                }

            } else if (state.equals("CODE")) {

                Instruction instr = Instruction.getInstructionByName(currentLine);
                //  System.out.println(instr.getOpcode());
                // so when i get operation i make it into code. so i can know what to execute.
                rm.writeWord(offset++, instr.getOpcode());
                for (int i = 0; i < instr.getArgCount(); i++) {
                    //writeWord(offset++, Integer.parseInt(split[i+1]));
                    rm.writeWord(offset++, Integer.parseInt(split[i + 1]));
                }
                if (currentLine.equals("HALT")) {
                    return;
                }
            }

        }
        } catch(IOException ioe)
        {
            ioe.printStackTrace();
        }
    }

    public void runProgram() throws Exception {
        rm.setIC(this.Code_segment_start);
        try {
            while (true) {
                executeInstruction();
                 //rm.printVirtualMemory(0, 255);
                rm.test();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (Exception e) {
            if (e.getMessage().equals("HALT")) {
                return;
            } else {
                throw e;
            }
        }
    }

    public void writeWord(int address, int word) {
        if (address < 0 || address >= MEMORY_SIZE) {
            return;
        }
        int page = address / PAGE_SIZE;
        int offset = address - page * PAGE_SIZE;
        writeWord(page, offset, word);
    }

    public void writeWord(int page, int offset, int word) {
        if (page < 0 || page >= PAGE_COUNT || offset < 0 || offset >= PAGE_SIZE) {
            return;
        }
        //System.out.println(page+ " off: "+ offset+ " word : " + word);
        rm.setWord(page, offset, word);
    }

    public int readWord(int address) {
        if (address < 0 || address >= MEMORY_SIZE) {
            return -1;
        }
        int page = address / PAGE_SIZE;
        int offset = address - page * PAGE_SIZE;
        return rm.getWord(page, offset);
    }

    public int readWord(int page, int offset) {
        return readWord(page * PAGE_SIZE + offset);
    }

    public void executeInstruction() throws Exception {
        int op = readWord(rm.getIC());
        if (ManoOperacineSystema.DEBUG) {
            System.out.println("iveskite tuscia eilute jog ivykdytumet instruckija: " + Instruction.getCommandName(op));
            System.out.println("rasykite help noredami gauti daugiau komandu");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String input = reader.readLine();
            System.out.println(input);

            if (input.equals("help")) {
                System.out.println("naudoti <rm >kad pamatytumete tikra atminti: rm <start> <end>"); // reiks pakeisti 
                System.out.println("\t\t- rm 0 5");
                System.out.println("naudoti <vm> kad pamatytuumete Virtualia  atminti:vm <start> <end>");
                System.out.println("\t\t- vm 0 5");
                System.out.println("naudoti <print S> kad pamatytumete stecka");
                System.out.println("\n");
                System.out.println("naudoti <print vm> kad pamatytumete virtualia masina");
                System.out.println("\n");
                System.out.println("naudoti <pt> jog pamatytumete PTR");
                System.out.println("\n");
                return;
            } else if (input.equals("print rm")) {
                System.out.println(rm.toString());
                return;
            } else if (input.equals("print S")) {
                printSTACK();
                return;
            }
                        else if(input.equals("print vm"))
            {
                printRegisters();
                return;
            }else if (input.equals("pt")) {
                rm.printPageTable();
                return;
            } else if (input.contains("vm") || input.contains("rm")) {
                try {
                    String[] split = input.split(" ");
                    if (split.length == 3) {
                        int start = Integer.parseInt(split[1]);
                        int end = Integer.parseInt(split[2]);
                        if (input.contains("vm")) {
                            rm.printVirtualMemory(start, end);
                        } else if (input.contains("rm")) {
                            rm.printRealMemory(start, end);
                        }
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
        rm.setIC(rm.getIC() + 1);
        if (op == Instruction.ADD.getOpcode()) {
            ADD();
        } else if (op == Instruction.SUB.getOpcode()) {
            SUB();
        } else if (op == Instruction.MUL.getOpcode()) {
            MUL();
        } else if (op == Instruction.CMP.getOpcode()) {
            CMP();
        } else if (op == Instruction.DIV.getOpcode()) {
            DIV();
        } else if (op == Instruction.PUSH.getOpcode()) {
            PUSH();
        } else if (op == Instruction.POP.getOpcode()) {
           // System.out.println("STACK POPED: "+POP());
        } else if (op == Instruction.JM.getOpcode()) {
            JM();
        } else if (op == Instruction.JE.getOpcode()) {
            JE();
        } else if (op == Instruction.JG.getOpcode()) {
            JG();
        
        } else if (op == Instruction.JL.getOpcode()) {
            JL();
        
        } else if (op == Instruction.READ.getOpcode()) {
            READ();
        } else if (op == Instruction.LC.getOpcode()) {
            LC();
        } else if (op == Instruction.UC.getOpcode()) {
            UC();
        } else if (op == Instruction.PRTS.getOpcode()) {
            PRTS();
        } else if (op == Instruction.PRTN.getOpcode()) {
            PRTN();
        } else if (op == Instruction.WB.getOpcode()) {
            WB();
        } else if (op == Instruction.RB.getOpcode()) {
            RB();
        } else if (op == Instruction.HALT.getOpcode()) {
            HALT();
        }else if (op == Instruction.PRT.getOpcode()) {
            PRT();
        }else if (op == Instruction.SWAP.getOpcode()) {
            SWAP();
        }
        else {
            throw new Exception("Unrecognized instruction's opcode: " + op);
        }
        if (ManoOperacineSystema.DEBUG) {
            System.out.println(rm.toString());
        }
    }

    public void printSTACK() {
        for (int i = 0; i < SP; i++) {
            System.out.print("SP :" + i + "  VALUE:" + this.readWord(0x90, i + 1) + "||");
        }
        System.out.println();
    }
    public void fuckupRmMemory()
    {
        for(int i = 0; i < 256; i++)
        {
            for(int j = 0 ; j <256; j++)
            {
                rm.setWord(i, j, -1);
            }
        }
    }
    public void printRegisters()
    {
        System.out.println("-----------VM-------------");
        System.out.print("STACK: "  );
        printSTACK();
        System.out.println("IC: " + rm.getIC()+ "\t CMP: " + rm.getSF());
        System.out.println("--------------------------");
    }
    public void ADD() {
        int i = (POP() + POP());
        // System.out.println(i);
        PUSH(i);
        rm.setTI(rm.getTI() + 1);
    } // veikia  // REIKIA PATIKRINTI AR NEPERSOKO PER TA RIBA

    public void SUB() {

        int kuri_minusuojame = POP();
        int minusuojamas = POP();
        //System.out.println((minusuojamas - kuri_minusuojame));
        PUSH((minusuojamas - kuri_minusuojame));
        rm.setTI(rm.getTI() + 1);
    }  // WORKS // REIKIA PATIKRINTI AR NEPERSOKO PER TA RIBA

    public void MUL() {
        PUSH(POP() * POP());
        rm.setTI(rm.getTI() + 1);
    } // REIKIA PATIKRINTI AR NEPERSOKO PER TA RIBA

    public void DIV() {
        int kuri_minusuojame = POP();
        int minusuojamas = POP();
        PUSH((minusuojamas / kuri_minusuojame));
        rm.setTI(rm.getTI() + 1);
    } // REIKIA PATIKRINTI AR NEPERSOKO PER TA RIBA

    public void CMP() {
        int lyginamasis = POP();
        int kitasLyginamasis = POP();
        if (kitasLyginamasis == lyginamasis) {
            rm.setSF(1);
        } else if (kitasLyginamasis < lyginamasis) {
            rm.setSF(0);
        } else if (kitasLyginamasis > lyginamasis) {
            rm.setSF(2);
        }
        rm.setTI(rm.getTI() + 1);
    } // VEIKIA.

    public void PUSH(int i) {
        //System.out.println(i+ " SP : "+ SP+1 );
        
        SP++;
        //writeWord(0x90, SP, i);
        rm.setTI(rm.getTI() + 1);
        
        

    }

    public void PUSH() {
        SP++;
      // rm.printRealMemory(0, 400);
        writeWord(0x90, SP, readWord(rm.getIC()));
        rm.setIC(rm.getIC() + 1);
        rm.setTI(rm.getTI() + 1);
      //  System.out.println(" *-----------------------------------------------------------------------------");
      //  rm.printRealMemory(0, 255);

    }

    public int POP() {
        SP--;
        //System.out.println(SP +  "popas ");
        return readWord(0x90, SP + 1);
    }

    public void JM() { // jump 
        int x1 = readWord(rm.getIC());
        rm.setIC(rm.getIC() + 1);
        int x2 = readWord(rm.getIC());
        rm.setIC(rm.getIC() + 1);
        rm.setIC(PAGE_SIZE * x1 + x2);
        rm.setTI(rm.getTI() + 1);
    }

    public void JE() { // jmp if equals
        
            int x1 = readWord(rm.getIC());
            rm.setIC(rm.getIC() + 1);
            int x2 = readWord(rm.getIC());
            rm.setIC(rm.getIC() + 1);
            if (rm.getSF() == 1) {
            rm.setIC(PAGE_SIZE * x1 + x2);
            
        }
            rm.setTI(rm.getTI() + 1);
    }

    public void JG() { // jump if greater
        
            int x1 = readWord(rm.getIC());
            rm.setIC(rm.getIC() + 1);
            int x2 = readWord(rm.getIC());
            rm.setIC(rm.getIC() + 1);
            if (rm.getSF() == 0) {
            rm.setIC(PAGE_SIZE * x1 + x2);
            
        }
            rm.setTI(rm.getTI() + 1);
    }

    public void JL() // jump lover
    {
        
            int x1 = readWord(rm.getIC());
            rm.setIC(rm.getIC() + 1);
            int x2 = readWord(rm.getIC());
            rm.setIC(rm.getIC() + 1);
            if (rm.getSF() == 2) {
            rm.setIC(PAGE_SIZE * x1 + x2);
           
        }
             rm.setTI(rm.getTI() + 1);

    }


    public void READ() { // kaip veiks bus is klaviaturos nureadins mano inputa, 
        //tada ji ikels i HDD tada swappinsiu komandu ivykdyma, persetinsiu setIC is naujo ir pabaigsiu vykdyti HDD komandas.
        rm.setCH1((byte)1);
        rm.setMode((byte)1);
        rm.setIOL(1);
        rm.setTI(rm.getTI()+3);
        
    }
    
    public void SWAP() { // kaip veiks bus is klaviaturos nureadins mano inputa, 
        //tada ji ikels i HDD tada swappinsiu komandu ivykdyma, persetinsiu setIC is naujo ir pabaigsiu vykdyti HDD komandas.
        rm.setCH1((byte)2);
        rm.setMode((byte)1);
        rm.setSW(1);
        rm.setTI(rm.getTI()+3);
        
    }

    public void LC() {
        rm.setSI((byte)1);
        shrVm = this;
        rm.setTI(rm.getTI() + 1);

    }

    public void UC() {
        rm.setSI((byte) 0);
        shrVm = null;
        rm.setTI(rm.getTI() + 3);
    }

    public void HALT() throws Exception { // done here .
        rm.setSI((byte) 3);
        rm.setTI(rm.getTI() + 1);
        throw new Exception("HALT");
    }

    public void PRTS() {// reikia pakeist kad per supervizorine printintu
       // System.out.println((char) POP());
        rm.setIOL(4);
        rm.setMode((byte) 1);
        RealMachine.setCH3((byte)1);
        rm.setTI(rm.getTI() + 3);
        rm.setSP(SP);
        
    }

    public void PRTN() {// reikia pakeist kad per sueprvizorine printintu
       // System.out.println(POP());
        rm.setIOL(3);
        rm.setMode((byte) 1);
        RealMachine.setCH3((byte)1);
        rm.setTI(rm.getTI() + 3);
        rm.setSP(SP);
    }

    public void PRT() {// reikia padaryt kad per supervizorine printtu.
       /* int x1 = readWord(rm.getIC());
        rm.setIC(rm.getIC() + 1);
        for(int i = 0 ; i <256 ; i ++)
        {
            System.out.print(readWord(x1*256 + i)+ " ");
        }*/
        rm.setIOL(2);
        rm.setMode((byte) 1);
        RealMachine.setCH3((byte)1);
        rm.setTI(rm.getTI() + 3);
        rm.setSP(SP);
         
    }

    public void WB() {
        int x1 = readWord(rm.getIC());
        rm.setIC(rm.getIC() + 1);
        int x2 = readWord(rm.getIC());
        rm.setIC(rm.getIC() + 1);
        if (rm.getSI() == 1 && shrVm != this) {
            rm.setPI((byte) 3);
            return;
        }
        writeWord((SHARED_MEMORY_SEGMENT + x1) * PAGE_SIZE + x2, POP());
        rm.setTI(rm.getTI() + 1);

    }

    public void RB() {
        int x1 = readWord(rm.getIC());
        rm.setIC(rm.getIC() + 1);
        int x2 = readWord(rm.getIC());
        rm.setIC(rm.getIC() + 1);
        if (rm.getSI() == 1 && shrVm != this) {
            rm.setPI((byte) 3);
            return;
        }
        PUSH(readWord((SHARED_MEMORY_SEGMENT + x1) * PAGE_SIZE + x2));
        rm.setTI(rm.getTI() + 1);
    }
}
/*

00 
*/
