package lean.nio.channel.mutithread;

import com.google.common.base.MoreObjects;

/**
 * @Description TODO
 * @Author wanghao@cncloudsec.com
 * @Date 19-3-5 下午3:17
 */
public class FileInfo {

    /**
     * 分块索引从0开始
     */
    private int chunkIndex;

    /**
     * 分块大小
     */
    private long size;

    /**
     * 实际分片大小，最后一个分片大小不确定
     */
    private long realSize;

    /**
     * 原始文件名
     */
    private String originFileName;

    /**
     * 分片文件名
     */
    private String partFileName;

    /**
     * 文件分块总数
     */
    private int chunks;

    public int getChunkIndex() {
        return chunkIndex;
    }

    public void setChunkIndex(int chunkIndex) {
        this.chunkIndex = chunkIndex;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public int getChunks() {
        return chunks;
    }

    public void setChunks(int chunks) {
        this.chunks = chunks;
    }

    public String getOriginFileName() {
        return originFileName;
    }

    public void setOriginFileName(String originFileName) {
        this.originFileName = originFileName;
    }

    public String getPartFileName() {
        return partFileName;
    }

    public void setPartFileName(String partFileName) {
        this.partFileName = partFileName;
    }

    public long getRealSize() {
        return realSize;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("chunkIndex", chunkIndex)
                .add("size", size)
                .add("realSize", realSize)
                .add("originFileName", originFileName)
                .add("partFileName", partFileName)
                .add("chunks", chunks)
                .toString();
    }

    public void setRealSize(long realSize) {
        this.realSize = realSize;
    }
}
