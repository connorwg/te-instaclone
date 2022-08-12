package com.techelevator.model;

public class Follow {

    private int followerId;
    private int followeeId;

    public Follow(int followerId, int followeeId) {
        this.followerId = followerId;
        this.followeeId = followeeId;
    }

    public int getFollowerId() {
        return followerId;
    }

    public void setFollowerId(int followerId) {
        this.followerId = followerId;
    }

    public int getFolloweeId() {
        return followeeId;
    }

    public void setFolloweeId(int followeeId) {
        this.followeeId = followeeId;
    }
}
