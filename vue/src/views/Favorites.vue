<template>
  <div>
  <Header></Header>

  <div id="user-profile">

            <post
            v-for="post in favorites"
            v-bind:key="post.id"
            :post_id = post.post_id
            :picture-link="post.pictureLink"
            :description="post.description"
            :user_id="post.user_id"></post>
        </div>
  </div>
</template>

<script>

import Post from "@/components/Post";
import photoService from "@/services/PhotoService";
import Header from "@/views/Header";

export default {
  components: {Header, Post},
  data(){
    return{
      favorites:[]
    }
  },
    created() {
      photoService.getFavorites().then((response) => {
        this.favorites = response.data;
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