/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manooperacinesystema;

/**
 *
 * @author User
 */
public enum Instruction {
    ADD(0x01),// VEIKIA
    SUB(0x02),// VEIKIA
    MUL(0x03),// VEIKIA
    CMP(0x04),// VEKIA
    DIV(0x05),// VEKIKA
    PUSH(0x06,1),// VEIKIA 
    POP(0x07),// VEIKIA 
    //UL(0x08), //neveikia
    JM(0x08, 2),// Veikia
    HALT(0x10),// Veikia
    JE(0x0C, 2), // veikia.  
    JG(0x0D, 2), // veikia.
    JL(0x0E, 2), // veikia
    PRTS(0x09), // Veikia
    PRTN(0x0A), // Veikia
    LC(0x22, 0),// veikia
    UC(0x23, 0),// veikia
    WB(0x24, 2), // veikia
    RB(0x25, 2), // veikia
    
    
    //WRT(0x20, 1),// neveikia // tokio net nera,. pas mane PTR
    READ(0x21, 0), // neveikia

    PRT(0x12, 1); //neveikia
    private int opcode;
    private int args;
    private Instruction(int opcode)
    {
        this(opcode, 0);
    }
    private Instruction(int opcode, int argCount)
    {
        this.opcode = opcode;
        this.args = argCount;
    }
    public int getOpcode()
    {
        return opcode;
    }
    public int getArgCount()
    {
        return args;
    }
    public static int getOpcodeByName(String name){
       return Instruction.valueOf(name).getOpcode();
    }
    public static Instruction getInstructionByName(String name){
       return Instruction.valueOf(name);
    }
    public static String getCommandName(int op)
    {
        for (Instruction instruction : Instruction.values()) {
            if(instruction.opcode == op)
                return instruction.name();
        }
        return "";
    }
}