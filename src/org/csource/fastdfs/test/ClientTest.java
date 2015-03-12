package org.csource.fastdfs.test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import org.csource.client.FdfsClient;
import org.csource.client.FdfsClientConfig;
import org.csource.common.MyException;

public class ClientTest {
	public static void main(String[] args) throws URISyntaxException, FileNotFoundException, IOException, MyException {
		FdfsClientConfig config=new FdfsClientConfig();
		config.setConfClassPath("fdfs_client.conf");
		config.setGroupName("wifiinWS");
		config.setTrackerGroupHost("172.16.1.6");
		config.setTrackerPort(22122);
		config.init();
		FdfsClient client=null;
		try{
			client=config.getFdfsClient();
//			System.out.println(client.upload("D:\\docs\\work\\workspace\\fastdfs-client-java\\src\\fdfs_client.conf"));
			client.download("wifiinWS/M00/00/03/rBABBlUBXzuIVepiAAAA6A2H7OoAAABQAAAFdMAAAEA67.conf","D:\\docs\\work\\workspace\\fastdfs-client-java\\src\\fdfs_client2.conf");
		}finally{
			client.close();
		}
	}
}
