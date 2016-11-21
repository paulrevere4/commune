package xyz.communeapp.commune.Classes;

import com.google.firebase.database.DatabaseReference;

/**
 * Created by Rabi on 11/21/16.
 */

public class ResourceCreator {
    private DatabaseReference mResourceGroupRef;
    private Resource mResource;

    public ResourceCreator(DatabaseReference resourceGroupRef, Resource resource) {
        mResourceGroupRef = resourceGroupRef;
        mResource = resource;
    }

    public void create(){
        String ID = mResourceGroupRef.push().getKey();
        mResourceGroupRef.child(ID).child("Name").setValue(mResource.getName());
        mResourceGroupRef.child(ID).child("Details").setValue(mResource.getDetails());
    }
}
