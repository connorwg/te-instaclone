<template>
  <div class="home">
    <h1 id="home-header">
      <Header></Header>
    </h1>

    <div
      style="
        position: relative;
        display: flex;
        flex-direction: row;
        flex-wrap: wrap;
        padding-bottom: 10px;
        padding-top: 80px;
        padding-left: 10px;
        padding-right: 10px;
        border-color: black;
        margin: 10px;
        justify-content: space-evenly;
        background-color: cadetblue;
      "
    >
      <section class="post" v-for="p in posts" v-bind:key="p.id">
        <a class="author" href="userprofile" @click.prevent="userprofile(p.userId)">
          {{ p.username }} - new post!
        </a>

        <p class="description">
          {{ p.description }}
        </p>
        <!--<router-link v-bind:to="{ name: 'postdetails', params: {postId: p.id} }">
          <img :src="p.picture" alt="none"  @click="this.$store.state.currentPostId = p.id;"/>
        </router-link>-->
        <img :src="p.pictureLink" alt="none"  @click.prevent="postDetails(p)"/>

        <p button>
          <button
            class="btn btn-like"
          >
            Like <i class="fa-regular fa-thumbs-up"></i>
          </button>
          <button class="btn btn-unlike"  >
            Unlike <i class="fa-regular fa-thumbs-down"></i>
          </button>
<!--          {{ p.likes.length }} Likes-->
<!--        </p>-->

<!--        <p class="comments">-->
<!--          {{ p.comments[1] }}-->
<!--        </p>-->

<!--        <p class="comments">-->
<!--          {{ p.comments[0] }}-->
        </p >


        <p class="addCom">Add Comment</p>

        <input
          type="addComment"
          id="name"
          class="addComment"
          placeholder="add your comment here"
          v-model="newComment"
        />
        <button
          id="commentb"
          class="badge bg-info"
          type="submit"
          v-on:click.prevent="addComment(p.id)"
        >
          Submit
        </button>
        <div id="select-post" v-if="userId_filter && userId_filter===$store.state.user.id">
          <input type="checkbox" @change="$store.commit('ADD_POSTS_TO_DELETE', p.id)"/>
        </div>
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
      filteredImages: [],
      posts: [],
      currentPostId: -1,
    };
  },

  methods: {
    postDetails(currentImage){
      this.$store.commit("SET_CURRENT_PHOTO", currentImage);
      this.$router.push({name: "postdetails"});
    },
    userprofile(userId){
      this.$router.push({name: 'userprofile', params :{userId: userId}});
    },
    likeVerifier(p) {
      let liked = false;
      for (let i = 0; i < p.likes.length; i++) {
        if (p.likes[i] === this.$store.state.user.id) {
          liked = true;
        }
      }
      return liked;
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

      for (let i = 0; i < p.likes.length; i++) {
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
      if (this.newComment.trim() !== "") {
        let commentObj = {
          postId: id,
          comment: this.newComment,
        };
        photoService.addComment(commentObj).then((response) => {
          if (response.status === 201) {
            this.$store.commit("ADD_COMMENT", commentObj);
            this.$router.push({ name: "home" });
          }
        });
      }
    },
  },

  props: ["images", "userId_filter"],
   postId: {
      type: Number,
      default: 0
    }, 
  computed: {
    currentLikes() {
      return Number.parseInt(this.$store.images.likes);
    },
    filterPosts(){
      if(this.userId_filter!=null){
        return this.$store.state.images.filter(image => {
          return image.userId===this.userId_filter;
        });
      } else{
        return this.$store.state.images
      }
    }
  },

  created() {
    photoService.getPhotos().then((response) => {
      this.posts = response.data;
      console.log(this.posts)
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
  background-color: aliceblue;
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
  border-radius: 5px 5px 0px 0px;
  margin-top: 10px;
  margin-bottom: 0px;
  line-height: 30px;
  width: 50%;
}
.addCom {
  color: purple;
  border-radius: 5px;
  margin-top: 0px;
  margin-bottom: 0px;
}
.addComment {
  border: 1px solid lightgray;
  color: purple;
  border-radius: 5px;
}
.btn-like {
  color: green;
  border-radius: 5px;
}
.btn-unlike {
  color: red;
  border-radius: 5px;
}
.comments {
  background-color: linen;
  border-radius: 5px;
  margin-top: 0px;
  margin-bottom: 5px;
}
.description {
  background-color: lightblue;

  border-radius: 0px 5px 0px 0px;
  margin-top: 0px;
  margin-bottom: 0px;
  line-height: 40px;

}
#commentb {
  width: 20%;
  height:150%
}

#home-header {
font-family:"Billabong";
}

#select-post{
  margin-top: 20px;
}
</style>
