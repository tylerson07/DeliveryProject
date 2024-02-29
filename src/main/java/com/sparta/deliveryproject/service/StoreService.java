package com.sparta.deliveryproject.service;

import com.sparta.deliveryproject.dto.StoreRequestDto;
import com.sparta.deliveryproject.dto.StoreResponseDto;
import com.sparta.deliveryproject.entity.Category;
import com.sparta.deliveryproject.entity.Store;
import com.sparta.deliveryproject.entity.User;
import com.sparta.deliveryproject.repository.CategoryRepository;
import com.sparta.deliveryproject.repository.MenuRepository;
import com.sparta.deliveryproject.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<StoreResponseDto> getStoreListByCategory(String categoryName) {
        Category category = categoryRepository.findByName(categoryName).orElseThrow(
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

    @Transactional(readOnly = true)
    public List<StoreResponseDto> getTopCountStoreList(User user) {
        List<Store> storeList = storeRepository.findAllByUserOrderByTotalSalesDesc(user);
        if (storeList.isEmpty()) {
            throw new IllegalArgumentException("아직 매장이 없습니다..");
        } else if (storeList.size() <= 3) {
            return storeList.stream()
                    .map(StoreResponseDto::new)
                    .toList();
        } else {
            List<StoreResponseDto> returnValue = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                returnValue.add(new StoreResponseDto(storeList.get(i)));
            }
            return returnValue;
        }
    }

    @Transactional(readOnly = true)
    public List<StoreResponseDto> getTopSalesStoreList(User user) {
        List<Store> storeList = storeRepository.findAllByUserOrderByOrderCountDesc(user);
        if (storeList.isEmpty()) {
            throw new IllegalArgumentException("아직 매장이 없습니다.");
        } else if (storeList.size() <= 3) {
            return storeList.stream()
                    .map(StoreResponseDto::new)
                    .toList();
        } else {
            List<StoreResponseDto> returnValue = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                returnValue.add(new StoreResponseDto(storeList.get(i)));
            }
            return returnValue;
        }
    }

    public void createStore(StoreRequestDto storeRequestDto, User user) {
        Category category = categoryRepository.findByName(storeRequestDto.getCategory()).orElseThrow(
                () -> new NullPointerException("해당하는 카테고리가 없습니다.")
        );

        Store store = new Store(storeRequestDto, category, user);
        storeRepository.save(store);
    }

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
}
