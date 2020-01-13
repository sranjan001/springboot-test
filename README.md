Push to cloud foundry (no Manifest)
----------------------

cf login -a <api_end_point>

cf push springboot-test --random-route -p target/springboot-test-0.0.1-SNAPSHOT.jar

cf create-service cleardb spark mysql1

cf bind-service springboot-test mysql

cf logs springboot-test

cf logs springboot-test --recent

cf restage springboot-test


cf scale cf-demo -i 2

cf scale cf-demo -m 1G

**Decrease the disk limit for each app instance**

cf scale cf-demo -k 512M
