import * as pulumi from "@pulumi/pulumi";
import * as aws from "@pulumi/aws";
import * as awsx from "@pulumi/awsx";
import * as fs from 'fs'
import * as mi from 'mime'
import * as paths from 'path'

// Create an AWS resource (S3 Bucket)
const bucket = new aws.s3.Bucket("harta-notari-bucket");

// Export the name of the bucket
export const bucketName = bucket.id;


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

uploadDirToS3("www", "", bucket)

// Configure IAM so that the AWS Lambda can be run.
const rawCsvHandlerRole = new aws.iam.Role("rawCsvHandlerRole", {
   assumeRolePolicy: {
      Version: "2012-10-17",
      Statement: [{
         Action: "sts:AssumeRole",
         Principal: {
            Service: "lambda.amazonaws.com",
         },
         Effect: "Allow",
         Sid: "",
      }],
   },
});
new aws.iam.RolePolicyAttachment("rawCsvHandlerFuncRoleAttach", {
   role: rawCsvHandlerRole,
   policyArn: aws.iam.ManagedPolicy.AWSLambdaBasicExecutionRole,
});

// Next, create the Lambda function itself:
const rawCsvFunction = new aws.lambda.Function("rawCsvFunction", {
   // Upload the code for our Lambda from the directory:
   code: new pulumi.asset.AssetArchive({
      ".": new pulumi.asset.FileArchive("./deployables/s3-java.zip"),
   }),
   handler: "example.HandlerForAddressNormalization",
   runtime: "java8",
   role: rawCsvHandlerRole.arn,
   timeout: 120,
   memorySize: 256
});

// Finally, register the Lambda to fire when a new Object arrives:
bucket.onObjectCreated("onObjectCreate_rawCsvFunction", rawCsvFunction);

exports.bucketName = bucket.bucket; // create a stack export for bucket name




