package br.edu.utfpr.cp.emater.midmipsystem.service.base;

import br.edu.utfpr.cp.emater.midmipsystem.entity.base.City;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.State;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityNotFoundException;
import br.edu.utfpr.cp.emater.midmipsystem.repository.base.CityRepository;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CityServiceTest {


    @MockBean
    private CityRepository cityRepository;

    @Autowired
    private CityService cityService;

    private City city1, city2, city3, city4;

    @Before
    public void setUp() {
        this.city1 = City.builder().name("Lajes").state(State.SC).build();
        this.city1.setId((long) 1);
        this.city2 = City.builder().name("Cornelio Procopio").state(State.PR).build();
        this.city2.setId((long) 2);
        this.city3 = City.builder().name("Cachoeirinha").state(State.RS).build();
        this.city3.setId((long) 3);
        this.city4 = City.builder().name("Bandeirantes").state(State.PR).build();
        this.city4.setId((long) 4);

        List<City> cities = asList(this.city1,this.city2,this.city3);
        BDDMockito.when(this.cityRepository.findAll())
                .thenReturn(cities);
    }

    @Test
    public void readAllCityServiceTest() {
        assertThat(this.cityService.readAll()).doesNotContain(this.city4).isNotEmpty().doesNotContainNull()
                .containsExactlyInAnyOrder(this.city1,this.city2,this.city3);
    }

    @Test
    public void readByIdCityServiceTest() throws EntityNotFoundException {
        when(this.cityRepository.findById((long) 1))
            .thenReturn(java.util.Optional.ofNullable(this.city1));
        assertThat(this.cityService.readById((long) 1)).isEqualTo(this.city1);
    }


    @Test(expected = EntityNotFoundException.class)
    public void readByIdCityServiceEntityNotFoundExceptionTest() throws EntityNotFoundException {
        when(this.cityRepository.findById((long) 12))
                .thenReturn(java.util.Optional.ofNullable(null));
        this.cityService.readById((long) 12);
    }
}