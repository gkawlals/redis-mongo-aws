package poly.persistance.mongo.comm;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.client.model.Indexes;

public abstract class AbstractMongoDBComm {
	
	private Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	private MongoTemplate mongodb;

	protected boolean createCollection(String colNm, String[] indexArr) throws Exception {
		return createCollection(colNm, "");
	}

	protected boolean createCollection(String colNm, String index) throws Exception {

		String[] indexArr = { index };
		
		return createCollection(colNm, indexArr);
	}

}
