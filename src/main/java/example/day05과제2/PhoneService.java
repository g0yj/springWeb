package example.day05과제2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Service
public class PhoneService {
    @Autowired
    private PhoneEntityRepository phoneEntityRepository;

    @PostMapping
    public boolean doPost(PhoneDto dto) {
        //1.DTO를 Entity로 변환
        PhoneEntity phoneEntity = PhoneEntity.builder()
                .name(dto.getName())
                .number(dto.getNumber())
                .build();
        //2.인터페이스명.save( 저장할 엔티티 )
        phoneEntityRepository.save(phoneEntity);


        return false;
    }

    public List<PhoneDto> doGet() {
        //1.모든엔티티호출 -> 인터페이스명.findAll()
        // 반환타입 : 검색된 엔티티 여러개 List<Entity>
        List<PhoneEntity> entities = phoneEntityRepository.findAll();

        //2. List<Entity> 를 List<DTO> 로 변환
        List<PhoneDto> list = new ArrayList<>();

        //3. 리스트 하나씩 꺼내기
        entities.forEach(r -> {
            //3. 엔티티를 dto로 변환
            PhoneDto dto = PhoneDto.builder()
                    .pno(r.getPno())
                    .name(r.getName())
                    .number(r.getNumber())
                    .build();
            //4.list에 저장
            list.add(dto);
        });
        return list;
    }






}
