bucket=$(aws s3 ls | cut -d' ' -f3 | grep harta-notari-data)
 aws s3 cp  s3://"$bucket"/notari-normalized.csv .