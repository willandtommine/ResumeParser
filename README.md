# ResumeParser

# How to Build and Test Through AWS

1. Generate .jar file
- Run `mvn package shade:shade`

1. Create a new Lambda Function
- Select Java 11 as the runtime
- Create a new role from the following AWS policy templates
    ~ `AWSLambdaFullAccess`
    ~ `AmazonAPIGatewayInvokeFullAccess`
    ~ `AmazonAPIGatewayPushToCloudWatchLogs`
    ~ `CloudWatchFullAccess`
    ~ `AmazonAPIGatewayAdministrator`
- Edit Basic settings
- Set memory to 3008 MB
- Set Timeout to 30s
- Set the Handler to `com.flexhire.Handler::handleRequest`

1. Create a new Api
- Add a trigger to your lambda function
- select API Gateway
- Select Create an API
- Select REST API
- Add `*/*` in Binary Media Types and click add

1. Testing
- Once the API is set up you can issue this curl command `curl --location --request PUT 'API_URL' \
--header 'Content-Type: application/pdf' \
--data-binary '@RESUME_FILE_PATH'`

# JSON

Currently the program can return data into five different categories. These categories include: Basic information, summary, education, skills, and work experience. Each of these can have sub categories like "date start" or "Job title"

