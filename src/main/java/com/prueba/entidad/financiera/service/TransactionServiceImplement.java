package com.prueba.entidad.financiera.service;

import com.prueba.entidad.financiera.entity.Product;
import com.prueba.entidad.financiera.entity.Transaction;
import com.prueba.entidad.financiera.entity.enums.ProductType;
import com.prueba.entidad.financiera.entity.enums.StatusProduct;
import com.prueba.entidad.financiera.entity.enums.TransactionType;
import com.prueba.entidad.financiera.exception.CustomException;
import com.prueba.entidad.financiera.repository.ITransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImplement implements ITransactionService{
    @Autowired
    ITransactionRepository _iTransactionRepository;
    @Autowired
    IProductService _iProductService;
    @Override
    public List<Transaction> GetAll() {
        List<Transaction> transactions = new ArrayList<>();
        _iTransactionRepository.findAll().forEach(transactions::add);
        return transactions;
    }

    @Override
    public Transaction GetById(Long id) {
        Optional<Transaction> transaction = _iTransactionRepository.findById(id);
        return transaction.orElse(null);
    }

    @Override
    public Transaction Save(Transaction transaction) {
        System.out.println(transaction.toString());
        ValidateTransaction(transaction);

        return _iTransactionRepository.save(transaction);
    }

    @Override
    public Transaction Update(Long id, Transaction transaction) {
        return null;
    }

    @Override
    public boolean Delete(Long id) {
        if(_iTransactionRepository.existsById(id)){
            Transaction transaction = GetById(id);
            switch (transaction.getTransactionType()){
                case TRANSFER -> {
                    AddValueToBalance(transaction.getOriginProduct(),transaction.getAmount());
                    DecreaseValueToBalance(transaction.getDestinationProduct(),transaction.getAmount());
                    UpdateProduct(transaction.getOriginProduct());
                    UpdateProduct(transaction.getDestinationProduct());
                }
                case DEPOSIT -> {
                    DecreaseValueToBalance(transaction.getDestinationProduct(),transaction.getAmount());
                    UpdateProduct(transaction.getDestinationProduct());
                }
                case WITHDRAWAL -> {
                    AddValueToBalance(transaction.getOriginProduct(),transaction.getAmount());
                    UpdateProduct(transaction.getOriginProduct());
                }
            }

            _iTransactionRepository.deleteById(id);
            return true;
        }else{
            throw new CustomException("La transacción con ID = "+id+" No existe");
        }
    }

    @Override
    public boolean ExistById(Long id) {
        return false;
    }

    private void ValidateTransaction(Transaction transaction){


        if(ValidateTypeTransaction(transaction.getTransactionType())){
            switch (transaction.getTransactionType()){

                case TRANSFER -> {
                    ValidateTransfer(transaction);

                    DecreaseValueToBalance(transaction.getOriginProduct(),transaction.getAmount());
                    UpdateProduct(transaction.getOriginProduct());

                    AddValueToBalance(transaction.getDestinationProduct(),transaction.getAmount());
                    UpdateProduct(transaction.getDestinationProduct());
                }
                case DEPOSIT -> {
                    ValidateDeposit(transaction);

                    AddValueToBalance(transaction.getDestinationProduct(),transaction.getAmount());
                    UpdateProduct(transaction.getDestinationProduct());
                }
                case WITHDRAWAL -> {
                    ValidateWithdrawal(transaction);

                    DecreaseValueToBalance(transaction.getOriginProduct(),transaction.getAmount());
                    UpdateProduct(transaction.getOriginProduct());
                }
            }
        }
    }

    private boolean ValidateTypeTransaction(TransactionType type){
        if (type == null) {
            throw new CustomException("El tipo de producto no puede ser nulo");
        }
        if (!Arrays.asList(TransactionType.values()).contains(type)) {
            throw new CustomException("El tipo de producto no es válido");
        }
        return true;
    }
    private void ValidateTransfer(Transaction transaction){

        transaction.setOriginProduct(ValidateProduct(transaction.getOriginProduct(),"Origen"));
        transaction.setDestinationProduct(ValidateProduct(transaction.getDestinationProduct(),"Destino"));

        ValidateAmount(transaction.getAmount());
    }

    private void ValidateStateProduct(Product product,String reference){
        if(product.getStatus().equals(StatusProduct.CANCELED)){
            throw new CustomException("La cuenta "+reference+" No puede estar CANCELADA");
        }
    }

    private void ValidateDeposit(Transaction transaction){
        ValidateAmount(transaction.getAmount());
        ValidateProductNotNecessary(transaction.getOriginProduct(),"Origen");
        transaction.setDestinationProduct(ValidateProduct(transaction.getDestinationProduct(),"Destino"));


    }

    private Product ValidateProduct(Product product,String reference){
        if(product == null){
            throw new CustomException("El Producto "+reference+" no puede ser nulo");
        }

        if(!ValidateExistProductById(product.getId())){
            throw new CustomException("El Producto "+reference+" NO Existe");
        }
        Product product1 = FindProductById(product.getId());
        ValidateStateProduct(product1,reference);
        return  product1;
    }


    private void ValidateAmount(BigDecimal amount){
        if(amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new CustomException("El Monto debe ser mayor a Cero");
        }
    }

    private boolean ValidateExistProductById(Long id){
        return _iProductService.ExistById(id);
    }

    private void UpdateProduct(Product product){
        _iProductService.UpdateBalance(product.getId(),product.getBalance());
    }


    private Product FindProductById(Long id){
        return _iProductService.GetById(id);
    }


    private void ValidateProductNotNecessary(Product product, String reference){
        if(product != null){
            throw new CustomException("El producto "+reference+" No es necesario en esta transacción");
        }
    }

    private void ValidateWithdrawal(Transaction transaction){
        ValidateAmount(transaction.getAmount());
        ValidateProductNotNecessary(transaction.getDestinationProduct(),"Destino");

        Product originProduct = ValidateProduct(transaction.getOriginProduct(),"Origen");
        transaction.setOriginProduct(originProduct);

        ValidateAmountToDecrease(transaction.getOriginProduct().getBalance(),transaction.getAmount(),transaction.getOriginProduct().getProductType());
    }

    private void AddValueToBalance(Product product, BigDecimal amountToAdd){
        product.setBalance(product.getBalance().add(amountToAdd));
    }

    private void DecreaseValueToBalance(Product product, BigDecimal amountToDecrease){
        ValidateAmountToDecrease(product.getBalance(),amountToDecrease,product.getProductType());
        product.setBalance(product.getBalance().subtract(amountToDecrease));
    }

    private void ValidateAmountToDecrease(BigDecimal currentAmount, BigDecimal decreseAmount, ProductType productType){
        if(productType.equals(ProductType.SAVINGS_ACCOUNT)){
            BigDecimal newAmount = currentAmount.subtract(decreseAmount);
            if(newAmount.compareTo(BigDecimal.ZERO) < 0){
                throw new CustomException("Saldo Insuciente en la cuenta de Ahorros");
            }
        }
    }
}
