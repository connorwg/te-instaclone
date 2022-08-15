<template>
    <div>
        <img src="../../public/default_profile_picture.jpeg"/>
        <div>User Id : {{user.id}}</div>
        <div>Username : {{user.username}}</div>
        <div>First Name : {{user.firstName}}</div>
        <div>Last Name : {{user.lastName}}</div>
        <div>email : {{user.email}}</div>
        <div>{{user.firstName}} {{user.lastName}}'s Posts</div>
        <home v-bind:userId_filter="userId"></home>
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