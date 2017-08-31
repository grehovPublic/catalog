package catalog.service.impl;

import catalog.service.CategoryService;
import catalog.domain.Category;
import catalog.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.cdi.Eager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;


/**
 * Service Implementation for managing Category.
 */
@Service
@Transactional
@CacheConfig(cacheNames = "categories")
public class CategoryServiceImpl implements CategoryService{

    private final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);

    private final CategoryRepository categoryRepository;
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Save a category.
     *
     * @param category the entity to save
     * @return the persisted entity
     */
    @Override
    @CachePut
    public Category save(Category category) {
        log.debug("Request to save Category : {}", category);
        return categoryRepository.save(category);
    }

    /**
     *  Get all the categories.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    @Cacheable
    public Page<Category> findAll(Pageable pageable) {
        log.debug("Request to get all Categories");
        return categoryRepository.findAll(pageable);
    }

    /**
     *  Get all the categories.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    @Cacheable
    public List<Category> findAll(){
        log.debug("Request to get all Categories");
        return categoryRepository.findAll();
    }

    /**
     *  Get one category by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    @Cacheable
    public Category findOne(Long id) {
        log.debug("Request to get Category : {}", id);
        return categoryRepository.findOne(id);
    }

    /**
     *  Delete the  category by id.
     *
     *  @param id the id of the entity
     */
    @Override
    @CacheEvict
    public void delete(Long id) {
        log.debug("Request to delete Category : {}", id);
        categoryRepository.delete(id);
    }


    @CacheEvict(allEntries = true)
    public void freeCache() {}

    /**
     * Fill redis cache with products
     */
    @PostConstruct
    void init() {
        categoryRepository.findAll().forEach(category -> findOne(category.getId()));
    }

    @PreDestroy
    void preDestroy() {
        freeCache();
    }
}
