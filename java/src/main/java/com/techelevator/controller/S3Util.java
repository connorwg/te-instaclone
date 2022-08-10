package com.techelevator.controller;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.io.InputStream;

public class S3Util {

    private static final String BUCKET = "finalprojectco";
    private static final String FILE = "projectimages";

    public static void uploadFile(String fileName, InputStream inputStream)
            throws AwsServiceException, SdkClientException, IOException {
        String url = "";
        Region region = Region.US_EAST_2;
        AwsBasicCredentials awsCredentials = AwsBasicCredentials.create("AKIA4IP5ZTPZVEC6KU52",
                "IjLWi91RCppT/UKGcAU51KgRcoiUjx3yWtqDdJ+R");

        S3Client client = S3Client.builder().credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                .region(region)
                .build();


        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(BUCKET)
                .key(FILE + "/" + fileName)
                .acl("public-read")
                .contentType("image/png")
                .build();

        client.putObject(request,
                RequestBody.fromInputStream(inputStream, inputStream.available()));


    }
}
