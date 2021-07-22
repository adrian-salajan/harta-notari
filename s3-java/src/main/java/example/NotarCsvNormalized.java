package example;

import com.opencsv.bean.CsvBindByName;

import java.util.Objects;

public class NotarCsvNormalized {

    public NotarCsvNormalized(String name, String address, String rawAddress, String city) {
        this.name = name;
        this.address = address;
        this.rawAddress = rawAddress;
        this.city = city;
    }

    @CsvBindByName(column = "nume", required = true)
    private String name;

    @CsvBindByName(column = "adresa", required = false)
    private String address;

    @CsvBindByName(column = "localitate", required = false)
    private String city;

    @CsvBindByName(column = "adresaRaw", required = false)
    private String rawAddress;

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

    public String getRawAddress() {
        return rawAddress;
    }

    public void setRawAddress(String rawAddress) {
        this.rawAddress = rawAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotarCsvNormalized that = (NotarCsvNormalized) o;
        return name.equals(that.name) && address.equals(that.address) && city.equals(that.city) && rawAddress.equals(that.rawAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address, city, rawAddress);
    }

    @Override
    public String toString() {
        return "NotarCsvNormalized{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", rawAddress='" + rawAddress + '\'' +
                '}';
    }
}
