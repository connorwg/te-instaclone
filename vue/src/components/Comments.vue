<template>
  <div>
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
    >
      Submit
    </button>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "Comments",
  props: ["post_id"],
  data() {
    return {
      comments: []
    }
  },
  methods: {
    async getComments(id) {
      return await axios.get(`http://localhost:9000/post/${id}/comments`)

    },
  },
  async created() {
    this.comments = (await this.getComments(this.post_id)).data
  }
}
</script>

<style scoped>

</style>