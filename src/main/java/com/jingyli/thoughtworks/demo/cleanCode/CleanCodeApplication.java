package com.jingyli.thoughtworks.demo.cleanCode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CleanCodeApplication {

    public static void main(String[] args) {
        try {
            Args arg = new Args("l,p#,d*", args);
            boolean logging = arg.getBoolean('l');
            int port = arg.getInt('p');
            String directory = arg.getString('d');
            executeApplication(logging, port, directory);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void executeApplication(boolean logging, int port, String directory) {
        System.out.println("logging:" + logging + ", port: " + port + ", directory " + directory);
    }
}
