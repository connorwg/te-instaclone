<template>
        <div id="user-profile">
            <div id="profile-pic" v-if="item.imageUrl">
                <img :src="item.imageUrl"/>
            </div>
            <div id="profile-pic" v-else-if="user.profileImage">
                <img :src="user.profileImage"/>
            </div>
            <div id="profile-pic" v-else>
                <img src="../../public/default_profile_picture.jpeg"/>
            </div>
            <label id="upload-label" v-if="user.id===$store.state.user.id">Upload Profile Picture</label>
            <input id="upload-dp" v-if="user.id===$store.state.user.id" ref="imagefile" type="file" accept="image/*" @change="preview"/>
            <div id="dp-buttons" v-if="item.imageUrl">
                <button id="postpicture" class="badge bg-info" v-on:click.prevent="postPicture">Post</button><!--to .prevent default function of click-->
                <button id="cancelpost" class="badge bg-secondary" v-on:click.prevent="reset">Cancel</button>
                <div>
                    <label class="progress-bar bg-info progress-bar-striped" style= "width:40%" v-if="!gotResponse && postRequestSent">Uploading... Please wait...</label>
                    <label style="color: blue; font-weight: bold" v-else-if="gotResponse">Picture uploaded!</label>
                </div> 
            </div>
            <div id="user-detail">
                <div>User Id : {{user.id}}</div>
                <div>Username : {{user.username}}</div>
                <div>First Name : {{user.firstName}}</div>
                <div>Last Name : {{user.lastName}}</div>
                <div>email : {{user.email}}</div>
                <div>{{user.firstName}} {{user.lastName}}'s Posts:</div>
            </div>
            <div id="remove-post" v-if="user.id===$store.state.user.id">
                <button @click.prevent="deletePosts">Delete Selected Posts</button>
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
            },
            item:{
                //...
                image : null,
                imageUrl: null,
                caption: null
            },
            gotResponse: false,
            postRequestSent: false
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
    },
    methods: {
        preview(event) {
            const file = event.target.files[0]//to retrieve data from event
            this.item.image = file
            this.item.imageUrl = URL.createObjectURL(file)
        },
        postPicture() {
            this.postRequestSent = true;
            photoService.addDisplayPicture(this.item.image).then(response => {
                if(response.status === 200){
                    this.gotResponse = true;
                }
            }).catch(error => {
                alert(error.response.status);
                alert('Photo upload failed');
            });
        },
        deletePosts(){
            photoService.deletePosts(this.$store.state.postsToDelete).then(response => {
                if(response.status === 200){
                    this.$store.state.postsToDelete.forEach(post => {
                        this.$store.commit('REMOVE_POST_TO_DELETE', post);
                    });
                }
            });
            this.$store.commit('RESET_POSTS_TO_DELETE');
        }
    }
}
</script>

<style>
#user-profile{
    display: grid;
    grid-template-areas: "profile-pic user-detail"
                         "upload-label user-detail"
                         "upload-dp user-detail"
                         "dp-buttons user-detail"
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
#dp-buttons{
    grid-area: dp-buttons;
    margin-left: 30%;
    margin-top: 10px;
    align-self: center;
}
</style>