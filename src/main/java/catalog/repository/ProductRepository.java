package catalog.repository;

import catalog.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Product entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     *  Get all the products from given category.
     *
     *  @param pageable the pagination information
     *  @param idCat the category id
     *  @return the list of entities
     */
    Page<Product> findAllByCategoryId(Pageable pageable, Long idCat);
}
