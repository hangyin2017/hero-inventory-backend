package com.hero.services;

import com.hero.dtos.brand.BrandGetDto;
import com.hero.dtos.item.ItemGetDto;
import com.hero.entities.Brand;
import com.hero.entities.Item;
import com.hero.mappers.BrandMapper;
import com.hero.repositories.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BrandService {

    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;

    private List<BrandGetDto> fromEntity(List<Brand> brands) {
        return brands.stream()
                .map(brand -> brandMapper.fromEntity(brand))
                .collect(Collectors.toList());
    }
    public List<BrandGetDto> getAllBrands() {
        return fromEntity(brandRepository.findAll());
    }

}
