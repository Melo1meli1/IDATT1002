package no.idatt1002.service.config;

public class RegisterRequest {

    private int age;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String address;

    public RegisterRequest(int age, String name, String email, String password, String phone, String address) {
        this.age = age;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public static char[] toString(RegisterRequest user) {
        return null;
    }

}
