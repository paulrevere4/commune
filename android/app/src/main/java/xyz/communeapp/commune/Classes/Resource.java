package xyz.communeapp.commune.Classes;

/**
 * Created by Rabi on 11/21/16.
 */

public class Resource {
    private String mName;
    private String mID;
    private String mDetails;

    public Resource(String Name, String Details) {
        this.mName = Name;
        this.mDetails = Details;
    }

    public String getName() {
        return mName;
    }

    public String getDetails() {
        return mDetails;
    }

    public String getID() {
        return mID;
    }
}
