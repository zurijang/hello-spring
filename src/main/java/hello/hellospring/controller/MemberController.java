package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

// Ctrl + N : 클래스 찾아서 파일 전환

@Controller
public class MemberController {

    // new 로 객체를 쓸 수 있지만, Spring이 관리하게되면 스프링 컨테이너에 등록하고 스프링 컨테이너로부터 받아서 쓰도록 바꿔야 함
    // new 로 쓰면 MemberController 말고 여러 컨트롤러들이 memberService를 쓸 수 있음
    // Spring Container에 등록하고 쓰면 됨
    private final MemberService memberService;

    // Spring Container에 등록 : 생성자에 @Autowired
    // @Autowired : 스프링 컨테이너가 생기면서 생성자 호출하면, memberService를 스프링에 memberService에 연결시켜줌
    // 단 Service 클래스에 @Service 를 명시해줘야 함 (어노테이션을 단다 -> 스프링에서 관리하도록 하겠다)
    // Alt + Insert : Generate
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
        // AOP 적용하여 스프링 실행되는 것 확인하면 프록시의 memberService를 확인할 수 있음
        // AOP를 적용한 memberService
        System.out.println("memberService = " + memberService.getClass());
    }

    // GetMapping : URL을 통해 주로 조회할 때
    @GetMapping("/members/new")
    public String createForm() {
        return "members/createMemberForm";
    }

    // PostMapping : Data Form 전달할 때
    // Form에서 key는 입력 태그의 name으로 지정된 값
    @PostMapping("/members/new")
    public String create(MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());

        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);

        return "members/memberList";
    }

}
