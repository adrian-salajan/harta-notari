import * as pulumi from "@pulumi/pulumi";
import * as aws from "@pulumi/aws";
import * as awsx from "@pulumi/awsx";
import * as fs from 'fs'
import * as mi from 'mime'
import * as paths from 'path'

// Create an AWS resource (S3 Bucket)
const webBucket = new aws.s3.Bucket("harta-notari-web");
const dataBucket = new aws.s3.Bucket("harta-notari-data");

// Export the name of the bucket
//export const bucketName = bucket.id;


function uploadDirToS3(source: string, destination: string, bucket: aws.s3.Bucket) {
    let w = fs.readdirSync(source)    
    for (var e of w) {
        let filePath = paths.join(source, e)
        let s3Path = paths.join(destination, e)
        let stats = fs.statSync(filePath)
        if (stats.isDirectory()) {
          uploadDirToS3(filePath, paths.join(destination, e), bucket)
        } else {
          let mt = (mi.getType(e) || undefined)

          let abc = new aws.s3.BucketObject(s3Path, {
            bucket:  bucket, source: new pulumi.asset.FileAsset(filePath), contentType: mt
          })
       }
    }
}

// uploadDirToS3("www", "", webBucket)

// Configure IAM so that the AWS Lambda can be run.
const normalizeAddressesLamdaRole = new aws.iam.Role("normalizeAddressesLamdaRole", {
   assumeRolePolicy: {
      Version: "2012-10-17",
      Statement: [{
         Action: "sts:AssumeRole",
         Principal: {
            Service: "lambda.amazonaws.com",
         },
         Effect: "Allow",
         Sid: "AllowLambdaRun",
      }],
   },
});
new aws.iam.RolePolicyAttachment("assignLambdaExecutionPolicy", {
   role: normalizeAddressesLamdaRole,
   policyArn: aws.iam.ManagedPolicy.AWSLambdaBasicExecutionRole,
});



const lambdaReadFromHartaNotariData = new aws.iam.Policy("allowReadFromS3HartaNotariDataBucket", {
   policy:  {
      Version: "2012-10-17",
      Statement: [{
         Action: "s3:*",
         Effect: "Allow",
         Resource: [ "arn:aws:s3:::*"],
         Sid: "AllowReadFromS3HartaNotariData",
      }],
   }
})
new aws.iam.RolePolicyAttachment("assignLambdaReadObjectPolicy", {
   role: normalizeAddressesLamdaRole,
   policyArn: lambdaReadFromHartaNotariData.arn,
});

// Next, create the Lambda function itself:
const rawCsvFunction = new aws.lambda.Function("rawCsvFunction", {
   // Upload the code for our Lambda from the directory:
   code: new pulumi.asset.AssetArchive({
      ".": new pulumi.asset.FileArchive("./deployables/s3-java.zip"),
   }),
   handler: "example.api.HandlerForAddressNormalization",
   runtime: "java11",
   role: normalizeAddressesLamdaRole.arn,
   timeout: 120,
   memorySize: 256
});

// Finally, register the Lambda to fire when a new Object arrives:
dataBucket.onObjectCreated("onObjectCreate_rawCsvFunction", rawCsvFunction);

//exports.bucketName = bucket.bucket; // create a stack export for bucket name




 