package fr.imt_atlantique.myfirstapplication;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

public class UserInfo implements Parcelable {
    private String lastName;
    private String firstName;
    private String birthCity;
    private String birthDate;
    private String department;
    private List<String> phoneNumbers;

    // Constructor
    public UserInfo(String lastName, String firstName, String birthCity, String birthDate, String department, List<String> phoneNumbers) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthCity = birthCity;
        this.birthDate = birthDate;
        this.department = department;
        this.phoneNumbers = phoneNumbers;
    }

    // Parcelable methods
    protected UserInfo(Parcel in) {
        lastName = in.readString();
        firstName = in.readString();
        birthCity = in.readString();
        birthDate = in.readString();
        department = in.readString();
        phoneNumbers = in.createStringArrayList();
    }

    public static final Creator<UserInfo> CREATOR = new Creator<UserInfo>() {
        @Override
        public UserInfo createFromParcel(Parcel in) {
            return new UserInfo(in);
        }

        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(lastName);
        dest.writeString(firstName);
        dest.writeString(birthCity);
        dest.writeString(birthDate);
        dest.writeString(department);
        dest.writeStringList(phoneNumbers);
    }

    // Getters
    public String getLastName() { return lastName; }
    public String getFirstName() { return firstName; }
    public String getBirthCity() { return birthCity; }
    public String getBirthDate() { return birthDate; }
    public String getDepartment() { return department; }
    public List<String> getPhoneNumbers() { return phoneNumbers; }
}
