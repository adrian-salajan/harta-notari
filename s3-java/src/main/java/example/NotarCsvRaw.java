package example;

import com.opencsv.bean.CsvBindByName;

import java.util.Objects;

public class NotarCsvRaw {

    public NotarCsvRaw() {
    }

    public NotarCsvRaw(String name, String address, String city) {
        this.name = name;
        this.address = address;
        this.city = city;
    }

    @CsvBindByName(column = "Nume si prenume", required = true)
    private String name;

    @CsvBindByName(column = "Adresa sediu", required = false)
    private String address;

    @CsvBindByName(column = "Localitate", required = false)
    private String city;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotarCsvRaw notarCsvRaw = (NotarCsvRaw) o;
        return name.equals(notarCsvRaw.name) && Objects.equals(address, notarCsvRaw.address) && Objects.equals(city, notarCsvRaw.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address, city);
    }
}
