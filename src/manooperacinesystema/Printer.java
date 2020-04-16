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

public class Printer {

    public static void print(Object data) {
        System.out.print("Printer: ");
        if (data instanceof char[]) {
            System.out.println((char[]) data);
        } else {
            System.out.println(data);
        } 
    }
}