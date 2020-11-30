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

    private List<ManufacturerGetDto> fromEntity(List<Manufacturer> manufacturers) {
        return manufacturers.stream()
                .map(manufacturer -> manufacturerMapper.fromEntity(manufacturer))
                .collect(Collectors.toList());
    }

    public List<ManufacturerGetDto> getAllManufacturers() {
        return fromEntity(manufacturerRepository.findAll());
    }

    public List<ManufacturerGetDto> findByName(String name) {
        List<Manufacturer> manufacturers = manufacturerRepository.findByNameLike("%" + name.toLowerCase() +"%");

        return fromEntity(manufacturers);
    }

    public ManufacturerGetDto postManufacturer(ManufacturerPostDto manufacturerPostDto) {
        Manufacturer manufacturer = manufacturerMapper.toEntity(manufacturerPostDto);

        try {
            Manufacturer savedManufacturer = manufacturerRepository.save(manufacturer);
            return manufacturerMapper.fromEntity(savedManufacturer);
        } catch (Exception e) {
            throw new RuntimeException("Manufacturer '" + manufacturerPostDto.getName() + "' already exists.");
        }
    }

    public ManufacturerGetDto modifyManufacturer(Long manufacturerId, ManufacturerPutDto manufacturerPutDto) {
       Manufacturer manufacturer = new Manufacturer();

       manufacturerMapper.copy(manufacturerPutDto, manufacturer);
       manufacturer.setId(manufacturerId);

       return manufacturerMapper.fromEntity(manufacturerRepository.save(manufacturer));
    }

    public void deleteManufacturer(Long manufacturerId) {
        Manufacturer manufacturer = manufacturerRepository.findById(manufacturerId).orElse(null);

        if (manufacturer.getItems() == null || manufacturer.getItems().isEmpty()) {
            manufacturerRepository.deleteById(manufacturerId);
        } else {
            throw new RuntimeException("Can not delete manufacturer with related items.");
        }
    }

}
