package example.day05과제2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PhoneService {
    @Autowired
    private PhoneEntityRepository phoneEntityRepository;

    @PostMapping
    public boolean doPost(PhoneDto dto) {
        System.out.println("PhoneService.doPost");
        System.out.println("dto = " + dto);
        //1.DTO를 Entity로 변환
        PhoneEntity phoneEntity = PhoneEntity.builder()
                .name(dto.getName())
                .number(dto.getNumber())
                .build();
        //2.인터페이스명.save( 저장할 엔티티 )
        phoneEntityRepository.save(phoneEntity);
        return true;
    }

    public List<PhoneDto> doGet() {
        System.out.println("PhoneService.doGet");

        List<PhoneEntity> entities = phoneEntityRepository.findAll(Sort.by(Sort.Order.asc("name")));

        //List<Entity> 를 List<DTO> 로 변환
        List<PhoneDto> list = new ArrayList<>();

        // 리스트 하나씩 꺼내기
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
    @Transactional
    public boolean doPut( PhoneDto dto) {
        System.out.println("PhoneService.doPut");
        System.out.println("put서비스실행");
        Optional<PhoneEntity>entities=  phoneEntityRepository.findById(dto.getPno());
        if(entities.isPresent()) {
            PhoneEntity updateEntity = entities.get();
            updateEntity.setName(dto.getName());
            updateEntity.setNumber(dto.getNumber());
            return true;
        }
       return false;
    }

    public boolean doDelete(int pno) {
        phoneEntityRepository.deleteById(pno);
        return true;
    }




}
