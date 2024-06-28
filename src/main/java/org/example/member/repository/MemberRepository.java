package org.example.member.repository;

import org.example.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    // 첫번쨰 인자는 어떤 entity클래스를 받을 것인가 , 두번째 인자는 Id라는 어노테이션을 붙인 column의 type이 뭔지 써줌
    // 이메일로 회원 정보 조회하는 추상 메서드 정의 (select * from member_table where member_email=?)
    Optional<MemberEntity> findByMemberEmail(String memberEmail);
}
