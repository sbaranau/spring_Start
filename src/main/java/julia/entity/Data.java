package julia.entity;

/**
 * @author Ivan Shyrma
 */
public class Data extends NamedEntity {

    private static final long serialVersionUID = 1L;

    private Long sensorId;

    private byte[] data;

    private String mimeType;

    /**
     * @return the data
     */
    public byte[] getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(byte[] data) {
        this.data = data;
    }

    /**
     * @return the mimeType
     */
    public String getMimeType() {
        return mimeType;
    }

    /**
     * @param mimeType the mimeType to set
     */
    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    /**
     * @return the sensorId
     */
    public Long getSensorId() {
        return sensorId;
    }

    /**
     * @param sensorId the sensorId to set
     */
    public void setSensorId(Long sensorId) {
        this.sensorId = sensorId;
    }

}
