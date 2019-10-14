package br.edu.utfpr.cp.emater.midmipsystem.service.base;

import br.edu.utfpr.cp.emater.midmipsystem.entity.base.City;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.MacroRegion;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.Region;
import br.edu.utfpr.cp.emater.midmipsystem.entity.base.State;
import br.edu.utfpr.cp.emater.midmipsystem.exception.AnyPersistenceException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityAlreadyExistsException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityInUseException;
import br.edu.utfpr.cp.emater.midmipsystem.exception.EntityNotFoundException;
import br.edu.utfpr.cp.emater.midmipsystem.repository.base.CityRepository;
import br.edu.utfpr.cp.emater.midmipsystem.repository.base.MacroRegionRepository;
import br.edu.utfpr.cp.emater.midmipsystem.repository.base.RegionRepository;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RegionServiceTest {


    @MockBean
    private MacroRegionRepository macroRegionRepository;
    @MockBean
    private CityRepository cityRepository;
    @MockBean
    private RegionRepository regionRepository;

    @Autowired
    private RegionService regionService;

    private City city1, city2, city3, city4, city5;
    private MacroRegion macroRegion1, macroRegion2, macroRegion3, macroRegion4;
    private Region region1, region2, region3, region4;


    @Before
    public void setUp() {
        this.macroRegion1 = MacroRegion.builder().id((long) 1).name("Macro Leste").build();
        this.macroRegion2 = MacroRegion.builder().id((long) 2).name("Polo Oeste").build();
        this.macroRegion3 = MacroRegion.builder().id((long) 3).name("Centro Sul").build();
        this.macroRegion4 = MacroRegion.builder().id((long) 4).name("Costa Norte").build();

        this.city1 = City.builder().name("Lajes").state(State.SC).build();
        this.city1.setId((long) 1);
        this.city2 = City.builder().name("Cornelio Procopio").state(State.PR).build();
        this.city2.setId((long) 2);
        this.city3 = City.builder().name("Cachoeirinha").state(State.RS).build();
        this.city3.setId((long) 3);
        this.city4 = City.builder().name("Bandeirantes").state(State.PR).build();
        this.city4.setId((long) 4);
        this.city5 = City.builder().name("Pato Branco").state(State.PR).build();
        this.city5.setId((long) 4);

        this.region1 = Region.builder().id((long) 11).name("Capital Norte").macroRegion(this.macroRegion1).build();
        this.region1.addCity(this.city2);
        this.region1.addCity(this.city3);

        this.region2 = Region.builder().id((long) 12).name("Interior Norte").macroRegion(this.macroRegion2).build();
        this.region2.addCity(this.city2);

        this.region3 = Region.builder().id((long) 13).name("Centro Oeste").macroRegion(this.macroRegion3).build();
        this.region3.addCity(this.city3);

        this.region4 = Region.builder().id((long) 14).name("Capital Sul").macroRegion(this.macroRegion2).build();
        this.region4.addCity(this.city1);
        this.region4.addCity(this.city2);
        this.region4.addCity(this.city3);

        List<City> cities = asList(this.city1,this.city2,this.city3, this.city4, this.city5);
        BDDMockito.when(this.cityRepository.findAll())
                .thenReturn(cities);

        List<MacroRegion> macroRegions = asList(this.macroRegion1,this.macroRegion2,this.macroRegion3, this.macroRegion4);
        BDDMockito.when(this.macroRegionRepository.findAll())
                .thenReturn(macroRegions);

        List<Region> regions = asList(this.region1,this.region2,this.region3);
        BDDMockito.when(this.regionRepository.findAll())
                .thenReturn(regions);

        when(this.cityRepository.findById((long) 1)).thenReturn(java.util.Optional.ofNullable(this.city1));
        when(this.cityRepository.findById((long) 2)).thenReturn(java.util.Optional.ofNullable(this.city2));
        when(this.cityRepository.findById((long) 3)).thenReturn(java.util.Optional.ofNullable(this.city3));
        when(this.cityRepository.findById((long) 4)).thenReturn(java.util.Optional.ofNullable(this.city4));
        when(this.cityRepository.findById((long) 4)).thenReturn(java.util.Optional.ofNullable(this.city5));
    }

    @Test
    public void readAllRegionServiceTest() {
        assertThat(this.regionService.readAll()).doesNotContain(this.region4).isNotEmpty().doesNotContainNull()
                .containsExactlyInAnyOrder(this.region1,this.region2,this.region3);
    }

    @Test
    public void readAllMacroRegionsRegionServiceTest() {
        assertThat(this.regionService.readAllMacroRegions()).isNotEmpty().doesNotContainNull()
                .containsExactlyInAnyOrder(this.macroRegion1,this.macroRegion2,this.macroRegion3, this.macroRegion4);
    }

    @Test
    public void readAllCitiesRegionServiceTest() {
        assertThat(this.regionService.readAllCities()).isNotEmpty().doesNotContainNull()
                .containsExactlyInAnyOrder(this.city1,this.city2,this.city3, this.city5, this.city4);
    }

    @Test
    public void readMacroRegionByIdRegionServiceTest() {
        when(this.macroRegionRepository.findById((long) 1))
                .thenReturn(java.util.Optional.ofNullable(this.macroRegion1));
        assertThat(this.regionService.readMacroRegionById((long) 1)).isEqualTo(this.macroRegion1);
    }

    @Test // Test falha pois - Ha o lançamento de um NullPointe pois o contrutor precisa de um nome para ser executado.
    public void readMacroRegionByIdRegionServiceEntityNotFoundExceptionTest() {
        when(this.macroRegionRepository.findById((long) 4))
                .thenReturn(java.util.Optional.ofNullable(null));
        assertThat(this.regionService.readMacroRegionById((long) 4).getClass()).isEqualTo(MacroRegion.class);
    }

    @Test
    public void readAllCitiesWithoutRegionRegionServiceTest() {
        assertThat(this.regionService.readAllCitiesWithoutRegion()).doesNotContain(this.city2, this.city3).isNotEmpty().doesNotContainNull()
                .containsExactly(this.city1, this.city4, this.city5);
    }

    @Test
    public void readByIdRegionServiceTest() {
        when(this.regionRepository.findById((long) 11)).thenReturn(java.util.Optional.ofNullable(this.region1));
    }

    @Test
    public void createRegionServiceTest() throws AnyPersistenceException, EntityNotFoundException, EntityAlreadyExistsException {
        when(this.macroRegionRepository.findById((long) 2))
                .thenReturn(java.util.Optional.ofNullable(this.macroRegion2));
        when(this.regionRepository.findById((long) 14))
            .thenReturn(java.util.Optional.ofNullable(null));
        when(this.regionRepository.save(this.region4)).thenReturn(this.region4);

        try {
            this.regionService.readById(this.region4.getId());
            Assertions.fail("EntityNotFoundException it is not throws");
        } catch (EntityNotFoundException e){    }

        this.regionService.create(this.region4);

        when(this.regionRepository.findById((long) 14))
                .thenReturn(java.util.Optional.ofNullable(this.region4));

        Region region = this.regionService.readById((long) 14);
        assertThat(region.getName()).isEqualTo("Capital Sul");
    }

    @Test (expected = EntityAlreadyExistsException.class)
    public void createRegionServiceEntityAlreadyExistsExceptionTest() throws AnyPersistenceException, EntityNotFoundException, EntityAlreadyExistsException {
        when(this.macroRegionRepository.findById((long) 1))
                .thenReturn(java.util.Optional.ofNullable(this.macroRegion1));

        this.regionService.create(this.region1);
        Assertions.fail("EntityAlreadyExistsException it is not throws");
    }

    @Test (expected = AnyPersistenceException.class)
    public void createRegionServiceAnyPersistenceExceptionTest() throws AnyPersistenceException, EntityNotFoundException, EntityAlreadyExistsException {
        when(this.macroRegionRepository.findById((long) 2))
                .thenReturn(java.util.Optional.ofNullable(this.macroRegion2));
        when(this.regionRepository.findById((long) 14))
                .thenReturn(java.util.Optional.ofNullable(null));
        doThrow(IllegalArgumentException.class).when(this.regionRepository).save(this.region4);

        try {
            this.regionService.readById(this.region4.getId());
            Assertions.fail("EntityNotFoundException it is not throws");
        } catch (EntityNotFoundException e){    }

        this.regionService.create(this.region4);

        Assertions.fail("AnyPersistenceException it is not throws");
    }

    @Test (expected = EntityNotFoundException.class)
    public void createRegionServiceEntityNotFoundExceptionTest() throws AnyPersistenceException, EntityNotFoundException, EntityAlreadyExistsException {
        when(this.macroRegionRepository.findById((long) 44))
                .thenReturn(java.util.Optional.ofNullable(null));
        when(this.regionRepository.findById((long) 14))
                .thenReturn(java.util.Optional.ofNullable(null));
        doThrow(IllegalArgumentException.class).when(this.regionRepository).save(this.region4);

        try {
            this.regionService.readById(this.region4.getId());
            Assertions.fail("EntityNotFoundException it is not throws");
        } catch (EntityNotFoundException e){    }

        this.region4.getMacroRegion().setId((long) 44);
        this.regionService.create(this.region4);

        Assertions.fail("EntityNotFoundException it is not throws");
    }

    @Test
    public void updateRegionServiceTest() throws EntityNotFoundException, AnyPersistenceException, EntityAlreadyExistsException {
        when(this.macroRegionRepository.findById((long)1))
                .thenReturn(java.util.Optional.ofNullable(this.macroRegion1));
        when(this.macroRegionRepository.findById((long)2))
                .thenReturn(java.util.Optional.ofNullable(this.macroRegion2));
        when(this.regionRepository.findById((long) 11))
                .thenReturn(java.util.Optional.ofNullable(this.region1));
        when(this.regionRepository.saveAndFlush(any(Region.class))).thenReturn(this.region1);

        Region region = this.regionService.readById((long) 11);
        assertThat(region.getName()).isEqualTo("Capital Norte");
        assertThat(region.getMacroRegion()).isEqualTo(this.macroRegion1);
        assertThat(region.getCities()).containsExactlyInAnyOrder(this.city2, this.city3);

        region.setName("Update Teste");
        region.setMacroRegion(this.macroRegion2);
        region.addCity(this.city1);

        this.regionService.update(region);

        when(this.regionRepository.findById((long)1))
                .thenReturn(java.util.Optional.ofNullable(region));

        Region regionTemp = this.regionService.readById((long) 11);

        assertThat(regionTemp.getName()).isEqualTo("Update Teste");
        assertThat(regionTemp.getMacroRegion()).isEqualTo(this.macroRegion2);
        assertThat(regionTemp.getCities()).containsExactlyInAnyOrder(this.city2, this.city3, this.city1);
    }

    @Test (expected = EntityNotFoundException.class)
    public void updateRegionServiceEntityNotFoundExceptionTest() throws EntityNotFoundException, AnyPersistenceException, EntityAlreadyExistsException {
        when(this.regionRepository.findById((long) 11))
                .thenReturn(java.util.Optional.ofNullable(this.region1));
        when(this.regionRepository.findById((long) 113))
                .thenReturn(java.util.Optional.ofNullable(null));


        Region region = this.regionService.readById((long) 11);
        assertThat(region.getName()).isEqualTo("Capital Norte");
        assertThat(region.getMacroRegion()).isEqualTo(this.macroRegion1);
        assertThat(region.getCities()).containsExactlyInAnyOrder(this.city2, this.city3);

        region.setName("Update Teste");
        region.setMacroRegion(this.macroRegion2);
        region.addCity(this.city1);
        region.setId((long) 113);

        this.regionService.update(region);
        Assertions.fail("EntityNotFoundException it is not throws");
    }

    @Test (expected = AnyPersistenceException.class)
    public void updateRegionServiceAnyPersistenceExceptionTest() throws EntityNotFoundException, AnyPersistenceException, EntityAlreadyExistsException {
        when(this.macroRegionRepository.findById((long)2))
                .thenReturn(java.util.Optional.ofNullable(this.macroRegion2));
        when(this.regionRepository.findById((long) 11))
                .thenReturn(java.util.Optional.ofNullable(this.region1));
        doThrow(IllegalArgumentException.class).when(this.regionRepository).saveAndFlush(any(Region.class));

        Region region = this.regionService.readById((long) 11);
        assertThat(region.getName()).isEqualTo("Capital Norte");
        assertThat(region.getMacroRegion()).isEqualTo(this.macroRegion1);
        assertThat(region.getCities()).containsExactlyInAnyOrder(this.city2, this.city3);

        region.setName("Update Teste");
        region.setMacroRegion(this.macroRegion2);
        region.addCity(this.city1);

        this.regionService.update(region);
        Assertions.fail("AnyPersistenceException it is not throws");
 }

    @Test(expected = EntityAlreadyExistsException.class)
    public void updateRegionServiceEntityAlreadyExistsExceptionTest() throws EntityNotFoundException, AnyPersistenceException, EntityAlreadyExistsException {
        Region regionCopy = this.region1;

        List<Region> regions = asList(this.region1,this.region2,this.region3,regionCopy);
        BDDMockito.when(this.regionRepository.findAll())
                .thenReturn(regions);

        when(this.regionRepository.findById((long) 11))
                .thenReturn(java.util.Optional.ofNullable(this.region1));

        Region region = this.regionService.readById((long) 11);
        assertThat(region.getName()).isEqualTo("Capital Norte");
        assertThat(region.getMacroRegion()).isEqualTo(this.macroRegion1);
        assertThat(region.getCities()).containsExactlyInAnyOrder(this.city2, this.city3);

        region.setName("Update Teste");
        region.setMacroRegion(this.macroRegion2);
        region.addCity(this.city1);

        this.regionService.update(region);
        Assertions.fail("EntityAlreadyExistsException it is not throws");
}

    @Test
    public void deleteRegionServiceTest() throws EntityNotFoundException, AnyPersistenceException, EntityInUseException {
        when(this.regionRepository.findById((long) 1))
                .thenReturn(java.util.Optional.ofNullable(this.region1));
        doNothing().when(this.regionRepository).delete(any(Region.class));

        this.regionService.delete((long) 1);

        when(this.regionRepository.findById((long) 1))
                .thenReturn(java.util.Optional.ofNullable(null));

        try {
            this.regionService.readById((long) 1);
            Assertions.fail("EntityNotFoundException it is not throws");
        } catch (EntityNotFoundException e){
            assertThat(e.getClass()).isEqualTo(EntityNotFoundException.class);
        }
    }

    @Test(expected = AnyPersistenceException.class)
    public void deleteRegionServiceAnyPersistenceExceptionTest() throws EntityNotFoundException, AnyPersistenceException, EntityInUseException {
        when(this.regionRepository.findById((long) 1))
                .thenReturn(java.util.Optional.ofNullable(this.region1));
        doThrow(IllegalArgumentException.class).when(this.regionRepository).delete(any(Region.class));

        this.regionService.delete((long) 1);
        Assertions.fail("AnyPersistenceException it is not throws");
    }

    @Test(expected = EntityInUseException.class)
    public void deleteRegionServiceEntityInUseExceptionTest() throws EntityNotFoundException, AnyPersistenceException, EntityInUseException {
        when(this.regionRepository.findById((long) 1))
                .thenReturn(java.util.Optional.ofNullable(this.region1));
        doThrow(DataIntegrityViolationException.class).when(this.regionRepository).delete(any(Region.class));

        this.regionService.delete((long) 1);
        Assertions.fail("EntityInUseException it is not throws");
    }

    @Test (expected = EntityNotFoundException.class)
    public void deleteRegionServiceEntityNotFoundExceptionTest() throws EntityNotFoundException, AnyPersistenceException, EntityInUseException {
        when(this.regionRepository.findById((long) 12))
                .thenReturn(java.util.Optional.ofNullable(null));
        doNothing().when(this.regionRepository).delete(any(Region.class));

        this.regionService.delete((long) 12);
         Assertions.fail("EntityNotFoundException it is not throws");
    }
}