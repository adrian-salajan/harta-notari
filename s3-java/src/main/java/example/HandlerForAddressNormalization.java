package example;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.event.S3EventNotification.S3EventNotificationRecord;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

public class HandlerForAddressNormalization implements RequestHandler<S3Event, String> {

  private static final Logger logger = LoggerFactory.getLogger(HandlerForAddressNormalization.class);
  private static final String FILE_NAME = "notari-raw.csv";

  @Override
  public String handleRequest(S3Event s3event, Context context) {
    try {
      logger.info("EVENT: from a bucket");
      S3EventNotificationRecord record = s3event.getRecords().get(0);
      String srcBucket = record.getS3().getBucket().getName();
      logger.info("EVENT: from bucket:" + srcBucket);

//      // Object key may have spaces or unicode non-ASCII characters.
      String srcKey = record.getS3().getObject().getUrlDecodedKey();
      if (srcKey.endsWith(FILE_NAME)) {
        AmazonS3 s3Client = AmazonS3ClientBuilder.defaultClient();
        S3Object s3Object = s3Client.getObject(new GetObjectRequest(
                srcBucket, srcKey));
        InputStream objectData = s3Object.getObjectContent();

        new NotariAddressNormalizer().process(objectData);


        logger.info("EVENT: Finished <<<<");
      } else {
        logger.info("EVENT: Skipping for " + srcKey);
      }
      return "Ok";
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}