package com.example.boec.Model;

public class User {
    private String username, password, name, address;

    public User() {
    }

    public User(UserBuilder builder){
        this.username = builder.username;
        this.password = builder.password;
        this.name = builder.name;
        this.address = builder.address;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public static class UserBuilder{
        private String username, password, name, address;
        public UserBuilder(String username, String password){
            this.username = username;
            this.password = password;
        }
        public UserBuilder name(String name){
            this.name = name;
            return this;
        }
        public UserBuilder address(String address){
            this.address = address;
            return this;
        }

        public UserBuilder password(String password){
            this.password = password;
            return this;
        }

        public UserBuilder username(String username){
            this.username = username;
            return this;
        }

        public User build(){
            User u = new User(this);
            return u;
        }
    }
}
