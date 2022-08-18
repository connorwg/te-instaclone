<template>
  <div
      id="homepage"
  >
    <section>
      <a class="author" href="userprofile" @click.prevent="userprofile(this.userId)">
        {{ currentUsername }}
      </a>

      <p class="description">
        {{ this.description }}
      </p>
      <!--<router-link v-bind:to="{ name: 'postdetails', params: {postId: p.id} }">
        <img :src="p.picture" alt="none"  @click="this.$store.state.currentPostId = p.id;"/>
      </router-link>-->
      <img :src="this.pictureLink" alt="none"/>

      <p>
        <button class="btn btn-like" v-if="!userLikes" @click="likePicture(post_id)">
          Like <i class="fa-regular fa-thumbs-up"></i>
        </button>
        <button class="btn btn-unlike" v-if="userLikes" @click="likePicture(post_id)">
          Unlike <i class="fa-regular fa-thumbs-down"></i>
        </button>
      <p id="likes">{{ likes }} </p>
      <comments
          :post_id=this.post_id
      />

    </section>
  </div>
</template>

<script>
import axios from "axios";
import Comments from "./Comments";

export default {
  name: "Post",
  components: {Comments},
  props: ['post_id', 'user_id', 'pictureLink', 'description'],
  data() {
    return {
      likes: 0,
      userLikes: "",
      currentUsername: "",
    }
  },
  methods: {
    async getLikes(id) {
      return await axios.get(`http://localhost:9000/post/likes?postId=` + id)

    },
    async getUserame(id) {
      return await axios.get(`http://localhost:9000/user/` + id)
    },
    async checkIfLiked(id) {
      return await axios.get(`http://localhost:9000/post/liked?postId=` + id)
    },
    likePicture(id) {
      axios.post(`http://localhost:9000/post/like?postId=` + id)
          .then(async (res) => {
            if(res.data) {
              this.userLikes = true;
            } else {
              this.userLikes = false;
            }
            this.likes = (await this.getLikes(this.post_id)).data;
          })
    }


  },
  async created() {
    this.likes = (await this.getLikes(this.post_id)).data;
    this.userLikes = (await this.checkIfLiked(this.user_id)).data;
    this.currentUsername = (await this.getUserame(this.user_id)).data.username
  }

}

</script>

<style>
#homepage {
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
        
}
.home {
  display: flex;
  flex-direction: column;
  align-items: center;
   background-color: cadetblue;
  background: rgb(255, 255, 255);
  color: rgb(38, 38, 38);
  font-size: 14px;
  line-height: 18px;
  font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Oxygen,
    Ubuntu, Cantarell, "Open Sans", "Helvetica Neue", sans-serif;
  background-color: cadetblue;  
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
  grid-template-rows: 50px 1fr 2fr 1fr 1fr 1fr 1fr 1fr 1fr 25px 10px 25px;
  border-radius: 10px;
  background-color: white;
  color: black;
  border: 1px solid lightgray;
  margin-bottom: 20px;
  padding: 20px;
  font-size: 0.8rem;
  text-align: left;
  max-height: 800px;
  max-width: 800px;
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
  min-height: 20px;
  max-height: 50px;
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

img {
  max-height: 500px;
  max-width: 500px;
}

.badge {
  max-height: 25px;
}

#addToFavb {
  width: 50%;
}


</style>