bucket=$(aws s3 ls | cut -d' ' -f3 | grep harta-notari-data)
aws s3 rm s3://"$bucket"/notari-raw.csv

cd pulumi || exit
pulumi destroy
