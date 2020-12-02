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

    public List<SupplierGetDto> getAllSuppliers() {
        return fromEntity(supplierRepository.findAll());
    }

    public List<SupplierGetDto> findByName(String name) {
        List<Supplier> suppliers = supplierRepository.findByNameLike("%" + name.toLowerCase() +"%");

        return fromEntity(suppliers);
    }

    public SupplierGetDto postSupplier(SupplierPostDto supplierPostDto) {
        Supplier supplier = supplierMapper.toEntity(supplierPostDto);
        Supplier savedSupplier = supplierRepository.save(supplier);

        return supplierMapper.fromEntity(savedSupplier);
    }

    public SupplierGetDto modify(Long supplierId, SupplierPutDto supplierPutDto) {
        Supplier customer = supplierRepository.findById(supplierId).orElse(null);
        if (supplierId == null) {
            throw new RuntimeException("This supplier is not exist.");
        }
        supplierMapper.copy(supplierPutDto, customer);

        customer.setId(supplierId);

        return supplierMapper.fromEntity(supplierRepository.save(customer));
    }

    public void delete(Long supplierId) {
        Supplier supplier = supplierRepository.findById(supplierId).orElse(null);

        //if (supplier.getPurchaseOrders() == null || supplier.getPurchaseOrders().isEmpty()) {
        //    supplierRepository.deleteById(supplierId);
        //} else {
        //    throw new RuntimeException("Can not delete supplier with related items.");
        //}
    }
}