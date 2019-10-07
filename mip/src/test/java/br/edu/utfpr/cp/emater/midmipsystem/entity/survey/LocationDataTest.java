package br.edu.utfpr.cp.emater.midmipsystem.entity.survey;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;


import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LocationDataTest {

    private LocationData locationData;

    @Test
    public void builderTest() {
        this.locationData = new LocationData();
        this.locationData = LocationData.builder().longitude(11).latitude(22).build();
    }

    @Test
    public void getAndSetLongitudeTest() {
        this.locationData = LocationData.builder().build();
        this.locationData.setLongitude(33);
        assertThat(this.locationData.getLongitude()).isEqualTo(33);
    }

    @Test
    public void getAndSetLatitudeTest() {
        this.locationData = LocationData.builder().build();
        this.locationData.setLatitude(22);
        assertThat(this.locationData.getLatitude()).isEqualTo(22);
    }

    @Test
    public void equalsFalseLocationDataTest() {
        LocationData locationData = LocationData.builder().latitude(22).longitude(11).build();
        this.locationData = LocationData.builder().latitude(22).longitude(202).build();
        assertThat(this.locationData.equals(locationData)).isFalse();
    }

    @Test
    public void equalsTrueLocationDataTest() {
        LocationData locationData = LocationData.builder().latitude(22).longitude(11).build();
        this.locationData = locationData;
        assertThat(this.locationData.equals(locationData)).isTrue();
    }

    @Test
    public void hashCodeTrueLocationDataTest() {
        LocationData locationData = LocationData.builder().latitude(22).longitude(11).build();
        int value = locationData.hashCode();
        assertThat(locationData.hashCode()).isEqualTo(value);
    }

    @Test
    public void hashCodeFalseLocationDataTest() {
        LocationData locationData = LocationData.builder().latitude(22).longitude(11).build();
        int value = locationData.hashCode();
        value++;
        assertThat(locationData.hashCode()==value).isFalse();
    }
}