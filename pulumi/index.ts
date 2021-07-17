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

exports.bucketName = bucket.bucket; // create a stack export for bucket name




