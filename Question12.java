/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab1;
abstract class Calculation {
   
    int add(int x, int y) {
        return x + y;
    }

    
    abstract int mul(int x, int y);
}

class Demo extends Calculation {
  
    @Override
    int mul(int x, int y) {
        return x * y;
    }

    // Custom method to calculate division
    int calcDiv(int x, int y) {
        if (y == 0) {
            System.out.println("Cannot divide by zero.");
            return 0;
        }
        return x / y;
    }
}

public class Question12{
    public static void main(String[] args) {
        
        Demo demo = new Demo();

     
        int sum = demo.add(10, 5);
        System.out.println("Sum: " + sum);

     
        int product = demo.mul(5, 2);
        System.out.println("Product: " + product);


        int division = demo.calcDiv(10, 2);
        System.out.println("Division: " + division);

       
        Calculation calculation = new Demo();
        int result = calculation.mul(3, 4);
        System.out.println("Result of multiplication using abstract class reference: " + result);
        System.out.println();
        System.out.println("Name: Robin Devkota\nRoll: 23498 7th-sem-prime");
    }
}


