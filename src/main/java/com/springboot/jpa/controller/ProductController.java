package com.springboot.jpa.controller;

import com.springboot.jpa.dto.ChangeProductDto;
import com.springboot.jpa.dto.ProductDto;
import com.springboot.jpa.entity.ProductJpa;
import com.springboot.jpa.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductRepository productRepository;

    private final ProductJpa productJpa;

    @Autowired
    public ProductController(ProductRepository productRepository, ProductJpa productJpa) {
        this.productRepository = productRepository;
        this.productJpa = productJpa;
    }


    @GetMapping("/")
    public String home(){
        return "Welcome";
    }

    @GetMapping("/get/{id}")
    public Optional<ProductJpa> getProduct(@PathVariable long id){
//        ProductJpa a = productRepository.getById(id);
        Optional<ProductJpa> p = productRepository.findById(id); //옵셔널. NULL 일수도 있음
        return p;
    }

    @PostMapping("/post") //db 저장
    public ProductJpa saveProduct(@RequestBody ProductDto productDto){
        /**
         * db 연결해서 저장 => jpa
         * productRepository.save(엔티티 객체만 가능);
         * */

        log.info(productDto.getName());

        productJpa.setName(productDto.getName());
        productJpa.setPrice(productDto.getPrice());
        productJpa.setStock(productDto.getStock());
        /**
         * Json 으로 넘어온 값이 일반 vo (ProductDto) 안에 담기면 ->
         * jpa table 에 담기 위해서 get 해서 꺼낸다.
         * 그리고 jpa table 에 set 한다.
         * */
        productRepository.save(productJpa);
        return productJpa;
    }

    @DeleteMapping("/delete/{id}")
    public String deleteProduct(@PathVariable long id){
        productRepository.deleteById(id);
        return "삭제 성공";
    }


    @PutMapping("/put")
    public ProductJpa updateProduct(@RequestBody ChangeProductDto cp) throws Exception {
        //아이디로 특정 정보를 [찾아서] [이름을] 바꾼다
        long id = cp.getId();
        String name = cp.getName();
        //update 할 값을 업데이트 전용 vo (ChangeProductDto) 에서 가져옴
        
        Optional<ProductJpa> selectProduct = productRepository.findById(id);
        //아이디로 수정할 컬럼을 가져옴

        ProductJpa updateProduct = null;

        if(selectProduct.isPresent()){ //selectProduct.isPresent() ->selectProduct 조회한 결과가 있는지 여부를 묻는 메소드 isPresent
            ProductJpa product = selectProduct.get(); //있으면, selectProduct 에 저장이 되었을 것이니까, 거기서 get 해서 값을 가져옴
            product.setName(name); //가져온 값을 변경함 setName(name)
            updateProduct = productRepository.save(product); //위에서 초기화한 변수 updateProduct 에 저장 메소드와 변경할 값이 들어있는 product 넘김
        }else{
            throw new Exception();
        }
        return updateProduct;
    }
}
