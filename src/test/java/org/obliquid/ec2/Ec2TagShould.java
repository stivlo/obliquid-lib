package org.obliquid.ec2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Collection;
import java.util.LinkedList;

import org.junit.Test;

import com.amazonaws.services.ec2.model.Tag;

/** Class under test: Ec2Tag. */
public class Ec2TagShould {

        /**
         * Build a name tag collection and then find the value of name tag.
         */
        @Test
        public final void findNameTagReturnsTheTagWhenItExists() {
                assertEquals("Erma", Ec2Tag.getValueOfNameTag(Ec2Tag.buildNameTagCollection("Erma")));
        }

        /**
         * Find name should return null when the name tag doesn't exists.
         */
        @Test
        public final void findNameTagReturnsNullWhenItDoesntExist() {
                Collection<Tag> tags = new LinkedList<Tag>();
                tags.add(Ec2Tag.buildTag("another", "content"));
                assertNull(Ec2Tag.getValueOfNameTag(tags));
        }

        /**
         * Create a tag collection with one name and test it.
         */
        @Test
        public final void buildNameTagCollection() {
                Collection<Tag> tagCollection = Ec2Tag.buildNameTagCollection("ServerName");
                assertEquals(1, tagCollection.size());
                Tag myTag = tagCollection.iterator().next();
                assertEquals("Name", myTag.getKey());
                assertEquals("ServerName", myTag.getValue());
        }

        /**
         * Build a name tag and retrieve it.
         */
        @Test
        public final void buildNameTag() {
                Tag myTag = Ec2Tag.buildTag("Name", "MyServer");
                assertEquals("Name", myTag.getKey());
                assertEquals("MyServer", myTag.getValue());
        }

}
