package springmvc.servlet.web.frontcontroller.v4;

import springmvc.servlet.domain.member.Member;
import springmvc.servlet.domain.member.MemberRepository;

import java.util.Map;

public class MemberSaveControllerV4 implements ControllerV4{

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public String process(Map<String, String> paramMap, Map<String, Object> model) {

        String username = paramMap.get("username");
        int age = Integer.parseInt(paramMap.get("age"));

        Member member = new Member(username, age);
        memberRepository.save(member);

        // 모델이 파라미터로 전달되므로 ModelView 클래스의 객체를 따로 만들지 않아도 됨.
        model.put("member", member);
        return "save-result";
    }
}