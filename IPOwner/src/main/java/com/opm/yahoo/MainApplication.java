package com.opm.yahoo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MainApplication   {
	
//	@Value("${server.port}")
//    int port;
//	
	private static Map<String, String> imaps = new HashMap<String,String>();
    
	public static void main(String[] args) {
       SpringApplication.run(MainApplication.class, args);
    }
    
}