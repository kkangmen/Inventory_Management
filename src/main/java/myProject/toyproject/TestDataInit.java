package myProject.toyproject;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import myProject.toyproject.item.dto.ItemCreateRequest;
import myProject.toyproject.item.entity.Item;
import myProject.toyproject.item.service.ItemService;
import myProject.toyproject.member.dto.MemberCreateRequest;
import myProject.toyproject.member.service.MemberService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final ItemService itemService;
    private final MemberService memberService;

    @PostConstruct
    public void init(){
        initItems();
        initMembers();
    }
    private void initItems() {
        /**
         * 더미 데이터 중복 방지
         */
        List<Item> allItems = itemService.getAllItems();
        if (allItems.isEmpty()){
            itemService.createItem(new ItemCreateRequest("itemD", 40000, 400));
            itemService.createItem(new ItemCreateRequest("itemE", 50000, 500));
            itemService.createItem(new ItemCreateRequest("itemF", 60000, 600));
        }
    }

    private void initMembers() {
        /**
         * 더미 데이터 중복 방지
         */
        MemberCreateRequest memberCreateRequest = new MemberCreateRequest("test", "test", "tester");

        if (!memberService.existsByLoginId("test")) {
            memberService.save(memberCreateRequest);
        }
    }


}
