package catalog.repository;

import catalog.CatalogApp;
import catalog.domain.Category;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {CatalogApp.class})
@Transactional
public class CategoryRepositoryUnitTest {


    @Autowired
    CategoryRepository categoryRepository;

    @Test
    public void count() {
        assertEquals(3, categoryRepository.count());
    }

    @Test
    public void findAll() {
        List<Category> categorys = categoryRepository.findAll();
        assertEquals(3, categorys.size());
        assertProduct(0, categorys.get(0));
        assertProduct(1, categorys.get(1));
        assertProduct(2, categorys.get(2));
    }


    @Test
    public void findOne() {
        assertProduct(0, categoryRepository.findOne(CATEGORIES[0].getId()));
        assertProduct(1, categoryRepository.findOne(CATEGORIES[1].getId()));
        assertProduct(2, categoryRepository.findOne(CATEGORIES[2].getId()));
    }

    @Test
    public void save_newCategory() {
        assertEquals(3, categoryRepository.count());
        Category newCategory = new Category();
        newCategory.setId(CATEGORIES[2].getId() + 1000);
        newCategory.setName("mice");
        newCategory.setUpdated(ZonedDateTime.now());
        Category saved = categoryRepository.save(newCategory);
        assertEquals(4, categoryRepository.count());
        assertEquals(newCategory.getName(), saved.getName());
    }

    @Test
    public void save_existingCategory() {
        assertEquals(3, categoryRepository.count());
        Category saved = categoryRepository.save(CATEGORIES[2]);
        assertEquals(3, categoryRepository.count());
        assertEquals(CATEGORIES[2], saved);
    }

    private static void assertProduct(int expectedJitterIndex, Category actual) {
        Category expected = CATEGORIES[expectedJitterIndex];
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getImagePath(), actual.getImagePath());
        assertEquals(expected.getUpdated(), actual.getUpdated());
        assertEquals(expected.getParent(), actual.getParent());
        assertEquals(expected.getChildCategories(), actual.getChildCategories());
    }

    private static Category[] CATEGORIES = new Category[3];
    @BeforeClass
    public static void init() {
        CATEGORIES[0] = new Category();
        CATEGORIES[0].setName("notebooks");
        CATEGORIES[0].setUpdated(ZonedDateTime.now());
        CATEGORIES[1] = new Category();
        CATEGORIES[1].setName("phones");
        CATEGORIES[1].setUpdated(ZonedDateTime.now());
        CATEGORIES[2] = new Category();
        CATEGORIES[2].setName("notebook_business");
        CATEGORIES[2].setUpdated(ZonedDateTime.now());
    }

    @Before
    public void before() {
        CATEGORIES[0] = categoryRepository.save(CATEGORIES[0]);
        CATEGORIES[1] = categoryRepository.save(CATEGORIES[1]);

        CATEGORIES[2].setParent(CATEGORIES[0]);
        CATEGORIES[2] = categoryRepository.save(CATEGORIES[2]);
    }

}
