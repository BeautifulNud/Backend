package ggudock.domain.item.api;

import ggudock.domain.item.application.ItemService;
import ggudock.domain.item.entity.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/item")
@RequiredArgsConstructor
public class ItemController {
    private ItemService itemService;
    // 상품 상세보기
    @GetMapping("/{item_id}")
    public ResponseEntity<Item> getDetail(@PathVariable("item_id") String itemId) {
        return new ResponseEntity<>(itemService.getDetail(itemId), HttpStatusCode.valueOf(200));
    }
    // 상품 전체 리스트 가져오기
    @GetMapping("/list")
    public ResponseEntity<List<Item>> getList() {
        return new ResponseEntity<>(itemService.getList(), HttpStatusCode.valueOf(200));
    }
}
