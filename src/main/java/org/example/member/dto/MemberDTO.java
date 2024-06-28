package org.example.member.dto;

import lombok.*;
import org.example.member.entity.MemberEntity;

@Getter
@Setter
// 각각의 필드에 대한 Getter & Setter를 자동으로 생성
@NoArgsConstructor
// 기본생성자를 자동으로 만들어주는 기능
@AllArgsConstructor
// 필드를 매개변수로 하는 생성자를 만들어주는 기능
@ToString
// DTO객체가 가지고 있는 필드값을 출력할 떄 Tostring을 사용하는데 이를 자동으로 만들어주는 기능

public class MemberDTO {
    // DTD라는 클래스는 회원정보에 필요한 내용을 필드에 정의. 필드의 내용은 모두 private으로
    private Long id;
    private String memberEmail;
    private String memberPassword;
    private String memberName;

    public static MemberDTO toMemberDTO(MemberEntity memberEntity){
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(memberEntity.getId());
        memberDTO.setMemberEmail(memberEntity.getMemberEmail());
        memberDTO.setMemberPassword(memberEntity.getMemberPassword());
        memberDTO.setMemberName(memberEntity.getMemberName());
        return memberDTO;
    }
}
