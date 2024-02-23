class StaticPolymorphism {
    // Method Overloading
    static int add(int a, int b) {
        return a + b;
    }

    static double add(double a, double b) {
        return a + b;
    }

    public static void main(String[] args) {
        System.out.println(add(5, 3));       // Calls the int version of add()
        System.out.println(add(5.5, 3.3));   // Calls the double version of add()
    }
}
