package myProject.toyproject.item.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myProject.toyproject.item.dto.ItemCreateRequest;
import myProject.toyproject.item.dto.ItemUpdateRequest;
import myProject.toyproject.item.entity.Item;
import myProject.toyproject.item.service.ItemService;
import myProject.toyproject.weather.dto.WeatherResponse;
import myProject.toyproject.weather.service.WeatherService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.URI;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/items")
@Controller
@Tag(name = "상품 관리", description = "상품 생성, 조회, 수정, 삭제 API")
public class ItemController {

    private final ItemService itemService;
    private final WeatherService weatherService;

    /***
     * 기상청 단기예보 API 확인
     */
    @GetMapping("/weather")
    @ResponseBody
    public ResponseEntity<String> jsonWeatherApi(){
        String currentWeather = weatherService.jpaGetKmaWeather();
        return ResponseEntity.ok()
                .header("Content-Type", "application/json; charset=UTF-8")
                .body(currentWeather);
    }

    /***
     * 상품 목록을 가져온다.
     * @param model
     * @return
     */
    @GetMapping
    public String items(Model model){
        List<Item> allItems = itemService.getAllItems();
        model.addAttribute("items", allItems);

        WeatherResponse currentWeather = weatherService.getCurrentWeather();
        if (currentWeather != null){
            model.addAttribute("weather", currentWeather);
        }

        return "item/items";
    }

    /***
     * postman 테스트용
     * @param request
     * @return
     */
    //@PostMapping
    @ResponseBody
    public ResponseEntity<Void> jsonCreateItem(ItemCreateRequest request){
        Long itemId = itemService.createItem(request);
        return ResponseEntity.created(URI.create("/api/items/" + itemId)).build();
    }

    /***
     * 상품 등록 폼
     */
    @GetMapping("/add")
    public String addForm(Model model){
        model.addAttribute("form", new ItemCreateRequest());
        return "item/addForm";
    }

    /***
     * 상품을 생성하고, 상품 상세 페이지로 PRG 된다.
     * @param form ItemCreateRequest dto
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/add")
    public String save(@Valid @ModelAttribute("form") ItemCreateRequest form,
                       BindingResult bindingResult, RedirectAttributes redirectAttributes){
        log.info("상품명: " + form.getItemName());

        // 1. 검증 실패 시
        if (bindingResult.hasErrors()){
            log.info("error = {}", bindingResult);
            return "item/addForm";
        }

        // 2. 성공 로직
        Long itemId = itemService.createItem(form);
        redirectAttributes.addAttribute("itemId", itemId);
        redirectAttributes.addAttribute("status", true);
        return "redirect:/api/items/{itemId}";
    }

    /***
     * item 고유 ID의 상세 페이지를 받아온다.
     * @param itemId item 고유 ID
     * @param model
     * @return
     */
    @GetMapping("/{itemId}")
    public String item(@PathVariable("itemId") Long itemId, Model model){
        Item item = itemService.getItem(itemId);
        model.addAttribute("item", item);
        return "item/item";
    }

    /***
     * 상품 수정 폼으로 이동한다.
     * @param itemId
     * @param model
     * @return
     */
    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model){
        log.info("itemId: " + itemId);
        Item item = itemService.getItem(itemId);

        ItemUpdateRequest form = new ItemUpdateRequest(item.getItemName(), item.getPrice(), item.getQuantity());
        model.addAttribute("itemId", itemId);
        model.addAttribute("form", form);
        return "item/editForm";
    }

    /***
     * 상품 수정을 완료하고 에러가 없다면 수정된 아이템의 상세페이지로 이동한다.
     * @param form
     * @param bindingResult
     * @param itemId
     * @return
     */
    @PostMapping("/{itemId}/edit")
    public String edit(@Valid @ModelAttribute("form") ItemUpdateRequest form,
                       BindingResult bindingResult, @PathVariable Long itemId){
        log.info("itemId: " + itemId);
        if (bindingResult.hasErrors()){
            return "item/editForm";
        }

        itemService.updateItem(itemId, form);
        return "redirect:/api/items/{itemId}";
    }
}
