<template>
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
    <section>
      <a class="author" href="userprofile" @click.prevent="userprofile(p.userId)">
        {{ this.username }}
      </a>

      <p class="description">
        {{ this.description }}
      </p>
      <!--<router-link v-bind:to="{ name: 'postdetails', params: {postId: p.id} }">
        <img :src="p.picture" alt="none"  @click="this.$store.state.currentPostId = p.id;"/>
      </router-link>-->
      <img :src="this.pictureLink" alt="none"/>

      <p>
        <button class="btn btn-like">
          Like <i class="fa-regular fa-thumbs-up"></i>
        </button>
        <button class="btn btn-unlike">
          Unlike <i class="fa-regular fa-thumbs-down"></i>
        </button>
      <p id="likes">{{ likes }} </p>

      <p v-for="comment in comments" v-bind:key="comment.comment_id" class="comments">
        {{ comment.comment }} - {{ comment.author_id }}
      </p>


      <p class="addCom">Add Comment</p>

      <input
          id="name"
          v-model="newComment"
          class="addComment"
          placeholder="add your comment here"
          type="addComment"
      />
      <button
          id="commentb"
          class="badge bg-info"
          type="submit"
          v-on:click.prevent="addComment(p.id)"
      >
        Submit
      </button>
    </section>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "Post",
  props: ['post_id', 'username', 'pictureLink', 'description'],
  data() {
    return {
      likes: 0,
    }
  },
  methods: {
    async getLikes(id) {
      return await axios.get(`http://localhost:9000/post/likes?postId=` + id)

    },
    getComments() {

    }

  },
  async created() {
    this.likes = (await this.getLikes(this.post_id)).data
  }

}

</script>

<style scoped>

</style>