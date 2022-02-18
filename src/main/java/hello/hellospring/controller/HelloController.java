package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.websocket.server.PathParam;

@Controller
public class HelloController {
    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("data", "hello!!!!!");

        return "hello";
    }

    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam(name = "name", required = false) String name, Model model ) {
        model.addAttribute("name", name);
        return "hello-template";
    }
    
    // ResponseBody 사용할 때, 단순 문자열이면 문자열로 반환, 객체면 JSON으로 반환
    @GetMapping("hello-string")
    @ResponseBody
    public String helloString(@RequestParam("name") String name) {
        return "hello " + name; // 뷰를 타지않고 문자 그대로 전달됨
    }

    @RequestMapping("/hello-api")
    @ResponseBody
    public Hello helloApi(@PathParam("name") String name) {
        Hello hello = new Hello();

        hello.setName(name);

        return hello;
    }

    // 스태틱으로 만들면 여기 클래스에서 staticd class를 그냥 사용할 수 있음
    // HelloController.Hello
    static class Hello {
        private String name;

        // getter setter 생성 단축키
        // Win : Alt + Insert
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
