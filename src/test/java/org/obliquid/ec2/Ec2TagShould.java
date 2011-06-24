package org.obliquid.ec2;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.LinkedList;

import org.junit.Test;

import com.amazonaws.services.ec2.model.Tag;

public class Ec2TagShould {

    @Test
    public void findNameTagReturnsTheTagWhenItExists() {
        assertEquals("Erma", Ec2Tag.getValueOfNameTag(Ec2Tag.buildNameTagCollection("Erma")));
    }

    @Test
    public void findNameTagReturnsNullWhenItDoesntExit() {
        Collection<Tag> tags = new LinkedList<Tag>();
        tags.add(Ec2Tag.buildTag("another", "content"));
        assertNull(Ec2Tag.getValueOfNameTag(tags));
    }

    @Test
    public void buildNameTagCollection() {
        Collection<Tag> tagCollection = Ec2Tag.buildNameTagCollection("ServerName");
        assertEquals(1, tagCollection.size());
        Tag myTag = tagCollection.iterator().next();
        assertEquals("Name", myTag.getKey());
        assertEquals("ServerName", myTag.getValue());
    }

    @Test
    public void buildNameTag() {
        Tag myTag = Ec2Tag.buildTag("Name", "MyServer");
        assertEquals("Name", myTag.getKey());
        assertEquals("MyServer", myTag.getValue());
    }

}
