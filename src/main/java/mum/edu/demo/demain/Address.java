package mum.edu.demo.demain;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
public class Address implements Cloneable {
    public static class AddressBuilder implements Builder<Address> {
        private Address address;

        public AddressBuilder() {
            this.address = new Address();
        }

        public Address.AddressBuilder withId(int id) {
            address.setId(id);
            return this;
        }

        public Address.AddressBuilder withStreet(String street) {
            address.setStreet(street);
            return this;
        }

        public Address.AddressBuilder withState(String state) {
            address.setState(state);
            return this;
        }

        public Address.AddressBuilder withZipCode(String zipCode) {
            address.setZipCode(zipCode);
            return this;
        }

        @Override
        public Address build() {
            return address;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(min = 5)
    private String Street;

    @NotEmpty
    private String State;
    @NotEmpty
    private String zipCode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStreet() {
        return Street;
    }

    public void setStreet(String street) {
        Street = street;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public static Address.AddressBuilder create() {
        return new Address.AddressBuilder();
    }
}
