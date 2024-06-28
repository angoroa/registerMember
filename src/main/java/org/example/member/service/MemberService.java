package org.example.member.service;

import lombok.RequiredArgsConstructor;
import org.example.member.dto.MemberDTO;
import org.example.member.entity.MemberEntity;
import org.example.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
//Spring이 관리해주는 객체 즉, Spring Bean으로 등록
public class MemberService {
    private final MemberRepository memberRepository;
    public void save(MemberDTO memberDTO) {
        // save는 JPA가 제공해주는 메서드
        // 1. dto -> entity로 변환
        // 2. repository의 save 메서드 호출
        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDTO);
        memberRepository.save(memberEntity);
        // repository의 save 메서드 호출 ( 조건. entity객체를 넘겨줘야 함 )
    }

    public MemberDTO login(MemberDTO memberDTO) {
        /*
         1. 회원이 입력한 이메일로 DB에서 조회를 함
         2. DB에서 조회한 비밀번호와 사용자가 입력한 비밀번호와 일치하는지 판단
        */
        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(memberDTO.getMemberEmail());
        if (byMemberEmail.isPresent()){
            // 조회 결과가 있다(해당 이메일을 가진 회원 정보가 있다.)
            MemberEntity memberEntity = byMemberEmail.get();
            if (memberEntity.getMemberPassword().equals(memberDTO.getMemberPassword())){
                // String 비교할 떄는 무조건 equals()
                // entity 객체는 service안에서만 사용하게끔 나머지는 다 DTO 객체를 활용
                // entity -> dto 변환 후 리턴
                MemberDTO dto = MemberDTO.toMemberDTO(memberEntity);
                return dto;
            } else {
                return null;
            }
        } else{
            // 조회 결과가 없다(해당 이메일을 가진 회원이 없다.)
            return null;
        }
    }

    public List<MemberDTO> findAll() {
        List<MemberEntity> memberEntityList = memberRepository.findAll();
        List<MemberDTO> memberDTOList = new ArrayList<>();
        // memberEntityList에 있는 걸 하나하나 꺼내서 memberDTOList에 옮겨담는 과정
        for (MemberEntity memberEntity : memberEntityList) {
            memberDTOList.add(MemberDTO.toMemberDTO(memberEntity));

//            MemberDTO memberDTO = MemberDTO.toMemberDTO(memberEntity);
//            memberDTOList.add(memberDTO);
        }
        return memberDTOList;

    }

    public MemberDTO findById(Long id) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);
        if (optionalMemberEntity.isPresent()){
            return MemberDTO.toMemberDTO(optionalMemberEntity.get());
            // Optional로 감싸져있는걸 까야한다
            // MemberEntity memberEntity = optionalMemberEntity.get();
            // MemberDTO memberDTO = MemberDTO.toMemberDTO(memberEntity);
            // return memberDTO;
        } else{
            return null;
        }
    }
    public MemberDTO updateForm(String myEmail) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(myEmail);
        if (optionalMemberEntity.isPresent()){
            return MemberDTO.toMemberDTO(optionalMemberEntity.get());
        } else {
            return null;
        }
    }

    public void update(MemberDTO memberDTO) {
        memberRepository.save(MemberEntity.toUpdateMemberEntity(memberDTO));
        // update 메서드는 별도로 존재 X save메서드를 이용해주는데 Entity를 넘겨줄 때
        // 어떻게 보면 save 메서드로 두 가지 기능 가능
        // id가 있으면 -> update 쿼리를 날려준다
        // id가 없으면 -> insult 쿼리를 수행한다.
    }

    public void deleteById(Long id) {
        memberRepository.deleteById(id);
    }
}
