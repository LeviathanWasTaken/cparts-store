package com.leviathan.catalogue.repo;

import com.leviathan.catalogue.entity.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends CrudRepository<Product, Integer> {

    //@Query(value = "select * from catalogue.t_product where c_title ilike :filter", nativeQuery = true)
    @Query(name = "Product.findAllByTitleLikeIgnoringCase")
    Iterable<Product> findAllByTitleLikeIgnoreCase(@Param("filter") String filter);
}
