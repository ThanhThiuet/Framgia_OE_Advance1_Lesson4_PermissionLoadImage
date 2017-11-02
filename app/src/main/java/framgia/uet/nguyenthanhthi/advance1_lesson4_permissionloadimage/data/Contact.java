package framgia.uet.nguyenthanhthi.advance1_lesson4_permissionloadimage.data;

/**
 * Created by thanhthi on 02/11/2017.
 */

public class Contact {

    private String mName;
    private String mPhone;

    public Contact(String name, String phone) {
        this.mName = name;
        this.mPhone = phone;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        this.mPhone = phone;
    }
}
