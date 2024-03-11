package com.sparta.deliveryproject.serviceImpl;

import com.sparta.deliveryproject.requestDto.StoreRequestDto;
import com.sparta.deliveryproject.responseDto.StoreResponseDto;
import com.sparta.deliveryproject.entity.Category;
import com.sparta.deliveryproject.entity.Store;
import com.sparta.deliveryproject.entity.User;
import com.sparta.deliveryproject.repository.CategoryRepository;
import com.sparta.deliveryproject.repository.MenuRepository;
import com.sparta.deliveryproject.repository.StoreRepository;
import com.sparta.deliveryproject.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public List<StoreResponseDto> getStoreListByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new NullPointerException("해당하는 카테고리가 없습니다.")
        );

        List<Store> storeList = storeRepository.findAllByCategory(category);

        if (storeList.isEmpty()) {
            throw new IllegalArgumentException(category + "에 속하는 식당이 없습니다.");
        }

        return storeList.stream()
                .map(StoreResponseDto::new)
                .toList();
    }

    @Override
    public List<StoreResponseDto> getTopCountStoreList(User user) {
        List<Store> storeList = storeRepository.findAllByUserOrderByTotalSalesDesc(user);
        return substringList(storeList);
    }

    @Override
    public List<StoreResponseDto> getTopSalesStoreList(User user) {
        List<Store> storeList = storeRepository.findAllByUserOrderByOrderCountDesc(user);
        return substringList(storeList);
    }

    @Override
    @Transactional
    public void createStore(StoreRequestDto storeRequestDto, User user) {
        Category category = categoryRepository.findByName(storeRequestDto.getCategory()).orElseThrow(
                () -> new NullPointerException("해당하는 카테고리가 없습니다.")
        );

        Store store = new Store(storeRequestDto, category, user);
        storeRepository.save(store);
    }

    @Override
    @Transactional
    public void editStore(Long id, StoreRequestDto storeRequestDto, User userDetails) {
        Category category = categoryRepository.findByName(storeRequestDto.getCategory()).orElseThrow(
                () -> new NullPointerException("해당하는 카테고리가 없습니다.")
        );

        Store store = storeRepository.findById(id).orElseThrow(
                () -> new NullPointerException("해당 id의 store가 존재하지 않습니다.")
        );

        if (!store.getUser().getUserID().equals(userDetails.getUserID())) {
            throw new IllegalArgumentException("매장을 수정할 수 있는 권한이 없습니다.");
        }

        store.edit(storeRequestDto, category);
    }

    @Override
    @Transactional
    public void deleteStore(Long id, User userDetails) {
        Store store = storeRepository.findById(id).orElseThrow(
                () -> new NullPointerException("해당 id의 store가 존재하지 않습니다.")
        );

        if (!store.getUser().getUserID().equals(userDetails.getUserID())) {
            throw new IllegalArgumentException("매장을 삭제할 수 있는 권한이 없습니다.");
        }

        menuRepository.deleteAllByStore(store);
        storeRepository.delete(store);
    }

    private List<StoreResponseDto> substringList(List<Store> storeList) {
        if (storeList.isEmpty()) {
            throw new NullPointerException("해당 매장에 메뉴가 없습니다.");
        } else if (storeList.size() <= 3) {
            return storeList.stream().map(StoreResponseDto::new).toList();
        } else {
            List<StoreResponseDto> returnValue = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                returnValue.add(new StoreResponseDto(storeList.get(i)));
            }
            return returnValue;
        }
    }
}