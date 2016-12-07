package xyz.communeapp.commune;

import org.junit.Test;

import static org.junit.Assert.*;

import xyz.communeapp.commune.MainClasses.Issue;

public class IssueTest {

    @Test
    public static void constructorTest () {
        Issue issue = new Issue();
        assertEquals(issue.getIssueID(), null);
        assertEquals(issue.getName(), null);
        assertEquals(issue.getDueDate(), null);
        assertEquals(issue.getIssueRef(), null);
        assertEquals(issue.getDescription(), null);
        assertEquals(issue.getAssigedToName(), null);
        assertEquals(issue.getAssigedToUID(), null);
        assertEquals(issue.getGroupID(), null);
        assertEquals(issue.getStatus(), null);
    }

    @Test
    public static void populateObjectTest () {
        Issue issue = new Issue();

        issue.setIssueID("fakeID");
        issue.setName("fakeName");
        issue.setDueDate("01/01/1970");
        issue.setDescription("fakeDescription");
        issue.setAssignedToName("fakeName");
        issue.setAssignedToUID("fakeUID");
        issue.setGroupID("fakeGID");
        issue.setStatus("fakeStatus");

        assertEquals(issue.getIssueID(), "fakeID");
        assertEquals(issue.getName(), "fakeName");
        assertEquals(issue.getDueDate(), "01/01/1970");
        assertEquals(issue.getDescription(), "fakeDescription");
        assertEquals(issue.getAssigedToName(), "fakeName");
        assertEquals(issue.getAssigedToUID(), "fakeUID");
        assertEquals(issue.getGroupID(), "fakeGID");
        assertEquals(issue.getStatus(), "fakeStatus");
    }
}
