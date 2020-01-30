package me.bokov.tasks.modules.thymeleaf;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@RequestScoped
@Named ("test")
public class TestThymeleafController {

    public String getHello () {
        return "Hi from controller!";
    }

}
