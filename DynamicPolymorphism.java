class DynamicPolymorphism {
    // Parent class
    static class Animal {
        void sound() {
            System.out.println("Animal makes a sound");
        }
    }

    // Child class
    static class Dog extends Animal {
        // Method overriding
        @Override
        void sound() {
            System.out.println("Dog barks");
        }
    }

    public static void main(String[] args) {
        Animal animal = new Dog(); // Parent reference but Child object
        animal.sound(); // Calls the sound() method of Dog class
    }
}
