cd s3-java || exit
sbt s3-java/universal:packageBin
cd ..
cp s3-java/target/universal/s3java-0.1.0-SNAPSHOT.zip pulumi/deployables/s3-java.zip
cd pulumi || exit
pulumi up
