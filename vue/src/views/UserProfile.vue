<template>
        <div id="user-profile">
            <div id="profile-pic">
                <img src="../../public/default_profile_picture.jpeg"/>
            </div>
            <label id="upload-label" v-if="user.id===$store.state.user.id">Upload Profile Picture</label>
            <input id="upload-dp" v-if="user.id===$store.state.user.id" ref="imagefile" type="file" accept="image/*" />
            <div id="user-detail">
                <div>User Id : {{user.id}}</div>
                <div>Username : {{user.username}}</div>
                <div>First Name : {{user.firstName}}</div>
                <div>Last Name : {{user.lastName}}</div>
                <div>email : {{user.email}}</div>
                <div>{{user.firstName}} {{user.lastName}}'s Posts:</div>
            </div>
            <home id="posts" v-bind:userId_filter="userId"></home>
        </div>
</template>

<script>
//import Header from "./Header.vue";
import photoService from "../services/PhotoService.js";
import Home from "./Home.vue";

export default {
    name: "userprofile",
    components: {
        Home   
    },
    data() {
        return {
            userId: null,
            user: {
                id: null,
                email: null,
                firstName: null,
                lastName: null,
                profileImage: null,
            }
        };
    },
    created() {
        this.userId = this.$route.params.userId;
        photoService.getUser(this.userId).then(response => {
            const data = response.data;
            this.user.id = data.id;
            this.user.email = data.email;
            this.user.firstName = data.firstName;
            this.user.lastName = data.lastName;
            this.user.profileImage = data.profileImage;
        });
    }
}
</script>

<style>
#user-profile{
    display: grid;
    grid-template-areas: "profile-pic user-detail"
                         "upload-label user-detail"
                         "upload-dp user-detail"
                         "posts posts";
}
#profile-pic {
    margin-top: 10%;
    margin-left: 15%;
    grid-area: profile-pic;
    align-self: center;
}
#user-detail {
    grid-area: user-detail;
    align-self: center;
}
#posts {
    grid-area: posts;
}
#upload-dp{
    grid-area: upload-dp;
    margin-left: 25%;
    align-self: center;
}
#upload-label{
    grid-area: upload-label;
    margin-left: 30%;
    margin-bottom: 10px;
    align-self: center;
}
</style>