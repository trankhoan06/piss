package com.app.pis.service;

import com.app.pis.dto.request.SupplierRequest;
import com.app.pis.dto.response.SupplierResponse;
import com.app.pis.entity.Supplier;
import com.app.pis.ex.BadRequestException;
import com.app.pis.mapper.SupplierMapper;
import com.app.pis.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.List;

@Service
public class SupplierService {
    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private SupplierMapper supplierMapper;

    @Transactional
    public List<SupplierResponse> getAllSupplier () {
        return supplierRepository.getAll()
                .map(supplierMapper::toResponse).toList();
    }

    @Transactional
    public SupplierResponse createSupplier (SupplierRequest request) {
        Supplier supplier = supplierRepository.save(supplierMapper.toEntity(request));
        return supplierMapper.toResponse(supplier);
    }

    @Transactional
    public SupplierResponse updateSupplier (Integer id, SupplierRequest request)  {
        Supplier supplier = supplierRepository.findById(id).orElseThrow(() -> new BadRequestException("Supplier not found"));
        // use reflection
        try {
           Class<? extends SupplierRequest> clazz = request.getClass();
           Class<? extends Supplier> aClass = supplier.getClass();
           Field[] fields = clazz.getDeclaredFields();
           for (var field : fields) {
               field.setAccessible(true);
               Object o = field.get(request);
               if (o != null) {
                   String fieldName = field.getName();
                   Field declaredField = aClass.getDeclaredField(fieldName);
                   declaredField.setAccessible(true);
                   declaredField.set(supplier, o);
               }
           }
        } catch (Exception e) {
            throw new BadRequestException("Error");
        }
        Supplier updatedSupplier = supplierRepository.save(supplier);
        return supplierMapper.toResponse(updatedSupplier);
    }

    @Transactional
    public void deleteSupplier (Integer id) {
        Supplier supplier = supplierRepository.findById(id).orElseThrow((() -> new BadRequestException("Supplier not found")));
        try {
            supplierRepository.delete(supplier);
        } catch (Exception e) {
            throw new BadRequestException("Cannot delete Supplier");
        }

    }



}
