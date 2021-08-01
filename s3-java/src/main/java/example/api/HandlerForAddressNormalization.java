package example.api;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.event.S3EventNotification.S3EventNotificationRecord;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

import example.NotariCsvNormalizedWriter;
import example.NotariCsvRawParser;
import example.model.NotariAddressNormalizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class HandlerForAddressNormalization implements RequestHandler<S3Event, String> {

  private static final Logger logger = LoggerFactory.getLogger(HandlerForAddressNormalization.class);
  private static final String FILE_NAME = "notari-raw.csv";
  private static final String FILE_NOTARI_NORMALIZED = "notari-normalized.csv";

  @Override
  public String handleRequest(S3Event s3event, Context context) {
    try {
      logger.info("EVENT: from a bucket");
      S3EventNotificationRecord record = s3event.getRecords().get(0);
      String srcBucket = record.getS3().getBucket().getName();
      logger.info("EVENT: from bucket:" + srcBucket);

      // Object key may have spaces or unicode non-ASCII characters.
      String srcKey = record.getS3().getObject().getUrlDecodedKey();
      if (srcKey.endsWith(FILE_NAME)) {
        AmazonS3 s3Client = AmazonS3ClientBuilder.defaultClient();
        S3Object s3Object = s3Client.getObject(new GetObjectRequest(
                srcBucket, srcKey));
        InputStream objectData = s3Object.getObjectContent();

        logger.info("EVENT: Normalizing notari");
        var notari = new NotariAddressNormalizer().normalized(NotariCsvRawParser.parse(objectData));

        logger.info("EVENT: Writing normalized notari");
        var notariIS = new NotariCsvNormalizedWriter().write(notari);

        logger.info("EVENT: Uploading normalized notari");
        uploadToS3(s3Client, srcBucket, s3Object.getObjectMetadata().getContentType(), notariIS);


        logger.info("EVENT: Finished <<<<");
      } else {
        logger.info("EVENT: Skipping for " + srcKey);
      }
      return "Ok";
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private void uploadToS3(AmazonS3 s3Client, String srcBucket, String contentType, byte[] notariIS) {
    var metadata =  new ObjectMetadata();
    metadata.setContentLength(notariIS.length);
    metadata.setContentType(contentType);
    var putRequest = new PutObjectRequest(
            srcBucket,
            FILE_NOTARI_NORMALIZED,
            new BufferedInputStream(new ByteArrayInputStream(notariIS)),
            metadata
    );
    s3Client.putObject(putRequest);
  }
}