# interop-ilp-conditions-demo

This repository contains a Maven based Java project which main purpose is to demo the interop-ilp-condition.jar functionality. 

Demo project can be run by executing:

mvn -s resources/settings.xml  exec:java -Dexec.mainClass="com.pdp.interop.demo.DemoRunner"

## Description

Demo project exercises the getIlpPacket, generateCondtion and generateFulfillment and validateFulfillmentAgainstCondtion methods of the interop-ilp-condition.jar.

```java
        //Call interop-ilp-conditions jar getIlpPacket()
        IlpConditionHandlerImpl ilpConditionHandlerImpl = new IlpConditionHandlerImpl();
        String ilpPacket = ilpConditionHandlerImpl.getILPPacket(ilpAddress, amount, transaction);

        //Call interop-ilp-conditions jar generateCondition()
        String ilpCondition = ilpConditionHandlerImpl.generateCondition(ilpPacket, secret);

        //Call interop-ilp-conditions jar generateFulfillment()
        String fulfillment = ilpConditionHandlerImpl.generateFulfillment(ilpPacket, secret);

        //Call interop-ilp-conditions jar validateFulfillmentAgainstCondition
        boolean validation = ilpConditionHandlerImpl.validateFulfillmentAgainstCondition(fulfillment, ilpCondition);
```

NOTE: The actual calls to getIlpPacket(), generateCondition(), generateFulfillment() and validateFulfillmentAgainstCondition() can be found at com.pdp.interop.demo.BuildIlpPacketCondition.java as part of the interop-ilp-conditions-demo project.

## Two options to run the Demo project

In order to run the Demo project, it is required to configure Maven settings.xml as detailed below. Two options are offered. 

Maven settings.xml must be configured to obtain the interop-ilp-conditions.jar used by the interop-ilp-conditions-demo project.

### Option A. Use the Maven settings.xml provided as part of of this project

Just run the following command:

    mvn -s resources/settings.xml  exec:java -Dexec.mainClass="com.pdp.interop.demo.DemoRunner"

### Option B. Update your Maven settings.xml

Add the following entries to your your Maven settings.xml.

    .
    .
    .
    <servers>
      <server>
        <username>mb-gatesprojects</username>
        <password>ModusBox</password>
        <id>modusbox-release-local</id>
      </server>
      .
      .
      .
    </servers>
    .
    .
    .
    <repositories>
      <repository>
        <snapshots><enabled>false</enabled></snapshots>
        <id>modusbox-release-local</id>
        <name>libs-release</name>
        <url>https://modusbox.jfrog.io/modusbox/libs-release</url>
      </repository>
      .
      .
      .
    </repositories>
    .
    .
    .
    <pluginRepositories>
      <pluginRepository>
        <snapshots><enabled>false</enabled></snapshots>
        <id>modusbox-plugin-release</id>
        <name>plugins-release</name>
        <url>https://modusbox.jfrog.io/modusbox/plugins-release</url>
      </pluginRepository>
      .
      .
      .
    </pluginRepositories>
    
Then fun the following command:

    mvn exec:java -Dexec.mainClass="com.pdp.interop.demo.DemoRunner"
