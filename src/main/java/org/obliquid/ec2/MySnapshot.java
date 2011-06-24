package org.obliquid.ec2;

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.ReadableDateTime;

import com.amazonaws.services.ec2.model.Snapshot;
import com.amazonaws.services.ec2.model.Tag;

/**
 * Wrapper around Amazon Snapshot, which represents a snapshot of an Amazon EC2 EBS Volume.
 * 
 * @author stivlo
 */
public class MySnapshot {

    private Snapshot snapshot;
    private boolean listedForaVolume = false;

    public MySnapshot(Snapshot snapshot) {
        this.snapshot = snapshot;
    }

    /**
     * The ID of the volume from which this snapshot was created.
     * 
     * @return The ID of the volume from which this snapshot was created.
     */
    public String getVolumeId() {
        return snapshot.getVolumeId();
    }

    /**
     * Return the Name tag, if exists, otherwise null.
     * 
     * @return the Name tag or null.
     */
    public String getName() {
        List<Tag> tags = snapshot.getTags();
        return Ec2Tag.getValueOfNameTag(tags);
    }

    /**
     * The size of the snapshot volume in Gb.
     * 
     * @return the size in Gb
     */
    public int getSizeInGb() {
        return snapshot.getVolumeSize();
    }

    /**
     * Return when the snapshot was initiated.
     * 
     * @return a Joda Time ReadableDateTime with the instant of snapshot initiation.
     */
    public ReadableDateTime getStartTime() {
        return new DateTime(snapshot.getStartTime());
    }

    /**
     * How old is the snapshot in days.
     * 
     * @return number of days since this snapshot was taken.
     */
    public int howManyDaysAgo() {
        return Days.daysBetween(getStartTime(), new DateTime()).getDays();
    }

    @Override
    public String toString() {
        return getName() + ' ' + getSizeInGb() + "Gb " + howManyDaysAgo() + " days ago ("
                + getStartTime().toString("yyyy-MM-dd") + ") " + getProgress();
    }

    /**
     * The progress of the snapshot in percentage.
     * 
     * @return The progress of the snapshot in percentage.
     */
    public String getProgress() {
        return snapshot.getProgress();
    }

    /**
     * Set the snapshot as listed for a volume, so if we scan all volumes we can see dangling
     * snapshots.
     * 
     * @param listedForaVolume
     *            set it to true if it was listed.
     */
    public void setListedForaVolume(boolean listedForaVolume) {
        this.listedForaVolume = listedForaVolume;
    }

    /**
     * Return true if it was listed for a volume, false otherwise.
     * 
     * @return true if it was listed.
     */
    public boolean isListedForaVolume() {
        return this.listedForaVolume;
    }

    /**
     * Return the snapshot Id
     * 
     * @return snapshot id
     */
    public String getSnapshotId() {
        return snapshot.getSnapshotId();
    }

}
