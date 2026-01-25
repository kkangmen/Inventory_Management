package myProject.toyproject;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import myProject.toyproject.item.dto.ItemCreateRequest;
import myProject.toyproject.item.repository.MemoryItemRepository;
import myProject.toyproject.item.service.ItemServiceImpl;
import myProject.toyproject.member.dto.MemberCreateRequest;
import myProject.toyproject.member.repository.MemoryMemberRepository;
import myProject.toyproject.member.service.MemberService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final ItemServiceImpl itemService;
    private final MemberService memberService;

    @PostConstruct
    public void init(){

        /***
         * 더미 데이터
         */
        ItemCreateRequest itemD = new ItemCreateRequest("itemD", 40000, 400);
        ItemCreateRequest itemE = new ItemCreateRequest("itemE", 50000, 500);
        ItemCreateRequest itemF = new ItemCreateRequest("itemF", 60000, 600);

        itemService.createItem(itemD);
        itemService.createItem(itemE);
        itemService.createItem(itemF);

        MemberCreateRequest memberCreateRequest = new MemberCreateRequest("test", "test", "tester");
        memberService.save(memberCreateRequest);
    }
}
