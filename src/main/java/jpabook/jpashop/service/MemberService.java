package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)//jakarta가 제공하는것 보다 spring이 제공하는 기능이 더 많다
//읽기 전용에는 readOnly = true 추가 해주자

public class MemberService {

    // @Autowired  Spring bean에 등록 되어 있는 memberRepository를 injection
    private MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository){
        this.memberRepository=memberRepository;
    }

    /**
     * 회원 가입
     */
    @Transactional//쓰기 전용에는 readOnly = true 추가 하면 안됨 값이 바뀌지 않는다
    public Long join(Member member){

        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    // 중복회원 검증
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = MemberRepository.findByName(member.getName()); 
        //실무에서는 동시에 회원 가입하는 경우를 대비해 unique 제약조건으로 잡아주는것을 권장
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재한는 회원입니다.");
        }
    }
    //회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }
    //1건만 조회
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }

}
