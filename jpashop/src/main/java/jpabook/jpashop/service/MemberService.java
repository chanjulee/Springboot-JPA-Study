package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) //조회기능 최적화
@RequiredArgsConstructor //롬복 생성자만들어주는...
public class MemberService {

    private final MemberRepository memberRepository;

    //회원 가입
    @Transactional //readOnly = false
    public Long join(Member member){
        validateDuplicateMember(member); //중복회원검증 비지니스로직
        memberRepository.save(member); //문제가없다면 정상적으로 저장
        return member.getId(); //아이디 반환
    }

    private void validateDuplicateMember(Member member) {
        //EXCEPTION
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원 전체 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    //한 건 조회
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }

}
