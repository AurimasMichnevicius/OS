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

public class ManoOperacineSystema { // main class 
 public static final boolean DEBUG = false;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        //System.out.println("hello Wrold");
        RealMachine rm = new RealMachine();
        VirtualMachine vm = new VirtualMachine(rm);
        rm.createVirtualMemory();
        vm.loadProgram("program.txt");
        //vm.fuckupRmMemory();
        vm.runProgram();
        //rm.printRealMemory(0, 255);
        //vm.printSTACK();
        System.out.println(rm.toString());    
}
}
