package br.edu.utfpr.cp.emater.midmipsystem.service.pulverisation;

import br.edu.utfpr.cp.emater.midmipsystem.entity.pulverisation.Product;
import br.edu.utfpr.cp.emater.midmipsystem.entity.pulverisation.ProductUnit;
import br.edu.utfpr.cp.emater.midmipsystem.entity.pulverisation.Target;
import br.edu.utfpr.cp.emater.midmipsystem.entity.pulverisation.TargetCategory;
import br.edu.utfpr.cp.emater.midmipsystem.exception.AnyPersistenceException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityAlreadyExistsException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityInUseException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityNotFoundException;
import br.edu.utfpr.cp.emater.midmipsystem.repository.pulverisation.ProductRepository;
import br.edu.utfpr.cp.emater.midmipsystem.repository.pulverisation.TargetRepository;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;



@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductServiceTest {

    @MockBean
    private TargetRepository targetRepository;
    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    private Target target1, target2, target3;
    private Product product1, product2, product3;

    @Before
    public void setUp(){
        this.target1 = Target.builder().id((long) 232).description("Test Target1").category(TargetCategory.ADJUVANTE).build();
        this.target2 = Target.builder().id((long) 343).description("Test Target2").category(TargetCategory.HERBICIDA).build();
        this.target3 = Target.builder().id((long) 545).description("Test Target3").category(TargetCategory.ADUBO_FOLIAR).build();

        this.product1 = Product.builder().id((long) 121).unit(ProductUnit.L).name("Veneno Amarelo").target(this.target1).build();
        this.product2 = Product.builder().id((long) 232).unit(ProductUnit.L).name("Veneno Amarelo").target(this.target2).build();
        this.product3 = Product.builder().id((long) 454).unit(ProductUnit.L).name("Veneno Amarelo").target(this.target3).build();

        List<Target> targets= asList(this.target1,this.target2);
        when(this.targetRepository.findAll())
                .thenReturn(targets);

        List<Product> products= asList(this.product1,this.product2);
        when(this.productRepository.findAll())
                .thenReturn(products);
    }

    @Test
    public void readAllProductServiceTest() {
        assertThat(this.productService.readAll()).isNotEmpty().doesNotContainNull().doesNotContain(this.product3)
                .containsExactlyInAnyOrder(this.product1, this.product2);
    }

    @Test
    public void readByIdProductServiceTest() throws EntityNotFoundException {
        when(this.productRepository.findById((long) 121)).thenReturn(java.util.Optional.ofNullable(this.product1));
        assertThat(this.productService.readById((long) 121)).isEqualTo(this.product1);
    }

    @Test (expected = EntityNotFoundException.class)
    public void readByIdProductServiceEntityNotFoundExceptionTest() throws EntityNotFoundException {
        when(this.productRepository.findById((long) 555)).thenReturn(java.util.Optional.ofNullable(null));
        this.productService.readById((long) 555);
        fail("EntityNotFoundException it is not throws");
    }


    @Test
    public void createProductServiceTest() throws AnyPersistenceException, EntityNotFoundException, EntityAlreadyExistsException {
        when(this.productRepository.findById((long) 454)).thenReturn(java.util.Optional.ofNullable(null));
        when(this.productRepository.save(this.product3)).thenReturn(this.product3);

        try {
            this.productService.readById((long) 454);
            fail("EntityNotFoundException it is not throws");
        } catch (EntityNotFoundException e) {
        }
        this.productService.create(this.product3);
        when(this.productRepository.findById((long) 454)).thenReturn(java.util.Optional.ofNullable(this.product3));
        Product productTemp = this.productService.readById((long) 454);
        assertThat(productTemp.getTarget()).isEqualTo(this.target1);
        assertThat(productTemp.getId()).isEqualTo(454);
    }

    @Test (expected = AnyPersistenceException.class)
    public void createProductServiceAnyPersistenceExceptionTest() throws AnyPersistenceException, EntityNotFoundException, EntityAlreadyExistsException {
        when(this.productRepository.findById((long) 454)).thenReturn(java.util.Optional.ofNullable(null));
        doThrow(IllegalArgumentException.class).when(this.productRepository)
                .save(any(Product.class));

        try {
            this.productService.readById((long) 454);
            fail("EntityNotFoundException it is not throws");
        } catch (EntityNotFoundException e) {
        }
        this.productService.create(this.product3);
        fail("AnyPersistenceException it is not throws");
    }

    @Ignore //EntityNotFoundException Nunca é disparada
    @Test (expected = EntityNotFoundException.class)
    public void createProductServiceEntityNotFoundExceptionTest() throws AnyPersistenceException, EntityNotFoundException, EntityAlreadyExistsException {
        when(this.productRepository.findById((long) 454)).thenReturn(java.util.Optional.ofNullable(null));
        when(this.productRepository.save(this.product3)).thenReturn(this.product3);
        this.productService.create(this.product3);
        fail("EntityNotFoundException it is not throws");
    }

    @Test (expected = EntityAlreadyExistsException.class)
    public void createProductServiceEntityAlreadyExistsExceptionTest() throws AnyPersistenceException, EntityNotFoundException, EntityAlreadyExistsException {
        this.productService.create(this.product1);
        fail("EntityAlreadyExistsException it is not throws");
    }

    @Test
    public void updateProductServiceTest() {
    }

    @Test
    public void deleteProductServiceTest() throws EntityNotFoundException, AnyPersistenceException, EntityInUseException {
        when(this.productRepository.findById((long) 121)).thenReturn(java.util.Optional.ofNullable(this.product1));
        doNothing().when(this.productRepository).delete(any(Product.class));
        this.productService.delete((long) 121);
        when(this.productRepository.findById((long) 121)).thenReturn(java.util.Optional.ofNullable(null));
        try {
            this.productService.readById((long) 121);
            fail("EntityNotFoundException it is not throws");
        } catch (EntityNotFoundException e){
            assertThat(e.getClass()).isEqualTo(EntityNotFoundException.class);
        }
    }

    @Test
    public void readAllTargetsProductServiceTest() {
        assertThat(this.productService.readAllTargets()).containsExactlyInAnyOrder(this.target1, this.target2).doesNotContain(this.target3)
                .isNotEmpty().doesNotContainNull();
    }

    @Test
    public void readTargetByIdTest() throws EntityNotFoundException {
        when(this.targetRepository.findById((long) 232)).thenReturn(java.util.Optional.ofNullable(this.target1));
        assertThat(this.productService.readTargetById((long) 232)).isEqualTo(this.target1);
    }


    @Test (expected = EntityNotFoundException.class)
    public void readTargetByIdEntityNotFoundExceptionTest() throws EntityNotFoundException {
        when(this.targetRepository.findById((long) 976)).thenReturn(java.util.Optional.ofNullable(null));
        assertThat(this.productService.readTargetById((long) 976)).isEqualTo(this.target1);
        fail("EntityNotFoundException it is not throws");
    }
}