package example;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.event.S3EventNotification.S3EventNotificationRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// RawCsvHandler value: example.RawCsvHandler
public class RawCsvHandler implements RequestHandler<S3Event, String> {

  private static final Logger logger = LoggerFactory.getLogger(RawCsvHandler.class);


  @Override
  public String handleRequest(S3Event s3event, Context context) {
    try {
      logger.info("EVENT: from a bucket");
      S3EventNotificationRecord record = s3event.getRecords().get(0);
      String srcBucket = record.getS3().getBucket().getName();
      logger.info("EVENT: from bucket:" + srcBucket);

//      // Object key may have spaces or unicode non-ASCII characters.
//      String srcKey = record.getS3().getObject().getUrlDecodedKey();
//
//      String dstBucket = srcBucket;
//      String dstKey = "resized-" + srcKey;
//
//      // Infer the image type.
//      Matcher matcher = Pattern.compile(".*\\.([^\\.]*)").matcher(srcKey);
//
//
//      // Download the image from S3 into a stream
//      AmazonS3 s3Client = AmazonS3ClientBuilder.defaultClient();
//      S3Object s3Object = s3Client.getObject(new GetObjectRequest(
//              srcBucket, srcKey));
//      InputStream objectData = s3Object.getObjectContent();

      // Read the source image
//      BufferedImage srcImage = ImageIO.read(objectData);

      // Re-encode image to target format
//      ByteArrayOutputStream os = new ByteArrayOutputStream();
//      ImageIO.write(resizedImage, imageType, os);
//      InputStream is = new ByteArrayInputStream(os.toByteArray());
      // Set Content-Length and Content-Type
//      ObjectMetadata meta = new ObjectMetadata();
//      meta.setContentLength(os.size());
      // Uploading to S3 destination bucket
//      logger.info("Writing to: " + dstBucket + "/" + dstKey);
//      try {
//        s3Client.putObject(dstBucket, dstKey, is, meta);
//      }
//      catch(AmazonServiceException e)
//      {
//        logger.error(e.getErrorMessage());
//        System.exit(1);
//      }
//      logger.info("Successfully resized " + srcBucket + "/"
//              + srcKey + " and uploaded to " + dstBucket + "/" + dstKey);

      logger.info("EVENT: Finished <<<<");
      return "Ok";
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}