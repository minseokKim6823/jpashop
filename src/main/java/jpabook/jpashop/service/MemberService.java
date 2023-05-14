package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
//jakarta가 제공하는것 보다 spring이 제공하는 기능이 더 많다
//읽기 전용에는 readOnly = true 추가 해주자
//@AllArgsConstructor public MemberService~~~안써도됨 lombok적용
@RequiredArgsConstructor // private final있는 필드만 가지고 생성자를 만들어준다. 원래 setter나 생성자 injection을 써야됨
public class MemberService {

    private final MemberRepository memberRepository;

    //@Autowired 이 없어도 자동으로 memberRepository를 injection 해줌

    /**
     * 회원 가입
     */
    @Transactional//쓰기 전용에는 readOnly = true 추가 하면 안됨 값이 바뀌지 않는다
    public Long join(Member member){
        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member);//동시에 회원가입 하는경우 동시에 호출가능
        return member.getId();
    }

    // 중복회원 검증
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        //실무에서는 동시에 회원 가입하는 경우를 대비해 unique 제약조건으로 잡아주는것을 권장
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
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
