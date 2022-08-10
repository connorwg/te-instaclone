<template>
  <div class="home">
    <h1>
      <Header></Header>
    </h1>

    <div
      style="
        position: relative;
        display: flex;
        flex-direction: column;
        padding-bottom: 150px;
        padding-top: 80px;
        border-color: black;
        margin: 10px;
      "
    >
      <section class="post" v-for="p in $store.state.images" v-bind:key="p.id">
        <author>
          {{ p.userId }}
        </author>

        <description>
          {{ p.description }}
        </description>

        <img :src="p.picture" alt="none" />

        <p button>
          <button class="btn btn-like" v-on:click="likeThis()">Like</button>
          {{ p.likes }} Likes
        </p>

        <p class="comments">
          {{ p.comments[0] }}
        </p>
        <p class="comments">
          {{ p.comments[1] }}
        </p>

        <p class="addCom">Add Comment</p>

        <input
          type="addComment"
          id="name"
          class="addComment"
          placeholder="add your comment here"
          v-model="newComment"
          v-on:submit="addComment(p.id)"
        />
        <button class="btn btn-submit" type="submit">Submit</button>
      </section>
    </div>
  </div>
</template>

<script>
import Header from "./Header.vue";

import photoService from "../services/PhotoService.js";

export default {
  name: "home",
  components: {
    Header,
  },
  data() {
    return {
      images: [],
      newComment: ''
    };
  },
  methods: {
    addComment(id) {
      if(this.newComment !== ''){
        let commentObj = {
          post_id: id,
          author_id: this.$store.state.user,
          comment: this.newComment
        };
        photoService.addComment(commentObj).then(response => {
          if(response.status === 201){
            commentObj = response.data;
            this.$store.commit("ADD_COMMENT", commentObj);
            this.$router.push({name: 'home'});
          }
        });
      }
    },
    likeThis() {

      this.$store.images.likes++;// instead of this, write a mutation to update likes in $store.state. this mutation
                                // should be called when we get 200/201 response from the REST Service.
      photoService.addLike(this.$store.images.id);
    },
  },
  
  created() {
    photoService.getPhotos().then(response => {
      this.$store.commit("SET_PHOTOS", response.data);
      
    });
  }
  
};
</script>

<style scoped>
.home {
  display: flex;
  flex-direction: column;
  align-items: center;
  background: rgb(255, 255, 255);

  color: rgb(38, 38, 38);
  font-size: 14px;
  line-height: 18px;
  font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Oxygen,
    Ubuntu, Cantarell, "Open Sans", "Helvetica Neue", sans-serif;
}

h1 {
  position: fixed;
  margin-top: 0;
  top: 0;
  z-index: 200;
  background-color: rgb(230, 230, 230);
  width: 100%;
  justify-content: space-around;
}

section {
  display: grid;
  border-radius: 10px;
  background-color: white;
  color: black;
  border: 1px solid lightgray;
  margin-bottom: 20px;
  padding: 20px;
  font-size: 0.8rem;
  text-align: left;
}
author {
  font-size: 1rem;
  background-color: lightgray;
}
.addCom {
  color: purple;
}
.addComment {
  border: 1px solid lightgray;
  color: purple;
}
</style>