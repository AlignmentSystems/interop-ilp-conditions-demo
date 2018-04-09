package com.pdp.interop.demo;

import java.io.IOException;

public class DemoRunner {

    public static void main(String[] args){

        BuildIlpPacketAndCondition buildIlpPacketAndCondition = new BuildIlpPacketAndCondition();
        try {
            buildIlpPacketAndCondition.build();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
