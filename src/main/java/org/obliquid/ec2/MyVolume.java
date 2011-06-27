package org.obliquid.ec2;

import java.util.Arrays;
import java.util.List;

import org.obliquid.ec2.Ec2Tag;

import org.obliquid.util.ClientFactory;

import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.model.DescribeVolumesRequest;
import com.amazonaws.services.ec2.model.DescribeVolumesResult;
import com.amazonaws.services.ec2.model.InstanceBlockDeviceMapping;
import com.amazonaws.services.ec2.model.Tag;
import com.amazonaws.services.ec2.model.Volume;

/**
 * Wrapper around InstanceBlockDeviceMapping Amazon class which represents an EBS Volume. After
 * creation the blockDeviceMapping can't be changed with another one, create a new object instead.
 * 
 * @author stivlo
 */
public class MyVolume {

    private InstanceBlockDeviceMapping blockDeviceMapping;
    private AmazonEC2 ec2 = null;
    private Volume volume = null;

    /**
     * 
     * @param blockDeviceMapping
     */
    public MyVolume(InstanceBlockDeviceMapping blockDeviceMapping) {
        this.blockDeviceMapping = blockDeviceMapping;
        ec2 = ClientFactory.createElasticComputeCloudClient();
    }

    /**
     * Return the Volume of this EBS. Subsequent calls will use a cached value within the object.
     * 
     * @return the Volume.
     */
    private Volume getVolume() {
        if (volume != null) {
            return volume;
        }
        DescribeVolumesRequest req = new DescribeVolumesRequest();
        req.setVolumeIds(Arrays.<String> asList(getId()));
        DescribeVolumesResult res = ec2.describeVolumes(req);
        volume = res.getVolumes().get(0);
        return volume;
    }

    /**
     * Return the Name tag, if exists, otherwise null.
     * 
     * @return the Name tag or null.
     */
    public String getName() {
        List<Tag> tags = getVolume().getTags();
        return Ec2Tag.getValueOfNameTag(tags);
    }

    /**
     * The device name (eg. /dev/sdh) at which the block device is exposed to the instance.
     * 
     * @return the device name as a String
     */
    public String getDeviceName() {
        return blockDeviceMapping.getDeviceName();
    }

    /**
     * The ID of the EBS Volume.
     * 
     * @return ID of the EBS Volume.
     */
    public String getId() {
        return blockDeviceMapping.getEbs().getVolumeId();
    }

    /**
     * The size of this volume in Gb
     * 
     * @return the size in Gb
     */
    public int getSizeInGb() {
        return getVolume().getSize();
    }

    /**
     * Build a String with the Name, the device name and the size in Gb
     * 
     * @return a String with some info about this volume
     */
    @Override
    public String toString() {
        return getName() + ' ' + getDeviceName() + ' ' + getSizeInGb() + "Gb";
    }

}
