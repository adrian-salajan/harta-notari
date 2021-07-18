cd s3-java || exit
gradle build -i
cd ..
cp s3-java/build/distributions/s3-java.zip pulumi/deployables/s3-java.zip
cd pulumi || exit
pulumi up
