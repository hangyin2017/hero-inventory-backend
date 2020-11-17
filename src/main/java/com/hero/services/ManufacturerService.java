package com.hero.services;

import com.hero.dtos.manufacturer.ManufacturerGetDto;
import com.hero.dtos.manufacturer.ManufacturerPostDto;
import com.hero.dtos.manufacturer.ManufacturerPutDto;
import com.hero.entities.Manufacturer;
import com.hero.mappers.ManufacturerMapper;
import com.hero.repositories.ManufacturerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ManufacturerService {

    private final ManufacturerRepository manufacturerRepository;
    private final ManufacturerMapper manufacturerMapper;

    private List<ManufacturerGetDto> manufacturersToManufacturerGetDtos(List<Manufacturer> manufacturers) {
        return manufacturers.stream()
                .map(manufacturer -> manufacturerMapper.manufacturerToManufacturerGetDto(manufacturer))
                .collect(Collectors.toList());
    }

    public List<ManufacturerGetDto> getAllManufacturers() {
        return manufacturersToManufacturerGetDtos(manufacturerRepository.findAll());
    }

    public List<ManufacturerGetDto> findByName(String name) {
        List<Manufacturer> manufacturers = manufacturerRepository.findByNameLike("%" + name.toLowerCase() +"%");
        return manufacturersToManufacturerGetDtos(manufacturers);
    }

    public ManufacturerGetDto postManufacturer(ManufacturerPostDto manufacturerPostDto) {
        Manufacturer manufacturer = manufacturerMapper.manufacturerPostDtoToManufacturer(manufacturerPostDto);
        Manufacturer savedManufacturer = manufacturerRepository.save(manufacturer);
        return manufacturerMapper.manufacturerToManufacturerGetDto(savedManufacturer);
    }

    public ManufacturerGetDto modifyManufacturer(Long manufacturerId, ManufacturerPutDto manufacturerPutDto) {
       Manufacturer manufacturer = new Manufacturer();
       manufacturerMapper.copy(manufacturerPutDto, manufacturer);
       manufacturer.setId(manufacturerId);
       return manufacturerMapper.manufacturerToManufacturerGetDto(manufacturerRepository.save(manufacturer));
    }

    public void deleteManufacturer(Long manufacturerId) {
        Manufacturer manufacturer = manufacturerRepository.findById(manufacturerId).orElse(null);
        if (manufacturer.getItems() == null || manufacturer.getItems().isEmpty()) {
            manufacturerRepository.deleteById(manufacturerId);
        }
    }

}
