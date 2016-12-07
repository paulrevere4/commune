package xyz.communeapp.commune;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import xyz.communeapp.commune.MainClasses.Issue;
import xyz.communeapp.commune.MainClasses.Resource;


public class ResourceTest {

    @Test
    public void simpleTest () {
        Resource resource = new Resource("fakeName", "fakeDetails");
        assertEquals(resource.getName(), "fakeName");
        assertEquals(resource.getDetails(), "fakeDetails");
    }
}
