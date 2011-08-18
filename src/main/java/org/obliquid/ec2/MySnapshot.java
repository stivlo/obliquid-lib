package org.obliquid.ec2;

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.ReadableDateTime;

import com.amazonaws.services.ec2.model.Snapshot;
import com.amazonaws.services.ec2.model.Tag;

/**
 * Wrapper around Amazon Snapshot, which represents a snapshot of an Amazon EC2
 * EBS Volume.
 * 
 * @author stivlo
 */
public class MySnapshot {

        /** An Amazon Snapshot. */
        private Snapshot snapshot;

        /**
         * Whether the snapshot was already listed as snapshot of a volume when
         * listing attached volumes, so it's possible to recognise dangling
         * snapshots.
         */
        private boolean listedForaVolume = false;

        /**
         * Constructor with the Amazon Snapshot to wrap.
         * 
         * @param snapshotIn
         *                the Amazon Snapshot to be wrapped
         */
        public MySnapshot(final Snapshot snapshotIn) {
                snapshot = snapshotIn;
        }

        /**
         * The ID of the volume from which this snapshot was created.
         * 
         * @return The ID of the volume from which this snapshot was created.
         */
        public final String getVolumeId() {
                return snapshot.getVolumeId();
        }

        /**
         * Return the Name tag, if exists, otherwise null.
         * 
         * @return the Name tag or null.
         */
        public final String getName() {
                List<Tag> tags = snapshot.getTags();
                return Ec2Tag.getValueOfNameTag(tags);
        }

        /**
         * The size of the snapshot volume in Gb.
         * 
         * @return the size in Gb
         */
        public final int getSizeInGb() {
                return snapshot.getVolumeSize();
        }

        /**
         * Return when the snapshot was initiated.
         * 
         * @return a Joda Time ReadableDateTime with the instant of snapshot
         *         initiation.
         */
        public final ReadableDateTime getStartTime() {
                return new DateTime(snapshot.getStartTime());
        }

        /**
         * How old is the snapshot in days.
         * 
         * @return number of days since this snapshot was taken.
         */
        public final int howManyDaysAgo() {
                return Days.daysBetween(getStartTime(), new DateTime()).getDays();
        }

        @Override
        public final String toString() {
                return getName() + ' ' + getSizeInGb() + "Gb " + howManyDaysAgo() + " days ago ("
                                + getStartTime().toString("yyyy-MM-dd") + ") " + getProgress();
        }

        /**
         * The progress of the snapshot in percentage.
         * 
         * @return The progress of the snapshot in percentage.
         */
        public final String getProgress() {
                return snapshot.getProgress();
        }

        /**
         * Set the snapshot as listed for a volume, so if we scan all volumes we
         * can see dangling snapshots.
         * 
         * @param listedForaVolumeIn
         *                set it to true if it was listed.
         */
        public final void setListedForaVolume(final boolean listedForaVolumeIn) {
                listedForaVolume = listedForaVolumeIn;
        }

        /**
         * Return true if it was listed for a volume, false otherwise.
         * 
         * @return true if it was listed.
         */
        public final boolean isListedForaVolume() {
                return listedForaVolume;
        }

        /**
         * Return the snapshot Id.
         * 
         * @return snapshot id
         */
        public final String getSnapshotId() {
                return snapshot.getSnapshotId();
        }

}
