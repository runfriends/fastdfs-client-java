package org.csource.pool;

import org.apache.commons.pool2.KeyedPooledObjectFactory;
import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;
import org.csource.client.FdfsClient;
import org.csource.client.FdfsClientConfig;

public class FdfsClientPool extends GenericKeyedObjectPool<FdfsClientConfig,FdfsClient>{
	
	public FdfsClientPool(KeyedPooledObjectFactory<FdfsClientConfig,FdfsClient> factory){
		super(factory);
	}
	public FdfsClientPool(KeyedPooledObjectFactory<FdfsClientConfig,FdfsClient> factory,GenericKeyedObjectPoolConfig config){
		super(factory,config);
	}
}
