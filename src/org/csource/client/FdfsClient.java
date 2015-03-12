package org.csource.client;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.DownloadCallback;
import org.csource.fastdfs.FileInfo;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerGroup;
import org.csource.fastdfs.TrackerServer;
import org.csource.fastdfs.UploadCallback;

public class FdfsClient {
	
	private TrackerGroup trackerGroup;
	private TrackerClient trackerClient;
	
	private TrackerServer trackerServer;
	private StorageServer storageServer;
	private StorageClient1 storageClient1;
	
	FdfsClient(String host,int port) throws IOException{
		this(host,port,null);
	}
	FdfsClient(String host,int port,String groupName) throws IOException{
		trackerGroup=new TrackerGroup(new InetSocketAddress[]{new InetSocketAddress(host,port)});
		trackerClient=new TrackerClient(trackerGroup);
		trackerServer=trackerClient.getConnection();
		if(trackerServer==null){
			throw new IllegalStateException("TrackerClient.getConnection returns null");
		}
		if(groupName==null || groupName.length()==0){
			storageServer=trackerClient.getStoreStorage(trackerServer);
		}else{
			storageServer=trackerClient.getStoreStorage(trackerServer,groupName);
		}
		if(storageServer==null){
			throw new IllegalStateException("TrackerClient.getStoreStorage returns null");
		}
		storageClient1=new StorageClient1(trackerServer,storageServer);
	}
	
	public void close() throws IOException{
		if(storageServer!=null){
			storageServer.close();
		}
		if(trackerServer!=null){
			trackerServer.close();
		}
	}
	
	private String getExtraName(String fileName){
		int extIdx=fileName.lastIndexOf('.');
		return extIdx<0?"":fileName.substring(extIdx+1);
	}
	
	public String upload(String filePath) throws IOException, MyException{
		return upload(filePath,(NameValuePair[])null);
	}
	public String upload(String filePath,String extName) throws IOException, MyException{
		return upload(filePath,extName,(NameValuePair[])null);
	}
	public String upload(File file) throws IOException, MyException{
		return upload(file,(NameValuePair[])null);
	}
	
	public String upload(String extraName,InputStream in,int length) throws IOException, MyException{
		return upload(extraName,in,length,(NameValuePair[])null);
	}
	public String upload(String extraName,byte[] bytes) throws IOException, MyException{
		return upload(extraName,(NameValuePair[])null);
	}
	public String upload(String extraName,byte[] bytes, int offset,int length) throws IOException, MyException{
		return upload(extraName,bytes,offset,length,(NameValuePair[])null);
	}
	
	public String upload(String filePath, NameValuePair[] metaList) throws IOException, MyException{
		return upload(filePath,getExtraName(filePath),metaList);
	}
	public String upload(String filePath,String extraName,NameValuePair[] metaList) throws IOException, MyException{
		return this.storageClient1.upload_file1(filePath,extraName,metaList);
	}
	public String upload(File file, NameValuePair[] metaList) throws IOException, MyException{
		return upload(getExtraName(file.getName()),new FileInputStream(file),(int)file.length(),metaList);
	}
	
	public String upload(String extraName,InputStream in,int length, NameValuePair[] metaList) throws IOException, MyException{
		byte[] buf=new byte[length];
		length=in.read(buf, 0, length);
		return upload(extraName,buf,metaList);
	}
	public String upload(String extraName,byte[] bytes, NameValuePair[] metaList) throws IOException, MyException{
		return this.storageClient1.upload_file1(bytes, extraName, metaList);
	}
	public String upload(String extraName,byte[] bytes, int offset,int length, NameValuePair[] metaList) throws IOException, MyException{
		byte[] buf=new byte[length];
		System.arraycopy(bytes, offset, buf, 0, length);
		return upload(extraName,buf,metaList);
	}
	
	
	public String upload(String masterFileId,String prefix,String filePath) throws IOException, MyException{
		return upload(masterFileId,prefix,filePath,(NameValuePair[])null);
	}
	public String upload(String masterFileId,String prefix,String filePath,String extName) throws IOException, MyException{
		return upload(masterFileId,prefix,filePath,extName,(NameValuePair[])null);
	}
	public String upload(String masterFileId,String prefix,File file) throws IOException, MyException{
		return upload(masterFileId,prefix,file,(NameValuePair[])null);
	}
	
