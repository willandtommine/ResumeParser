# ResumeParser

How to build:

1. clone the repo
2. cd into folder
3. Build with mvn package shade:shade


Uploading to amazon:
1. Create a new Lambda Function
2. Add an S3 event for all object creations to the function
3. Create a new S3 bucket (for testing adding all public permissions is helpful) making sure the region is the same for the function
and the bucket.

Here are my S3 permissions:
{
    "Version": "2012-10-17",
    "Id": "Policy1234567890123",
    "Statement": [
        {
            "Sid": "Statement1",
            "Effect": "Allow",
            "Principal": "*",
            "Action": "s3:GetObject",
            "Resource": "arn:aws:s3:::eastbuckettestwilliam/*"
        },
        {
            "Sid": "Statement1",
            "Effect": "Allow",
            "Principal": "*",
            "Action": "s3:PutObject",
            "Resource": "arn:aws:s3:::eastbuckettestwilliam/*"
        }
    ]
}

4. In the lambda function basic settings increate the memory to 1024 mb and copy:
code4goal.antony.resumeparser.Handler::handleRequest
into the handler section. 
5. Create a new custom role (click use existing role and then click the link to view the role) and add AmazonS3 Full Access policy to this role.

How to test:
1. Upload .jar file generated in the target folder to the function through S3
2. Upload a file to parsed into the S3 bucket associated with the function.

