package org.obliquid.ec2;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements a backup policy for Volumes.
 * 
 * @author stivlo
 */
public class VolumeBackupPolicy {

    private String message = "";

    /**
     * A volume is considered small if is less or equal the thresholdGb
     */
    private int thresholdGb = 140;
    private int everyHowManyDaysForSmallVolumes = 7;
    private int everyHowManyDaysForBigVolumes = 14;
    private int howManyCopiesForSmallVolumes = 2;
    private int howManyCopiesForBigVolumes = 1;
    private int howManyDaysBeforeDeleting = 15;

    /**
     * Build a new VolumeBackupPolicy with default values
     */
    public VolumeBackupPolicy() {
        //nothing to do
    }

    /**
     * Build a new VolumeBackupPolicy with parameter customization
     */
    public VolumeBackupPolicy(int thresholdGb, int everyHowManyDaysForSmallVolumes,
            int everyHowManyDaysForBigVolumes, int howManyCopiesForSmallVolumes,
            int howManyCopiesForBigVolumes, int howManyDaysBeforeDeleting) {
        this.thresholdGb = thresholdGb;
        this.everyHowManyDaysForSmallVolumes = everyHowManyDaysForSmallVolumes;
        this.everyHowManyDaysForBigVolumes = everyHowManyDaysForBigVolumes;
        this.howManyCopiesForSmallVolumes = howManyCopiesForSmallVolumes;
        this.howManyCopiesForBigVolumes = howManyCopiesForBigVolumes;
        this.howManyDaysBeforeDeleting = howManyDaysBeforeDeleting;
    }

    /**
     * Return the message of the last operation.
     * 
     * @return a message, if it's empty it will return the empty String, it'll never return null.
     */
    public String getMessage() {
        return message;
    }

    /**
     * From a list of snapshots of a volume, tells if the snapshots need a backup or not. The
     * current policy is that if the last snapshot is older than 7 days (8 or more), we need a
     * backup. If the snapshot is greater than 140 Gb, we need a backup only every 14 days.
     * 
     * @param snapShotsOfAVolume
     *            the snapshots of a volume (it's responsibility of the caller to provide those)
     * @return true
     */
    public boolean needBackup(List<MySnapshot> snapShotsOfAVolume) {
        int size, daysAgo;
        message = "";
        for (MySnapshot snapshot : snapShotsOfAVolume) {
            size = snapshot.getSizeInGb();
            daysAgo = snapshot.howManyDaysAgo();
            if (size <= thresholdGb && daysAgo <= everyHowManyDaysForSmallVolumes) {
                return false;
            } else if (size > thresholdGb && daysAgo <= everyHowManyDaysForBigVolumes) {
                return false;
            }
        }
        return true;
    }

    /**
     * Will delete snapshots older than 15 days, if there are at least two snapshots in the list.
     * Will set the message that can be read with getMessage() to have more insight of what has
     * happened.
     * 
     * @param snapshotsOfaVolume
     *            the list of snapshots of a volume
     * @return a list of snapshots to be deleted. if none, it will be an empty list
     */
    public List<MySnapshot> snapshotsToBeDeleted(List<MySnapshot> snapshotsOfaVolume) {
        List<MySnapshot> toBeDeleted = new ArrayList<MySnapshot>();
        message = "";
        if (snapshotsOfaVolume.size() == 0) {
            return toBeDeleted; //there are no Snapshots at all, so nothing to be deleted
        }
        int volumeSize = snapshotsOfaVolume.get(0).getSizeInGb();
        if (volumeSize <= thresholdGb && snapshotsOfaVolume.size() <= howManyCopiesForSmallVolumes) {
            message = "Skipped deletion of " + volumeSize + " volume because I don't have more than "
                    + howManyCopiesForSmallVolumes + " snapshots";
            return toBeDeleted; //empty list
        }
        if (volumeSize > thresholdGb && snapshotsOfaVolume.size() <= howManyCopiesForBigVolumes) {
            message = "Skipped deletion of " + volumeSize + " volume because I don't have more than "
                    + howManyCopiesForSmallVolumes + " snapshots";
            return toBeDeleted; //empty list
        }
        for (MySnapshot snapshot : snapshotsOfaVolume) {
            if (snapshot.howManyDaysAgo() > howManyDaysBeforeDeleting) {
                toBeDeleted.add(snapshot);
            }
        }
        return toBeDeleted;
    }

}
