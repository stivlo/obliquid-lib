package org.obliquid.ec2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.obliquid.util.ClientFactory;
import org.obliquid.util.StopWatch;

import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.model.CreateSnapshotRequest;
import com.amazonaws.services.ec2.model.CreateSnapshotResult;
import com.amazonaws.services.ec2.model.CreateTagsRequest;
import com.amazonaws.services.ec2.model.DeleteSnapshotRequest;
import com.amazonaws.services.ec2.model.DescribeSnapshotsRequest;
import com.amazonaws.services.ec2.model.DescribeSnapshotsResult;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.InstanceBlockDeviceMapping;
import com.amazonaws.services.ec2.model.Reservation;
import com.amazonaws.services.ec2.model.Snapshot;

/**
 * List instances, volumes, create / delete snapshots and set the Name tag of a
 * snapshot.
 * 
 * @author stivlo
 */
public class SnapshotManager {

        /**
         * Amazon EC2 client.
         */
        private AmazonEC2 ec2 = null;

        /**
         * A list of MySnapshot.
         */
        private List<MySnapshot> mySnapshots = null;

        /** How many seconds to wait before setting the snapshot name. */
        private static final int WAIT_NAME_SEC = 5;

        /**
         * Default constructor.
         */
        public SnapshotManager() {
                ec2 = ClientFactory.createElasticComputeCloudClient();
        }

        /**
         * Describe all instances by returning a List of Instance.
         * 
         * @return a list of all Instances
         */
        public final List<Instance> listInstances() {
                List<Instance> instanceList = new ArrayList<Instance>();
                List<Reservation> reservationList = ec2.describeInstances().getReservations();
                for (Reservation aReservation : reservationList) {
                        instanceList.addAll(aReservation.getInstances());
                }
                return instanceList;
        }

        /**
         * Return a list of the EBS volumes of an instance, of type MyVolume
         * which has a simplified interface.
         * 
         * @param instance
         *                an Amazon EC2 instance
         * @return a list of EBS volumes of an instance
         */
        public final List<MyVolume> listVolumesOfAnInstance(final Instance instance) {
                List<InstanceBlockDeviceMapping> mappings = instance.getBlockDeviceMappings();
                List<MyVolume> myVolumes = new ArrayList<MyVolume>();
                for (InstanceBlockDeviceMapping mapping : mappings) {
                        myVolumes.add(new MyVolume(mapping));
                }
                return myVolumes;
        }

        /**
         * Return a list of my Snapshots, of type MySnapshot which has a
         * simplified interface. It will cache the results inside this object,
         * so if called multiple times it will return the same info.
         * 
         * @return a list of my Snapshots
         */
        public final List<MySnapshot> listMySnapshots() {
                if (this.mySnapshots != null) {
                        return this.mySnapshots;
                }
                DescribeSnapshotsRequest req = new DescribeSnapshotsRequest();
                req.setOwnerIds(Arrays.<String> asList("self"));
                DescribeSnapshotsResult res = ec2.describeSnapshots(req);
                List<Snapshot> ec2Snapshots = res.getSnapshots();
                mySnapshots = new ArrayList<MySnapshot>();
                for (Snapshot ec2Snapshot : ec2Snapshots) {
                        mySnapshots.add(new MySnapshot(ec2Snapshot));
                }
                return mySnapshots;
        }

        /**
         * Return a list of snapshots of a volume (looking only in my snapshot
         * list). Each Snapshot is of type MySnapshot, which has a simplified
         * interface.
         * 
         * @param volume
         *                an instance of MyVolume
         * @return a list of the snapshots of a volume.
         */
        public final List<MySnapshot> listSnapshotsOfaVolume(final MyVolume volume) {
                List<MySnapshot> snapshots = listMySnapshots();
                List<MySnapshot> snapshotsOfTheVolume = new ArrayList<MySnapshot>();
                for (MySnapshot snapshot : snapshots) {
                        if (snapshot.getVolumeId().equals(volume.getId())) {
                                snapshot.setListedForaVolume(true);
                                snapshotsOfTheVolume.add(snapshot);
                        }
                }
                return snapshotsOfTheVolume;
        }

        /**
         * List snapshots that weren't listed from a volume. Call this method
         * after calling listSnapshotsOfaVolume() for all the volumes to find
         * dangling snapshots.
         * 
         * @return a list of snapshots which weren't listed from a volume.
         */
        public final List<MySnapshot> listSnapshotsWhichWerentListed() {
                List<MySnapshot> snapshots = listMySnapshots();
                List<MySnapshot> danglingSnapshots = new ArrayList<MySnapshot>();
                for (MySnapshot snapshot : snapshots) {
                        if (!snapshot.isListedForaVolume()) {
                                danglingSnapshots.add(snapshot);
                        }
                }
                return danglingSnapshots;
        }

        /**
         * Build a compact string representation of an Instance. Fields are
         * separated by space. The first field is the instance-id, the second
         * the IP address, the third is the instance short name. Example output:
         * 
         * <pre>
         * i-a6da4ece 174.129.41.125 web01
         * </pre>
         * 
         * @param instance
         *                Represents an Amazon EC2 instance
         * @return a String representation of the instance
         */
        public final String toString(final Instance instance) {
                String ipAddressString = instance.getPublicIpAddress();
                StringBuilder sb = new StringBuilder(ipAddressString);
                //TODO reverse DNS lookup to find the server name
                return sb.toString();
        }

        /**
         * Create a new snapshot of the volume.
         * 
         * @param volume
         *                the volume to be snapshot
         */
        public final void createSnapshot(final MyVolume volume) {
                CreateSnapshotRequest createSnapshotRequest = new CreateSnapshotRequest();
                createSnapshotRequest.setVolumeId(volume.getId());
                CreateSnapshotResult res = ec2.createSnapshot(createSnapshotRequest);
                StopWatch.sleepSeconds(WAIT_NAME_SEC);
                setNameTagOfSnapshot(res.getSnapshot(), volume.getName());
                System.out.println("    Setting Name=" + volume.getName());
        }

        /**
         * Set the Name tag of a snapshot.
         * 
         * @param snapshot
         *                the snapshot for which we have to set the name
         * @param value
         *                the value to give to Name tag
         */
        public final void setNameTagOfSnapshot(final Snapshot snapshot, final String value) {
                CreateTagsRequest createTagsRequest = new CreateTagsRequest();
                createTagsRequest.setResources(Arrays.<String> asList(snapshot.getSnapshotId()));
                createTagsRequest.setTags(Ec2Tag.buildNameTagCollection(value));
                ec2.createTags(createTagsRequest);
        }

        /**
         * Delete a snapshot.
         * 
         * @param snapshot
         *                the snapshot to delete
         */
        public final void deleteSnapshot(final MySnapshot snapshot) {
                DeleteSnapshotRequest deleteSnapshotRequest = new DeleteSnapshotRequest();
                deleteSnapshotRequest.setSnapshotId(snapshot.getSnapshotId());
                ec2.deleteSnapshot(deleteSnapshotRequest);
        }

}
