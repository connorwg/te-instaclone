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
        
        <p class="author">
          {{ p.userId }}
        </p>

        <p class="description">
          {{ p.description }}
        </p>
        
        <img :src="p.picture" alt="none" />

        <p button>
          <button class="btn btn-like" v-on:click="likeThis(p)" v-if="!likeVerifier(p)">Like <i class="fa-regular fa-thumbs-up"></i></button>
          <button class="btn btn-unlike" v-on:click="unLikeThis(p)" v-else>Unlike <i class="fa-regular fa-thumbs-down"></i></button>
          {{ p.likes.length }} Likes
        </p>

        <p class="comments">
          {{ p.comments[1] }}
        </p>
        <p class="comments">
          {{ p.comments[0] }}
        </p>

        <p class="addCom">Add Comment</p>

        <input
          type="addComment"
          id="name"
          class="addComment"
          placeholder="add your comment here"
          v-model="newComment"
        />
        <button class="btn btn-submit" type="submit" v-on:click.prevent="addComment(p.id)">Submit</button>
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
      image: [],

      newImage: {
        id: "",
        userId: "",
        picture: "",
        description: "",
        timeStamp: "",
        likes: [],
        comments: [],
      },
    };
  },

  methods: {
    likeVerifier(p) {
      let liked = false;
      for (let i=0; i<p.likes.length; i++) {
        if (p.likes[i] === this.$store.state.user.id) {
          liked = true;
        }
        
      }return liked;
     
    },
    /*
    addComment(comment) {
      this.$store.images.comments.unshift(comment);
      this.$router.push({ name: "home" });
    },
*/
    likeThis(p) {
      let newImage = {};
      newImage.id = p.id;
      newImage.userId = p.userId;
      newImage.picture = p.picture;
      newImage.description = p.description;
      newImage.timeStamp = p.timeStamp;
      newImage.likes = p.likes;
      newImage.comments = p.comments;
      //newImage.likes.push(p.userId); // should be the current user's id here
      newImage.likes.push(this.$store.state.user.id);
      
/*
      photoService
  
        .addLike(newImage)

        .then(() => {
          photoService.getPhotos().then((response) => {
            this.$store.commit("SET_PHOTOS", response.data);
          });
          this.$router.push({ name: "home" });
        });
        */
       this.$store.commit("SET_PHOTO_LIKES", newImage);
    },
    unLikeThis(p) {
      let newImage = {};
      newImage.id = p.id;
      newImage.userId = p.userId;
      newImage.picture = p.picture;
      newImage.description = p.description;
      newImage.timeStamp = p.timeStamp;
      newImage.likes = [];
      newImage.comments = p.comments;

      for (let i=0; i<p.likes.length; i++) {
        if (!(p.likes[i] === this.$store.state.user.id)) {
          newImage.likes.push(p.likes[i]);
        }
      }
      
/*
      photoService
  
        .addLike(newImage)

        .then(() => {
          photoService.getPhotos().then((response) => {
            this.$store.commit("SET_PHOTOS", response.data);
          });
          this.$router.push({ name: "home" });
        });
        */
       this.$store.commit("SET_PHOTO_LIKES", newImage);
    },

    addComment(id) {
      if(this.newComment.trim() !== ''){
        let commentObj = {
          postId: id,
          comment: this.newComment
        };
        photoService.addComment(commentObj).then(response => {
          if(response.status === 201){
            this.$store.commit("ADD_COMMENT", commentObj);
            this.$router.push({name: 'home'});
          }
        });
      }
    }
  },

  props: ["images"],
  computed: {
    currentLikes() {
      return Number.parseInt(this.$store.images.likes);
    },
  },

  created() {
    photoService.getPhotos().then((response) => {
      this.$store.commit("SET_PHOTOS", response.data);
    });
  },
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
.author {
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
.btn-like {
  color: green;
  
}
.btn-unlike {
  color: red;
  
}

</style>