package org.example.member.entity;

// Entity 클래스가 일종의 테이블 역할을 하게 된다.
// 당연히 table이 있고 메서드를 이용해서 쿼리를 쓰는 쿼리를 전송해서 테이블과 CRUD작업을 한다 이렇게 알고 계시는데
// spring data JPA라는 것은 database의 테이블을 자바의 객체처럼 이용할수 있게 해주는게 가장 큰 특징이다.

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.member.dto.MemberDTO;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Entity
@Setter
@Getter
// 실제 테이블이 생성되었을 때 이름을 정하는 부분
@Table(name = "member_table")
public class MemberEntity {
    // 우리가 보통 TABLE을 정의할 떄는 PK
    @Id // pk지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long id;

    // member_table이라는 테이블에 아래의 형식을 갖는 Column들이 생성돼서 JPA가 테이블을 알아서 DB에 만들어준다.
    @Column(unique = true) // unique 제약조건 추가
    private String memberEmail;

    @Column
    private String memberPassword;

    @Column
    private String memberName;

    public static MemberEntity toMemberEntity(MemberDTO memberDTO){
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberDTO.getMemberPassword());
        memberEntity.setMemberName(memberDTO.getMemberName());
        return memberEntity;
    }
    public static MemberEntity toUpdateMemberEntity(MemberDTO memberDTO){
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setId(memberDTO.getId());
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberDTO.getMemberPassword());
        memberEntity.setMemberName(memberDTO.getMemberName());
        return memberEntity;
    }
}

