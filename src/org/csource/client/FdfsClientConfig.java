package org.csource.client;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;

public class FdfsClientConfig implements Comparable<FdfsClientConfig>{
	private String confAbsolutePath;
	private String trackerGroupHost;
	private int trackerPort;
	
	private String groupName;
	
	public FdfsClientConfig(){}

	private void throwOnSetConfPath(){
		if(!(confAbsolutePath==null || confAbsolutePath.length()==0)){
			throw new IllegalArgumentException("setConfClassPath and setConfAbsolutePath must not be invoked both");
		}
	}
	
	public String getConfAbsolutePath() {
		return confAbsolutePath;
	}
	
	public void setConfAbsolutePath(String confAbsolutePath) {
		throwOnSetConfPath();
		this.confAbsolutePath = confAbsolutePath;
	}

	public String getConfClassPath(){
		return getConfAbsolutePath();
	}
	public void setConfClassPath(String confClassPath) throws URISyntaxException{
		throwOnSetConfPath();
		this.confAbsolutePath=new File(FdfsClientConfig.class.getClassLoader().getResource(confClassPath).toURI()).getAbsolutePath();
	}
	
	public String getTrackerGroupHost() {
		return trackerGroupHost;
	}
	
	public void setTrackerGroupHost(String trackerGroupHost) {
		this.trackerGroupHost = trackerGroupHost;
	}

	public int getTrackerPort() {
		return trackerPort;
	}

	public void setTrackerPort(int trackerPort) {
		this.trackerPort = trackerPort;
	}
	
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public void init() throws FileNotFoundException, IOException, MyException{
		ClientGlobal.init(confAbsolutePath);
	}
	
	public FdfsClient getFdfsClient() throws IOException{
		return getFdfsClient(groupName);
	}
	public FdfsClient getFdfsClient(String groupName) throws IOException{
		if(groupName==null){
			return new FdfsClient(trackerGroupHost,trackerPort);
		}else{
			return new FdfsClient(trackerGroupHost,trackerPort,groupName);
		}
	}
	@Override
	public int hashCode(){
		return groupName==null?0:groupName.hashCode();
	}
	@Override
	public boolean equals(Object o){
		return o instanceof FdfsClientConfig && ((groupName==null && ((FdfsClientConfig)o).groupName==null) || groupName.equals(((FdfsClientConfig)o).groupName));
	}

	@Override
	public int compareTo(FdfsClientConfig o) {
		return (groupName==null && o.groupName==null) ? 0 : (groupName.compareTo(o.groupName));
	}
}
