package catalog.service.impl;

import catalog.service.ProductService;
import catalog.domain.Product;
import catalog.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;


/**
 * Service Implementation for managing Product.
 */
@Service
@Transactional
@CacheConfig(cacheNames = "products")
public class ProductServiceImpl implements ProductService{

    private final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Save a product.
     *
     * @param product the entity to save
     * @return the persisted entity
     */
    @Override
    @CachePut
    public Product save(Product product) {
        log.debug("Request to save Product : {}", product);
        return productRepository.save(product);
    }

    /**
     *  Get all the products.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    @Cacheable
    public Page<Product> findAll(Pageable pageable) {
        log.debug("Request to get all Products");
        return productRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable
    public Page<Product> findAllByCategory(Pageable pageable, Long idCat) {
        log.debug("Request to get all Products");
        return productRepository.findAllByCategoryId(pageable, idCat);
    }

    /**
     *  Get one product by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    @Cacheable("product")
    public Product findOne(Long id) {
        log.debug("Request to get Product : {}", id);
        return productRepository.findOne(id);
    }

    /**
     *  Delete the  product by id.
     *
     *  @param id the id of the entity
     */
    @Override
    @CacheEvict
    public void delete(Long id) {
        log.debug("Request to delete Product : {}", id);
        productRepository.delete(id);
    }

    @CacheEvict(allEntries = true)
    public void freeCache() {}

    /**
     * Fill redis cache with products
     */
    @PostConstruct
    void init() {
        productRepository.findAll().forEach(product -> findOne(product.getId()));
    }

    @PreDestroy
    void preDestroy() {
        freeCache();
    }
}
