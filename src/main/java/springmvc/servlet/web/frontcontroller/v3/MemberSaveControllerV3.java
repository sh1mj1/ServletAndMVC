package springmvc.servlet.web.frontcontroller.v3;

import springmvc.servlet.domain.member.Member;
import springmvc.servlet.domain.member.MemberRepository;
import springmvc.servlet.web.frontcontroller.ModelView;

import java.util.Map;

public class MemberSaveControllerV3 implements ControllerV3 {
    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public ModelView process(Map<String, String> paramMap) {

        String username = paramMap.get("username"); // paramMap 에서 필요한 request 파라미터 조회
        int age = Integer.parseInt(paramMap.get("age"));

        Member member = new Member(username, age);
        memberRepository.save(member);

        ModelView mv = new ModelView("save-result");    // 논리적인 이름.
        mv.getModel().put("member", member);    // 모델에 뷰에서 필요한 객체를 담고 리턴.
        return mv;
    }
}