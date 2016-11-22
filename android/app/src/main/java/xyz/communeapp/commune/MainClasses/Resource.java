package xyz.communeapp.commune.MainClasses;

/**
 * Holds information about a resource in the app
 */
public class Resource {
    private String mName;
    private String mID;
    private String mDetails;

    /**
     * Constructor for a resource
     *
     * @param Name
     * @param Details
     */
    public Resource(String Name, String Details) {
        this.mName = Name;
        this.mDetails = Details;
    }

    /**
     * Getter for resource name
     *
     * @return resource name
     */
    public String getName() {
        return mName;
    }

    /**
     * Getter for resource details
     *
     * @return resource details
     */
    public String getDetails() {
        return mDetails;
    }

    /**
     * Getter for resource ID
     *
     * @return resource ID
     */
    public String getID() {
        return mID;
    }
}
