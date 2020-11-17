package com.hero.services;

import com.hero.dtos.brand.BrandGetDto;
import com.hero.dtos.brand.BrandPostDto;
import com.hero.dtos.brand.BrandPutDto;
import com.hero.entities.Brand;
import com.hero.entities.Item;
import com.hero.mappers.BrandMapper;
import com.hero.repositories.BrandRepository;
import com.hero.repositories.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BrandService {

    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;
    private final ItemRepository itemRepository;

    private List<BrandGetDto> fromEntity(List<Brand> brands) {
        return brands.stream()
                .map(brand -> brandMapper.fromEntity(brand))
                .collect(Collectors.toList());
    }

    public List<BrandGetDto> getAllBrands() {
        return fromEntity(brandRepository.findAll());
    }

    public BrandGetDto addBrand(BrandPostDto brandPostDto) {
        Brand brand = brandMapper.toEntity(brandPostDto);
        Brand savedBrand = brandRepository.save(brand);
        return brandMapper.fromEntity(savedBrand);
    }

    public BrandGetDto modify(Long brandId, BrandPutDto brandPutDto) {
        Brand brand = new Brand();
        brandMapper.copy(brandPutDto, brand);
        brand.setId(brandId);
        return brandMapper.fromEntity(brandRepository.save(brand));
    }

    public void delete(Long brandId) {
        Brand brand = brandRepository.findById(brandId).orElse(null);
        List<Item> itemsList = itemRepository.findByBrand(brand);
        if (itemsList.isEmpty()) {
            brandRepository.deleteById(brandId);
        } else {
            throw new RuntimeException("can not delete.");
        }
    }

}
