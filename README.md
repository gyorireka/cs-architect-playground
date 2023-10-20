First step:
Add your own azure bus namespace connection url to dapr/components/x.yml file.

# Analysis Service
Start by:

`dapr run --app-id analysisservice --app-port 6000 --dapr-http-port 3600 --dapr-grpc-port 60000 --resources-path ../dapr/components mvn spring-boot:run `

# User Management Service
Start by:

`dapr run --app-id usermanagementservice --app-port 6001 --dapr-http-port 3601 --dapr-grpc-port 60001 --resources-path ../dapr/components mvn spring-boot:run`