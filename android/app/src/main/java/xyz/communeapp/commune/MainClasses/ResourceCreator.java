package xyz.communeapp.commune.MainClasses;

import com.google.firebase.database.DatabaseReference;

/**
 * Writes resource to database
 */
public class ResourceCreator {
    private DatabaseReference mResourceGroupRef;
    private Resource mResource;

    /**
     * Constructor for resource creator
     *
     * @param resourceGroupRef database reference to the group the resource belongs to
     * @param resource         resource to be written
     */
    public ResourceCreator(DatabaseReference resourceGroupRef, Resource resource) {
        mResourceGroupRef = resourceGroupRef;
        mResource = resource;
    }

    /**
     * Writes resource to the database under the group the resource belongs to
     */
    public void create() {
        // Randomly create a ID for the new resource and retrieve it
        String ID = mResourceGroupRef.push().getKey();
        mResourceGroupRef.child(ID).child("Name").setValue(mResource.getName());
        mResourceGroupRef.child(ID).child("Details").setValue(mResource.getDetails());
    }
}
