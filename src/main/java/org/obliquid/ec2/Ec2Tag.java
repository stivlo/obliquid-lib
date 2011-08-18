package org.obliquid.ec2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.amazonaws.services.ec2.model.Tag;

/**
 * Helps to deal with EC2 Tags, especially with Name tag.
 * 
 * @author stivlo
 */
public final class Ec2Tag {

        /**
         * Utility class.
         */
        private Ec2Tag() {
        }

        /**
         * Find the "Name" tag in the list of tags and return its value.
         * 
         * @param tags
         *                the tag Collection
         * @return the value associated with the Tag or null if the "Name" tag
         *         wasn't found.
         */
        public static String getValueOfNameTag(final Collection<Tag> tags) {
                for (Tag tag : tags) {
                        if (tag.getKey().equals("Name")) {
                                return tag.getValue();
                        }
                }
                return null;
        }

        /**
         * Create a Tag collection with only the Tag Name set as requested.
         * 
         * @param value
         *                value of the Tag Name
         * @return a newly created Tag Collection
         */
        public static Collection<Tag> buildNameTagCollection(final String value) {
                List<Tag> tags = new ArrayList<Tag>();
                tags.add(buildTag("Name", value));
                return tags;
        }

        /**
         * Create an EC2 Tag.
         * 
         * @param key
         *                the key to set
         * @param value
         *                the value to set
         * @return the name tag
         */
        public static Tag buildTag(final String key, final String value) {
                Tag nameTag = new Tag();
                nameTag.setKey(key);
                nameTag.setValue(value);
                return nameTag;
        }

}
