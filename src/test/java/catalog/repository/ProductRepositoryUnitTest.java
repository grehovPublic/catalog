package catalog.repository;

import catalog.CatalogApp;
import catalog.domain.Category;
import catalog.domain.Product;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {CatalogApp.class})
@Transactional
public class ProductRepositoryUnitTest {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Test
    public void count() {
        assertEquals(6, productRepository.count());
    }

    @Test
    public void findAll() {
        List<Product> products = productRepository.findAll();
        assertEquals(6, products.size());
        assertProduct(0, products.get(0));
        assertProduct(1, products.get(1));
        assertProduct(2, products.get(2));
        assertProduct(3, products.get(3));
    }

    @Test
    public void findAllByCategoryId() throws Exception {
        List<Product> products = productRepository
            .findAllByCategoryId(new PageRequest(0, 100), CATEGORY_PHONES.getId())
            .getContent();
        assertEquals(2, products.size());
        assertProduct(4, products.get(0));
        assertProduct(5, products.get(1));
    }

    @Test
    public void findOne() {
        assertProduct(0, productRepository.findOne(PRODUCTS[0].getId()));
        assertProduct(1, productRepository.findOne(PRODUCTS[1].getId()));
        assertProduct(2, productRepository.findOne(PRODUCTS[2].getId()));
    }

    @Test
    public void save_newProduct() {
        assertEquals(6, productRepository.count());
        Product product = new Product(PRODUCTS[0]);
        product.setId(product.getId() + 1000);
        product.setName("notebook6");
        Product saved = productRepository.save(product);
        assertEquals(7, productRepository.count());
        assertEquals(product.getName(), saved.getName());
    }

    @Test
    public void save_existingProduct() {
        assertEquals(6, productRepository.count());
        Product saved = productRepository.save(PRODUCTS[5]);
        assertEquals(6, productRepository.count());
        assertEquals(PRODUCTS[5], saved);
    }

    @Test
    public void delete_product() {
        assertEquals(6, productRepository.count());
        assertEquals(true, productRepository.exists(PRODUCTS[5].getId()));
        productRepository.delete(PRODUCTS[5]);
        assertEquals(5, productRepository.count());
        assertEquals(false, productRepository.exists(PRODUCTS[5].getId()));
    }

    @Test
    public void delete_productsCascadeByCategory() {
        assertEquals(6, productRepository.count());
        assertEquals(true, productRepository.exists(PRODUCTS[4].getId()));
        assertEquals(true, productRepository.exists(PRODUCTS[5].getId()));

        categoryRepository.delete(CATEGORY_PHONES);
        assertEquals(4, productRepository.count());
        assertEquals(false, productRepository.exists(PRODUCTS[4].getId()));
        assertEquals(false, productRepository.exists(PRODUCTS[5].getId()));
    }

    private static void assertProduct(int expectedJitterIndex, Product actual) {
        Product expected = PRODUCTS[expectedJitterIndex];
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getPrice(), actual.getPrice());
        assertEquals(expected.getImagePath(), actual.getImagePath());
        assertEquals(expected.getUpdated(), actual.getUpdated());
        assertEquals(expected.getCategory(), actual.getCategory());
    }

    private static Product[] PRODUCTS = new Product[6];
    private static Category CATEGORY_NOTEBOOKS = new Category();
    private static Category CATEGORY_PHONES = new Category();

    @BeforeClass
    public static void init() {
        CATEGORY_NOTEBOOKS.setName("notebooks");
        CATEGORY_NOTEBOOKS.setUpdated(ZonedDateTime.now());
        CATEGORY_PHONES.setName("phones");
        CATEGORY_PHONES.setUpdated(ZonedDateTime.now());

        Product prod = new Product(null, "notebook0", "green", new BigDecimal("1000.00"),
            ZonedDateTime.now(), "", null);
        PRODUCTS[0] = new Product(prod);
        prod.setName("notebook1");
        PRODUCTS[1] = new Product(prod);
        prod.setName("notebook2");
        PRODUCTS[2] = new Product(prod);
        prod.setName("notebook3");
        PRODUCTS[3] = new Product(prod);
        prod.setName("notebook4");
        PRODUCTS[4] = new Product(prod);
        prod.setName("notebook5");
        PRODUCTS[5] = new Product(prod);
    }

    @Before
    public void before() {
        CATEGORY_NOTEBOOKS = categoryRepository.save(CATEGORY_NOTEBOOKS);
        CATEGORY_PHONES = categoryRepository.save(CATEGORY_PHONES);

        PRODUCTS[0].setCategory(CATEGORY_NOTEBOOKS);
        PRODUCTS[1].setCategory(CATEGORY_NOTEBOOKS);
        PRODUCTS[2].setCategory(CATEGORY_NOTEBOOKS);
        PRODUCTS[3].setCategory(CATEGORY_NOTEBOOKS);
        PRODUCTS[4].setCategory(CATEGORY_PHONES);
        PRODUCTS[5].setCategory(CATEGORY_PHONES);

        for (int i = 0; i < PRODUCTS.length; i++) {
            PRODUCTS[i] = productRepository.save(PRODUCTS[i]);
        }
    }

}
