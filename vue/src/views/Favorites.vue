<template>
        <div id="user-profile">
            <home id="posts" v-bind:isFavorites="true"></home>
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
                if(response.status === 204){
                    this.$store.state.postsToDelete.forEach(post => {
                        this.$store.commit('REMOVE_POST_TO_DELETE', post);
                    });
                    this.$store.commit('RESET_POSTS_TO_DELETE');
                }
            });
        },
        reset(){
          this.item.image = null,
          this.item.imageUrl = null,
          this.item.caption = null,
          this.gotResponse = false,
          this.postRequestSent = false;
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