package dz.btesto.upmc.jiaanapp.activities.account;

import org.parceler.Parcel;

/**
 * Created by Xo on 28/08/2016.
 */
@Parcel
public class Account {
    private String uID;
    private String userName;
    private String address;
    private String mobile;
    private String email;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Account() {

    }

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Account(String email, String userName, String address, String mobile, String password) {
        this.email = email;
        this.userName = userName;
        this.address = address;
        this.mobile = mobile;
        this.password = password;
    }
}
