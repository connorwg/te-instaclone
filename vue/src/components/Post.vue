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