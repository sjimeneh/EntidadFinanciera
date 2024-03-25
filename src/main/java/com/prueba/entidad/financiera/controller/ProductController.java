package com.prueba.entidad.financiera.controller;

import com.prueba.entidad.financiera.entity.Product;
import com.prueba.entidad.financiera.entity.enums.StatusProduct;
import com.prueba.entidad.financiera.exception.CustomException;
import com.prueba.entidad.financiera.service.IProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
    @Autowired
    IProductService _iProductService;
    @PostMapping("/create")
    public ResponseEntity<?> CreateProduct(@Valid @RequestBody Product product){
        try {
            product = _iProductService.Save(product);
            return new ResponseEntity<>(product, HttpStatus.CREATED);
        } catch (CustomException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Product> FindProductByID(@PathVariable Long id){

        Product ResponseProduct =  _iProductService.GetById(id);

        if(ResponseProduct == null){
            return new ResponseEntity<Product>(ResponseProduct, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Product>(ResponseProduct, HttpStatus.OK);
    }

    @GetMapping("/find/number/{productNumber}")
    public ResponseEntity<Product> FindProductByProductNumber(@PathVariable String productNumber){

        Product ResponseProduct =  _iProductService.GetByProductNumber(productNumber);

        if(ResponseProduct == null){
            return new ResponseEntity<Product>(ResponseProduct, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Product>(ResponseProduct, HttpStatus.OK);
    }

    @GetMapping("/find/all")
    public List<Product> FindAllProducts(){
        return _iProductService.GetAll();
    }



    @PutMapping("/update/balance")
    public ResponseEntity<?> UpdateBalanceProduct(@RequestParam("id") Long id,
                                                 @RequestParam("balance") BigDecimal balance) {
        try{
            if (!_iProductService.ExistById(id)) {
                return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
            }

            Product responseProduct = _iProductService.UpdateBalance(id, balance);

            return new ResponseEntity<Product>(responseProduct, HttpStatus.OK);
        }catch (CustomException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("/update/status")
    public ResponseEntity<?> UpdateBalanceProduct(@RequestParam("id") Long id,
                                                  @RequestParam("status") StatusProduct status) {
        try{
            if (!_iProductService.ExistById(id)) {
                return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
            }

            Product responseProduct = _iProductService.UpdateStatus(id, status);

            return new ResponseEntity<Product>(responseProduct, HttpStatus.OK);
        }catch (CustomException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> DeleteProduct(@PathVariable Long id) {
        Product product = _iProductService.GetById(id);

        if(product==null){
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }

        if (_iProductService.Delete(id)) {
            return ResponseEntity.ok("Producto eliminado con éxito");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("No se puede eliminar el Producto: Tiene Transacciones asociadas");
        }
    }
}
