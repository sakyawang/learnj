package lean.jdk8.stream.domain;

import com.google.common.base.MoreObjects;

public class SecurityServicePacket {

    private long id;

    private int type;

    private String policyState;

    public SecurityServicePacket(long id, int type, String policyState) {
        this.id = id;
        this.type = type;
        this.policyState = policyState;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPolicyState() {
        return policyState;
    }

    public void setPolicyState(String policyState) {
        this.policyState = policyState;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("type", type)
                .add("policyState", policyState)
                .toString();
    }
}
