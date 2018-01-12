package model;

public class TablespaceBean {
	private String tableName;
	private long totalSize;
	private long usedSize;
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public long getTotalSize() {
		return totalSize;
	}
	public void setTotalSize(long l) {
		this.totalSize = l;
	}
	public long getUsedSize() {
		return usedSize;
	}
	public void setUsedSize(long usedSize) {
		this.usedSize = usedSize;
	}
	
}
