package xyz.communeapp.commune;

import org.junit.Test;

import xyz.communeapp.commune.MainClasses.Resource;

import static org.junit.Assert.assertEquals;


public class ResourceTest {

    @Test
    public void simpleTest() {
        Resource resource = new Resource("fakeName", "fakeDetails");
        assertEquals(resource.getName(), "fakeName");
        assertEquals(resource.getDetails(), "fakeDetails");
    }
}
