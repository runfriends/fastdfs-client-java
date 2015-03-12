package org.csource.pool;

import org.apache.commons.pool2.BaseKeyedPooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.csource.client.FdfsClient;
import org.csource.client.FdfsClientConfig;

public class FdfsClientFactory extends BaseKeyedPooledObjectFactory<FdfsClientConfig,FdfsClient>{

	@Override
	public void destroyObject(FdfsClientConfig config,PooledObject<FdfsClient> object) throws Exception {
		super.destroyObject(config, object);
		object.getObject().close();
	}

	@Override
	public FdfsClient create(FdfsClientConfig config) throws Exception {
		return config.getFdfsClient();
	}

	@Override
	public PooledObject<FdfsClient> wrap(FdfsClient object) {
		return new DefaultPooledObject<FdfsClient>(object);
	}

}