	public String upload(String masterFileId,String prefix,String extraName,InputStream in,int length) throws IOException, MyException{
		return upload(masterFileId,prefix,extraName,in,length,(NameValuePair[])null);
	}
	public String upload(String masterFileId,String prefix,String extraName,byte[] bytes) throws IOException, MyException{
		return upload(masterFileId,prefix,extraName,(NameValuePair[])null);
	}
	public String upload(String masterFileId,String prefix,String extraName,byte[] bytes, int offset,int length) throws IOException, MyException{
		return upload(masterFileId,prefix,extraName,bytes,offset,length,(NameValuePair[])null);
	}
	
	public String upload(String masterFileId,String prefix,String filePath, NameValuePair[] metaList) throws IOException, MyException{
		return upload(masterFileId,prefix,filePath,getExtraName(filePath),metaList);
	}
	public String upload(String masterFileId,String prefix,String filePath,String extraName,NameValuePair[] metaList) throws IOException, MyException{
		return this.storageClient1.upload_file1(masterFileId,prefix,filePath,extraName,metaList);
	}
	public String upload(String masterFileId,String prefix,File file, NameValuePair[] metaList) throws IOException, MyException{
		return upload(masterFileId,prefix,getExtraName(file.getName()),new FileInputStream(file),(int)file.length(),metaList);
	}
	
	public String upload(String masterFileId,String prefix,String extraName,InputStream in,int length, NameValuePair[] metaList) throws IOException, MyException{
		byte[] buf=new byte[length];
		length=in.read(buf, 0, length);
		return upload(masterFileId,prefix,extraName,buf,metaList);
	}
	public String upload(String masterFileId,String prefix,String extraName,byte[] bytes, NameValuePair[] metaList) throws IOException, MyException{
		return this.storageClient1.upload_file1(masterFileId,prefix,bytes, extraName, metaList);
	}
	public String upload(String masterFileId,String prefix,String extraName,byte[] bytes, int offset,int length, NameValuePair[] metaList) throws IOException, MyException{
		return this.storageClient1.upload_file1(masterFileId,prefix,bytes,offset,length,extraName,metaList);
	}
	
	
	
	
	public String uploadAppend(String filePath) throws IOException, MyException{
		return uploadAppend(filePath,(NameValuePair[])null);
	}
	public String uploadAppend(String filePath,String extName) throws IOException, MyException{
		return uploadAppend(filePath,extName,(NameValuePair[])null);
	}
	public String uploadAppend(File file) throws IOException, MyException{
		return uploadAppend(file,(NameValuePair[])null);
	}
	
	public String uploadAppend(String extraName,InputStream in,int length) throws IOException, MyException{
		return uploadAppend(extraName,in,length,(NameValuePair[])null);
	}
	public String uploadAppend(String extraName,byte[] bytes) throws IOException, MyException{
		return uploadAppend(extraName,(NameValuePair[])null);
	}
	public String uploadAppend(String extraName,byte[] bytes, int offset,int length) throws IOException, MyException{
		return uploadAppend(extraName,bytes,offset,length,(NameValuePair[])null);
	}
	
	public String uploadAppend(String filePath, NameValuePair[] metaList) throws IOException, MyException{
		return uploadAppend(filePath,getExtraName(filePath),metaList);
	}
	public String uploadAppend(String filePath,String extraName,NameValuePair[] metaList) throws IOException, MyException{
		return this.storageClient1.upload_appender_file1(filePath,extraName,metaList);
	}
	public String uploadAppend(File file, NameValuePair[] metaList) throws IOException, MyException{
		return uploadAppend(getExtraName(file.getName()),new FileInputStream(file),(int)file.length(),metaList);
	}
	
	public String uploadAppend(String extraName,InputStream in,int length, NameValuePair[] metaList) throws IOException, MyException{
		byte[] buf=new byte[length];
		length=in.read(buf, 0, length);
		return uploadAppend(extraName,buf,metaList);
	}
	public String uploadAppend(String extraName,byte[] bytes, NameValuePair[] metaList) throws IOException, MyException{
		return this.storageClient1.upload_appender_file1(bytes, extraName, metaList);
	}
	public String uploadAppend(String extraName,byte[] bytes, int offset,int length, NameValuePair[] metaList) throws IOException, MyException{
		byte[] buf=new byte[length];
		System.arraycopy(bytes, offset, buf, 0, length);
		return uploadAppend(extraName,bytes,offset,length,metaList);
	}
	
	
	
	public int append(String appendFileId,String filePath) throws IOException, MyException{
		return this.storageClient1.append_file1(appendFileId,filePath);
	}
	public int append(String appendFileId,File file) throws IOException, MyException{
		return append(appendFileId,new FileInputStream(file),(int)file.length());
	}
	
