package edu.byui.myapplication.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.security.KeyStore;

@Entity(tableName = "user")
public class User {

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private int userID;
    private String username;
    private String password;
    @ColumnInfo(name = "display_name")
    private String displayName;
    private String email;
    private String phone;
    private String address;
    @ColumnInfo(name = "birthdate")
    private String bday;

    // The following @ignore needs to be here for ROOM. It's telling the database not to instantiate
    // using this constructor.
    @Ignore
    public User() {

    }
    // This will be the constructor ROOM uses. If any other constructors are created that use a non
    // primitive datatype other than String, it should include this @Ignore directive. MT.
    public User(String username,
                String displayName,
                String email,
                String phone,
                String address,
                String password) {
        this.username = username;
        this.displayName = displayName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.password = password;
    }

    public int getUserID() { return userID; }
    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String diaplayName) {
        this.displayName = diaplayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBday() {
        return bday;
    }

    public void setBday(String bday) {
        this.bday = bday;
    }

    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", displayName='" + displayName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", bday='" + bday + '\'' +
                '}';
    }
}
