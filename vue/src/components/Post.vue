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

      <p button>
        <button
            class="btn btn-like"
        >
          <!--            Like <i class="fa-regular fa-thumbs-up"></i>-->
          <!--          </button>-->
          <!--          <button class="btn btn-unlike" v-on:click="unLikeThis(p)" v-else>-->
          <!--            Unlike <i class="fa-regular fa-thumbs-down"></i>-->
        </button>
                  {{ this.likes}} Likes
        <!--        </p>-->

        <!--        <p class="comments">-->
        <!--          {{ p.comments[1] }}-->
        <!--        </p>-->

        <!--        <p class="comments">-->
        <!--          {{ p.comments[0] }}-->
        <!--        </p>-->

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
    </section>
  </div>
</template>

<script>
export default {
  name: "Post",
  props: ['post_id', 'username', 'pictureLink','description','likes', 'comments']
}
</script>

<style scoped>

</style>