package dev.tien.runnerz;

import org.springframework.stereotype.Component;

@Component
public class Welcome {
    public String getWelcome(){
        return "Hello World!";
    }
}
