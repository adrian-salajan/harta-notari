bucket=$(aws s3 ls | cut -d' ' -f3 | grep harta-notari)
aws s3 cp s3-java/src/test/resources/notari-raw.csv s3://"$bucket"