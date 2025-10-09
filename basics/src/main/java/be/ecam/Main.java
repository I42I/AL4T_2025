package be.ecam;

public class Main {

    public static void main(String[] args) {
        String hello = "hello";
        String world = "world";
        System.out.println(hello + " "  +  world);

        StringBuilder builder = new StringBuilder();
        for( int i =0; i < 100 ; i++) {
            builder.append(i);
        }
        System.out.println(builder.toString());



    }
}
