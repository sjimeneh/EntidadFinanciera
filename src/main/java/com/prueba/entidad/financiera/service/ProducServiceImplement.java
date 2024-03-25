package com.prueba.entidad.financiera.service;

import com.prueba.entidad.financiera.entity.Product;
import com.prueba.entidad.financiera.entity.enums.ProductType;
import com.prueba.entidad.financiera.entity.enums.StatusProduct;
import com.prueba.entidad.financiera.exception.CustomException;
import com.prueba.entidad.financiera.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ProducServiceImplement implements IProductService {
    @Autowired
    IProductRepository _iProductRepository;
    @Override
    public List<Product> GetAll() {
        List<Product> products = new ArrayList<>();
        _iProductRepository.findAll().forEach(products::add);
        return products;
    }

    @Override
    public Product GetById(Long id) {
        Optional<Product> product = _iProductRepository.findById(id);
        return product.orElse(null);
    }

    @Override
    public Product Save(Product product) {
        if(product.getCustomer().getId() == null){
            throw new CustomException("El ID del cliente es obligatorio");
        }

        // Validar Tipo de cuenta
        ValidateProductType(product.getProductType());

        product.setProductNumber(GenerateAccountNumber(product.getProductType()));

        // Validar saldo de cuenta de ahorros
        if (product.getProductType() == ProductType.SAVINGS_ACCOUNT) {
            ValidateBalanceAccountSavings(product.getBalance());
        }

        // Activar cuenta de ahorros por defecto
        ActivateDefaultSavingsAccount(product);

        try{
            return _iProductRepository.save(product);
        }catch (DataIntegrityViolationException div){
            throw new CustomException("El Cliente No existe");
        }


    }


    @Override
    public Product UpdateBalance(Long id, BigDecimal newBalance) {
        Product product = GetById(id);
        if(product == null){
            throw new CustomException("No existe el producto con ID = "+id);
        }
        if(product.getProductType().equals(ProductType.SAVINGS_ACCOUNT) && newBalance.compareTo(BigDecimal.ZERO) < 0){
            throw new CustomException("Las cuentas de ahorros no puede tener un saldo menor a $0 (cero).");
        }
        product.setBalance(newBalance);

        return _iProductRepository.save(product);
    }

    @Override
    public Product UpdateStatus(Long id, StatusProduct status) {
        Product product = GetById(id);
        if(product == null){
            throw new CustomException("No existe el producto con ID = "+id);
        }
        if(status.equals(StatusProduct.CANCELED) && product.getBalance().compareTo(BigDecimal.ZERO) != 0){
            throw new CustomException("Solo se puede cancelar una cuentas que tengan un saldo igual a $0.");
        }

        product.setStatus(status);

        return _iProductRepository.save(product);
    }

    @Override
    public boolean Delete(Long id) {
        if(ExistById(id)){
            _iProductRepository.deleteById(id);
            return true;
        }

        return false;
    }

    @Override
    public boolean ExistById(Long id) {
        return _iProductRepository.existsById(id);
    }

    public Product GetProductByProductNumber(String productNumber) {
        return _iProductRepository.findByProductNumber(productNumber)
                .orElse(null);
    }


    private String GenerateAccountNumber(ProductType type) {
        String prefix = switch (type) {
            case CURRENT_ACCOUNT -> "33";
            case SAVINGS_ACCOUNT -> "53";
            default -> throw new CustomException("Tipo de producto no válido");
        };
        int randomNumber = (int) (Math.random() * (99999999 - 10000000 + 1)) + 10000000;
        return prefix + randomNumber;
    }

    private void ValidateProductType(ProductType type) {
        if (type == null) {
            throw new CustomException("El tipo de producto no puede ser nulo");
        }
        if (!Arrays.asList(ProductType.values()).contains(type)) {
            throw new CustomException("El tipo de producto no es válido");
        }
    }

    private void ValidateBalanceAccountSavings(BigDecimal balance) {
        if (balance == null) {
            throw new CustomException("El saldo no puede ser nulo");
        }
        if (balance.compareTo(BigDecimal.ZERO) < 0) {
            throw new CustomException("El saldo de la cuenta de ahorros no puede ser menor a $0");
        }
    }

    private void ActivateDefaultSavingsAccount(Product product) {
        if (product.getProductType() == ProductType.SAVINGS_ACCOUNT) {
            product.setStatus(StatusProduct.ACTIVE);
        }
    }

    private void ValidateBalanceToCancelAccount(Product product) {
        if (product.getBalance().compareTo(BigDecimal.ZERO) != 0) {
            throw new CustomException("No se puede cancelar una cuenta con saldo diferente a $0");
        }
    }


}
