package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService; //기본-controller가 service를 쓴다는 것

    @GetMapping("/members/new")
    public String createForm(Model model){
        model.addAttribute("memberForm", new MemberForm()); //memberForm이라는 빈껍데기를 들고가
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result){
        //Member 엔티티 대신 MemberForm 넣는 이유...
        //화면(도메인)에 맞는 엔티티가 따로 있는 부분... 필요한 데이터만 채워서 넣자!

        if (result.hasErrors()){
            return "members/createMemberForm";
        }

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);

        memberService.join(member);
        return "redirect:/";

    }

    @GetMapping("/members")
    public String list(Model model){ //Model 객체를 통해서 화면에 데이터 전달
        //화면에 뿌릴 때도 dto만들어서 하는 것이 좋다.
        //API를 만들때는 절대 엔티티를 외부로 반환하면 안된다.
        //템플릿 엔진에서는 써도 무방..
        List<Member> members = memberService.findMembers(); //Ctrl+alt+v 선언하기
        model.addAttribute("members",members); //Ctrl+alt+N 인라인 만들기
        return "members/memberList";
    }

}
