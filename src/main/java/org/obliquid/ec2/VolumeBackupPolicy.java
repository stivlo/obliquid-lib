package org.obliquid.ec2;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements a backup policy for Volumes.
 * 
 * @author stivlo
 */
public class VolumeBackupPolicy {

        /**
         * A message to be returned.
         */
        private String message = "";

        /** Default value. */
        private static final int THRESHOLD_GB = 140;

        /** Default value. */
        private static final int EVERY_HOW_MANY_DAYS_SMALL_VOL = 7;

        /** Default value. */
        private static final int EVERY_HOW_MANY_DAYS_BIG_VOL = 14;

        /** Default value. */
        private static final int HOW_MANY_DAYS_BEFORE_DEL = 15;

        /**
         * A volume is considered small if is less or equal the thresholdGb.
         */
        private int thresholdGb = THRESHOLD_GB;

        /**
         * Every how many days we should backup a small volume.
         */
        private int everyHowManyDaysForSmallVolumes = EVERY_HOW_MANY_DAYS_SMALL_VOL;

        /**
         * Every how many days we should backup a big volume.
         */
        private int everyHowManyDaysForBigVolumes = EVERY_HOW_MANY_DAYS_BIG_VOL;

        /**
         * How many copies we should keep for small volumes.
         */
        private int howManyCopiesForSmallVolumes = 2;

        /**
         * How many copies we should keep for big volumes.
         */
        private int howManyCopiesForBigVolumes = 1;

        /**
         * How many days we should keep a volume before deleting it.
         */
        private int howManyDaysBeforeDeleting = HOW_MANY_DAYS_BEFORE_DEL;

        /**
         * Build a new VolumeBackupPolicy with default values.
         */
        public VolumeBackupPolicy() {
                //nothing to do
        }

        /**
         * Build a new VolumeBackupPolicy with parameter customisation.
         * 
         * @param theThresholdGb
         *                A volume is considered small if is less or equal the
         *                thresholdGb (in Gb)
         * @param everyHowManyDaysForSmallVol
         *                Every how many days we should backup a small volume.
         * @param everyHowManyDaysForBigVol
         *                Every how many days we should backup a big volume.
         * @param howManyCopiesForSmallVol
         *                How many copies we should keep for small volumes.
         * @param howManyCopiesForBigVol
         *                How many copies we should keep for a big volume.
         * @param howManyDaysBeforeDel
         *                How many days we should keep a volume before deleting
         *                it.
         */
        public VolumeBackupPolicy(final int theThresholdGb, final int everyHowManyDaysForSmallVol,
                        final int everyHowManyDaysForBigVol, final int howManyCopiesForSmallVol,
                        final int howManyCopiesForBigVol, final int howManyDaysBeforeDel) {
                thresholdGb = theThresholdGb;
                everyHowManyDaysForSmallVolumes = everyHowManyDaysForSmallVol;
                everyHowManyDaysForBigVolumes = everyHowManyDaysForBigVol;
                howManyCopiesForSmallVolumes = howManyCopiesForSmallVol;
                howManyCopiesForBigVolumes = howManyCopiesForBigVol;
                howManyDaysBeforeDeleting = howManyDaysBeforeDel;
        }

        /**
         * Return the message of the last operation.
         * 
         * @return a message, if it's empty it will return the empty String,
         *         it'll never return null.
         */
        public final String getMessage() {
                return message;
        }

        /**
         * From a list of snapshots of a volume, tells if the snapshots need a
         * backup or not. The current policy is that if the last snapshot is
         * older than 7 days (8 or more), we need a backup. If the snapshot is
         * greater than 140 Gb, we need a backup only every 14 days.
         * 
         * @param snapShotsOfAVolume
         *                the snapshots of a volume (it's responsibility of the
         *                caller to provide those)
         * @return true
         */
        public final boolean needBackup(final List<MySnapshot> snapShotsOfAVolume) {
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
         * Will delete snapshots older than 15 days, if there are at least two
         * snapshots in the list. Will set the message that can be read with
         * getMessage() to have more insight of what has happened.
         * 
         * @param snapshotsOfaVolume
         *                the list of snapshots of a volume
         * @return a list of snapshots to be deleted. if none, it will be an
         *         empty list
         */
        public final List<MySnapshot> snapshotsToBeDeleted(final List<MySnapshot> snapshotsOfaVolume) {
                List<MySnapshot> toBeDeleted = new ArrayList<MySnapshot>();
                message = "";
                if (snapshotsOfaVolume.size() == 0) {
                        return toBeDeleted; //there are no Snapshots at all, so nothing to be deleted
                }
                int volumeSize = snapshotsOfaVolume.get(0).getSizeInGb();
                if (volumeSize <= thresholdGb && snapshotsOfaVolume.size() <= howManyCopiesForSmallVolumes) {
                        message = "Skipped deletion because I don't have more than "
                                        + howManyCopiesForSmallVolumes + " snapshots";
                        return toBeDeleted; //empty list
                }
                if (volumeSize > thresholdGb && snapshotsOfaVolume.size() <= howManyCopiesForBigVolumes) {
                        message = "Skipped deletion because I don't have more than "
                                        + howManyCopiesForBigVolumes + " snapshots";
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
