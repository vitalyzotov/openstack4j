package org.openstack4j.model.storage.block;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.openstack4j.common.Buildable;
import org.openstack4j.model.ModelEntity;
import org.openstack4j.model.storage.block.builder.VolumeBuilder;
import org.slf4j.LoggerFactory;

/**
 * An OpenStack Volume
 *
 * @author Jeremy Unruh
 */
public interface Volume extends ModelEntity, Buildable<VolumeBuilder> {

    /**
     * @return the identifier for the volume
     */
    String getId();

    /**
     * @return the name of the volume
     */
    String getName();

    /**
     * @return the display name of the volume
     */
    @Deprecated
    String getDisplayName();

    /**
     * @return the description of the volume
     */
    String getDescription();

    /**
     * @return the display description of the volume
     */
    @Deprecated
    String getDisplayDescription();

    /**
     * @return the status of the volume
     */
    Status getStatus();

    /**
     * @return the size in GB of the volume
     */
    int getSize();

    /**
     * @return the zone of availability to use
     */
    String getZone();

    /**
     * @return the created date of the volume
     */
    Date getCreated();

    /**
     * @return the type of volume
     */
    String getVolumeType();

    /**
     * @return the snapshot identifier
     */
    String getSnapshotId();

    /**
     * @return the image reference identifier (if an image was associated) otherwise null
     */
    String getImageRef();

    /**
     * @return To enable this volume to attach
     */
    Boolean multiattach();

    /**
     * @return ID of source volume to clone from
     */
    String getSourceVolid();

    /**
     * @return extended meta data information. key value pair of String key, String value
     */
    Map<String, String> getMetaData();

    /**
     * @return volume attachment data information.
     */
    List<? extends VolumeAttachment> getAttachments();

    /**
     * @return the status of volume migrate status, default null
     */
    MigrationStatus getMigrateStatus();

    /**
     * @return the tenant id
     */
    String getTenantId();

    /**
     * @return whether the volume is bootable
     */
    boolean bootable();

    /**
     * @return whether this volume is encrypted.
     */
    boolean encrypted();

    /**
     * @return current back-end of the volume.
     */
    String host();

    /**
     * The current Volume Status
     */
    enum Status {
        AVAILABLE, ATTACHING, BACKING_UP, CREATING, DELETING, DOWNLOADING, UPLOADING, ERROR, ERROR_DELETING, ERROR_RESTORING, IN_USE, RESTORING_BACKUP, DETACHING, UNRECOGNIZED;

        @JsonCreator
        public static Status fromValue(String status) {
            try {
                return valueOf(Objects.requireNonNull(status, "migrationStatus").toUpperCase().replace('-', '_'));
            } catch (IllegalArgumentException e) {
                return UNRECOGNIZED;
            }
        }

        @JsonValue
        public String value() {
            return name().toLowerCase().replace('_', '-');
        }

        @Override
        public String toString() {
            return value();
        }
    }

    enum MigrationStatus {
        MIGRATING, ERROR, SUCCESS, COMPLETING, NONE, STARTING;

        @JsonCreator
        public static MigrationStatus fromValue(String migrationStatus) {
            try {
                return valueOf(Objects.requireNonNull(migrationStatus, "migrationStatus").toUpperCase().replace('-', '_'));
            } catch (IllegalArgumentException e) {
                LoggerFactory.getLogger(MigrationStatus.class).error(e.getMessage(), e);
            }

            return NONE;
        }

        @JsonValue
        public String value() {
            return name().toLowerCase().replace('_', '-');
        }

        @Override
        public String toString() {
            return value();
        }
    }
}
