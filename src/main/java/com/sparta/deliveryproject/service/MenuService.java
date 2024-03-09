package com.sparta.deliveryproject.service;


import com.sparta.deliveryproject.requestDto.MenuRequestDto;
import com.sparta.deliveryproject.responseDto.MenuResponseDto;
import com.sparta.deliveryproject.entity.Menu;
import com.sparta.deliveryproject.entity.Store;
import com.sparta.deliveryproject.entity.User;
import com.sparta.deliveryproject.exception.DuplicatedMenuException;
import com.sparta.deliveryproject.repository.MenuRepository;
import com.sparta.deliveryproject.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;

    @Transactional(readOnly = true)
    public List<MenuResponseDto> getMenuListByStore(Long storeId) {
        Store store = storeRepository.findById(storeId).orElseThrow(
                () -> new NullPointerException("해당 id의 매장이 없습니다.")
        );
        List<Menu> menuList = menuRepository.findAllByStore(store);

        if (menuList.isEmpty()) {
            throw new NullPointerException("해당 매장에 메뉴가 없습니다.");
        }

        return menuList.stream().map(MenuResponseDto::new).toList();
    }

    @Transactional(readOnly = true)
    public List<MenuResponseDto> getTopThreeSalesMenuListByStore(Long storeId) {
        Store store = storeRepository.findById(storeId).orElseThrow(
                () -> new NullPointerException("해당 id의 매장이 없습니다.")
        );
        List<Menu> menuList = menuRepository.findAllByStoreOrderBySalesCountDesc(store);
        return substringList(menuList);
    }


    @Transactional(readOnly = true)
    public List<MenuResponseDto> getTopThreeCountsMenuListByStore(Long storeId) {
        Store store = storeRepository.findById(storeId).orElseThrow(
                () -> new NullPointerException("해당 id의 매장이 없습니다.")
        );
        List<Menu> menuList = menuRepository.findAllByStoreOrderByTotalSalesDesc(store);
        return substringList(menuList);
    }

    public void createMenu(Long storeId, MenuRequestDto menuRequestDto, User userDetails) throws DuplicatedMenuException {
        Store store = storeRepository.findById(storeId).orElseThrow(
                () -> new NullPointerException("해당 id의 매장이 없습니다.")
        );

        if (!store.getUser().getUserID().equals(userDetails.getUserID())) {
            throw new IllegalArgumentException("메뉴를 생성할 수 있는 권한이 없습니다.");
        }

        List<Menu> menuList = menuRepository.findAllByStore(store);

        for (Menu menu : menuList) {
            if (menu.getName().equals(menuRequestDto.getName())) {
                throw new DuplicatedMenuException("이미 매장에 동일한 메뉴가 존재합니다.");
            }
        }

        try {
            int priceInt = Integer.parseInt(menuRequestDto.getPrice());
            Menu menu = new Menu(store, menuRequestDto, priceInt);
            menuRepository.save(menu);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("가격은 정수만 입력해주세요");
        }
    }

    public void editMenu(Long menuId, MenuRequestDto menuRequestDto, User userDetails) throws DuplicatedMenuException {
        Menu menu = getMenuById(menuId);

        Store store = storeRepository.findById(menu.getStore().getId()).orElseThrow(
                () -> new NullPointerException("해당 id의 매장이 없습니다.")
        );

        if (!store.getUser().getUserID().equals(userDetails.getUserID())) {
            throw new IllegalArgumentException("메뉴를 생성할 수 있는 권한이 없습니다.");
        }

        List<Menu> menuList = menuRepository.findAllByStore(store);

        for (Menu m : menuList) {
            if (m.getName().equals(menuRequestDto.getName()) && !Objects.equals(m.getId(), menuId)) {
                throw new DuplicatedMenuException("이미 매장에 동일한 메뉴가 존재합니다.");
            }
        }

        try {
            int priceInt = Integer.parseInt(menuRequestDto.getPrice());
            menu.update(menuRequestDto, priceInt);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("가격은 정수만 입력해주세요");
        }
    }

    public void deleteMenu(Long menuId, User userDetails) {
        Menu menu = getMenuById(menuId);

        Store store = storeRepository.findById(menu.getStore().getId()).orElseThrow(
                () -> new NullPointerException("해당 id의 매장이 없습니다.")
        );

        if (!store.getUser().getUserID().equals(userDetails.getUserID())) {
            throw new IllegalArgumentException("메뉴를 생성할 수 있는 권한이 없습니다.");
        }
        menuRepository.delete(menu);
    }

    private List<MenuResponseDto> substringList(List<Menu> menuList) {
        if (menuList.isEmpty()) {
            throw new NullPointerException("해당 매장에 메뉴가 없습니다.");
        } else if (menuList.size() <= 3) {
            return menuList.stream().map(MenuResponseDto::new).toList();
        } else {
            List<MenuResponseDto> returnValue = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                returnValue.add(new MenuResponseDto(menuList.get(i)));
            }
            return returnValue;
        }
    }

    private Menu getMenuById(Long menuId) {
        return menuRepository.findById(menuId).orElseThrow(
                () -> new NullPointerException("해당 id의 메뉴가 없습니다.")
        );
    }
}