	public int append(String appendFileId,InputStream in,int length) throws IOException, MyException{
		byte[] buf=new byte[length];
		length=in.read(buf, 0, length);
		return append(appendFileId,buf);
	}
	public int append(String appendFileId,byte[] bytes) throws IOException, MyException{
		return this.storageClient1.append_file1(appendFileId,bytes);
	}
	public int append(String appendFileId,byte[] bytes, int offset,int length) throws IOException, MyException{
		return this.storageClient1.append_file1(appendFileId, bytes, offset, length);
	}
	
	public int append(String appendFileId,long fileSize,UploadCallback callback) throws IOException, MyException{
		return this.storageClient1.append_file1(appendFileId, fileSize,callback);
	}
	
	public int setMetaData(String fileId,NameValuePair[] metaList,byte op) throws IOException, MyException{
		return this.storageClient1.set_metadata1(fileId, metaList, op);
	}
	
	public FileInfo queryFileInfo(String fileId) throws IOException, MyException{
		return this.storageClient1.query_file_info1(fileId);
	}
	
	
	public NameValuePair[] getFileMetaData(String fileId) throws IOException, MyException{
		return this.storageClient1.get_metadata1(fileId);
	}
	
	public FileInfo getFileInfo(String fileId) throws IOException, MyException{
		return this.storageClient1.get_file_info1(fileId);
	}
	
	
	public int modifyAppender(String appendFileId,long fileOffset,String filePath) throws IOException, MyException{
		return this.storageClient1.modify_file1(appendFileId,fileOffset,filePath);
	}
	public int modifyAppender(String appendFileId,long fileOffset,File file) throws IOException, MyException{
		return modifyAppender(appendFileId,fileOffset,new FileInputStream(file),(int)file.length());
	}
	public int modifyAppender(String appendFileId,long fileOffset,InputStream in,int length) throws IOException, MyException{
		byte[] buf=new byte[length];
		length=in.read(buf, 0, length);
		return modifyAppender(appendFileId,fileOffset,buf);
	}
	public int modifyAppender(String appendFileId,long fileOffset,byte[] bytes) throws IOException, MyException{
		return this.storageClient1.modify_file1(appendFileId,fileOffset,bytes);
	}
	public int modifyAppender(String appendFileId,long fileOffset,byte[] bytes, int offset,int length) throws IOException, MyException{
		return this.storageClient1.modify_file1(appendFileId,fileOffset, bytes, offset, length);
	}
	
	public int modifyAppender(String appendFileId,long fileOffset,long fileSize,UploadCallback callback) throws IOException, MyException{
		return this.storageClient1.modify_file1(appendFileId, fileOffset, fileSize,callback);
	}
	
	
	public byte[] download(String fileId) throws IOException, MyException{
		return this.storageClient1.download_file1(fileId);
	}
	public InputStream read(String fileId) throws IOException, MyException{
		return new ByteArrayInputStream(this.storageClient1.download_file1(fileId));
	}
	public void download(String fileId,OutputStream out) throws IOException, MyException{
		out.write(download(fileId));
	}
	public int download(String fileId,DownloadCallback callback) throws IOException, MyException{
		return this.storageClient1.download_file1(fileId, callback);
	}
	public int download(String fileId,String filePath) throws IOException, MyException{
		return this.storageClient1.download_file1(fileId, filePath);
	}
	public byte[] download(String fileId,long fileOffset,long downloadBytes) throws IOException, MyException{
		return this.storageClient1.download_file1(fileId, fileOffset, downloadBytes);
	}
	public InputStream read(String fileId,long fileOffset,long downloadBytes) throws IOException, MyException{
		return new ByteArrayInputStream(this.storageClient1.download_file1(fileId, fileOffset, downloadBytes));
	}
	public void download(String fileId,long fileOffset,long downloadBytes,OutputStream out) throws IOException, MyException{
		out.write(download(fileId,fileOffset,downloadBytes));
	}
	public int download(String fileId,long fileOffset,long downloadBytes,DownloadCallback callback) throws IOException, MyException{
		return this.storageClient1.download_file1(fileId,fileOffset,downloadBytes,callback);
	}
	public int download(String fileId,long fileOffset,long downloadBytes,String localFilePath) throws IOException, MyException{
		return this.storageClient1.download_file1(fileId,fileOffset,downloadBytes,localFilePath);
	}
	
	
	public int delete(String fileId) throws IOException, MyException{
		return this.storageClient1.delete_file1(fileId);
	}
}
