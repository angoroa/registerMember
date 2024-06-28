package org.example.member.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.member.dto.MemberDTO;
import org.example.member.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
// final 필드는 반드시 생성자에서 초기화되어야 하는데 롬복이 이를 자동으로 처리
//    public MemberController(MemberService memberService) {
//    this.memberService = memberService;
//}
public class MemberController {
    // 생성자 주입
    private final MemberService memberService;
    // 'MemberService' 타입의 빈을 찾아서 'MemberController'의 생성자에 주입
    // 이제 'MemberController'는 'MemberService' 객체를 의존성으로 가지게 되고 그의 메서드 호출 가능
    // 의존성 주입하는 이유 : 두 클래스 간의 결합도를 낮추고 테스트 용이성을 높일 수 있음

    // 회원가입 페이지 출력 요청
    @GetMapping("/member/save")
    public String saveForm(){
        return "save";
    }
    @PostMapping("/member/save")
    public String save(@ModelAttribute MemberDTO memberDTO){
        // MemberDTO를 통쨰로 넘겨준다
        // memberEmail에 담겨온 값을 String mamberEmail에 옮겨 담는다.
        // parameter -> Service 클래스 -> Repository 클래스 -> DATABASE
        System.out.println("MemberController.save"); // 메서드가 잘 실행되는지 확인할 수 있는 방법 soutm
        System.out.println("memberDTO = " + memberDTO); // 매개변수가 잘 넘어왔는지 확인할 수 있는 방법 soutp
        memberService.save(memberDTO);
        return "login";
    }
    @GetMapping("/member/login")
    public String loginForm(){
        return "login";
    }
    @PostMapping("/member/login")
    public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session){
        MemberDTO loginResult = memberService.login(memberDTO);
        if(loginResult != null){
            // login 성공
            session.setAttribute("loginEmail", loginResult.getMemberEmail());
            // loginEmail(세션)에 담아준다.    <------   (로그인 한 회원의 이메일 정보를)
            return "main";
        }else{
            // login 실패
            return "login";
        }
    }
    @GetMapping("/member/")
    public String findAll(Model model){
        List<MemberDTO> memberDTOList = memberService.findAll();
        // 어떠한 html로 가져갈 데이터가 있다면 model 사용
        model.addAttribute("memberList", memberDTOList);
        return "list";
    }
    @GetMapping("/member/{id}")
    // 아래의 매개변수에 있는 값(id)을 받아오겠다
    public String findById(@PathVariable Long id, Model model){
        MemberDTO memberDTO = memberService.findById(id);
        model.addAttribute("member", memberDTO);
        return "detail";
    }
    @GetMapping("/member/update")
    public String updateForm(HttpSession session, Model model){
        String myEmail = (String) session.getAttribute("loginEmail");
        // session에는 Object 타입으로 값들이 저장되기 떄문에 캐스팅이 필요
        MemberDTO memberDTO = memberService.updateForm(myEmail);
        model.addAttribute("updateMember", memberDTO);
        return "update";
    }
    @PostMapping("/member/update")
    public String update(@ModelAttribute MemberDTO memberDTO){
        memberService.update(memberDTO);
        return "redirect:/member/" + memberDTO.getId();
    }
    @GetMapping("/member/delete/{id}")
    public String deleteById(@PathVariable Long id){
        memberService.deleteById(id);
        return "redirect:/member/";
    }
    @GetMapping("/member/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "index";
    }
    // save에서 POST로 작성했는데 Controller 상에서는 Get으로 받는 것 밖에 존재하지 않음
    // 주소는 있는데 방식이 다르기 때문에 생기는 오류 405 / 주소가 없으면 404
}
