package com.hero.services;

import com.hero.dtos.supplier.SupplierGetDto;
import com.hero.dtos.supplier.SupplierPostDto;
import com.hero.dtos.supplier.SupplierPutDto;
import com.hero.entities.Supplier;
import com.hero.mappers.SupplierMapper;
import com.hero.repositories.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SupplierService {

    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;

    private List<SupplierGetDto> fromEntity(List<Supplier> suppliers) {
        return suppliers.stream()
                .map(supplier -> supplierMapper.fromEntity(supplier))
                .collect(Collectors.toList());
    }

    public List<SupplierGetDto> getAll() {
        return fromEntity(supplierRepository.findAll());
    }

    public List<SupplierGetDto> findByName(String name) {
        List<Supplier> suppliers = supplierRepository.findBySupplierNameLike("%" + name.toLowerCase() +"%");

        return fromEntity(suppliers);
    }

    public SupplierGetDto getOne(Long id) {
        Supplier supplier = supplierRepository.findById(id).orElse(null);
        if (supplier == null) { throw new RuntimeException("Supplier id=" + id + " does not exist."); }
        return supplierMapper.fromEntity(supplier);
    }

    public SupplierGetDto addOne(SupplierPostDto supplierPostDto) {
        Supplier supplier = supplierMapper.toEntity(supplierPostDto);
        Supplier savedSupplier = supplierRepository.save(supplier);

        return supplierMapper.fromEntity(savedSupplier);
    }

    public SupplierGetDto update(Long id, SupplierPutDto supplierPutDto) {
        Supplier supplier = supplierRepository.findById(id).orElse(null);
        if (id == null) {
            throw new RuntimeException("This supplier does not exist");
        }
        supplierMapper.copy(supplierPutDto, supplier);

        supplier.setId(id);

        return supplierMapper.fromEntity(supplierRepository.save(supplier));
    }

    public void delete(Long id) {
        Supplier supplier = supplierRepository.findById(id).orElse(null);

        if (supplier.getPurchaseOrders() == null || supplier.getPurchaseOrders().isEmpty()) {
            supplierRepository.deleteById(id);
        } else {
            throw new RuntimeException("Can not delete supplier with related order");
        }
    }
}