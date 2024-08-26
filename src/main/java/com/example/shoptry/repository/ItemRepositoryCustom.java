package com.example.shoptry.repository;

import com.example.shoptry.dto.ItemSearchDto;
import com.example.shoptry.dto.MainItemDto;
import com.example.shoptry.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ItemRepositoryCustom {

        Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);

        Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto,Pageable pageable);
}
