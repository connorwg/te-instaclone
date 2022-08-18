<template>
  <div>

    <p v-for="comment in comments" v-bind:key="comment.comment_id" class="comments">
      @{{ comment.username }}: {{ comment.comment }}


    <p class="addCom">Add Comment</p>

    <form v-on:submit.prevent="postComment">
      <div class="form__group">
        <label>Leave a comment</label>
        <input
            v-model="newComment"
            rows="1"
            required
            cols="50"
            placeholder="type in your comment"
        />
        <button>Submit</button>
      </div>
    </form>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "Comments",
  props: ["post_id"],
  data() {
    return {
      comments: [],
      newComment: '',
    }
  },
  methods: {

    async getComments(id) {
      return await axios.get(`http://localhost:9000/post/${id}/comments`)
    },
    postComment() {
      if(this.newComment === ''){
        return;
      }
      axios.post(`http://localhost:9000/post/${this.post_id}/create`, {},{params: {comment: this.newComment}})
          .then(async () => {
            this.newComment = '';
            this.comments = (await this.getComments(this.post_id)).data
          })
    }
  },
  async created() {
    this.comments = (await this.getComments(this.post_id)).data
    },
}
</script>

<style scoped>

</style>